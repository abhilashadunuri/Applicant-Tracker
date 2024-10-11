package com.example.ui.server;

import com.example.ui.DAO.ApplicantDAO;
import com.example.ui.DAOImpl.ApplicantDAOImpl;
import com.example.ui.DAOImpl.UserDAOImpl;
import com.example.ui.domain.User;
import com.example.ui.service.AuthService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class AuthServiceImpl extends RemoteServiceServlet implements AuthService{
	private UserDAOImpl userDao;
	@Override
	public User validate(String username, String password) {
	// TODO Auto-generated method stub
		userDao=(UserDAOImpl) ApplicationContextListener.applicationContext.getBean("authDao");
		System.out.println("here");
	    User user=userDao.findByUsername(username);
	    System.out.println(user==null);
	    System.out.println(username+" "+password);
	    if(user!=null&&user.getPassword().equals(password)){
	        HttpSession session = getThreadLocalRequest().getSession();
	        session.setAttribute("currentUser", user);
	        System.out.println("user found"+user);
	        return user;
	    }
	    return null;
	}
	public User getUserById(Long id) {
	    // TODO Auto-generated method stub
	    userDao=(UserDAOImpl) ApplicationContextListener.applicationContext.getBean("authDao");
	    User user=userDao.getUserById(id);
	    return user;
	}
	public User getCurrentUser() {
	    // TODO Auto-generated method stub
	    HttpSession session = getThreadLocalRequest().getSession();
	    if (session != null) {
	        return (User) session.getAttribute("currentUser");
	    }
	    return null;
	}
	public void signupUser(String username, String password,String email) {
	    // TODO Auto-generated method stub
	    userDao=(UserDAOImpl) ApplicationContextListener.applicationContext.getBean("authDao");
	    User user = new User();
	    
	    user.setUsername(username);
	    user.setPassword(password);
	    user.setEmail(email);
	    System.out.println("NO");
	    userDao.saveUser(user);
	    System.out.println("Yes");
	}
	public boolean isApplicantExist(String name){
		ApplicantDAOImpl applicantDAO = (ApplicantDAOImpl)ApplicationContextListener.applicationContext.getBean("applicant"); 
	    return applicantDAO.isApplicantExist(name);
	}
	
}
