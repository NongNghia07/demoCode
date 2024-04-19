package com.example.demo.Repository;

import com.example.demo.Entity.Users_Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface Users_RolesRepository extends JpaRepository<Users_Roles, Integer> {
    Set<Users_Roles> findByUser_Id(Integer userID);
}
