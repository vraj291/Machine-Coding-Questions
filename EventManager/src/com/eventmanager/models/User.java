package com.eventmanager.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class User {

	private int id;
	private String name;
	private TimeSlot workTime;
	private List<Event> events = new ArrayList<>();
	
	private List<Event> getEventsbyDate(Date date){
		return events.stream().filter(event -> event.hasSameDate(date)).collect(Collectors.toList());
	}
	
	private boolean isAttendableEvent(Date date,TimeSlot timeSlot) {
		return getEventsbyDate(date).stream().allMatch(event -> event.hasNoConflict(timeSlot));
	}
	
	public boolean isWithinWorkingTime(TimeSlot timeSlot) {
		return workTime.isWithin(timeSlot);
	}
	
	public User(int id,String name,TimeSlot timeSlot) {
		this.id = id;
		this.name = name;
		this.workTime = timeSlot;
	}
	
	public boolean checkId(int id) {
		return this.id == id;
	}
	
	public boolean canAttend(Date date,TimeSlot timeSlot) {
		if(isWithinWorkingTime(timeSlot) && isAttendableEvent(date,timeSlot)) {
			return true;
		}
		return false;
	}
	
	public void addEvent(Event event) {
		events.add(event);
	}
	
	public List<Event> getEvents(Date startDate,double startTime,Date endDate,double endTime){
		return events.stream().filter(event -> event.occursBetween(startDate,startTime,endDate,endTime)).collect(Collectors.toList());
	}
	
	public List<TimeSlot> getAvailableTimeSlots(Date date){
		List<Event> events = getEventsbyDate(date);
		List<TimeSlot> result = new ArrayList<>();
		double startTime = workTime.getStartTime();
		double endTime = workTime.getEndTime();
		for(Event event : events) {
			double eventStartTime = event.getTimeSlot().getStartTime();
			double eventEndTime = event.getTimeSlot().getEndTime();
			if(startTime < eventStartTime) {
				result.add(new TimeSlot(startTime,eventStartTime));
			}
			startTime = eventEndTime;
		}
		if(endTime > startTime) {
			result.add(new TimeSlot(startTime,endTime));
		}
		return result;
	}
}
