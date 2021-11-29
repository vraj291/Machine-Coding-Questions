package com.codingblox.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.codingblox.exceptions.ServiceException;

public class Contest {
	
	private int id;
	private String name;
	private User creator;
	private Difficulty difficulty;
	private int questionPenalty;
	private List<User> attendees = new ArrayList<>();
	private List<ContestHistory> contestHistory = new  ArrayList<>();

	public Contest(int id,User user,String name,String difficulty) {
		this.id = id;
		this.creator = user;
		this.name = name;
		this.difficulty = new Difficulty(difficulty);
		this.questionPenalty = this.difficulty.getPenalty();
		attendees.add(user);
	}
	
	public boolean isValid(int id) {
		return this.id == id;
	}
	
	public boolean validateCreator(User user) {
		return creator.equals(user);
	}
	
	public int getAttendeesCount() {
		return attendees.size();
	}
	
	public void addAttendee(User user) {
		attendees.add(user);
	}
	
	public Difficulty getDifficulty(){
		return difficulty;
	}
	
	public void updateScores(List<Question> questions,List<List<Integer>> selectedQuestions) {
		for(int i=0;i<selectedQuestions.size();i++) {
			int score = 0;
			for(int j=0;j<selectedQuestions.get(i).size();j++) {
				score += questions.get(selectedQuestions.get(i).get(j)).getScore();
			}
			score -= questionPenalty;
			attendees.get(i).updateScore(score);
			ContestHistory newHistory = new ContestHistory(attendees.get(i),score,selectedQuestions.get(i));
			contestHistory.add(newHistory);
		}
	}
	
	public void removeAttendee(User user) {
		if(user.equals(creator)) {
			throw new ServiceException("Creator can not withdraw from contest.");
		}
		if(contestHistory.size() != 0) {
			throw new ServiceException("User can not withdraw from contest after it has completed.");
		}
		attendees.remove(user);
	}
	
	public List<ContestHistory> getContestHistory(){
		if(contestHistory.size() == 0) {
			throw new ServiceException("Contest has not been completed yet.");
		}
		return contestHistory.stream().sorted((history1,history2) -> history1.compareTo(history2)).collect(Collectors.toList());
	}
	
	public String toString() {
		return "Id: " + id + ", Name: " + name + ", Creator : [ " + creator + "], Difficulty: " + difficulty;
	}
}
