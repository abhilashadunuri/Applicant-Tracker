package com.example.ui.service;

import java.util.List;

import com.example.ui.domain.ApplicantFullDetails;
import com.example.ui.domain.User;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ApplicantService")
public interface ApplicantService extends RemoteService{
	void saveApplicantDetails(ApplicantFullDetails applicant) throws IllegalArgumentException;
	
	ApplicantFullDetails getDetails(String name);
	List<ApplicantFullDetails> getApplicants();
}
