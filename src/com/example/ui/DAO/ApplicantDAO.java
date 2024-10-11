package com.example.ui.DAO;

import java.util.List;

import com.example.ui.domain.ApplicantFullDetails;

public interface ApplicantDAO {
	void saveApplicant(ApplicantFullDetails applicant);
	ApplicantFullDetails getAppDetails(String name);
	boolean isApplicantExist(String name);
	List<ApplicantFullDetails> getApplicants();
	
}
