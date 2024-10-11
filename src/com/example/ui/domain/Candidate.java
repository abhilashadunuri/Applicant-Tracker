package com.example.ui.domain;

public class Candidate {
	private int candidate_id;
	private String name;
	private String email;
	private String password;
	public Candidate(){}
	public Candidate(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password=password;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getCandidate_id() {
		return candidate_id;
	}
	public void setCandidate_id(int candidate_id) {
		this.candidate_id = candidate_id;
	}
	
}
