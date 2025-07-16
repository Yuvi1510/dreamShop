package com.yuvraj.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuvraj.main.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
