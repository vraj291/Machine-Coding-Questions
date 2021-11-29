package com.propertyhunt.models;

import com.propertyhunt.enums.SORT_TYPE;
import com.propertyhunt.exceptions.CommandException;

public class SortType {

	private SORT_TYPE value;
	
	public SortType(String input) {
		if(input == null) {
			value = SORT_TYPE.TITLE;
		}else if(input.equalsIgnoreCase("price")) {
			value = SORT_TYPE.PRICE;
		}else if(input.equalsIgnoreCase("size")) {
			value = SORT_TYPE.SIZE;
		}else {
			throw new CommandException("Sort Type is invalid.");
		}
	}
	
	public SORT_TYPE getValue() {
		return value;
	}
}
