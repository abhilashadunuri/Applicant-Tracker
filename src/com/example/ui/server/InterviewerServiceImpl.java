package com.example.ui.server;

import java.util.List;

import com.example.ui.DAOImpl.InterviewerDAOImpl;
import com.example.ui.domain.Interviewer;
import com.example.ui.service.InterviewerService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class InterviewerServiceImpl extends RemoteServiceServlet implements InterviewerService{
	private InterviewerDAOImpl interviewerDAO;

	@Override
	public List<Interviewer> getInterviewers() {
		System.out.println("Inside getInterviewer");
		// TODO Auto-generated method stub
		interviewerDAO=(InterviewerDAOImpl)ApplicationContextListener.applicationContext.getBean("interviewer");
		System.out.println("Inside getInterviewer1");
		System.out.println(interviewerDAO.getInterviewers().toString());
		return interviewerDAO.getInterviewers();
	}

}
