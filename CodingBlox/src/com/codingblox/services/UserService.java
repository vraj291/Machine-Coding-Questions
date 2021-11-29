package com.codingblox.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.codingblox.enums.SORT_TYPE;
import com.codingblox.models.SortType;
import com.codingblox.models.User;

public class UserService {

	private List<User> repository = new ArrayList<User>();
		
	public UserService() {}
	
	public User getUser(int id) {
		return repository.stream().filter(user -> user.isValid(id)).findFirst().orElse(null);
	}
		
	public User createUser(String name,String gender,String phoneNo) {
		User newUser = new User(repository.size()+1,name,gender,phoneNo);
		repository.add(newUser);
		return newUser;
	}
	
	public List<User> getUsers(String sortType){
		SortType sort = new SortType(sortType);
		if(sort.getValue() == SORT_TYPE.ASC) {
			return repository.stream().sorted((user1,user2) -> Integer.compare(user1.getScore(), user2.getScore())).collect(Collectors.toList());
		}
		return repository.stream().sorted((user1,user2) -> Integer.compare(user2.getScore(), user1.getScore())).collect(Collectors.toList());
	}
	
}

