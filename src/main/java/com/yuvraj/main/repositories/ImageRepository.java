package com.yuvraj.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuvraj.main.models.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{

}
