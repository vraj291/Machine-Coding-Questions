package com.codingblox;

import com.codingblox.services.CodingBloxService;

public class Main {

	public static void main(String[] args) {
		
		CodingBloxService codingBloxService = new CodingBloxService();
		
		codingBloxService.createQuestion("LOW", 10);
		codingBloxService.createQuestion("LOW", 8);
		codingBloxService.createQuestion("LOW", 5);
		codingBloxService.createQuestion("LOW", 2);
		codingBloxService.createQuestion("LOW", 10);
		codingBloxService.createQuestion("MEDIUM", 30);
		codingBloxService.createQuestion("MEDIUM", 38);
		codingBloxService.createQuestion("MEDIUM", 55);
		codingBloxService.createQuestion("MEDIUM", 40);
		
		codingBloxService.getQuestions("MEDIUM").forEach(contest -> {
			System.out.println(contest);
		});
		
		codingBloxService.createUser("Vraj", "M", "12345");
		codingBloxService.createUser("Vraj2", "M", "12345");
		codingBloxService.createUser("Vraj3", "M", "12345");
		codingBloxService.createUser("Vraj4", "M", "12345");
		
		codingBloxService.createContest(1, "Contest-1", "MEDIUM");
		
		codingBloxService.attendContest(1, 2);
		codingBloxService.attendContest(1, 3);
		codingBloxService.attendContest(1, 4);
		
		codingBloxService.withdrawContest(1, 2);
		
		codingBloxService.getContests(null).forEach(contest -> {
			System.out.println(contest);
		});
		
		codingBloxService.runContest(1, 1);
		
		codingBloxService.getContentHistory(1).forEach(history -> {
			System.out.println(history);
		});
		
		codingBloxService.getLeaderboard("desc").forEach(user -> {
			System.out.println(user);
		});
	}
	
}
