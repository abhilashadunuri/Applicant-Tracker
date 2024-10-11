package com.example.ui.service;

import com.example.ui.domain.User;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("auth")
public interface AuthService extends RemoteService {
	User validate(String username,String password);
	void signupUser(String name,String password,String email);
	User getCurrentUser();
	boolean isApplicantExist(String username) throws IllegalArgumentException; 
}
