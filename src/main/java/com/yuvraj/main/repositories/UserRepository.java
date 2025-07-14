package com.yuvraj.main.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuvraj.main.models.User;

public interface UserRepository extends JpaRepository<User, Long>{

	boolean existsByEmail(String email);

	Optional<User> findByEmail(String username);

}
