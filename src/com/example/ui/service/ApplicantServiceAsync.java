package com.example.ui.service;

import java.util.List;

import com.example.ui.domain.ApplicantFullDetails;
import com.example.ui.domain.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ApplicantServiceAsync{
	void saveApplicantDetails(ApplicantFullDetails applicant,AsyncCallback<Void> callback) ;
	void getDetails(String name,AsyncCallback<ApplicantFullDetails> callback);
	void getApplicants(AsyncCallback<List<ApplicantFullDetails>> callback);
	
}