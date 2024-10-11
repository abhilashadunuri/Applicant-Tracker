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
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlexTable;
import java.util.*;

import com.example.ui.domain.ApplicantFullDetails;
import com.example.ui.domain.Assignment;
import com.example.ui.domain.InterviewHistory;
import com.example.ui.domain.Interviewer;
import com.example.ui.service.ApplicantService;
import com.example.ui.service.ApplicantServiceAsync;
import com.example.ui.service.AssignmentService;
import com.example.ui.service.AssignmentServiceAsync;
import com.example.ui.service.EmailProcessingService;
import com.example.ui.service.EmailProcessingServiceAsync;
import com.example.ui.service.InterviewHistoryService;
import com.example.ui.service.InterviewHistoryServiceAsync;
import com.example.ui.service.InterviewerService;
import com.example.ui.service.InterviewerServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class MainPage extends Composite {
	
	private final EmailProcessingServiceAsync emailservice = GWT.create(EmailProcessingService.class);
    private final VerticalPanel contentPanel;
    private FlexTable sampleTable;
    private Button submitButton = new Button("Submit");
    String fileName="";
    Set<String> existingApplicantIds = new HashSet<>();
    final private HashMap<String, String> assignedInterviewersMap = new HashMap<>();
    public MainPage() {
        final HorizontalPanel mainContainer = new HorizontalPanel();
        final VerticalPanel sidebarPanel = new VerticalPanel();
        contentPanel = new VerticalPanel();
        contentPanel.setStyleName("content-panel");

        mainContainer.setWidth("100%");
        sidebarPanel.setWidth("100px");

        final Label adminLabel = new Label("Admin");
        final Button dashboardButton = new Button("Dashboard");
        final Button uploadDataButton = new Button("Import Data");
        final Button logoutButton = new Button("Logout");
        final Button accountButton = new Button("Profile");

        final VerticalPanel vp1 = new VerticalPanel();
        Label lb = new Label("Import Data to send emails to the candidates:");
        lb.getElement().getStyle().setProperty("fontWeight", "bold");
        lb.getElement().getStyle().setProperty("fontSize", "large");
        vp1.add(lb);

        final VerticalPanel uploadPanel = new VerticalPanel();
        final FileUpload fileUpload = new FileUpload();
        fileUpload.setStyleName("uploadFile");

        final Label dragAndDropLabel = new Label("Drag and drop a file here or click to upload");
        dragAndDropLabel.setStyleName("drag-drop-label");
        
        dragAndDropLabel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                fileUpload.getElement().<InputElement>cast().click(); 
            }
        });



        fileUpload.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent event) {
                fileName = "C:\\Users\\abhil\\OneDrive\\Desktop\\applicants.xlsx";
                if (!fileName.isEmpty()) {
                    Window.alert("File selected: " + fileName);
                    showSampleTable();
                }
            }
        });
        uploadPanel.add(dragAndDropLabel);
        uploadPanel.add(fileUpload);
        uploadPanel.setStyleName("upload-panel");

        vp1.add(uploadPanel);
        vp1.getElement().getStyle().setProperty("marginTop", "20px");

        VerticalPanel vp2 = new VerticalPanel();
        Image logoImage = new Image("images/images.png");
        logoImage.setSize("100%", "auto");
        vp2.add(logoImage);

        sidebarPanel.setStyleName("sidebar-panel");
        sidebarPanel.getElement().getStyle().setProperty("width", "200px");
        adminLabel.addStyleName("admin-label");
        accountButton.addStyleName("sidebar-button");
        dashboardButton.addStyleName("sidebar-button");
        uploadDataButton.addStyleName("sidebar-button");
        logoutButton.setStyleName("logout-button");
        
        vp2.add(adminLabel);
        sidebarPanel.add(vp2);
        sidebarPanel.add(accountButton);
        sidebarPanel.add(dashboardButton);
        sidebarPanel.add(uploadDataButton);
        sidebarPanel.add(logoutButton);
        sidebarPanel.setCellHorizontalAlignment(accountButton, HasHorizontalAlignment.ALIGN_CENTER);
        sidebarPanel.setCellHorizontalAlignment(dashboardButton, HasHorizontalAlignment.ALIGN_CENTER);
        sidebarPanel.setCellHorizontalAlignment(uploadDataButton, HasHorizontalAlignment.ALIGN_CENTER);
        sidebarPanel.setCellHorizontalAlignment(logoutButton, HasHorizontalAlignment.ALIGN_CENTER);
        
//        contentPanel.setWidth("700px");
        contentPanel.getElement().getStyle().setProperty("marginTop", "30px");
        contentPanel.add(vp1);
        contentPanel.setCellHorizontalAlignment(vp1, HasHorizontalAlignment.ALIGN_LEFT);

        mainContainer.add(sidebarPanel);
        mainContainer.add(contentPanel);
        
        accountButton.addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event){
        		showProfile();
        	}
        });

        dashboardButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                showDashboard();
            }
        });

        uploadDataButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                contentPanel.clear();
                contentPanel.add(vp1);
            }
        });
        logoutButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                logout();
            }
        });
        submitButton.addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {
                if (fileUpload.getFilename() == null || fileUpload.getFilename().isEmpty()) {
                    Window.alert("Please select a file.");
                    return;
                }
                emailservice.uploadFile(fileName,new AsyncCallback<Void>(){

					@Override
					public void onFailure(Throwable caught) {
						GWT.log(caught.toString());
						Window.alert("Did not sent emails");	
					}

					@Override
					public void onSuccess(Void result) {
						Window.alert("Emails sent");
						showDashboard();
						
					}});
            }
        });

        initWidget(mainContainer);
    }
    
    private void showSampleTable() {
        if (sampleTable != null) {
            contentPanel.remove(sampleTable);
        }

        sampleTable = new FlexTable();
        sampleTable.addStyleName("sample-table");

        sampleTable.setText(0, 0, "Name");
        sampleTable.setText(0, 1, "Email");

        
        String[][] sampleData = {
            {"Sai Charan", "charan@gmail.com",},
            {"Manojav", "manpj@gmail.com",},
            {"John Doe", "john.doe@example.com",}
        };

        for (int i = 0; i < sampleData.length; i++) {
            for (int j = 0; j < sampleData[i].length; j++) {
                sampleTable.setText(i + 1, j, sampleData[i][j]);
            }
        }

        contentPanel.add(sampleTable);
        contentPanel.setCellHorizontalAlignment(sampleTable, HasHorizontalAlignment.ALIGN_CENTER);
		contentPanel.add(submitButton);
		submitButton.setStyleName("preview");
        contentPanel.setCellHorizontalAlignment(submitButton,HasHorizontalAlignment.ALIGN_RIGHT);
        
    }

    private void showProfile() {
        contentPanel.clear();
//        contentPanel.setWidth("100%");
        Label label = new Label("Account Information:");
        label.getElement().getStyle().setProperty("marginTop","50px");
        label.getElement().getStyle().setProperty("fontWeight", "bold");
        label.getElement().getStyle().setProperty("fontSize", "large");

        FlexTable profileTable = new FlexTable();
        profileTable.addStyleName("profile-table");

        profileTable.setText(0, 0, "Field");
        profileTable.setText(0, 1, "Value");

        contentPanel.add(label);
        profileTable.getCellFormatter().addStyleName(0, 0, "profile-table-header");
        profileTable.getCellFormatter().addStyleName(0, 1, "profile-table-header");

        String interviewerName = "Sai Kiran"; 
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

        backButton.getElement().getStyle().setProperty("marginLeft", "20px");
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



    private List<ApplicantFullDetails> applicantList = new ArrayList<>(); 

    private void showDashboard() {
        contentPanel.clear();
        contentPanel.addStyleName("content-panel");
        contentPanel.getElement().getStyle().setProperty("marginTop", "0px");

        HorizontalPanel searchPanel = new HorizontalPanel();
        searchPanel.addStyleName("search-panel");
        TextBox searchBox = new TextBox();
        searchBox.setWidth("300px");
        searchBox.setStyleName("search-box");
        searchBox.getElement().setPropertyString("placeholder", "Search...");

        searchPanel.add(searchBox);

        final FlexTable flexTable = new FlexTable();
        flexTable.addStyleName("flex-table");

        flexTable.setText(0, 0, "SN");
        flexTable.setText(0, 1, "FName");
        flexTable.setText(0, 2, "LName");
        flexTable.setText(0, 3, "Email ID");
        flexTable.setText(0, 4, "Mobile Number");
        flexTable.setText(0, 5, "Resume");
        flexTable.setText(0, 6, "Assigned To");
        flexTable.setText(0, 7, "Interview Status");
        flexTable.setText(0, 8, "Interview History");
        flexTable.setText(0, 9, "Final Status");

        for (int i = 0; i < 10; i++) {
            flexTable.getCellFormatter().addStyleName(0, i, "flex-table-header");
            flexTable.getRowFormatter().addStyleName(0, "fixed-height-row");
        }

        contentPanel.add(searchPanel);
        contentPanel.add(flexTable);
        contentPanel.setCellHorizontalAlignment(flexTable, HasHorizontalAlignment.ALIGN_CENTER);

        fetchAndUpdateApplicantData(flexTable);

        Timer refreshTimer = new Timer() {
            @Override
            public void run() {
                fetchAndUpdateApplicantData(flexTable);
            }
        };
        refreshTimer.scheduleRepeating(10000);
    }
    private Map<String, String> interviewStatusMap = new HashMap<>();

    private void fetchAndUpdateApplicantData(final FlexTable flexTable) {
        AssignmentServiceAsync assignmentService = GWT.create(AssignmentService.class);
        assignmentService.getAllAssignedApplicants(new AsyncCallback<List<Assignment>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to fetch assigned applicants!");
            }

            public void onSuccess(List<Assignment> assignedApplicants) {
                assignedInterviewersMap.clear();
                interviewStatusMap.clear(); 
                applicantList.clear();
                existingApplicantIds.clear();

                for (Assignment assigned : assignedApplicants) {
                    String name = assigned.getApplicant_name();
                    String interviewerName = assigned.getInterviewer_name();
                    String interviewStatus = assigned.getStatus(); 

                    assignedInterviewersMap.put(name, interviewerName);
                    interviewStatusMap.put(name, interviewStatus); 
                }

                ApplicantServiceAsync applicantService = GWT.create(ApplicantService.class);
                applicantService.getApplicants(new AsyncCallback<List<ApplicantFullDetails>>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Failed to fetch applicant data!");
                    }

                    @Override
                    public void onSuccess(List<ApplicantFullDetails> result) {
                        for (ApplicantFullDetails applicant : result) {
                            String applicantId = applicant.getEmail();
                            if (!existingApplicantIds.contains(applicantId)) {
                                applicantList.add(applicant);
                                existingApplicantIds.add(applicantId);
                            }
                        }

                        populateFlexTable(flexTable);
                    }
                });
            }
        });
    }

    private void populateFlexTable(FlexTable flexTable) {
        for (int i = 1; i < flexTable.getRowCount(); i++) {
            flexTable.removeRow(1); 
        }

        int row = 1; 
        for (final ApplicantFullDetails applicant : applicantList) {
            String name = applicant.getFname() + " " + applicant.getLname();

            flexTable.setText(row, 0, String.valueOf(row));
            flexTable.setText(row, 1, applicant.getFname());
            flexTable.setText(row, 2, applicant.getLname());
            flexTable.setText(row, 3, applicant.getEmail());
            flexTable.setText(row, 4, applicant.getMobile());

            // Resume
            Label resumeFileLabel = new Label(applicant.getResumePath());
            resumeFileLabel.addStyleName("resume-file-label");
            flexTable.setWidget(row, 5, resumeFileLabel);

            String assignedTo = assignedInterviewersMap.get(name);
            if (assignedTo != null && !assignedTo.isEmpty()) {
                flexTable.setText(row, 6, assignedTo);
            } else {
                ListBox assignedDropdown = createAssignedDropdown(row, flexTable, name);
                flexTable.setWidget(row, 6, assignedDropdown);
            }

           
            String interviewStatus = interviewStatusMap.get(name);
            if (interviewStatus != null && !interviewStatus.isEmpty()) {
                flexTable.setText(row, 7, interviewStatus);
            } else {
                flexTable.setWidget(row, 7, createStatusTextBox());  
            }

            Button historyButton = new Button("View History");
            historyButton.setStyleName("interview-history");
            historyButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    Window.alert("NO");
                    updateStatus(applicant.getFname() + " " + applicant.getLname());
                }
            });
            flexTable.setWidget(row, 8, historyButton);

            flexTable.setWidget(row, 9, createFinalStatusDropdown());

            row++; 
        }
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

//        final Button submitButton = new Button("Submit Evaluation");
//        submitButton.addStyleName("submit-button");
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
//        buttonPanel.add(submitButton);
        buttonPanel.setCellHorizontalAlignment(backButton, HasHorizontalAlignment.ALIGN_LEFT);
        buttonPanel.setCellHorizontalAlignment(submitButton, HasHorizontalAlignment.ALIGN_RIGHT);
        
        
        contentPanel.add(buttonPanel);

       
//        contentPanel.getElement().getStyle().setProperty("paddingTop", "20px");
        contentPanel.setCellHorizontalAlignment(evaluationTable, HasHorizontalAlignment.ALIGN_LEFT);
        contentPanel.setCellHorizontalAlignment(backButton, HasHorizontalAlignment.ALIGN_LEFT);
        contentPanel.setCellHorizontalAlignment(submitButton, HasHorizontalAlignment.ALIGN_RIGHT);
    }


    private ListBox createFinalStatusDropdown() {
        ListBox statusDropdown = new ListBox();
        statusDropdown.addItem("Select Status");
        statusDropdown.addItem("Selected");
        statusDropdown.addItem("Rejected");
        statusDropdown.addItem("On Hold");
        return statusDropdown;
    }

    private ListBox createAssignedDropdown(final int rowIndex, final FlexTable flexTable, final String applicantName) {
        final ListBox assignedDropdown = new ListBox();
        assignedDropdown.addItem("Loading...");
        assignedDropdown.setEnabled(false);

        final String preSelectedInterviewer = assignedInterviewersMap.get(applicantName);

       
        InterviewerServiceAsync interviewerService = GWT.create(InterviewerService.class);
        interviewerService.getInterviewers(new AsyncCallback<List<Interviewer>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to load interviewers.");
            }

            @Override
            public void onSuccess(List<Interviewer> result) {
                assignedDropdown.clear();
                assignedDropdown.setEnabled(true);

                assignedDropdown.addItem("Select Interviewer");
                for (Interviewer interviewer : result) {
                    assignedDropdown.addItem(interviewer.getName(), interviewer.getUsername());
                }

               
                if (preSelectedInterviewer != null && !preSelectedInterviewer.isEmpty()) {
                    for (int i = 0; i < assignedDropdown.getItemCount(); i++) {
                        if (assignedDropdown.getValue(i).equals(preSelectedInterviewer)) {
                            assignedDropdown.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        });

        assignedDropdown.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                final String selectedInterviewer = assignedDropdown.getSelectedValue();
                if (!selectedInterviewer.equals("Select Interviewer")) {
                    if (Window.confirm("Are you sure you want to assign " + selectedInterviewer + " to " + applicantName + "?")) {
                        Assignment assignment = new Assignment();
                        assignment.setInterviewer_name(selectedInterviewer);
                        assignment.setApplicant_name(applicantName);
                        assignment.setStatus("in process");

                        AssignmentServiceAsync assignmentService = GWT.create(AssignmentService.class);
                        assignmentService.assignApplicantToInterviewer(assignment, new AsyncCallback<Void>() {
                            @Override
                            public void onSuccess(Void result) {
                                Window.alert("Assignment updated successfully!");
                             
                                assignedInterviewersMap.put(applicantName, selectedInterviewer);
                            }

                            @Override
                            public void onFailure(Throwable caught) {
                                Window.alert("Error updating assignment: " + caught.getMessage());
                            }
                        });
                    } else {
                      
                        if (preSelectedInterviewer != null && !preSelectedInterviewer.isEmpty()) {
                            for (int i = 0; i < assignedDropdown.getItemCount(); i++) {
                                if (assignedDropdown.getValue(i).equals(preSelectedInterviewer)) {
                                    assignedDropdown.setSelectedIndex(i);
                                    break;
                                }
                            }
                        } else {
                            assignedDropdown.setSelectedIndex(0); 
                        }
                    }
                }
            }
        });

        return assignedDropdown;
    }


    private TextBox createStatusTextBox() {
        TextBox statusBox = new TextBox();
        statusBox.setStyleName("status-box");
        statusBox.setWidth("80px");
        return statusBox;
    }

    private void logout() {
        RootPanel.get().clear();
        RootPanel.get().add(new HrLogin());
    }
    
    private void showPerformancePopup(int row) {
//      String performanceDetails;
//
//      if (row == 1) {
//          performanceDetails = "Sai Charan performed well in communication but needs improvement in technical skills.";
//      } else {
//          performanceDetails = "Manojav showed strong problem-solving skills but lacked time management.";
//      }
//
//      final DialogBox dialogBox = new DialogBox();
//      dialogBox.setText("Interview Performance Details");
//      dialogBox.setAnimationEnabled(true);
//
//      VerticalPanel dialogPanel = new VerticalPanel();
//      dialogPanel.addStyleName("dialog-panel");
//
//      Label detailsLabel = new Label(performanceDetails);
//      detailsLabel.addStyleName("performance-details-label");
//
//      Button closeButton = new Button("Close");
//      closeButton.addStyleName("close-button");
//
//      closeButton.addClickHandler(new ClickHandler() {
//          public void onClick(ClickEvent event) {
//              dialogBox.hide();
//          }
//      });
//
//      dialogPanel.add(detailsLabel);
//      dialogPanel.add(closeButton);
//      dialogPanel.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_CENTER);
//
//      dialogBox.setWidget(dialogPanel);
//      dialogBox.center();
//      dialogBox.show();
//  }

}
}
