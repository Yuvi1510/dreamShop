package com.yuvraj.main.serviceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yuvraj.main.dto.ImageDto;
import com.yuvraj.main.exception.ResourceNotFoundException;
import com.yuvraj.main.models.Image;
import com.yuvraj.main.models.Product;
import com.yuvraj.main.repositories.ImageRepository;
import com.yuvraj.main.repositories.ProductRepository;
import com.yuvraj.main.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
	@Autowired
	ImageRepository imageRepo;

	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public Image getImageById(Long id) {
		return this.imageRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Image","id",id));
	}

	@Override
	public void deleteImageById(Long id) {
		this.imageRepo.findById(id).ifPresentOrElse(this.imageRepo::delete, ()->{throw new ResourceNotFoundException("Image","id",id);});
		
	}

	@Override
	public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
		Product product = this.productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","id",productId));
		List<ImageDto> imageDtos = new ArrayList<>();
		
		for(MultipartFile file : files) {
			try {
				Image image = new Image();
				image.setFileName(file.getOriginalFilename());
				image.setFileType(file.getContentType());
				image.setImage(new SerialBlob(file.getBytes()));
				
				String buildDownloadUrl = "/api/v1/images/image/download";
				
				Image savedImage = this.imageRepo.save(image);
				
				savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
				
//				Image savedWithDownloadUrl = this.imageRepo.save(savedImage);
//				imageDtos.add(this.modelMapper.map(savedWithDownloadUrl, ImageDto.class));
				
				this.imageRepo.save(savedImage);
				imageDtos.add(this.modelMapper.map(savedImage, ImageDto.class));
				
			}catch(IOException | SQLException e) {
				e.printStackTrace();
			}
		}
		
		return imageDtos;
	}

	@Override
	public ImageDto updateImage(MultipartFile file, Long imageId) {
		Image image = this.imageRepo.findById(imageId).orElseThrow(()-> new ResourceNotFoundException("Image","id",imageId));
		
		try {
			image.setFileName(file.getOriginalFilename());
			image.setImage(new SerialBlob(file.getBytes()));
			image.setFileType(file.getContentType());
			Image updatedImage = this.imageRepo.save(image);
			return this.modelMapper.map(updatedImage, ImageDto.class);
		}catch(IOException | SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
