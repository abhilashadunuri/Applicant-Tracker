package com.example.ui.server;

import java.util.List;

import com.example.ui.DAOImpl.AssignmentDAOImpl;
import com.example.ui.domain.ApplicantFullDetails;
import com.example.ui.domain.Assignment;
import com.example.ui.service.AssignmentService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class AssignmentServiceImpl extends RemoteServiceServlet implements AssignmentService{

	private AssignmentDAOImpl assignmentDAO;
	@Override
	public void assignApplicantToInterviewer(Assignment assignment) throws Exception {
		assignmentDAO=(AssignmentDAOImpl)ApplicationContextListener.applicationContext.getBean("assignment");
		assignmentDAO.updateAssignment(assignment);
	}
	public List<ApplicantFullDetails> getAssignedApplicants(String interviewerName) {
		System.out.println("YES");
		assignmentDAO=(AssignmentDAOImpl)ApplicationContextListener.applicationContext.getBean("assignment");
		List<ApplicantFullDetails> a=assignmentDAO.getAssignedApplicants(interviewerName);
		System.out.println(a);
		return a;
	}
	
	public List<Assignment> getAllAssignedApplicants(){
		assignmentDAO=(AssignmentDAOImpl)ApplicationContextListener.applicationContext.getBean("assignment");
		return assignmentDAO.getAllAssignedApplicants();
	}
	@Override
	public void updateStatus(String applicantName, String status) {
		assignmentDAO=(AssignmentDAOImpl)ApplicationContextListener.applicationContext.getBean("assignment");
		assignmentDAO.updateStatus(applicantName,status);
	}
	public String getStatus(String applicantName) {
		assignmentDAO=(AssignmentDAOImpl)ApplicationContextListener.applicationContext.getBean("assignment");
		return assignmentDAO.getStatus(applicantName);
	}

}
