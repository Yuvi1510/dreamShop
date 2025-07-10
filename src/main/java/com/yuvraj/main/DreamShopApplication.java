package com.yuvraj.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.modelmapper.ModelMapper; // ✅ Add this import

@SpringBootApplication
public class DreamShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(DreamShopApplication.class, args);
	}
	
	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
