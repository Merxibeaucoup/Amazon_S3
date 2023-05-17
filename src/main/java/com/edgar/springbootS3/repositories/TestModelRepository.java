package com.edgar.springbootS3.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edgar.springbootS3.models.TestModel;

@Repository
public interface TestModelRepository extends JpaRepository<TestModel, Long> {

	
	Optional<TestModel> findByUserName(String userName);
}
