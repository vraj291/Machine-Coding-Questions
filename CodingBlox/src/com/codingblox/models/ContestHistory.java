package com.codingblox.models;

import java.util.ArrayList;
import java.util.List;

public class ContestHistory {

	private User user;
	private int score;
	private List<Integer> solvedQuestions = new ArrayList<>();
	
	public ContestHistory(User user,int score, List<Integer> solvedQuestions) {
		this.user = user;
		this.score = score;
		this.solvedQuestions = solvedQuestions;
	}
	
	public int getScore() {
		return score;
	}
	
	public int compareTo(ContestHistory other) {
		if(other.getScore() < score) {
			return -1;
		}else if(other.getScore() > score) {
			return 1;
		}else {
			return 0;
		}
	}
	
	public String toString() {
		return user + ", ContestScore: " + score + ", Solved Questions: " + solvedQuestions;
	}
}
