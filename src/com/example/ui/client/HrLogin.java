package com.example.ui.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

import java.util.ArrayList;
import java.util.List;

import com.example.ui.domain.Interviewer;
import com.example.ui.domain.User;
import com.example.ui.service.AuthService;
import com.example.ui.service.AuthServiceAsync;
import com.example.ui.service.InterviewerService;
import com.example.ui.service.InterviewerServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HrLogin extends Composite {
	private final AuthServiceAsync authService = GWT.create(AuthService.class);
    private final VerticalPanel vpanel = new VerticalPanel();
    private final TextBox usernamebox = new TextBox();
    private final PasswordTextBox passwordbox = new PasswordTextBox();
    private final Button submit = new Button("Login");
    private final Anchor signUp = new Anchor("Sign up");

    public HrLogin() {
        Label label = new Label("Applicant Tracker");
        vpanel.add(label);
        vpanel.setSpacing(10);
        vpanel.addStyleName("login-panel");
        label.addStyleName("label");
        usernamebox.addStyleName("login-field");
        passwordbox.addStyleName("login-field");
        submit.addStyleName("login-button");
        submit.getElement().getStyle().setProperty("backgroundColor","violet");
        signUp.addStyleName("Signup-button");

        Label username = new Label("Username");
        username.addStyleName("user");
        vpanel.add(username);
        vpanel.add(usernamebox);
        
 
        Label password = new Label("Password");
        password.addStyleName("pass");
        vpanel.add(password);
        vpanel.add(passwordbox);
        vpanel.add(submit);
        vpanel.add(signUp);

        vpanel.setCellHorizontalAlignment(submit, HasHorizontalAlignment.ALIGN_CENTER);
        vpanel.setCellHorizontalAlignment(signUp, HasHorizontalAlignment.ALIGN_CENTER);
        vpanel.setCellHorizontalAlignment(label, HasHorizontalAlignment.ALIGN_CENTER);

        submit.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                final String name=usernamebox.getText();
                String password=passwordbox.getText();
                if(name.equals("hr") && password.equals("hr")){
                	RootPanel.get().clear();
                	RootPanel.get().add(new MainPage());
                }
                else{
                authService.validate(name,password,new AsyncCallback<User>(){
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
					@Override
					public void onSuccess(User result) {
						if(result!=null){

				        InterviewerServiceAsync interviewerService = GWT.create(InterviewerService.class);
				        interviewerService.getInterviewers(new AsyncCallback<List<Interviewer>>() {
				            @Override
				            public void onFailure(Throwable caught) {
				                Window.alert("Failed to load interviewers.");
				            }

				            @Override
				            public void onSuccess(List<Interviewer> interviewerslist) {
				            	List<String> interviewers = new ArrayList<>();
				            	for(int i=0;i<interviewerslist.size();i++){
				            		interviewers.add(interviewerslist.get(i).getUsername());
				            	}
				            	if(interviewers.contains(name)){
				            		RootPanel.get().clear();
				            		RootPanel.get().add(new InterviewerPage());
				            	}
				            	else{
							authService.isApplicantExist(name, new AsyncCallback<Boolean>(){
	
								@Override
								public void onFailure(Throwable caught){
								}
	
								@Override
								public void onSuccess(Boolean result) {
									RootPanel.get().clear();
	                                if (result) {
	                                    ApplicantForm applicantForm = new ApplicantForm(name);
	                                    applicantForm.showTable();
	                                    RootPanel.get().add(applicantForm);
	                                } else {
	                                    RootPanel.get().add(new ApplicantForm(name));
	                                }
	                            }
							
						});
				      }
				   }
				            
				        });
					}
					else
						Window.alert("Invalid User");
                }
                
        });
                }
    }}); 

        signUp.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	RootPanel.get().clear();
            	RootPanel.get().add(new SignUpPage());
            }
        });

        initWidget(vpanel);
    }
}
            
