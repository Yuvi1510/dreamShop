package com.yuvraj.main.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yuvraj.main.dto.ImageDto;
import com.yuvraj.main.models.Image;
import com.yuvraj.main.response.ApiResponse;
import com.yuvraj.main.serviceImpl.ImageServiceImpl;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
	
	@Autowired
	private ImageServiceImpl imageService;
	
	@PostMapping("/upload")
	public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId){
		try {
			List<ImageDto> savedImageDtos = this.imageService.saveImage(files, productId);
			ApiResponse response = new ApiResponse("Upload success!",savedImageDtos);
			return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Upload failed!",e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 
	
	@GetMapping("/image/download/{imageId}")
	public ResponseEntity<ByteArrayResource> downloadImage(@PathVariable Long imageId) throws SQLException{
		Image image = this.imageService.getImageById(imageId);
		ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +image.getFileName() + "\"")
				.body(resource);
	}
	
	
	 
	@PutMapping("/image/{imageId}/update")
	public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestParam MultipartFile file){
		return new ResponseEntity<ApiResponse>(new ApiResponse("Image updated Successfully!",this.imageService.updateImage(file, imageId)),HttpStatus.OK);
	}
	

	 
}









