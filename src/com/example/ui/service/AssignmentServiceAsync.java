package com.example.ui.service;

import java.util.List;

import com.example.ui.domain.ApplicantFullDetails;
import com.example.ui.domain.Assignment;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AssignmentServiceAsync {
	void assignApplicantToInterviewer(Assignment assignment,AsyncCallback<Void> callback);
	void getAssignedApplicants(String interviewerName,AsyncCallback<List<ApplicantFullDetails>> callback);
	void getAllAssignedApplicants(AsyncCallback<List<Assignment>> callback);
	void updateStatus(String applicantName, String status,AsyncCallback<Void> callback);
	void getStatus(String applicantName,AsyncCallback<String> callback);
}
