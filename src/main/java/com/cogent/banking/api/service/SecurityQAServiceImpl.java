package com.cogent.banking.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cogent.banking.api.model.SecurityQA;
import com.cogent.banking.api.model.User;
import com.cogent.banking.api.repo.SecurityQARepo;
import com.cogent.banking.api.repo.UserRepository;

@Service
public class SecurityQAServiceImpl implements SecurityQAService {
	private SecurityQARepo securityQARepo;
	private UserService userService;
	private UserRepository userRepo;
	
	public SecurityQAServiceImpl(SecurityQARepo securityQARepo, UserService userService, UserRepository userRepository) {
		this.securityQARepo = securityQARepo;
		this.userService = userService;
		this.userRepo = userRepository;
	}
	
	
	
	@Override
	public List<String> getAllQuestionsFromUser(String username) {
		User u = userRepo.findByUsername(username);
		List<SecurityQA> securityQAList = u.getSecurityQA();
		List<String> securityQList = new ArrayList<String>();
		for(SecurityQA QA: securityQAList) {
			securityQList.add(QA.getQuestion());
		}
		return securityQList;
	}

	@Override
	public boolean matchAnswerToQuestion(SecurityQA QA, int userId) {
		List<SecurityQA> QAList = getAllQAFromUser(userRepo.findById(userId).get());
		for(SecurityQA QAresult: QAList) {
			if(QAresult.getQuestion().equals(QA.getQuestion()) && QAresult.getAnswer().equals(QA.getAnswer())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public SecurityQA createSecurityQA(SecurityQA securityQA, int userId) {
		User u = userRepo.findById(userId).get();
		securityQA.setUser(u);
		u.addSecurityQA(securityQA);
		SecurityQA securityQAToAdd = securityQARepo.save(securityQA);
		userRepo.save(u);
		
		return securityQAToAdd;
	}

	@Override
	public SecurityQA getSecurityQAById(int securityQAId) {
		return securityQARepo.findById(securityQAId).get();
	}

	@Override
	public SecurityQA updateSecurityQAById(int securityQAId, User user, SecurityQA securityQA) {
		User u = userService.loadUser(user.getUsername());
		List<SecurityQA> QAList = u.getSecurityQA();
		SecurityQA QAToUpdate = new SecurityQA();
		for(SecurityQA QA: QAList) {
			if(QA.getSecurityId()==securityQAId) {
				QAToUpdate = QA;
				QAToUpdate.setAnswer(securityQA.getAnswer());
				QAToUpdate.setQuestion(securityQA.getQuestion());
			}
		}
		securityQARepo.save(QAToUpdate);
		userRepo.save(u);
		
		return QAToUpdate;
	}

	//unused until we figure out how to delete shit right
	@Override
	public void deleteSecurityQAById(int securityQAId, User user) {
//		User u = userService.loadUser(user.getUsername());
//		List<SecurityQA> QAList = u.getSecurityQA();
//		//SecurityQA QAToUpdate = new SecurityQA();
//		for(SecurityQA QA: QAList) {
//			if(QA.getSecurityId()==securityQAId) {
//				QAList.remove(QA);
//				break;
//			}
//		}
//		
		System.out.println("unused until we figure out how to delete shit right");
	}



	@Override
	public List<SecurityQA> getAllQAFromUser(User user) {
		User u = userService.loadUser(user.getUsername());
		List<SecurityQA> securityQAList = u.getSecurityQA();
		return securityQAList;
	}

}
