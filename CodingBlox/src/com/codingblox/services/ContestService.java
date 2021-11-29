package com.codingblox.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.codingblox.exceptions.ServiceException;
import com.codingblox.models.Contest;
import com.codingblox.models.ContestHistory;
import com.codingblox.models.Difficulty;
import com.codingblox.models.Question;
import com.codingblox.models.User;

public class ContestService {
	
	private List<Contest> repository = new ArrayList<Contest>();
	
	private List<List<Integer>> getQuestions(int userCount,int questionCount){
		 List<List<Integer>> result = new ArrayList<>();
		 List<Integer> userQuestionCount = new ArrayList<>();
		 Random random = new Random();
		 for(int i=0;i<userCount;i++) {
			 userQuestionCount.add(random.nextInt(questionCount+1));
		 }
		 userQuestionCount.forEach(count -> {
			 List<Integer> questions = new ArrayList<>();
			 int i = 0,index;
			 while(i<count) {
				 index = random.nextInt(questionCount);
				 if(!questions.contains(index)) {
					 questions.add(index);
					 i++;
				 }
			 }
			 result.add(questions);
		 });
		 return result;
	}
	
	public ContestService() {}
	
	public Contest getContest(int id) {
		return repository.stream().filter(contest -> contest.isValid(id)).findFirst().orElse(null);
	}
	
	public Contest createContest(User user,String name,String difficulty) {
		Contest newContest = new Contest(repository.size()+1,user,name,difficulty);
		repository.add(newContest);
		return newContest;
	}
	
	public List<Contest> getContests(String difficulty){
		if(difficulty == null) {
			return repository;
		}else {
			Difficulty level = new Difficulty(difficulty);
			return repository.stream().filter(contest -> contest.getDifficulty().equals(level)).collect(Collectors.toList());
		}
	}
	
	public void attendContest(int contestId,User user) {
		Contest contest = getContest(contestId);
		if(contest == null) {
			throw new ServiceException("Contest does not exist.");
		}
		contest.addAttendee(user);
	}
	
	public void runContest(Contest contest,User user,List<Question> questions) {
		List<List<Integer>> selectedQuestions = getQuestions(contest.getAttendeesCount(),questions.size());
		contest.updateScores(questions, selectedQuestions);
	}
	
	public void withdrawContest(int contestId,User user) {
		Contest contest = getContest(contestId);
		if(contest == null) {
			throw new ServiceException("Contest does not exist.");
		}
		contest.removeAttendee(user);
	}
	
	public List<ContestHistory> getContestHistory(int contestId){
		Contest contest = getContest(contestId);
		if(contest == null) {
			throw new ServiceException("Contest does not exist.");
		}
		return contest.getContestHistory();
	}
}
