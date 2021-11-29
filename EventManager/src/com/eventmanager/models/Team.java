package com.eventmanager.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Team {

	private int id;
	private String name;
	private List<User> users;
	private List<Event> events = new ArrayList<>();
	
	public Team(int id,String name, List<User> users) {
		this.id = id;
		this.name = name;
		this.users = users;
	}
	
	public boolean checkId(int id) {
		return this.id == id;
	}
	
	public boolean canAttend(Date date,TimeSlot timeSlot,int representations) {
		List<User> attendingUsers = users.stream().filter(user -> user.canAttend(date,timeSlot)).collect(Collectors.toList());
		if(attendingUsers.size() < representations) {
			return false;
		}
		return true;
	}
	
	public void addEvent(Event event) {
		users.stream().filter(user -> user.canAttend(event.getDate(),event.getTimeSlot()))
		.limit(event.getRepresentations()).forEach(user -> {
			user.addEvent(event);
		});
		events.add(event);
	}
}
