package com.example.ui.DAO;

import com.example.ui.domain.InterviewHistory;

public interface InterviewHistoryDAO {
	void saveInterviewHistory(InterviewHistory interviewHistory);
    InterviewHistory getInterviewHistoryByApplicantName(String applicantName);
}
