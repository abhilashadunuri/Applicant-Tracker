package com.example.ui.domain;

import java.io.Serializable;

public class Interviewer implements Serializable {
	private static final long serialVersionUID = 4964475182237971507L;
	private int interviewer_id;
	private String name;
    private String username;
    private User user;

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Interviewer() {
    }

    public Interviewer(String name, String username) {
        this.name = name;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

	public int getInterviewer_id() {
		return interviewer_id;
	}

	public void setInterviewer_id(int interviewer_id) {
		this.interviewer_id = interviewer_id;
	}
}
