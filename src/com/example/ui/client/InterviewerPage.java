package com.example.ui.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.example.ui.domain.ApplicantFullDetails;
import com.example.ui.domain.InterviewHistory;
import com.example.ui.domain.User;
import com.example.ui.server.InterviewHistoryServiceImpl;
import com.example.ui.service.AssignmentService;
import com.example.ui.service.AssignmentServiceAsync;
import com.example.ui.service.AuthService;
import com.example.ui.service.AuthServiceAsync;
import com.example.ui.service.InterviewHistoryService;
import com.example.ui.service.InterviewHistoryServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InterviewerPage extends Composite {
	private List<ApplicantFullDetails> cachedApplicants = new ArrayList<>();

    private final VerticalPanel contentPanel;
    private Set<Integer> evaluatedCandidates = new HashSet<>();
    private Set<Integer> displayedApplicantIds = new HashSet<>();
    private List<ApplicantFullDetails> currentApplicants = new ArrayList<>();

    
    private final AuthServiceAsync authService = GWT.create(AuthService.class);
    User user;
    public InterviewerPage() {
        final HorizontalPanel mainContainer = new HorizontalPanel();
        final VerticalPanel sidebarPanel = new VerticalPanel();
        contentPanel = new VerticalPanel();

        mainContainer.setWidth("100%");
        sidebarPanel.setWidth("300px");
        contentPanel.setWidth("60%");

        final Label adminLabel = new Label("Interviewer");
        final Button dashboardButton = new Button("Interviews");
        final Button logoutButton = new Button("Logout");
        final Button profileButton = new Button("Profile");
        profileButton.addStyleName("sidebar-button");

        VerticalPanel vp = new VerticalPanel();
        Image logoImage = new Image("images/images.png");
        logoImage.setSize("100%", "auto");

        vp.add(logoImage);
        sidebarPanel.setCellHorizontalAlignment(logoImage, HasHorizontalAlignment.ALIGN_CENTER);
        sidebarPanel.setStyleName("sidebar-panel");
        sidebarPanel.getElement().getStyle().setProperty("width", "200px");

        adminLabel.addStyleName("admin-label");
        dashboardButton.addStyleName("sidebar-button");
        logoutButton.setStyleName("logout-button");

        vp.add(adminLabel);
        sidebarPanel.add(vp);
        sidebarPanel.add(profileButton);
        sidebarPanel.setCellHorizontalAlignment(profileButton, HasHorizontalAlignment.ALIGN_CENTER);
        sidebarPanel.add(dashboardButton);
        sidebarPanel.add(logoutButton);
        sidebarPanel.setCellHorizontalAlignment(dashboardButton, HasHorizontalAlignment.ALIGN_CENTER);
        sidebarPanel.setCellHorizontalAlignment(logoutButton, HasHorizontalAlignment.ALIGN_CENTER);

        mainContainer.add(sidebarPanel);
        mainContainer.add(contentPanel);

        authService.getCurrentUser(new AsyncCallback<User>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to authenticate user: " + caught.getMessage());
            }

            @Override
            public void onSuccess(User result) {
                user = result; 
                showDashboard(); 
            }
        });

        dashboardButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                showDashboard(); 
            }
        });

        logoutButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                logout(); 
            }
        });

        profileButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                showProfile();
            }
        });

        initWidget(mainContainer);
    }
    private void showDashboard() {
        contentPanel.clear();
        contentPanel.setWidth("950px");
        contentPanel.addStyleName("content-panel");
        contentPanel.getElement().getStyle().setProperty("marginTop", "0px");

        HorizontalPanel searchPanel = new HorizontalPanel();
        searchPanel.addStyleName("search-panel");
        searchPanel.getElement().getStyle().setProperty("width", "125%");
        TextBox searchBox = new TextBox();
        searchBox.setWidth("300px");
        searchBox.setStyleName("search-box");
        searchBox.getElement().setPropertyString("placeholder", "Search...");

        searchPanel.add(searchBox);

        final FlexTable flexTable = new FlexTable();
        flexTable.addStyleName("flex-table");
        flexTable.getElement().getStyle().setProperty("width", "125%");

        
        if (flexTable.getRowCount() == 0) { 
            flexTable.setText(0, 0, "Applicant Name");
            flexTable.setText(0, 1, "Resume");
            flexTable.setText(0, 2, "Applicant Details");
            flexTable.setText(0, 3, "Status");
            flexTable.setText(0, 4, "Interview Status");

            for (int i = 0; i < 5; i++) {
                flexTable.getCellFormatter().addStyleName(0, i, "flex-table-header");
            }
            flexTable.getColumnFormatter().setWidth(0, "100px");
            flexTable.getColumnFormatter().setWidth(1, "100px");
            flexTable.getColumnFormatter().setWidth(2, "100px");
            flexTable.getColumnFormatter().setWidth(3, "100px");
            flexTable.getColumnFormatter().setWidth(4, "100px");
        }

        
        populateFlexTableWithCurrentApplicants(flexTable);

        Timer refreshTimer = new Timer() {
            @Override
            public void run() {
                fetchAssignedCandidates(flexTable); 
            }
        };
        refreshTimer.scheduleRepeating(1000);

        contentPanel.add(searchPanel);
        contentPanel.add(flexTable);
        contentPanel.setCellHorizontalAlignment(flexTable, HasHorizontalAlignment.ALIGN_LEFT);
    }
    private void populateFlexTableWithCurrentApplicants(final FlexTable flexTable) {
        for (int i = 1; i < flexTable.getRowCount(); i++) {
            flexTable.removeRow(i);
        }

        for (ApplicantFullDetails applicant : currentApplicants) {
            populateFlexTable(flexTable, applicant);
        }
    }


    private void fetchAssignedCandidates(final FlexTable flexTable) {
        AssignmentServiceAsync interviewService = GWT.create(AssignmentService.class);
        interviewService.getAssignedApplicants(user.getUsername(), new AsyncCallback<List<ApplicantFullDetails>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Error fetching assigned candidates: " + caught.getMessage());
            }

            @Override
            public void onSuccess(List<ApplicantFullDetails> result) {
                
                for (ApplicantFullDetails applicant : result) {
                    if (!displayedApplicantIds.contains(applicant.getuser_id())) {
                        
                        cachedApplicants.add(applicant); 
                        populateFlexTable(flexTable, applicant);
                    }
                }
            }
        });
    }

    private void populateFlexTable(final FlexTable flexTable, final ApplicantFullDetails applicant) {
        int row = flexTable.getRowCount(); 

        if (displayedApplicantIds.contains(applicant.getuser_id())) {
            for (int r = 0; r < flexTable.getRowCount(); r++) {
                if (flexTable.getText(r, 0).equals(applicant.getFname() + " " + applicant.getLname())) {
                   
                    return;
                }
            }
        }
        currentApplicants.add(applicant);
        flexTable.setText(row, 0, applicant.getFname() + " " + applicant.getLname());
        flexTable.setText(row, 1, applicant.getResumePath());
        Button detailsButton = new Button("Details");
        flexTable.setWidget(row, 2, detailsButton);
        detailsButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                showApplicantDetails(applicant);
            }
        });

        final Button evaluateButton = new Button("Evaluate");
        flexTable.setWidget(row, 3, evaluateButton);
        if (evaluatedCandidates.contains(applicant.getuser_id())) {
            evaluateButton.setText("Evaluated");
            evaluateButton.getElement().getStyle().setProperty("background", "green");
        } else {
            evaluateButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    updateStatus(applicant.getFname() + " " + applicant.getLname());
//                    evaluatedCandidates.add(applicant.getuser_id());  
//                    evaluateButton.setText("Evaluated");
//                    evaluateButton.getElement().getStyle().setProperty("background", "green");
                }
            });
        }

        final ListBox statusDropdown = new ListBox();
        statusDropdown.addItem("Select Status");
        statusDropdown.addItem("Selected");
        statusDropdown.addItem("Rejected");

        AssignmentServiceAsync asservice = GWT.create(AssignmentService.class);
        asservice.getStatus(applicant.getFname() + " " + applicant.getLname(), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                // Handle failure
            }

            @Override
            public void onSuccess(String result) {
                String interviewStatus = result;
                if (interviewStatus != null && !interviewStatus.isEmpty()) {
                    for (int i = 0; i < statusDropdown.getItemCount(); i++) {
                        if (statusDropdown.getItemText(i).equals(interviewStatus)) {
                            statusDropdown.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        });

        statusDropdown.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent event) {
                String selectedStatus = statusDropdown.getSelectedItemText();
                AssignmentServiceAsync asservice = GWT.create(AssignmentService.class);
                asservice.updateStatus(applicant.getFname() + " " + applicant.getLname(), selectedStatus,
                        new AsyncCallback<Void>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                // Handle failure
                            }

                            @Override
                            public void onSuccess(Void result) {
                            
                            }
                        });
            }
        });

        flexTable.setWidget(row, 4, statusDropdown);
        displayedApplicantIds.add(applicant.getuser_id());
    }

private void showApplicantDetails(ApplicantFullDetails applicant) { 
    contentPanel.clear();
    contentPanel.setWidth("600px");

    FlexTable detailsTable = new FlexTable();
    detailsTable.addStyleName("details-table");
    
    FlexTable detailsTable2 = new FlexTable();
    detailsTable2.addStyleName("details-table");

    detailsTable.setText(0, 0, "Field");
    detailsTable.setText(0, 1, "Value");

    for (int i = 0; i < 2; i++) {
        detailsTable.getCellFormatter().addStyleName(0, i, "details-table-header");
    }

    detailsTable.setText(1, 0, "Full Name");
    detailsTable.setText(1, 1, applicant.getFname() + " " + applicant.getLname());

    detailsTable.setText(2, 0, "Email");
    detailsTable.setText(2, 1, applicant.getEmail());

    detailsTable.setText(3, 0, "Mobile");
    detailsTable.setText(3, 1, applicant.getMobile());

    detailsTable.setText(4, 0, "College Name");
    detailsTable.setText(4, 1, applicant.getCollegeName());

    detailsTable.setText(5, 0, "Current Education");
    detailsTable.setText(5, 1, applicant.getCurrentEducation());

    detailsTable.setText(6, 0, "Role Interested");
    detailsTable.setText(6, 1, applicant.getRoleInterested());

    detailsTable.setText(7, 0, "Major Skills");
    detailsTable.setText(7, 1, applicant.getSkills());

    detailsTable.setText(8, 0, "Current Address");
    detailsTable.setText(8, 1, applicant.getAddress());
    
    detailsTable.setText(9, 0, "Expected Package");
    detailsTable.setText(9, 1, String.valueOf(applicant.getExpectedPackage()));

    detailsTable.setText(10, 0, "Tech Stack");
    detailsTable.setText(10, 1, applicant.getTechStack());

    detailsTable.setText(11, 0, "Github Profile");
    detailsTable.setText(11, 1, applicant.getGithubProfile());

    detailsTable.setText(12, 0, "LinkedIn profile");
    detailsTable.setText(12, 1, applicant.getLinkedinProfile());
    
    detailsTable.setText(13, 0, "Experience");
    detailsTable.setText(13, 1, applicant.getExperience());

    detailsTable2.setText(14, 0, "Notice Period");
    detailsTable2.setText(14, 1, applicant.getNoticePeriod());

    Button backButton = new Button("Back");
    backButton.addStyleName("back-button");
    backButton.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
            showDashboard();
        }
    });
   
    contentPanel.add(detailsTable);
    contentPanel.add(backButton);

    contentPanel.setCellHorizontalAlignment(detailsTable, HasHorizontalAlignment.ALIGN_LEFT);
    contentPanel.setCellHorizontalAlignment(backButton, HasHorizontalAlignment.ALIGN_RIGHT);

    detailsTable.getElement().getStyle().setProperty("marginLeft", "20px");  
    backButton.getElement().getStyle().setProperty("marginLeft", "20px");  
}
    private void showProfile() {
        contentPanel.clear();
        contentPanel.setWidth("500px");
        Label label = new Label("Account Information:");
        label.getElement().getStyle().setProperty("marginTop","50px");
        label.getElement().getStyle().setProperty("fontWeight", "bold");
        label.getElement().getStyle().setProperty("fontSize", "large");
        contentPanel.add(label);
//        contentPanel.addStyleName("content-panel");

        FlexTable profileTable = new FlexTable();
        profileTable.addStyleName("profile-table");

        profileTable.setText(0, 0, "Field");
        profileTable.setText(0, 1, "Value");

        profileTable.getCellFormatter().addStyleName(0, 0, "profile-table-header");
        profileTable.getCellFormatter().addStyleName(0, 1, "profile-table-header");

        String interviewerName = "SaiKiran"; 
        String interviewerEmail = "saikiran@gmail.com"; 
        String interviewerPassword = "********"; 

        profileTable.setText(1, 0, "Name");
        profileTable.setText(1, 1, interviewerName);

        profileTable.setText(2, 0, "Email");
        profileTable.setText(2, 1, interviewerEmail);

        profileTable.setText(3, 0, "Password");
        profileTable.setText(3, 1, interviewerPassword);
        
        profileTable.getColumnFormatter().setWidth(0, "150px");
        profileTable.getColumnFormatter().setWidth(1, "150px");
        
        Button resetPasswordButton = new Button("Reset Password");
        resetPasswordButton.addStyleName("reset-password-button");

       
        resetPasswordButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                showResetPasswordDialog(); 
            }
        });

      
        profileTable.setText(4, 0, "Change password");
        profileTable.setWidget(4, 1, resetPasswordButton);

 
        Button backButton = new Button("Back");
        backButton.setStyleName("back-button-interviewer");
        backButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                showDashboard();
            }
        });

        contentPanel.add(profileTable);
        contentPanel.add(backButton);

        contentPanel.setCellHorizontalAlignment(profileTable, HasHorizontalAlignment.ALIGN_LEFT);
        contentPanel.setCellHorizontalAlignment(backButton, HasHorizontalAlignment.ALIGN_LEFT);

//        profileTable.getElement().getStyle().setProperty("marginLeft", "20px");
        backButton.getElement().getStyle().setProperty("marginLeft", "20px");
    }
    

	public void updateStatus(final String candidateName) {
        contentPanel.clear();
        contentPanel.setStyleName("content-panel-interviewer");

       
        contentPanel.setWidth("1020px");  
       
        
        FlexTable evaluationTable = new FlexTable();
        evaluationTable.setStyleName("evaluation-table");

        evaluationTable.setText(0, 0, "Candidate Name:");
        evaluationTable.setText(0, 1, candidateName);

        evaluationTable.getRowFormatter().setStyleName(0, "evaluation-table-header");
        evaluationTable.getElement().getStyle().setProperty("borderRadius","10px");

        final TextBox[] scoreTextBoxes = new TextBox[8];

        evaluationTable.setText(1, 0, "ObjectOriented Programming");
        evaluationTable.setText(2, 0, "Problem Solving Skills");
        evaluationTable.setText(3, 0, "Communication");
        evaluationTable.setText(4, 0, "Technical Skills");
        evaluationTable.setText(5, 0, "DataStuctures and Algorithms");
        evaluationTable.setText(6, 0, "Cultural Fit");
        evaluationTable.setText(7, 0, "Leadership quality");
        evaluationTable.setText(8, 0, "Behaviour");
        
        for (int i = 1; i <= 8; i++) {
            scoreTextBoxes[i - 1] = new TextBox();
            scoreTextBoxes[i - 1].setWidth("50px");
            scoreTextBoxes[i - 1].setStyleName("score-textbox");
            evaluationTable.setWidget(i, 1, scoreTextBoxes[i - 1]);
        }

        evaluationTable.getColumnFormatter().setWidth(0, "150px");
        evaluationTable.getColumnFormatter().setWidth(1, "150px");

        final Button backButton = new Button("Go-Back");
        backButton.addStyleName("back");

        final Button submitButton = new Button("Submit Evaluation");
        submitButton.addStyleName("submit-button");
        Label performance = new Label("Performance Overview:");
        performance.addStyleName("performanceLabel");

        final TextArea overview = new TextArea();
        overview.addStyleName("pOverview");

        HorizontalPanel ratingPanel = new HorizontalPanel();
        Label overallRatingLabel = new Label("Overall Rating:");
        overallRatingLabel.addStyleName("rating");

        final TextBox overallRatingBox = new TextBox();
        overallRatingBox.addStyleName("Rating");
        overallRatingBox.getElement().getStyle().setProperty("marginTop", "20px");
        overallRatingBox.getElement().getStyle().setProperty("marginLeft", "10px");
        overallRatingBox.getElement().getStyle().setProperty("border", "none");
        overallRatingBox.getElement().getStyle().setProperty("borderRadius", "5px");
        InterviewHistoryServiceAsync interviewHistoryService = GWT.create(InterviewHistoryService.class);
        interviewHistoryService.getInterviewDetails(candidateName, new AsyncCallback<InterviewHistory>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Failed to fetch");
			}

			@Override
			public void onSuccess(InterviewHistory result) {
				if (result != null) {
	                scoreTextBoxes[0].setText(String.valueOf(result.getOops()));
	                scoreTextBoxes[1].setText(String.valueOf(result.getPS()));
	                scoreTextBoxes[2].setText(String.valueOf(result.getCom()));
	                scoreTextBoxes[3].setText(String.valueOf(result.getTech()));
	                scoreTextBoxes[4].setText(String.valueOf(result.getDSA()));
	                scoreTextBoxes[5].setText(String.valueOf(result.getCF()));
	                scoreTextBoxes[6].setText(String.valueOf(result.getLQ()));
	                scoreTextBoxes[7].setText(String.valueOf(result.getBr()));
	                overallRatingBox.setText(String.valueOf(result.getORating()));
	                overview.setText(result.getPO());

	                for (TextBox textBox : scoreTextBoxes) {
	                    textBox.setEnabled(false);
	                }
	                overallRatingBox.setEnabled(false);
	                overview.setEnabled(false);
	            } 
				else{
					for (TextBox textBox : scoreTextBoxes) {
	                    textBox.setEnabled(true);
	                }
	                overallRatingBox.setEnabled(true);
	                overview.setEnabled(true);
				}
			}
        	});

        submitButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                boolean confirmed = Window.confirm("Are you sure you want to submit the evaluation?");
                
                if (confirmed) {
                	InterviewHistory interviewHistory = new InterviewHistory();
                	Window.alert("inside");
                    interviewHistory.setUsername("h"); 
                    interviewHistory.setName(candidateName);
                    interviewHistory.setOops(Integer.parseInt(scoreTextBoxes[0].getText()));
                    interviewHistory.setPS(Integer.parseInt(scoreTextBoxes[1].getText()));
                    interviewHistory.setCom(Integer.parseInt(scoreTextBoxes[2].getText()));
                    interviewHistory.setTech(Integer.parseInt(scoreTextBoxes[3].getText()));
                    interviewHistory.setDSA(Integer.parseInt(scoreTextBoxes[4].getText()));
                    interviewHistory.setCF(Integer.parseInt(scoreTextBoxes[5].getText()));
                    interviewHistory.setLQ(Integer.parseInt(scoreTextBoxes[6].getText()));
                    interviewHistory.setBr(Integer.parseInt(scoreTextBoxes[7].getText()));
                    interviewHistory.setORating(Integer.parseInt(overallRatingBox.getText()));
                    interviewHistory.setPO(overview.getText());
                   
                    InterviewHistoryServiceAsync interviewHistoryService = GWT.create(InterviewHistoryService.class);
                    Window.alert("got");
                    interviewHistoryService.submitInterviewScores(interviewHistory,new AsyncCallback<Void>(){

						@Override
						public void onFailure(Throwable caught) {
						}

						@Override
						public void onSuccess(Void result) {
							Window.alert("Details stored");
							for (int i = 0; i < scoreTextBoxes.length; i++) {
		                        scoreTextBoxes[i].setEnabled(false);
		                    }
		                    overallRatingBox.setEnabled(false);
		                    overview.setEnabled(false);
		                    
//		                    evaluateButton.setText("Evaluated");
//		                    evaluateButton.setStyleName("evaluated-button-green");
		                    showDashboard();
						}
                    	
                    });

                    
                }
            }
        });
        backButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event){
                showDashboard();
            }
        });

        
        
        ratingPanel.add(overallRatingLabel);
        ratingPanel.add(overallRatingBox);

        VerticalPanel performanceRatingPanel = new VerticalPanel();
        performanceRatingPanel.add(performance);
        performanceRatingPanel.add(overview);
        performanceRatingPanel.add(ratingPanel);
        performanceRatingPanel.addStyleName("performance-rating-panel"); 
        performanceRatingPanel.setWidth("100%");

        
        contentPanel.add(evaluationTable);
        contentPanel.add(performanceRatingPanel);

        
        HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.setWidth("100%");
        buttonPanel.add(backButton);
        buttonPanel.add(submitButton);
        buttonPanel.setCellHorizontalAlignment(backButton, HasHorizontalAlignment.ALIGN_LEFT);
        buttonPanel.setCellHorizontalAlignment(submitButton, HasHorizontalAlignment.ALIGN_RIGHT);
        
        
        contentPanel.add(buttonPanel);

       
//        contentPanel.getElement().getStyle().setProperty("paddingTop", "20px");
        contentPanel.setCellHorizontalAlignment(evaluationTable, HasHorizontalAlignment.ALIGN_LEFT);
        contentPanel.setCellHorizontalAlignment(backButton, HasHorizontalAlignment.ALIGN_LEFT);
        contentPanel.setCellHorizontalAlignment(submitButton, HasHorizontalAlignment.ALIGN_RIGHT);
    }

    private void showResetPasswordDialog() {
        
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Reset Password");
        dialogBox.setAnimationEnabled(true);

        VerticalPanel dialogPanel = new VerticalPanel();
        dialogPanel.setSpacing(10);

        
        final PasswordTextBox newPasswordTextBox = new PasswordTextBox();
        newPasswordTextBox.getElement().setPropertyString("placeholder", "Enter new password");

        final PasswordTextBox confirmPasswordTextBox = new PasswordTextBox();
        confirmPasswordTextBox.getElement().setPropertyString("placeholder", "Confirm new password");

        
        Button saveButton = new Button("Save");
        saveButton.setStyleName("save-button");  
        saveButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String newPassword = newPasswordTextBox.getText();
                String confirmPassword = confirmPasswordTextBox.getText();

                if (newPassword.equals(confirmPassword)) {
                  
                    Window.alert("Password reset successful!");
                    dialogBox.hide();
                } else {
                    Window.alert("Passwords do not match! Please try again.");
                }
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyleName("cancel-button"); 
        cancelButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
            }
        });

        HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.setStyleName("dialog-buttons");
        buttonPanel.setSpacing(10); 
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
       
        dialogPanel.add(new Label("New Password:"));
        dialogPanel.add(newPasswordTextBox);
        dialogPanel.add(new Label("Confirm Password:"));
        dialogPanel.add(confirmPasswordTextBox);
        dialogPanel.add(buttonPanel);  

        
        dialogBox.setWidget(dialogPanel);

        
        dialogBox.center();
        dialogBox.show();
    }



    private void logout() {
        RootPanel.get().clear();
        RootPanel.get().add(new HrLogin());
    }
}
