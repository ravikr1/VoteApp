package com.example.demo.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import com.example.demo.vote.model.Candidate;
import com.example.demo.vote.model.CandidateCount;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class CandidateRepositoryImpl implements CustomCandidateRepository {
	
	private final MongoTemplate mongoTemplate;
	
	@Autowired
	public CandidateRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public List<CandidateCount> aggregate() {
//		GroupOperation groupOperation = getGroupOperation();
//		return mongoTemplate.aggregate(Aggregation.newAggregation(
//				groupOperation
//				),Candidate.class,CandidateCount.class).getMappedResults();
		
		Aggregation agg = newAggregation(
				//match(Criteria.where("candidateId").lt(10)),
				group("name").count().as("count"),
				project("count").and("name").previousOperation());
		
		AggregationResults<CandidateCount> groupResults = mongoTemplate.aggregate(agg, Candidate.class, CandidateCount.class);
		List<CandidateCount> result = groupResults.getMappedResults();
		return result;
		
	}
	
	private GroupOperation getGroupOperation() {
		return group("name").sum("name").as("count");
	}

}
