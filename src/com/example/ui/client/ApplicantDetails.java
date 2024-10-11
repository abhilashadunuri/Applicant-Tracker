package com.example.ui.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class ApplicantDetails extends Composite {

    public ApplicantDetails(String name, String email, String mobile) {
        VerticalPanel detailsPanel = new VerticalPanel();
        
        detailsPanel.add(new Label("Applicant Details"));
        detailsPanel.add(new Label("Name: " + name));
        detailsPanel.add(new Label("Email: " + email));
        detailsPanel.add(new Label("Mobile: " + mobile));

        Button backButton = new Button("Back");
        backButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                goBackToDashboard();
            }
        });

        detailsPanel.add(backButton);

        initWidget(detailsPanel);
    }

    private void goBackToDashboard() {
        RootPanel.get().clear();
        RootPanel.get().add(new InterviewerPage());  // Go back to the interviewer dashboard
    }
}
