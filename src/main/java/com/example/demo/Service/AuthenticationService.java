package com.example.demo.Service;

import com.example.demo.Auth.AuthenticationRequest;
import com.example.demo.Auth.AuthenticationResponse;
import com.example.demo.Config.JwtService;
import com.example.demo.Config.ModelMapperConfig;
import com.example.demo.DTO.UserDTO;
import com.example.demo.DTO.Users_RolesDTO;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.Token;
import com.example.demo.Entity.User;
import com.example.demo.Entity.Users_Roles;
import com.example.demo.Repository.TokenRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.Users_RolesRepository;
import com.example.demo.token.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final Users_RolesRepository usersRolesRepository;
    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public AuthenticationResponse register(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Set<Users_RolesDTO> usersRolesDTOS = new HashSet<>();
        var savedUser = repository.save(modelMapper.map(userDTO, User.class));
        userDTO.getRoles().forEach(p -> {
            Users_RolesDTO usersRoles = new Users_RolesDTO();
            UserDTO userDTO1 = new UserDTO();
            userDTO1.setId(savedUser.getId());
            usersRoles.setUser(userDTO1);
            usersRoles.setRole(p);
            usersRolesDTOS.add(usersRoles);
        });
        List<Users_RolesDTO> lst = new ArrayList<>(usersRolesDTOS);
        usersRolesRepository.saveAll(ModelMapperConfig.mapList(lst, Users_Roles.class));
        var jwtToken = jwtService.generateToken(modelMapper.map(userDTO, User.class));
        var refreshToken = jwtService.generateRefreshToken(modelMapper.map(userDTO, User.class));
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                )
//        );

        UserDetails userDetails = userService.findByEmailOrSdt(request.getEmail());
        var user = repository.findByEmailOrSdt(request.getEmail(), request.getEmail()).orElseThrow();;
        var jwtToken = jwtService.generateToken(userDetails);
        var refreshToken = jwtService.generateRefreshToken(userDetails);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmailOrSdt(userEmail, userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
