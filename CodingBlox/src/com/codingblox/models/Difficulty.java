package com.codingblox.models;

import com.codingblox.enums.DIFFICULTY_LEVEL;
import com.codingblox.exceptions.ServiceException;

public class Difficulty {
	
	private DIFFICULTY_LEVEL value;
	private int questionPenalty;
	
	public Difficulty(String input) {
		if(input.equalsIgnoreCase("low")) {
			value = DIFFICULTY_LEVEL.LOW;
			questionPenalty = 50;
		}else if(input.equalsIgnoreCase("medium")) {
			value = DIFFICULTY_LEVEL.MEDIUM;
			questionPenalty = 30;
		}else if(input.equalsIgnoreCase("high")) {
			value = DIFFICULTY_LEVEL.HIGH;
			questionPenalty = 0;
		}else{
			throw new ServiceException("Difficulty Level is invalid.");
		}
	}
	
	public DIFFICULTY_LEVEL getValue() {
		return this.value;
	}
	
	public int getPenalty() {
		return questionPenalty;
	}

	public boolean equals(Difficulty other) {
		return this.value == other.getValue();
	}
	
	public String toString() {
		if(value == DIFFICULTY_LEVEL.LOW) {
			return "LOW";
		}else if(value == DIFFICULTY_LEVEL.MEDIUM) {
			return "MEDIUM";
		}else {
			return "HIGH";
		}
	}
}
