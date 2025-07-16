package com.yuvraj.main.exception;

import lombok.Data;

@Data
public class InvalidCredentialsException extends RuntimeException{
		
		public InvalidCredentialsException(String msg) {
			super(msg);
		}
}
