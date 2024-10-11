package com.example.ui.domain;

import java.io.Serializable;

public class ApplicantFullDetails implements Serializable {
	private static final long serialVersionUID = 1L;
	private int user_id;
	private String username;
	private String Fname;
	private String Lname;
	private String email;
	private String mobile;
	private String CollegeName;
	private String CurrentEducation;
	private String RoleInterested;
	private String skills;
	private String address;
	private String Experience;
	private String resumePath;
	private String techStack;
	private int ExpectedPackage;
	private String LinkedinProfile;
	private String GithubProfile;
	private String location;
	private String noticePeriod;
    private String additionalComments;
    private User user;
    
    
    public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public ApplicantFullDetails(){
    	
    }
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFname() {
		return Fname;
	}
	public void setFname(String fname) {
		Fname = fname;
	}
	public String getLname() {
		return Lname;
	}
	public void setLname(String sname) {
		Lname = sname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCollegeName() {
		return CollegeName;
	}
	public void setCollegeName(String collegeName) {
		CollegeName = collegeName;
	}
	public String getCurrentEducation() {
		return CurrentEducation;
	}
	public void setCurrentEducation(String currentEducation) {
		CurrentEducation = currentEducation;
	}
	public String getRoleInterested() {
		return RoleInterested;
	}
	public void setRoleInterested(String roleInterested) {
		RoleInterested = roleInterested;
	}
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills2) {
		this.skills = skills2;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getExperience() {
		return Experience;
	}
	public void setExperience(String experience2) {
		Experience = experience2;
	}
	public String getResumePath() {
		return resumePath;
	}
	public void setResumePath(String resumePath) {
		this.resumePath = resumePath;
	}
	public String getTechStack() {
		return techStack;
	}
	public void setTechStack(String techStack2) {
		this.techStack = techStack2;
	}
	public int getExpectedPackage() {
		return ExpectedPackage;
	}
	public void setExpectedPackage(int expectedPackage) {
		ExpectedPackage = expectedPackage;
	}
	public String getLinkedinProfile() {
		return LinkedinProfile;
	}
	public void setLinkedinProfile(String linkedinProfile) {
		LinkedinProfile = linkedinProfile;
	}
	public String getGithubProfile() {
		return GithubProfile;
	}
	public void setGithubProfile(String githubProfile) {
		GithubProfile = githubProfile;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getNoticePeriod() {
		return noticePeriod;
	}
	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}
	public String getAdditionalComments() {
		return additionalComments;
	}
	public void setAdditionalComments(String additionalComments) {
		this.additionalComments = additionalComments;
	}
	public int getuser_id() {
		return user_id;
	}
	public void setuser_id(int user_id) {
		this.user_id = user_id;
	}
}
