package com.example.ui.DAOImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.example.ui.DAO.InterviewHistoryDAO;
import com.example.ui.domain.InterviewHistory;

public class InterviewHistoryDAOImpl implements InterviewHistoryDAO{
	private SessionFactory sessionfactory;

	public SessionFactory getSessionfactory() {
		return sessionfactory;
	}

	public void setSessionFactory(SessionFactory sessionfactory) {
		this.sessionfactory = sessionfactory;
	}

	@Override
	public void saveInterviewHistory(InterviewHistory interviewHistory) {
		Session session = sessionfactory.openSession();
		Transaction transaction=null;
		try {
            transaction = session.beginTransaction();
            session.save(interviewHistory);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
		// TODO Auto-generated method stub

	@Override
	public InterviewHistory getInterviewHistoryByApplicantName(String applicantName) {
		Session session=sessionfactory.openSession();
		Transaction transaction= null;
		InterviewHistory interviewHistory = null;
        try {
        	transaction=session.beginTransaction();
            interviewHistory = (InterviewHistory) session.createQuery("FROM InterviewHistory WHERE name = :name")
                                .setParameter("name", applicantName)
                                .uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return interviewHistory;
	}

}
