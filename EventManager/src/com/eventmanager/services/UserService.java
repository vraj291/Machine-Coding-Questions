package com.eventmanager.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.eventmanager.exceptions.UserException;
import com.eventmanager.models.TimeSlot;
import com.eventmanager.models.User;
import com.eventmanager.utilities.StringUtility;

public class UserService {

	private List<User> repository = new ArrayList<>();
	
	public UserService() {}
	
	public User getUser(int id) {
		return repository.stream().filter(user -> user.checkId(id)).findFirst().orElse(null);
	}
	
	public void addUser(String name,String startTime,String endTime) {
		TimeSlot timeSlot = new TimeSlot(startTime,endTime);
		User newUser = new User(repository.size()+1,name,timeSlot);
		repository.add(newUser);
	}
	
	public List<User> getUsers(String teamMembers){
		try {
			List<Integer> ids = StringUtility.getIds(teamMembers);
			return ids.stream().map(id -> {
				User user = getUser(id);
				if(user == null) {
					throw new UserException("User with Id = " + id + " doesnt exist.");
				}
				return user;
			}).collect(Collectors.toList());
		}catch(UserException e) {
			throw e;
		}catch(Exception e) {
			throw new UserException("User input is invalid.");
		}
	}
	
}
