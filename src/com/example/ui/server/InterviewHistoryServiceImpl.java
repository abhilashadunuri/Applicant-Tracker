package com.example.ui.server;

import com.example.ui.DAOImpl.InterviewHistoryDAOImpl;
import com.example.ui.domain.InterviewHistory;
import com.example.ui.service.InterviewHistoryService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class InterviewHistoryServiceImpl extends RemoteServiceServlet implements InterviewHistoryService{
	private InterviewHistoryDAOImpl interviewHistoryDAO;
	
	@Override
	public void submitInterviewScores(InterviewHistory interviewHistory) {
		interviewHistoryDAO=(InterviewHistoryDAOImpl)ApplicationContextListener.applicationContext.getBean("interviewhistory");
		interviewHistoryDAO.saveInterviewHistory(interviewHistory);
		// TODO Auto-generated method stub
		
	}

	@Override
	public InterviewHistory getInterviewDetails(String applicantName) {
		interviewHistoryDAO=(InterviewHistoryDAOImpl)ApplicationContextListener.applicationContext.getBean("interviewhistory");
		 return interviewHistoryDAO.getInterviewHistoryByApplicantName(applicantName);
	}

}
