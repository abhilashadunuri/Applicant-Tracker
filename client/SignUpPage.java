package com.example.ui.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.example.ui.service.AuthService;
import com.example.ui.service.AuthServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SignUpPage extends Composite {
	private final AuthServiceAsync authService = GWT.create(AuthService.class);
    final private VerticalPanel vpanel = new VerticalPanel();
    private TextBox username = new TextBox();
    private PasswordTextBox password = new PasswordTextBox();
    private TextBox email = new TextBox();
    private Button submit = new Button("Sign Up");

    public SignUpPage() {
        vpanel.setSpacing(10);
        vpanel.addStyleName("signup-panel");
        

        username.addStyleName("signup-field");
        password.addStyleName("signup-field");
        email.addStyleName("signup-field");
        submit.addStyleName("signup-button");
        
        Label name = new Label("Enter Details");
        name.addStyleName("label");
        vpanel.add(name);
        
        vpanel.setCellHorizontalAlignment(name, HasHorizontalAlignment.ALIGN_CENTER);
        
        Label label1 = new Label("Username");
        label1.addStyleName("user");
        vpanel.add(label1);
        vpanel.add(username);
        
        Label label2 = new Label("Password");
        label2.addStyleName("pass");
        vpanel.add(label2);
        vpanel.add(password);
        
        Label label3 = new Label("email");
        label3.addStyleName("pass");
        vpanel.add(label3);
        vpanel.add(email);
       
        
        vpanel.add(submit);
        vpanel.setCellHorizontalAlignment(submit, HasHorizontalAlignment.ALIGN_CENTER);


        submit.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	String name=username.getText();
                String pass=password.getText();
                String mail=email.getText();
                authService.signupUser(name,pass,mail,new AsyncCallback<Void>(){
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Not saved");
					}

					@Override
					public void onSuccess(Void result) {
						RootPanel.get().clear();
				        RootPanel.get().add(new HrLogin());;
					}
                	
                });
            }
        });

        initWidget(vpanel);
    }

}
