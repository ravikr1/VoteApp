package com.example.demo.vote.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Votes")
public class Candidate {
	
	@Id
	private String id;
	private String name;
	private String candidateId;
	
	public Candidate() {
		super();
	
	}
	
	public Candidate(String name, String candidateId, String id) {
		super();
		this.id = id;
		this.name = name;
		this.candidateId = candidateId;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

}
