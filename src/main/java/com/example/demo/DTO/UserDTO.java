package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;
    private String hoTen;
    private String email;
    private String sdt;
    private String password;
    private Set<RoleDTO> roles;
    private Set<Users_RolesDTO> usersRoles;
}
