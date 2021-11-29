package com.codingblox.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.codingblox.models.Difficulty;
import com.codingblox.models.Question;

public class QuestionService {

	private List<Question> repository = new ArrayList<>();
	
	public QuestionService() {}
	
	public Question createQuestion(String difficulty,int score) {
		Question newQuestion = new Question(repository.size()+1,difficulty,score);
		repository.add(newQuestion);
		return newQuestion;
	}
	
	public List<Question> getQuestions(String difficulty){
		if(difficulty == null) {
			return repository;
		}else {
			Difficulty level = new Difficulty(difficulty);
			return repository.stream().filter(question -> question.getDifficulty().equals(level)).collect(Collectors.toList());
		}
	}
}
