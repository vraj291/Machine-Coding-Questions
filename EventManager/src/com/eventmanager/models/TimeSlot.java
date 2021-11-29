package com.eventmanager.models;

import com.eventmanager.exceptions.TimeException;
import com.eventmanager.utilities.Time;

public class TimeSlot {
	
	private double startTime;
	private double endTime;

	public TimeSlot(String startTime,String endTime) {
		this.startTime = Time.toDouble(startTime);
		this.endTime = Time.toDouble(endTime);
		if(this.endTime <= this.startTime) {
			throw new TimeException("Invalid start and end times.");
		}
	}
	
	public TimeSlot(double startTime,double endTime) {
		if(endTime <= startTime) {
			throw new TimeException("Invalid start and end times.");
		}
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public double getStartTime() {
		return startTime;
	}
	
	public double getEndTime() {
		return endTime;
	}
	
	public boolean isWithin(TimeSlot timeSlot) {
		return (this.startTime <= timeSlot.getStartTime() && this.endTime >= timeSlot.getEndTime());
	}
	
	public boolean hasNoOverlap(TimeSlot timeSlot) {
		return (this.startTime >= timeSlot.getEndTime() || this.endTime <= timeSlot.getStartTime());
	}
	
	public String toString() {
		return Time.toString(startTime) + " : " + Time.toString(endTime);
	}
}
