package com.cogent.banking.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cogent.banking.api.model.JWTRequest;
import com.cogent.banking.api.model.JWTResponse;
import com.cogent.banking.api.model.User;
import com.cogent.banking.api.security.JwtUtil;
import com.cogent.banking.api.service.UserService;

@RestController
@CrossOrigin
public class JwtController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserService userService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@PostMapping("/token")
	public ResponseEntity<?> generateToken(@RequestBody JWTRequest jwtRequest){
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
		
		UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
		
		String token = jwtUtil.generateToken(userDetails);
		System.out.println("Jwt Token: "+ token);
		
		return ResponseEntity.ok(new JWTResponse(token));
	}
	
	@PostMapping("/loadUser")
	public ResponseEntity<User> loadUser(@RequestBody JWTRequest jwtRequest){
		User user = userService.loadUser(jwtRequest.getUsername());
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	

}
