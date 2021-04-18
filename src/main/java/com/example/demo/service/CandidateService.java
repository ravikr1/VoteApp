package com.example.demo.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.CandidateRepository;
import com.example.demo.vote.model.Candidate;
import com.example.demo.vote.model.CandidateCount;

@Service
public class CandidateService {
	
	@Autowired
	private CandidateRepository candidateRepository;
	
	public void addCandidate(Candidate candidate) {
		String name = candidate.getName().substring(0, 1).toUpperCase() + candidate.getName().substring(1).toLowerCase();
		candidate.setName(name);
		candidateRepository.insert(candidate);
		
	}
	public void deleteCandidate() {
		
	}
	public void getCandidate() {
		
	}
	public List<Candidate> getAllResults() {
		// TODO Auto-generated method stub
		return candidateRepository.findAll();
	}
	public Long getResult(String name) {
		// TODO Auto-generated method stub
	
		name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
		System.out.println("name:"+name);
		return candidateRepository.countByName(name);
	}
	public List<CandidateCount> getTotalCount(){
		return candidateRepository.aggregate()
;	}

}
