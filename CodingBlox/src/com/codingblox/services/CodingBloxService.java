package com.codingblox.services;

import java.util.List;

import com.codingblox.exceptions.ServiceException;
import com.codingblox.models.Contest;
import com.codingblox.models.ContestHistory;
import com.codingblox.models.Question;
import com.codingblox.models.User;

public class CodingBloxService {
	
	QuestionService questionService = new QuestionService();
	UserService userService = new UserService();
	ContestService contestService = new ContestService();
	
	public CodingBloxService() {}
	
	public Question createQuestion(String difficulty,int score) {
		Question createdQuestion = questionService.createQuestion(difficulty,score);
		return createdQuestion;
	}
	
	public User createUser(String name,String gender,String phoneNo) {
		User createdUser = userService.createUser(name, gender, phoneNo);
		return createdUser;
	}
	
	public Contest createContest(int userId,String name,String difficulty) {
		User creator = userService.getUser(userId);
		if(creator == null) {
			throw new ServiceException("User does not exist.");
		}
		Contest createdContest = contestService.createContest(creator, name, difficulty);
		return createdContest;
	}
	
	public void attendContest(int contestId,int userId) {
		User user = userService.getUser(userId);
		if(user == null) {
			throw new ServiceException("User does not exist.");
		}
		contestService.attendContest(contestId, user);
	}
	
	public void withdrawContest(int contestId,int userId) {
		User user = userService.getUser(userId);
		if(user == null) {
			throw new ServiceException("User does not exist.");
		}
		contestService.withdrawContest(contestId, user);
	}
	
	public void runContest(int contestId,int userId) {
		User user = userService.getUser(userId);
		if(user == null) {
			throw new ServiceException("User does not exist.");
		}
		Contest contest = contestService.getContest(contestId);
		if(contest == null) {
			throw new ServiceException("Contest does not exist.");
		}
		if(!contest.validateCreator(user)) {
			throw new ServiceException("Contest can only be run by its creator.");
		}
		contestService.runContest(contest, user, questionService.getQuestions(contest.getDifficulty().toString()));
	}
	
	public List<Question> getQuestions(String difficulty){
		return questionService.getQuestions(difficulty);
	}
	
	public List<Contest> getContests(String difficulty){
		return contestService.getContests(difficulty);
	}
	
	public List<User> getLeaderboard(String sortType){
		return userService.getUsers(sortType);
	}
	
	public List<ContestHistory> getContentHistory(int contestId){
		return contestService.getContestHistory(contestId);
	}
}
