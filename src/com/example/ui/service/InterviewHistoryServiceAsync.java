package com.example.ui.service;

import com.example.ui.domain.InterviewHistory;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface InterviewHistoryServiceAsync {
	void submitInterviewScores(InterviewHistory interviewHistory,AsyncCallback<Void> callback);
    void getInterviewDetails(String applicantName,AsyncCallback<InterviewHistory> callback);
}
