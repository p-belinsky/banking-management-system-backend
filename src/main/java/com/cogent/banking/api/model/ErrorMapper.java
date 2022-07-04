package com.cogent.banking.api.model;

public class ErrorMapper {
	
	
	private String errorMessage;
	private int errorCode;
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public ErrorMapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ErrorMapper(String errorMessage, int errorCode) {
		super();
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}
	@Override
	public String toString() {
		return "ErrorMapper [errorMessage=" + errorMessage + ", errorCode=" + errorCode + "]";
	}
	
	

}
