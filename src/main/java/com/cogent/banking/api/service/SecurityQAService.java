package com.cogent.banking.api.service;

import java.util.List;

import com.cogent.banking.api.model.SecurityQA;
import com.cogent.banking.api.model.User;

public interface SecurityQAService {
	public List<SecurityQA> getAllQAFromUser(User user);
	public List<String> getAllQuestionsFromUser(String username);
	public boolean matchAnswerToQuestion(SecurityQA QA, int userId);
	public SecurityQA createSecurityQA(SecurityQA securityQA, int userId);
	public SecurityQA getSecurityQAById(int securityQAId);
	public SecurityQA updateSecurityQAById(int securityQAId, User user, SecurityQA securityQA);
	public void deleteSecurityQAById(int securityQAId, User user);
}
