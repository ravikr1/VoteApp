package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.vote.model.Candidate;

public interface CandidateRepository extends MongoRepository<Candidate, String>,CustomCandidateRepository {
	Long countByName(String name);

}
