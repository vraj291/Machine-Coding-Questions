package com.eventmanager.services;

import java.util.Date;
import java.util.List;

import com.eventmanager.exceptions.EventException;
import com.eventmanager.exceptions.UserException;
import com.eventmanager.models.Event;
import com.eventmanager.models.Team;
import com.eventmanager.models.TimeSlot;
import com.eventmanager.models.User;
import com.eventmanager.utilities.StringUtility;
import com.eventmanager.utilities.Time;

public class EventManagerService {
	
	private UserService userService = new UserService();
	private TeamService teamService = new TeamService();
	private EventService eventService = new EventService();
	
	public EventManagerService() {}
	
	public void createUser(String name,String startTime,String endTime) {
		userService.addUser(name, startTime, endTime);
	}
	
	public void createTeam(String name,String teamMembers) {
		List<User> users = userService.getUsers(teamMembers);
		teamService.addTeam(name, users);
	}
	
	public void createEvent(String name,String date,String startTime,String endTime,int representations,String users,String teams) {
		List<User> eventUsers = userService.getUsers(users);
		List<Team> eventTeams = teamService.getTeams(teams);
		TimeSlot timeSlot = new TimeSlot(startTime,endTime);
		Date eventDate = StringUtility.toDate(date);
		if(eventUsers.stream().allMatch(user -> user.canAttend(eventDate,timeSlot)) && eventTeams.stream().allMatch(team -> team.canAttend(eventDate,timeSlot, representations))) {
			Event event = eventService.addEvent(name,eventDate,timeSlot,representations);
			eventUsers.stream().forEach(user -> {
				user.addEvent(event);
			});
			eventTeams.stream().forEach(team -> {
				team.addEvent(event);
			});
		}
	}
	
	public List<Event> getUserEvents(int id,String startDate,String startTime,String endDate,String endTime){
		double eventStartTime = Time.toDouble(startTime);
		double eventEndTime = Time.toDouble(endTime);
		Date eventStartDate = StringUtility.toDate(startDate);
		Date eventEndDate = StringUtility.toDate(endDate);
		if(eventEndDate.before(eventStartDate)) {
			throw new EventException("Invalid start and end dates.");
		}else if(eventEndDate.equals(eventStartDate)){
			if(eventEndTime <= eventStartTime) {
				throw new EventException("Invalid start and end times.");
			}
		}
		User user = userService.getUser(id);
		if(user == null) {
			throw new UserException("User with Id = " + id + " doesnt exist.");
		}
		return user.getEvents(eventStartDate,eventStartTime,eventEndDate,eventEndTime);
	}

	public void getAvailableTimeSlots(String users,String teams,String startDate,String endDate,int representations) {
		List<User> eventUsers = userService.getUsers(users);
		List<Team> eventTeams = teamService.getTeams(teams);
		Date eventStartDate = StringUtility.toDate(startDate);
		Date eventEndDate = StringUtility.toDate(endDate);
		if(eventEndDate.before(eventStartDate)) {
			throw new EventException("Invalid start and end dates.");
		}
	}
}
