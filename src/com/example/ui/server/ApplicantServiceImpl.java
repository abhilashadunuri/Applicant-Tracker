package com.example.ui.server;

import java.util.List;

import com.example.ui.DAOImpl.ApplicantDAOImpl;
import com.example.ui.domain.ApplicantFullDetails;
import com.example.ui.domain.User;
import com.example.ui.service.ApplicantService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ApplicantServiceImpl extends RemoteServiceServlet implements ApplicantService{

	private ApplicantDAOImpl applicantDAO;
	@Override
	public void saveApplicantDetails(ApplicantFullDetails applicant) throws IllegalArgumentException {
		applicantDAO=(ApplicantDAOImpl)ApplicationContextListener.applicationContext.getBean("applicant");
		if (applicant == null) {
            throw new IllegalArgumentException("Applicant details cannot be null");
        }
		
        applicantDAO.saveApplicant(applicant); 
		
	}
	public ApplicantFullDetails getDetails(String name){
		applicantDAO=(ApplicantDAOImpl)ApplicationContextListener.applicationContext.getBean("applicant");
		return applicantDAO.getAppDetails(name);
				
	}
	@Override
	public List<ApplicantFullDetails> getApplicants() {
		// TODO Auto-generated method stub
		applicantDAO=(ApplicantDAOImpl)ApplicationContextListener.applicationContext.getBean("applicant");
		return applicantDAO.getApplicants();
	}

}
