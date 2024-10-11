package com.example.ui.domain;

import java.io.Serializable;

public class Assignment implements Serializable {
	private static final long serialVersionUID = 1L;
	private int assignmentId;
	private String interviewer_name;
	private String applicant_name;
	private String status;
	
	public Assignment(){	
	}

	public Assignment(String applicantName, String selectedInterviewer) {
		// TODO Auto-generated constructor stub
	}

	public int getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(int assignmentId) {
		this.assignmentId = assignmentId;
	}

	

	public String getInterviewer_name() {
		return interviewer_name;
	}

	public void setInterviewer_name(String interviewer_name) {
		this.interviewer_name = interviewer_name;
	}

	public String getApplicant_name() {
		return applicant_name;
	}

	public void setApplicant_name(String applicant_name) {
		this.applicant_name = applicant_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
