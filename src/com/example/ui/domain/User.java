package com.example.ui.domain;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	 private ApplicantFullDetails applicantFullDetails;
	public ApplicantFullDetails getApplicantFullDetails() {
		return applicantFullDetails;
	}

	public void setApplicantFullDetails(ApplicantFullDetails applicantFullDetails) {
		this.applicantFullDetails = applicantFullDetails;
	}
	private int userId;
    private String username;
    private String email;
    private String password;
    public User(){
    	
    }
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
