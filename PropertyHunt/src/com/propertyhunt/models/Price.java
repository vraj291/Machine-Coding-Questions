package com.propertyhunt.models;

import com.propertyhunt.exceptions.PropertyException;

public class Price {
	
	private int value;
	private String input;
	
	public Price(String value) {
		try {
			input = value;
			if(value.endsWith("k")) {
				this.value = Integer.parseInt(value.substring(0, value.length()-1))*1000;
			}else if(value.endsWith("L")) {
				this.value = Integer.parseInt(value.substring(0, value.length()-1))*100000;
			}else if(value.endsWith("Cr")) {
				this.value = Integer.parseInt(value.substring(0, value.length()-2))*10000000;
			}else {
				this.value = Integer.parseInt(value);
			}
		}catch(Exception e) {
			throw new PropertyException("Price is invalid.");
		}
	}
	
	public String toString() {
		return input;
	}
	
	public int getValue() {
		return value;
	}
	
}
