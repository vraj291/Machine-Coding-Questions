package com.propertyhunt.models;

import com.propertyhunt.exceptions.PropertyException;

public class Size {

	public double value;
	private String input;
	
	public Size(String value) {
		try {
			input = value;
			if(value.endsWith("sqft")) {
				this.value = Double.parseDouble(value.substring(0, value.length()-4))/10.764;
			}else if(value.endsWith("sqyd")) {
				this.value = Double.parseDouble(value.substring(0, value.length()-4))/1.196;
			}else{
				this.value = Double.parseDouble(value.substring(0, value.length()-4));
			}
		}catch(Exception c) {
			throw new PropertyException("Size is invalid.");
		}
	}
	
	public String toString() {
		return input;
	}
	
	public double getValue() {
		return value;
	}
	
}
