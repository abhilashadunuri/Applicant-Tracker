package com.example.ui.service;

import java.util.List;

import com.example.ui.domain.ApplicantFullDetails;
import com.example.ui.domain.Assignment;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("assignment")
public interface AssignmentService extends RemoteService{
	void assignApplicantToInterviewer(Assignment assignment) throws Exception;
	List<ApplicantFullDetails> getAssignedApplicants(String interviewerName);
	List<Assignment> getAllAssignedApplicants();
	void updateStatus(String applicantName, String status);
	String getStatus(String applicantName);
}
