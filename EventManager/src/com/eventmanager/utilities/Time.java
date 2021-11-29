package com.eventmanager.utilities;

import com.eventmanager.exceptions.TimeException;

public class Time {

	public static double toDouble(String time) {
		try {
			boolean isAmTime;
			int endIndex;
			double hours,minutes;
			if(time.endsWith("AM")) {
				isAmTime = true;
				endIndex = time.indexOf("AM");
			}else if(time.endsWith("PM")) {
				isAmTime = false;
				endIndex = time.indexOf("PM");
			}else {
				throw new Exception();
			}
			int index = time.indexOf(":");
			if(index == -1) {
				hours = Double.parseDouble(time.substring(0, endIndex));
				minutes = 0;
			}else {
				hours = Double.parseDouble(time.substring(0, index));
				minutes = Double.parseDouble(time.substring(index+1, endIndex));
			}
			if(hours < 1 || hours > 12) {
				throw new Exception();
			}
			if(isAmTime && hours == 12) {
				hours = 0;
			}else if(!isAmTime && hours != 12){
				hours += 12;	
			}
			double calculatedTime = hours + (minutes/60);
			if(calculatedTime < 0 || calculatedTime > 24) {
				throw new Exception();
			}
			return calculatedTime;
		}catch(Exception e) {
			throw new TimeException("Time is not valid.");
		}
	}
	
	public static String format(int value) {
		if(value < 10) {
			return "0"+Integer.toString(value);
		}
		return Integer.toString(value);
	}
	
	public static String toString(double time) {
		int hours = (int)(time);
		int minutes = (int)((time - ((int)time)) * 60);
		if(hours < 12) {
			if(hours == 0) {
				return format(12)+":"+format(minutes)+"AM";
			}
			return format(hours)+":"+format(minutes)+"AM";
		}else{
			if(hours == 12) {
				return format(12)+":"+format(minutes)+"PM";
			}
			return format(hours-12)+":"+format(minutes)+"PM";
		}
	}
}
