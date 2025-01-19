package com.fitbalance.main.Dto;

public class ApiResponse {
	private boolean success;
	private String message;

	public ApiResponse() {
	}

	// Getters y Setters
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
