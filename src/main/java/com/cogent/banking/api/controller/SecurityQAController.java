package com.cogent.banking.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cogent.banking.api.model.SecurityQA;
import com.cogent.banking.api.model.User;
import com.cogent.banking.api.service.SecurityQAService;
import com.cogent.banking.api.service.UserService;

@RestController
@RequestMapping("/api/securityQA")
@CrossOrigin
public class SecurityQAController {
	private SecurityQAService securityQAService;
	private UserService userService;
	
	public SecurityQAController(SecurityQAService securityQAService, UserService userService) {
		this.userService = userService;
		this.securityQAService = securityQAService;
	}
	
	@GetMapping("/questionsList/{username}")
	public ResponseEntity<List<String>> getAllQuestionsFromUser(@PathVariable("username")String username){
		List<String> result = securityQAService.getAllQuestionsFromUser(username);
		return new ResponseEntity<List<String>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/createQA/{userId}")
	public ResponseEntity<SecurityQA> createSecurityQA(@RequestBody SecurityQA securityQA, @PathVariable("userId")int userId){
		SecurityQA result = securityQAService.createSecurityQA(securityQA, userId);
		return new ResponseEntity<SecurityQA>(result,HttpStatus.CREATED);
	}
	
	@PostMapping("/matchQA/{userId}")
	public ResponseEntity<Boolean> matchAnswerToQuestion(@RequestBody SecurityQA QA, @PathVariable("userId") int userId){
		boolean result = securityQAService.matchAnswerToQuestion(QA, userId);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
}
