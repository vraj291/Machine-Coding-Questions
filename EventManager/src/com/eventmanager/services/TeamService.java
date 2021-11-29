package com.eventmanager.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.eventmanager.exceptions.TeamException;
import com.eventmanager.exceptions.UserException;
import com.eventmanager.models.Team;
import com.eventmanager.models.User;
import com.eventmanager.utilities.StringUtility;

public class TeamService {

	public List<Team> repository = new ArrayList<>();
	
	private Team getTeam(int id) {
		return repository.stream().filter(team -> team.checkId(id)).findFirst().orElse(null);
	}
	
	public TeamService() {}
	
	public void addTeam(String name,List<User> users) {
		Team newTeam = new Team(repository.size()+1,name,users);
		repository.add(newTeam);
	}
	
	public List<Team> getTeams(String teams){
		try {
			List<Integer> ids = StringUtility.getIds(teams);
			return ids.stream().map(id -> {
				Team team = getTeam(id);
				if(team == null) {
					throw new TeamException("Team with Id = " + id + " doesnt exist.");
				}
				return team;
			}).collect(Collectors.toList());
		}catch(TeamException e) {
			throw e;
		}catch(Exception e) {
			throw new UserException("Team input is invalid.");
		}
	}
	
}
