package com.eventmanager.models;

import java.util.Date;

public class Event {

	private int id;
	private String name;
	private Date date;
	private TimeSlot timeSlot;
	private int representations;
	
	public Event(int id,String name,Date date,TimeSlot timeSlot,int representations) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.timeSlot = timeSlot;
		this.representations = representations;
		System.out.println(name);
	}
	
	public boolean checkId(int id) {
		return this.id == id;
	}
	
	public TimeSlot getTimeSlot() {
		return timeSlot;
	}
	
	public Date getDate() {
		return date;
	}
	
	public int getRepresentations() {
		return representations;
	}
	
	public boolean hasSameDate(Date date) {
		return this.date.equals(date);
	}
	
	public boolean occursBetween(Date startDate,double startTime,Date endDate,double endTime) {
		return ((this.date.equals(startDate) && this.timeSlot.getStartTime() >= startTime) || (this.date.equals(endDate) && this.timeSlot.getEndTime() <= endTime) || (this.date.after(startDate) && this.date.before(endDate)));
	}
	
	public boolean hasNoConflict(TimeSlot timeSlot) {
		return this.timeSlot.hasNoOverlap(timeSlot);
	}
	
	public String toString() {
		return name;
	}
}
