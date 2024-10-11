package com.example.ui.service;

import java.util.List;

import com.example.ui.domain.Interviewer;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("interview")
public interface InterviewerService extends RemoteService{
	List<Interviewer> getInterviewers();
}
