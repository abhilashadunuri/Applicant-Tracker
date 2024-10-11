package com.example.ui.service;

import com.example.ui.domain.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AuthServiceAsync {
	void validate(String username,String password,AsyncCallback<User> callback);
	void signupUser(String name,String password,String email,AsyncCallback<Void> callback);
	void getCurrentUser(AsyncCallback<User> callback);
	void isApplicantExist(String username,AsyncCallback<Boolean> callback);
}
