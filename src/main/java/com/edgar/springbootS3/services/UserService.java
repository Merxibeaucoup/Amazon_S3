package com.edgar.springbootS3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgar.springbootS3.models.TestModel;
import com.edgar.springbootS3.repositories.TestModelRepository;

@Service
public class UserService {
	
	@Autowired
	private TestModelRepository testModelRepository;
	
	
	/* create user */
	public TestModel createUser(TestModel user) {
		return testModelRepository.save(user);
	}
	
	
	
	/* update user image */
	 public void updateUserProfileImage(String file, String username) {	 
	        TestModel user = testModelRepository.findByUserName(username)
	        		.orElseThrow(()-> new RuntimeException("Not found"));  
	        
	        user.setProfilePicLink(file);        
	        testModelRepository.save(user);       
	    }

}
