package com.example.ui.domain;

import java.io.Serializable;

public class InterviewHistory implements Serializable{
	private static final long serialVersionUID = 1L;
	private int historyId;
	private String username;
	private String name;
	private int oops;
	private int PS;
	private int com;
	private int tech;
	private int DSA;
	private int CF;
	private int LQ;
	private int br;
	private String PO;
	private int ORating;
	
	public InterviewHistory(){
		
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOops() {
		return oops;
	}
	public void setOops(int oops) {
		this.oops = oops;
	}
	public int getPS() {
	    return PS;
	}

	public void setPS(int PS) {
	    this.PS = PS;
	}

	public int getCom() {
		return com;
	}
	public void setCom(int com) {
		this.com = com;
	}
	public int getTech() {
		return tech;
	}
	public void setTech(int tech) {
		this.tech = tech;
	}
	public int getDSA() {
		return DSA;
	}
	public void setDSA(int dSA) {
		DSA = dSA;
	}
	public int getCF() {
		return CF;
	}
	public void setCF(int cF) {
		CF = cF;
	}
	public int getLQ() {
		return LQ;
	}
	public void setLQ(int lQ) {
		LQ = lQ;
	}
	public int getBr() {
		return br;
	}
	public void setBr(int br) {
		this.br = br;
	}
	public String getPO() {
		return PO;
	}
	public void setPO(String pO) {
		PO = pO;
	}
	public int getORating() {
		return ORating;
	}
	public void setORating(int oRating) {
		ORating = oRating;
	}
	public int getHistoryId() {
		return historyId;
	}
	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}
}
