package com.propertyhunt.models;

import java.util.List;

import com.propertyhunt.enums.ROOM_SIZE;
import com.propertyhunt.exceptions.PropertyException;

public class Rooms {
	
	private ROOM_SIZE value;
	private String input;
	
	public Rooms(String input) {
		if(input.equalsIgnoreCase("1BHK")) {
			this.value = ROOM_SIZE._1BHK;
			this.input = input;
		}else if(input.equalsIgnoreCase("2BHK")) {
			this.value = ROOM_SIZE._2BHK;
			this.input = input;
		}else if(input.equalsIgnoreCase("3BHK")) {
			this.value = ROOM_SIZE._3BHK;
			this.input = input;
		}else {
			throw new PropertyException("Room size is invalid.");
		}
	}
	
	public String toString() {
		return input;
	}
	
	public ROOM_SIZE getSize() {
		return value;
	}
	
	public boolean check(List<String> rooms) {
		return rooms.stream().anyMatch(room -> room.equalsIgnoreCase(input));
	}

}
