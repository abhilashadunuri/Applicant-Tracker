package com.example.ui.client;

import java.io.File;

import com.example.ui.domain.ApplicantFullDetails;
import com.example.ui.domain.User;
import com.example.ui.service.ApplicantService;
import com.example.ui.service.ApplicantServiceAsync;
import com.example.ui.service.AuthService;
import com.example.ui.service.AuthServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ApplicantForm extends Composite {

    private final VerticalPanel formPanel = new VerticalPanel(); 
    private final ApplicantServiceAsync storedetails = GWT.create(ApplicantService.class);
    String user;
    public ApplicantForm(final String username) {
        HorizontalPanel mainPanel = new HorizontalPanel();
        mainPanel.setWidth("100%");
        VerticalPanel leftNavPanel = new VerticalPanel();
        leftNavPanel.setStyleName("navPanel");
//        leftNavPanel.getElement().getStyle().setProperty("marginLeft", "20px");
        mainPanel.add(leftNavPanel);
        mainPanel.getElement().getStyle().setMargin(0, Unit.PX);
        mainPanel.getElement().getStyle().setPadding(0, Unit.PX);
        leftNavPanel.getElement().getStyle().setPaddingRight(0, Unit.PX);
        formPanel.getElement().getStyle().setPaddingLeft(0, Unit.PX);

        
        Image logoImage = new Image("images/images.png");
        logoImage.setSize("100%", "auto");
        VerticalPanel logoPanel = new VerticalPanel();
        logoPanel.setSpacing(10);
        logoPanel.add(logoImage);
        
        
        Button profileButton = new Button("Profile");
        profileButton.setStyleName("navButton");
        logoPanel.add(profileButton);
        logoPanel.addStyleName("upper");
        
        leftNavPanel.add(logoPanel);

        Button logoutButton = new Button("Logout");
        logoutButton.setStyleName("navlogoutButton");
        leftNavPanel.add(logoutButton);
        leftNavPanel.setCellHorizontalAlignment(logoImage, HasHorizontalAlignment.ALIGN_CENTER);
        leftNavPanel.setCellHorizontalAlignment(profileButton, HasHorizontalAlignment.ALIGN_CENTER);
        leftNavPanel.setCellHorizontalAlignment(logoutButton, HasHorizontalAlignment.ALIGN_CENTER);

        
        formPanel.setWidth("85%");
        formPanel.setStyleName("formPanel");
        mainPanel.add(formPanel);

        Label formTitle = new Label("Applicant Details:");
        formTitle.setStyleName("formTitle");
        formPanel.add(formTitle);

        HorizontalPanel gridPanel = new HorizontalPanel();
        gridPanel.addStyleName("stylingApplicantform");
        gridPanel.setWidth("100%");
        gridPanel.setSpacing(20);
        formPanel.add(gridPanel);

        Grid leftGrid = new Grid(10, 2);
        leftGrid.setCellSpacing(15);
        gridPanel.add(leftGrid); 

        leftGrid.setText(0, 0, "First Name:");
        final TextBox nameBox = new TextBox();
        nameBox.setWidth("250px");
        leftGrid.setWidget(0, 1, nameBox);
        
        leftGrid.setText(1, 0, "Last Name:");
        final TextBox LastName = new TextBox();
        LastName.setWidth("250px");
        leftGrid.setWidget(1, 1, LastName);


        leftGrid.setText(2, 0, "Email Id:");
        final TextBox emailBox = new TextBox();
        emailBox.setWidth("250px");
        leftGrid.setWidget(2, 1, emailBox);

        leftGrid.setText(4, 0, "College Name:");
        final ListBox collegeDropdown = new ListBox(); 
        collegeDropdown.addItem("Select College");
        collegeDropdown.addItem("Sreenidhi Institute of Science And Technology");
        collegeDropdown.addItem("Shreyas Institute of  Engeneering And Technology");
        collegeDropdown.addItem("Kakatiya Institute of Technology and Science (KITS)");
        collegeDropdown.addItem("Pulla Reddy Engineeing College,Kurnool");
        collegeDropdown.addItem("KL University");
        collegeDropdown.setWidth("260px");
        collegeDropdown.setHeight("29px");
        leftGrid.setWidget(4, 1, collegeDropdown);

        leftGrid.setText(3, 0, "Mobile Number:");
        final TextBox mobileBox = new TextBox();
        mobileBox.setWidth("250px");
        leftGrid.setWidget(3, 1, mobileBox);

        leftGrid.setText(5, 0, "Current Education:");
        final TextBox educationBox = new TextBox();
        educationBox.setWidth("250px");
        leftGrid.setWidget(5, 1, educationBox);

        leftGrid.setText(6, 0, "Role Interested:");
        final ListBox RoleDropdown = new ListBox(); 
        RoleDropdown.addItem("Select Role");
        RoleDropdown.addItem("Java Full Stack Developer");
        RoleDropdown.addItem("AI&ML  Developer");
        RoleDropdown.addItem("Dotnet Developer");
        RoleDropdown.addItem("Mobile App Developer");
        RoleDropdown.setWidth("260px");
        RoleDropdown.setHeight("29px");
        leftGrid.setWidget(6, 1, RoleDropdown);

        leftGrid.setText(7, 0, "Major Skills:");
        final TextBox skillsBox = new TextBox();
        skillsBox.setWidth("250px");
        leftGrid.setWidget(7, 1, skillsBox);

        leftGrid.setText(8, 0, "Current Address:");
        final TextBox addressBox = new TextBox();
        addressBox.setWidth("250px");
        leftGrid.setWidget(8, 1, addressBox);

        leftGrid.setText(9, 0, "Experience (years):");
        final TextBox experienceBox = new TextBox();
        experienceBox.setWidth("250px");
        leftGrid.setWidget(9, 1, experienceBox);

        Grid rightGrid = new Grid(9, 2);
        rightGrid.getElement().getStyle().setProperty("marginLeft", "20px");
        rightGrid.setCellSpacing(15);
        gridPanel.add(rightGrid);
        
        rightGrid.setText(0, 0, "Upload Resume:");
        final FileUpload resumeBox = new FileUpload();
        resumeBox.setWidth("250px");
        rightGrid.setWidget(0, 1, resumeBox);

        rightGrid.setText(1, 0, "Tech Stack:");
        final TextBox techStackBox = new TextBox();
        techStackBox.setWidth("250px");
        rightGrid.setWidget(1, 1, techStackBox);

        rightGrid.setText(2, 0, "Expected Package:");
        final TextBox packageBox = new TextBox();
        packageBox.setWidth("250px");
        rightGrid.setWidget(2, 1, packageBox);

        rightGrid.setText(3, 0, "Why are you interested?");
        final TextBox interestBox = new TextBox();
        interestBox.setWidth("250px");
        rightGrid.setWidget(3, 1, interestBox);


        rightGrid.setText(4, 0, "LinkedIn Profile:");
        final TextBox linkedInBox = new TextBox();
        linkedInBox.setWidth("250px");
        rightGrid.setWidget(4, 1, linkedInBox);

        rightGrid.setText(5, 0, "GitHub/Portfolio Link:");
        final TextBox portfolioBox = new TextBox();
        portfolioBox.setWidth("250px");
        rightGrid.setWidget(5, 1, portfolioBox);

        rightGrid.setText(6, 0, "Preferred Location:");
        final ListBox locationDropdown = new ListBox();
        locationDropdown.addItem("Select Location");
        locationDropdown.addItem("Hyderabad");
        locationDropdown.addItem("Bangalore");
        locationDropdown.addItem("USA");
        locationDropdown.setWidth("260px");
        locationDropdown.setHeight("29px");
        rightGrid.setWidget(6, 1, locationDropdown);

        rightGrid.setText(7, 0, "Notice Period:");
        final TextBox noticePeriodBox = new TextBox();
        noticePeriodBox.setWidth("250px");
        rightGrid.setWidget(7, 1, noticePeriodBox);


        rightGrid.setText(8, 0, "Additional Comments:");
        final TextArea commentsBox = new TextArea();
        commentsBox.setWidth("250px");
        commentsBox.setVisibleLines(4);
        rightGrid.setWidget(8, 1, commentsBox);


        Button submitButton = new Button("Submit");
        submitButton.setStyleName("submitButton");
        formPanel.add(submitButton);
        formPanel.setCellHorizontalAlignment(submitButton, HasHorizontalAlignment.ALIGN_CENTER);
        
        logoutButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                goback();
            }
        });
        submitButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                final DialogBox dialogBox = new DialogBox();
                dialogBox.setText("Confirmation");
                dialogBox.setAnimationEnabled(true);
                dialogBox.setGlassEnabled(true);
                
                VerticalPanel dialogVPanel = new VerticalPanel();
                dialogVPanel.setStyleName("dialog-box"); 
                
                Label confirmationLabel = new Label("Are you sure to submit details?");
                confirmationLabel.setStyleName("dialog-label"); 
                dialogVPanel.add(confirmationLabel);
                
                Button yesButton = new Button("Yes");
                yesButton.setStyleName("dialog-button dialog-button-yes"); 
                yesButton.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        String firstName = nameBox.getText().trim();
                        String lastName = LastName.getText().trim();
                        String email = emailBox.getText().trim();
                        
                        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                            return;
                        }
                        

                        String mobile = mobileBox.getText();
                        String college = collegeDropdown.getSelectedValue();
                        String education = educationBox.getText();
                        String roleInterested = RoleDropdown.getSelectedValue();
                        String skills = skillsBox.getText();
                        String address = addressBox.getText();
                        String experience = experienceBox.getText();
                        String resumeFileName = resumeBox.getFilename(); 
                        String techStack = techStackBox.getText();
                        int expectedPackage = Integer.parseInt(packageBox.getText());
                        String linkedIn = linkedInBox.getText();
                        String portfolio = portfolioBox.getText();
                        String preferredLocation = locationDropdown.getSelectedValue();
                        String noticePeriod = noticePeriodBox.getText();
                        String comments = commentsBox.getText();

                        ApplicantFullDetails applicant = new ApplicantFullDetails();
                        applicant.setUsername(username);
                        applicant.setFname(firstName);
                        applicant.setLname(lastName);
                        applicant.setEmail(email);
                        applicant.setMobile(mobile);
                        applicant.setCollegeName(college);
                        applicant.setCurrentEducation(education);
                        applicant.setRoleInterested(roleInterested);
                        applicant.setSkills(skills);
                        applicant.setAddress(address);
                        applicant.setExperience(experience);
                        applicant.setResumePath(resumeFileName);  
                        applicant.setTechStack(techStack);
                        applicant.setExpectedPackage(expectedPackage);
                        applicant.setLinkedinProfile(linkedIn);
                        applicant.setGithubProfile(portfolio);
                        applicant.setLocation(preferredLocation);
                        applicant.setNoticePeriod(noticePeriod);
                        applicant.setAdditionalComments(comments); 
                        
                        storedetails.saveApplicantDetails(applicant,new AsyncCallback<Void>() {
                            @Override
                            public void onFailure(Throwable caught) {
                            	 GWT.log("Error saving applicant details: " + caught.getMessage().toString(), caught);
                                 Window.alert("DB error: " + caught.getMessage()); 
                            }

                            @Override
                            public void onSuccess(Void result) {
                            	Window.alert("Details Stored1");
                                showTable();
                                dialogBox.hide(); 
                            }
                        });
                    }
                });

                Button noButton = new Button("No");
                noButton.setStyleName("dialog-button dialog-button-no"); 
                noButton.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        dialogBox.hide(); 
                    }
                });

                HorizontalPanel hp = new HorizontalPanel();
                hp.add(yesButton);
                hp.add(noButton);
                dialogVPanel.add(hp);
                dialogBox.setWidget(dialogVPanel);
                dialogBox.center();
                dialogBox.show();
            }
        });


        initWidget(mainPanel); 
    }

    public void goback(){
        RootPanel.get().clear();
        RootPanel.get().add(new HrLogin());
    }

    void showTable() {
        formPanel.clear();
        Label heading = new Label("Details Submitted:");
        heading.setStyleName("formTitle");
        formPanel.add(heading);
        
        HorizontalPanel gridPanel = new HorizontalPanel();
        gridPanel.setWidth("100%");
        gridPanel.setSpacing(20);
        gridPanel.setStyleName("stylingApplicantform");
        gridPanel.getElement().getStyle().setProperty("marginRight", "20px");
        gridPanel.getElement().getStyle().setProperty("fontSize","larger");
        formPanel.add(gridPanel);
        
        
        
        Grid leftGrid = new Grid(10, 2);
        leftGrid.setCellSpacing(15);
        leftGrid.getElement().getStyle().setProperty("marginLeft", "20px");
        leftGrid.setCellSpacing(10);
        gridPanel.add(leftGrid);

        String underlineStyle = "width:250px;";

        leftGrid.setText(0, 0, " First Name:");
        final TextBox firstNameBox = new TextBox();
        firstNameBox.setReadOnly(true);
        firstNameBox.getElement().setAttribute("style", underlineStyle);
        leftGrid.setWidget(0, 1, firstNameBox);

        leftGrid.setText(1, 0, "Last Name:");
        final TextBox lastNameBox = new TextBox();
        lastNameBox .setReadOnly(true);
        lastNameBox .getElement().setAttribute("style", underlineStyle);
        leftGrid.setWidget(1, 1, lastNameBox);

        leftGrid.setText(2, 0, "Email Id:");
        final TextBox  emailBox= new TextBox();
        emailBox.setReadOnly(true);
        emailBox.getElement().setAttribute("style", underlineStyle);
        leftGrid.setWidget(2, 1, emailBox);

        leftGrid.setText(3, 0, "Mobile Number:");
        final TextBox mobileBox = new TextBox();
        mobileBox.setReadOnly(true);
        mobileBox.getElement().setAttribute("style", underlineStyle);
        leftGrid.setWidget(3, 1, mobileBox);

        leftGrid.setText(4, 0, "Current Education:");
        final TextBox educationBox = new TextBox();
        educationBox.setReadOnly(true);
        educationBox.getElement().setAttribute("style", underlineStyle);
        leftGrid.setWidget(4, 1, educationBox);

        leftGrid.setText(5, 0, "College Name");
        final TextBox collegeBox = new TextBox();
        collegeBox.setReadOnly(true);
        collegeBox.getElement().setAttribute("style", underlineStyle);
        leftGrid.setWidget(5, 1, collegeBox);

        leftGrid.setText(6, 0, "Role Interested:");
        final TextBox roleBox = new TextBox();
        roleBox.setReadOnly(true);
        roleBox.getElement().setAttribute("style", underlineStyle);
        leftGrid.setWidget(6, 1, roleBox);

        leftGrid.setText(7, 0, "Major Skills:");
        final TextBox skillsBox = new TextBox();
        skillsBox.setReadOnly(true);
        skillsBox.getElement().setAttribute("style", underlineStyle);
        leftGrid.setWidget(7, 1, skillsBox);

        leftGrid.setText(8, 0, "Current Address:");
        final TextBox addressBox = new TextBox();
        addressBox.setReadOnly(true);
        addressBox.getElement().setAttribute("style", underlineStyle);
        leftGrid.setWidget(8, 1, addressBox);

        leftGrid.setText(9, 0, "Experience (years):");
        final TextBox expBox = new TextBox();
        expBox.setReadOnly(true);
        expBox.getElement().setAttribute("style", underlineStyle);
        leftGrid.setWidget(9, 1, expBox);
        
        Grid rightGrid = new Grid(9, 2);
        rightGrid.getElement().getStyle().setProperty("marginLeft", "20px");
        rightGrid.setCellSpacing(15);
        gridPanel.add(rightGrid);
        
        rightGrid.setText(0, 0, "Resume:");
        final TextBox resumeBox = new TextBox();
        resumeBox.setReadOnly(true);
        resumeBox.getElement().setAttribute("style", underlineStyle);
        rightGrid.setWidget(0, 1, resumeBox);

        rightGrid.setText(1, 0, "Tech Stack:");
        final TextBox techStackBox = new TextBox();
        techStackBox.setReadOnly(true);
        techStackBox.getElement().setAttribute("style", underlineStyle);
        rightGrid.setWidget(1, 1, techStackBox);

        rightGrid.setText(2, 0, "Expected Package:");
        final TextBox packageBox = new TextBox();
        packageBox.setReadOnly(true);
        packageBox.getElement().setAttribute("style", underlineStyle);
        rightGrid.setWidget(2, 1, packageBox);

        rightGrid.setText(3, 0, "Why are you interested?");
        final TextBox interestBox = new TextBox();
        interestBox.setReadOnly(true);
        interestBox.getElement().setAttribute("style", underlineStyle);
        rightGrid.setWidget(3, 1, interestBox);

        rightGrid.setText(4, 0, "LinkedIn Profile:");
        final TextBox linkedInBox = new TextBox();
        linkedInBox.setReadOnly(true);
        linkedInBox.getElement().setAttribute("style", underlineStyle);
        rightGrid.setWidget(4, 1, linkedInBox);

        rightGrid.setText(5, 0, "GitHub/Portfolio Link:");
        final TextBox portfolioBox = new TextBox();
        portfolioBox.setReadOnly(true);
        portfolioBox.getElement().setAttribute("style", underlineStyle);
        rightGrid.setWidget(5, 1, portfolioBox);

        rightGrid.setText(6, 0, "Preferred Location:");
        final TextBox preferredLocationBox = new TextBox();
        preferredLocationBox.setReadOnly(true);
        preferredLocationBox.getElement().setAttribute("style", underlineStyle);
        rightGrid.setWidget(6, 1, preferredLocationBox);

        rightGrid.setText(7, 0, "Notice Period:");
        final TextBox noticePeriodBox = new TextBox();
        noticePeriodBox.setReadOnly(true);
        noticePeriodBox.getElement().setAttribute("style", underlineStyle);
        rightGrid.setWidget(7, 1, noticePeriodBox);


        rightGrid.setText(8, 0, "Additional Comments:");
        final TextArea commentsBox = new TextArea();
        commentsBox.setReadOnly(true);
        commentsBox.getElement().setAttribute("style", underlineStyle);
        rightGrid.setWidget(8, 1, commentsBox);
        final AuthServiceAsync showdetails = GWT.create(AuthService.class);
        showdetails.getCurrentUser(new AsyncCallback<User>(){
			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(User result) {
				user=result.getUsername();
				final ApplicantServiceAsync get= GWT.create(ApplicantService.class);
				get.getDetails(user,new AsyncCallback<ApplicantFullDetails>(){

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("got it");	
					}

					@Override
					public void onSuccess(ApplicantFullDetails result) {
						firstNameBox.setText(result.getFname());
                        lastNameBox.setText(result.getLname());
                        emailBox.setText(result.getEmail());
                        mobileBox.setText(result.getMobile());
                        collegeBox.setText(result.getCollegeName());
                        educationBox.setText(result.getCurrentEducation());
                        roleBox.setText(result.getRoleInterested());
                        skillsBox.setText(result.getSkills());
                        addressBox.setText(result.getAddress());
                        expBox.setText(result.getExperience());
                        resumeBox.setText(result.getResumePath());
                        techStackBox.setText(result.getTechStack());  
                        int pack=result.getExpectedPackage();
                        packageBox.setText(String.valueOf(pack));
                        linkedInBox.setText(result.getLinkedinProfile());
                        portfolioBox.setText(result.getGithubProfile());
                        preferredLocationBox.setText(result.getLocation());
                        noticePeriodBox.setText(result.getNoticePeriod());
                        commentsBox.setText(result.getAdditionalComments());
                        
					}
				});
			}
        	
        });
        
        HorizontalPanel vp1 =new HorizontalPanel();
        vp1.addStyleName("vp1");
        
        HorizontalPanel vp2 =new HorizontalPanel();
        vp2.addStyleName("vp2");
        
        vp1.setSpacing(20);
        vp2.setSpacing(20);
        vp1.addStyleName("AppStatusField");
        
        Label statusUpdate = new Label("InterviewUpdate:");
        statusUpdate.setStyleName("Update");
        vp1.add(statusUpdate);
        
        TextBox update = new TextBox();
        update.setText("Process ongoing");
        update.setReadOnly(true);
        vp2.add(update);
        
        Label up = new Label("(Status Get Updated Once the process in completed)");
        vp2.add(up);
        vp1.add(vp2);
        
        formPanel.add(vp1);
        
    }

}
