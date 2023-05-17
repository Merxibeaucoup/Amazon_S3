package com.edgar.springbootS3.services;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3Service {

	private final AmazonS3 s3;

	

	public void save(String path, String fileName, InputStream inputStream,
			Optional<Map<String, String>> optionalMetaData) {

		ObjectMetadata objectMetadata = new ObjectMetadata();
		
		optionalMetaData.ifPresent((map) -> {
			if (!map.isEmpty()) {
				map.forEach(objectMetadata::addUserMetadata);
			}
		});

		try {
			s3.putObject(path, fileName, inputStream, objectMetadata);
		} catch (AmazonServiceException e) {
			throw new IllegalStateException("Failed to store file to s3", e);
		}
	}

}
