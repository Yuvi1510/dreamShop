package com.yuvraj.main.exception;

public class ResourceNotFoundException extends RuntimeException{
	public ResourceNotFoundException(String resource, String fieldName, Long fieldValue) {
		super(resource + " not found with "+ fieldName + ": "+fieldValue );
	}
	
	public ResourceNotFoundException(String resource, String fieldName, String fieldValue) {
		super(resource + " not found with "+ fieldName + ": "+fieldValue);
	}
}
