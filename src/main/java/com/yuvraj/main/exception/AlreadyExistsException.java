package com.yuvraj.main.exception;

public class AlreadyExistsException extends RuntimeException {
	public AlreadyExistsException(String resource, String fieldName, Long fieldValue) {
		super(resource + " already exists with "+ fieldName + " :"+ fieldValue);
	}
	
	public AlreadyExistsException(String resource, String fieldName, String fieldValue) {
		super(resource + " already exists with "+ fieldName + " :"+ fieldValue);
	}
	
	public AlreadyExistsException(String msg) {
		super(msg);
	}
}
