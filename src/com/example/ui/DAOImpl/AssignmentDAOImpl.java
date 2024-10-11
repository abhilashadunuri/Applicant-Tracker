package com.example.ui.DAOImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.example.ui.DAO.AssignmentDAO;
import com.example.ui.domain.ApplicantFullDetails;
import com.example.ui.domain.Assignment;

public class AssignmentDAOImpl implements AssignmentDAO{

	private SessionFactory sessionfactory;

	public SessionFactory getSessionfactory() {
		return sessionfactory;
	}

	public void setSessionFactory(SessionFactory sessionfactory) {
		this.sessionfactory = sessionfactory;
	}
	@Override
	public void updateAssignment(Assignment assignment) {
		Session session = sessionfactory.openSession();
		Transaction transaction=null;
		try{
			transaction=session.beginTransaction();
			session.save(assignment);
			transaction.commit();
		}
		catch(HibernateException e){
			if(transaction!=null)
				transaction.rollback();
		}
		finally{
			session.close();
		}
	}
	@Override
	public List<ApplicantFullDetails> getAssignedApplicants(String interviewerName) {
	    Session session = sessionfactory.openSession();
	    Transaction transaction = null;
	    List<ApplicantFullDetails> candidates = new ArrayList<>();

	    try {
	        System.out.println("Interviewer Name: " + interviewerName);
	        transaction = session.beginTransaction();
	        
	        // HQL query to fetch the assigned applicants based on interviewer name
	        String hql = "SELECT a FROM Assignment AS ass " +
	                     "JOIN ApplicantFullDetails AS a ON CONCAT(a.Fname, ' ', a.Lname) = ass.applicant_name " +
	                     "WHERE ass.interviewer_name = :interviewerName";
	        
	        Query<ApplicantFullDetails> query = session.createQuery(hql, ApplicantFullDetails.class);
	        query.setParameter("interviewerName", interviewerName);
	        
	        candidates = query.getResultList();

	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	    return candidates;
	}

	@Override
	public List<Assignment> getAllAssignedApplicants() {
		Session session = sessionfactory.openSession();
        Transaction tx = null;
        List<Assignment> assignments = null;

        try {
            tx = session.beginTransaction();
            Query<Assignment> query = session.createQuery("FROM Assignment", Assignment.class); // Adjust the HQL as per your model
            assignments = query.list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback(); 
            }
            e.printStackTrace(); 
        } finally {
            session.close();
        }

        return assignments;
	}

	@Override
	public void updateStatus(String applicantName, String status) {
        Session session = sessionfactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            // Create update query to change the status based on full applicant name in the assignment table
            Query query = session.createQuery("UPDATE Assignment SET status = :status WHERE applicant_name = :applicantName");
            query.setParameter("status", status);
            query.setParameter("applicantName", applicantName);

            // Execute update and commit transaction
            int updatedRows = query.executeUpdate();
            tx.commit();

            if (updatedRows == 0) {
                throw new Exception("No rows updated. Either applicant name does not exist or status is unchanged.");
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback(); 
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();  
            }
        }
    }

	@Override
	public String getStatus(String applicantName) {
		Session session = sessionfactory.openSession();
        Transaction tx = null;
        String status = null;

        try {
            tx = session.beginTransaction();
            Query<String> query = session.createQuery("SELECT a.status FROM Assignment a WHERE a.applicant_name = :applicantName", String.class);
            query.setParameter("applicantName", applicantName);
            status = query.uniqueResult();
            if ("In Progress".equals(status)) {
                status = null;
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return status; 
	}	
}
	

