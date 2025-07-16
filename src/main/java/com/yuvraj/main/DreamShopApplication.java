package com.yuvraj.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.yuvraj.main.models.Role;
import com.yuvraj.main.models.User;
import com.yuvraj.main.repositories.RoleRepository;
import com.yuvraj.main.repositories.UserRepository;
import com.yuvraj.main.serviceImpl.CartServiceImpl;

import org.modelmapper.ModelMapper; // ✅ Add this import

@SpringBootApplication
public class DreamShopApplication implements CommandLineRunner{
	
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	UserRepository userRepo;
	

	public static void main(String[] args) {
		SpringApplication.run(DreamShopApplication.class, args);
	}
	
	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	

	@Override
	public void run(String... args) throws Exception {
		Role role1 = new Role(1L , "ROLE_ADMIN");
		this.roleRepo.save(role1);
		Role role2 = new Role(2L , "ROLE_USER");
		this.roleRepo.save(role2);
	}
	

}
