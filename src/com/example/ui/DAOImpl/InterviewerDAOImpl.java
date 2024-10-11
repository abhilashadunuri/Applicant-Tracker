package com.example.ui.DAOImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.ui.DAO.InterviewerDAO;
import com.example.ui.domain.ApplicantFullDetails;
import com.example.ui.domain.Interviewer;

public class InterviewerDAOImpl implements InterviewerDAO{
	private SessionFactory sessionfactory;

	public SessionFactory getSessionfactory() {
		return sessionfactory;
	}

	public void setSessionFactory(SessionFactory sessionfactory) {
		this.sessionfactory = sessionfactory;
	}


	@Override
	public List<Interviewer> getInterviewers() {
		Session session = sessionfactory.openSession();
		Transaction tx=null;
		List<Interviewer> interviewers = null;
		try{
			tx=session.beginTransaction();
			Query<Interviewer> query=session.createQuery("From Interviewer",Interviewer.class);
			interviewers = query.list();
            tx.commit();
		}catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return interviewers;
	}

}
