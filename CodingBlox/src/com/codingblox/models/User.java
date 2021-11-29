package com.codingblox.models;

import com.codingblox.exceptions.UserException;

public class User {

	private int id;
	private String name;
	private String gender;
	private String phoneNo;
	private int score;
	
	public User(int id,String name,String gender,String phoneNo) {
		this.id = id;
		if(name == null) {
			throw new UserException("Name is not specified.");
		}
		this.name = name;
		if(gender == null) {
			throw new UserException("Gender is not specified.");
		}
		this.gender = gender;
		if(phoneNo == null) {
			throw new UserException("Phone Number is not specified.");
		}
		this.phoneNo = phoneNo;
		this.score = 1500;
	}
	
	public boolean isValid(int id) {
		return this.id == id;
	}
	
	public int getId() {
		return id;
	}
	
	public int getScore() {
		return score;
	}
	
	public void updateScore(int points) {
		score += points;
	}
	
	public boolean equals(User other) {
		return other.isValid(id);
	}
	
	public String toString() {
		return "Id: " + id + ", Name: " + name + ", Score: " + score ;
	}
}
