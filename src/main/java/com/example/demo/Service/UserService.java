package com.example.demo.Service;

import com.example.demo.DTO.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    public List<UserDTO> findAll();

    public UserDetails findByEmailOrSdt(String emailOrSdt);
}
