package com.example.ui.service;

import com.example.ui.domain.InterviewHistory;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("interviewhistory")
public interface InterviewHistoryService extends RemoteService {
	void submitInterviewScores(InterviewHistory interviewHistory);
    InterviewHistory getInterviewDetails(String applicantName);
}
