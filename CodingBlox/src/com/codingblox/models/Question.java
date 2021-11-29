package com.codingblox.models;

public class Question {
	
	private int id;
	private int score;
	private Difficulty difficulty;
	
	public Question(int id,String difficulty,int score) {
		this.id = id;
		this.score = score;
		this.difficulty = new Difficulty(difficulty);
	}

	public int getId() {
		return id;
	}
	
	public int getScore() {
		return score;
	}
	
	public Difficulty getDifficulty() {
		return difficulty;
	}
	
	public String toString() {
		return "Id: " + id + ", Difficulty: " + difficulty + ", Score: " + score;
	}
	
}
