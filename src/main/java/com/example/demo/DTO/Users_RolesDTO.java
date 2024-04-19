package com.example.demo.DTO;

import lombok.Data;

@Data
public class Users_RolesDTO {
    private int id;
    private UserDTO user;
    private RoleDTO role;
}
