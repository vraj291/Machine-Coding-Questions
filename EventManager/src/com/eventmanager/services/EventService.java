package com.eventmanager.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.eventmanager.models.Event;
import com.eventmanager.models.TimeSlot;

public class EventService {
	
	private List<Event> repository = new ArrayList<>();
	
	private Event getEvent(int id) {
		return repository.stream().filter(event -> event.checkId(id)).findFirst().orElse(null);
	}
	
	public EventService() {}
	
	public Event addEvent(String name,Date date,TimeSlot timeSlot,int representations) {
		Event newEvent = new Event(repository.size()+1,name,date,timeSlot,representations);
		repository.add(newEvent);
		return newEvent;
	}
	
//	public List<Event> getEvents(String teamMembers){
//		try {
//			List<Integer> ids = StringUtility.getIds(teamMembers);
//			return ids.stream().map(id -> {
//				Event event = getEvent(id);
//				if(event == null) {
//					throw new EventException("Event with Id = " + id + " doesnt exist.");
//				}
//				return event;
//			}).collect(Collectors.toList());
//		}catch(EventException e) {
//			throw e;
//		}catch(Exception e) {
//			throw new UserException("Event input is invalid.");
//		}
//	}

}
