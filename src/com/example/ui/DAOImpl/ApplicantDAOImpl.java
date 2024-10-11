package com.example.ui.DAOImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.ui.DAO.ApplicantDAO;
import com.example.ui.domain.ApplicantFullDetails;

public class ApplicantDAOImpl implements ApplicantDAO{
	private SessionFactory sessionfactory;

	public SessionFactory getSessionfactory() {
		return sessionfactory;
	}

	public void setSessionFactory(SessionFactory sessionfactory) {
		this.sessionfactory = sessionfactory;
	}

	public void saveApplicant(ApplicantFullDetails applicant) {
		Session session = sessionfactory.openSession();
		Transaction transaction=null;
		try{
			transaction=session.beginTransaction();
			session.save(applicant);
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
	public ApplicantFullDetails getAppDetails(String username) {
		ApplicantFullDetails applicant=null;
	    Transaction tx=null;
	    Session session=sessionfactory.openSession();
	    try{
	        tx=session.beginTransaction();
	        Query<ApplicantFullDetails> query=session.createQuery("from ApplicantFullDetails where username=:username", ApplicantFullDetails.class);
	        query.setParameter("username",username);
	        applicant=query.uniqueResult();
	        tx.commit();
	    }
	    catch(HibernateException e){
	        if(tx!=null){
	            tx.rollback();
	        }
	        e.printStackTrace();
	    }
	    finally{
	        session.close();
	    }
	    return applicant;
	}
	
	public boolean isApplicantExist(String username) {
	    if (username == null || username.trim().isEmpty()) {
	        throw new IllegalArgumentException("Username cannot be null or empty");
	    }

	    Session session = sessionfactory.openSession(); // Use openSession() if needed
	    try {
	        session.beginTransaction();
	        Query<Long> query = session.createQuery("SELECT count(a) FROM ApplicantFullDetails a WHERE a.username = :username");
	        query.setParameter("username", username);
	        
	        Long count = query.uniqueResult();
	        System.out.println("YES");
	        session.getTransaction().commit();
	        
	        return count != null && count > 0;
	    } catch (Exception e) {
	        if (session.getTransaction() != null) {
	            session.getTransaction().rollback();
	        }
	        e.printStackTrace(); 
	        throw e; 
	    } finally {
	        if (session != null) {
	            session.close(); 
	        }
	    }
	}

	@Override
	public List<ApplicantFullDetails> getApplicants() {
		// TODO Auto-generated method stub
		Session session = sessionfactory.openSession();
		Transaction tx=null;
		List<ApplicantFullDetails> applicants = null;
		try{
			tx=session.beginTransaction();
			Query<ApplicantFullDetails> query=session.createQuery("From ApplicantFullDetails",ApplicantFullDetails.class);
			applicants = query.list();
            tx.commit();
		}catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return applicants;
	}


}
