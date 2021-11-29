package com.propertyhunt.models;

import com.propertyhunt.enums.LISTING_TYPE;
import com.propertyhunt.exceptions.PropertyException;

public class ListingType {
	
	private LISTING_TYPE value;
	private String input;
	
	public ListingType(String input) {
		if(input.equalsIgnoreCase("sell")) {
			this.input = input;
			value = LISTING_TYPE.SELL;
		}else if(input.equalsIgnoreCase("rent")) {
			this.input = input;
			value = LISTING_TYPE.RENT;
		}else {
			throw new PropertyException("Listing Type is invalid.");
		}
	}
	
	public LISTING_TYPE getType() {
		return value;
	}
	
	public String toString() {
		return input;
	}
	
	public boolean check(String type) {
		return input.equalsIgnoreCase(type);
	}

}
