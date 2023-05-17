package com.edgar.springbootS3.services;

import static org.apache.http.entity.ContentType.IMAGE_GIF;
import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.edgar.springbootS3.models.TestModel;
import com.edgar.springbootS3.repositories.TestModelRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {


	private final  TestModelRepository testModelRepository;
	
	@Value("${application.bucket.name}")
	private String bucketName;
	
	private final UserService userService;
    private final S3Service s3Service;
	
	 public void uploadProfileImage(String username, MultipartFile file) {
	        if (file.isEmpty())
	            throw new IllegalStateException("Cannot upload empty file");
	        if (!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType(), IMAGE_GIF.getMimeType())
	                .contains(file.getContentType()))
	            throw new IllegalStateException("File must be an image [" + file.getContentType() + "]");

	        TestModel user = testModelRepository.findByUserName(username).get();

	        Map<String, String> metaData = new HashMap<>();
	        metaData.put("Content-Type", file.getContentType());
	        metaData.put("Content-Length", String.valueOf(file.getSize()));

	        String path = String.format("%s/%s", bucketName.toString(), user.getUserName());
	        String fileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
	        try {
	            s3Service.save(path, fileName, file.getInputStream(), Optional.of(metaData));
	            userService.updateUserProfileImage(fileName, username);
	        } catch (IOException e) {
	            throw new IllegalStateException(e);
	        }
	    }
}
