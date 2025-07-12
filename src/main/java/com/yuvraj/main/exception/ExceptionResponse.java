package com.yuvraj.main.exception;

public class ExceptionResponse {
	private String error;

	public ExceptionResponse(String msg) {
		this.error = msg;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
 