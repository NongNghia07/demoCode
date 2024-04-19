package com.example.demo.Service.Impl;

import com.example.demo.Config.ModelMapperConfig;
import com.example.demo.DTO.UserDTO;
import com.example.demo.Entity.User;
import com.example.demo.Entity.Users_Roles;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.Users_RolesRepository;
import com.example.demo.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Users_RolesRepository usersRolesRepository;

    @Override
    public List<UserDTO> findAll() {
        List<User> lst = userRepository.findAll();
        return ModelMapperConfig.mapList(lst, UserDTO.class);
    }

    @Override
    public UserDetails findByEmailOrSdt(String emailOrSdt) {
        var user = userRepository.findByEmailOrSdt(emailOrSdt, emailOrSdt)
                .orElseThrow();
        Set<Users_Roles> usersRoles = usersRolesRepository.findByUser_Id(user.getId());
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        usersRoles.forEach(p -> {
            authorities.add(new SimpleGrantedAuthority(p.getRole().getRoleName()));
        });
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
        return userDetails;
    }
}
