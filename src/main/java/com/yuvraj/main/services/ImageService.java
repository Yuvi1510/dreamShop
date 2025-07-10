package com.yuvraj.main.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.yuvraj.main.dto.ImageDto;
import com.yuvraj.main.models.Image;

public interface ImageService {
	Image getImageById(Long id);
	void deleteImageById(Long id);
	List<ImageDto> saveImage(List<MultipartFile> files, Long productId);
	ImageDto updateImage(MultipartFile file, Long imageId);
}
