package com.example.ui.service;

import java.util.List;

import com.example.ui.domain.Interviewer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface InterviewerServiceAsync {
	void getInterviewers(AsyncCallback<List<Interviewer>> callback);
}
