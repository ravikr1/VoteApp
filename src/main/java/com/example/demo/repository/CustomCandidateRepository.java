package com.example.demo.repository;

import java.util.HashMap;
import java.util.List;

import com.example.demo.vote.model.CandidateCount;

public interface CustomCandidateRepository {
	
	List<CandidateCount> aggregate();

}
