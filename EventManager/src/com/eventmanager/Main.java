package com.eventmanager;

import com.eventmanager.services.EventManagerService;
import com.eventmanager.utilities.Time;

public class Main {

	public static void main (String[] args) {
		
		EventManagerService eventManagerService = new EventManagerService();
		
		eventManagerService.createUser("A", "10AM", "7PM");
		eventManagerService.createUser("B", "9:30AM", "5:30PM");
		eventManagerService.createUser("C", "11:30AM", "6:30PM");
		eventManagerService.createUser("D", "10AM", "6PM");
		eventManagerService.createUser("E", "11AM", "7:30PM");
		eventManagerService.createUser("F", "11AM", "6:30PM");
		
		eventManagerService.createTeam("T1", "3,5");
		eventManagerService.createTeam("T2", "2,4,6");
		
		eventManagerService.createEvent("Event1", "2/1/2012", "2PM", "3PM", 2, "1", "1");
		eventManagerService.createEvent("Event2", "2/1/2012", "2PM", "3PM", 2, "3", null);
		eventManagerService.createEvent("Event3", "1/1/2012", "3PM", "4PM", 2, null, "1,2");
		eventManagerService.createEvent("Event4", "1/1/2012", "3PM", "4PM", 1, "1", "2");
		eventManagerService.createEvent("Event5", "1/1/2012", "10AM", "11AM", 2, "1,6", null);
		
		System.out.println(eventManagerService.getUserEvents(1,"1/1/2012","10AM","2/1/2012","5PM"));
		System.out.println(eventManagerService.getUserEvents(2,"1/1/2012","10AM","2/1/2012","5PM"));
		
	}
	
}
