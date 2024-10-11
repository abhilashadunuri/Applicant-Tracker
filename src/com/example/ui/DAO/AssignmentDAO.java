package com.example.ui.DAO;

import java.util.List;

import com.example.ui.domain.ApplicantFullDetails;
import com.example.ui.domain.Assignment;

public interface AssignmentDAO {
	void updateAssignment(Assignment assignment);
	List<ApplicantFullDetails> getAssignedApplicants(String interviewerId);
	List<Assignment>getAllAssignedApplicants();
	void updateStatus(String name,String status);
	String getStatus(String name);
}
