package com.edgar.springbootS3.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.edgar.springbootS3.models.TestModel;
import com.edgar.springbootS3.services.FileService;
import com.edgar.springbootS3.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	
	
	private final  FileService fileService;
	
	private final  UserService userService;
	
	@PostMapping("/new")
	public ResponseEntity<TestModel> newUser(@RequestBody TestModel model){
		return ResponseEntity.status(200).body(userService.createUser(model));
	}
	
	
	 @PostMapping(
	            value = "/{username}/image/upload",
	            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    ResponseEntity<Void> uploadUserProfileImage(@PathVariable String username,
	                                                @RequestParam("file") MultipartFile file) {
	        fileService.uploadProfileImage(username, file);
	        return ResponseEntity.ok().build();
	    }
	
	

}
