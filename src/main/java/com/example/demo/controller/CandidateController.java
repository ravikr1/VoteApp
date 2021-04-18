package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.json.simple.JSONObject;
import com.example.demo.service.CandidateService;
import com.example.demo.service.GeneratePdfReport;
import com.example.demo.vote.model.Candidate;

@RestController
public class CandidateController {
	
	@Autowired
	private CandidateService candidateService;
	
	@RequestMapping(method=RequestMethod.POST,value="/add")
	public ResponseEntity<?> addCandidate(@RequestBody Candidate candidate) {
		 if ((candidate.getCandidateId()==null) || (candidate.getCandidateId() == "")) {
		        return new ResponseEntity<>(
		          "Please provide candidate Id", 
		          HttpStatus.BAD_REQUEST);
		    }
		 else if((candidate.getName() ==null) || candidate.getName()=="") {
			   return new ResponseEntity<>(
				          "Please provide Name", 
				          HttpStatus.BAD_REQUEST);
		 }
		 else if (candidate.getCandidateId()!=null) {
			 if(candidate.getName().equals("Ram") && !candidate.getCandidateId().equals("1")) {
				 return new ResponseEntity<>(
				          "Please provide candidate id as 1 for Ram", 
				          HttpStatus.BAD_REQUEST);
			 }
			 if(candidate.getName().equals("Sham") && !candidate.getCandidateId().equals("2")) {
				 return new ResponseEntity<>(
				          "Please provide candidate id as 2 for Sham", 
				          HttpStatus.BAD_REQUEST);
			 }
		 }
		candidateService.addCandidate(candidate);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@RequestMapping("/getall")
	public ResponseEntity<List<Candidate>> getAllResults(){
		return ResponseEntity.ok(candidateService.getAllResults());
	}
	
	@RequestMapping("/get/{name}")
	public ResponseEntity<HashMap<String, Object>> getResult(@PathVariable String name){	
		HashMap<String, Object> map = new HashMap<>();
		map.put(name, candidateService.getResult(name));
		return ResponseEntity.ok(map);
	}
	
	@RequestMapping("/totalcount")
	public ResponseEntity<?> getTotalCount(){	
		HashMap<String, Object> map = new HashMap<>();
		return ResponseEntity.ok(candidateService.getTotalCount());
	}
	
	@RequestMapping("/pdfreport")
	public ResponseEntity<InputStreamResource> getPDFReport(){	
		HashMap<String, Object> map = new HashMap<>();
		
		ByteArrayInputStream bis = GeneratePdfReport.countReport(candidateService.getTotalCount());
		
		var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=votereport.pdf");
		
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bis));
	}
	
	@RequestMapping("/test")
	public String test() {
		return "Testing successful";
	}

}
