package com.cogent.banking.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cogent.banking.api.model.ErrorMapper;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = CustomerNotFoundException.class)
	public ResponseEntity<Object> handleProductNotFound(){
		ErrorMapper error = new ErrorMapper("Customer Not Found", HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = InsufficentBalanceException.class)
	public ResponseEntity<Object> handleInsufficientBalance(){
		ErrorMapper error = new ErrorMapper("Balance is Insufficient", HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = AccountNotFoundException.class)
	public ResponseEntity<Object> handleAccountNotFound(){
		ErrorMapper error = new ErrorMapper("Account Not Found", HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = AccountsNotUniqueException.class)
	public ResponseEntity<Object> handleInvalidTranser(){
		ErrorMapper error = new ErrorMapper("Invalid Transfer! Account Numbers Must Be Unique", HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = UserNameNotUniqueException.class)
	public ResponseEntity<Object> handleInvalidUserName(){
		ErrorMapper error = new ErrorMapper("Username is Taken! Please Try Another Username", HttpStatus.FORBIDDEN.value());
		return new ResponseEntity<Object>(error, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(value = StatusNotChangedException.class)
	public ResponseEntity<Object> handleStatus(){
		ErrorMapper error = new ErrorMapper("Status Not Changed", HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = BeneficiaryNotAddedException.class)
	public ResponseEntity<Object> handleBeneficiaryNotAdded(){
		ErrorMapper error = new ErrorMapper("Sorry Beneficiary Not Added, Account Not Found", HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = AccountNotCreatedException.class)
	public ResponseEntity<Object> handleAccountNotCreated(){
		ErrorMapper error = new ErrorMapper("Sorry Account Not Created, Account Number Is Taken", HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}
}
