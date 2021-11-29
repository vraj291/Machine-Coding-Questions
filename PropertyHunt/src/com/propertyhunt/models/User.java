package com.propertyhunt.models;

import java.util.ArrayList;
import java.util.List;

import com.propertyhunt.exceptions.PropertyException;

public class User {
	
	private int id;
	private String name;
	private String gender;
	private String phoneNo;
	private List<Property> listedProperties = new ArrayList<Property>();
	private List<Property> shortlistedProperties = new ArrayList<Property>();
	
	public User(int id,String name,String gender,String phoneNo) {
		this.id = id;
		if(name == null) {
			throw new PropertyException("Name is not specified.");
		}
		this.name = name;
		if(gender == null) {
			throw new PropertyException("Gender is not specified.");
		}
		this.gender = gender;
		if(phoneNo == null) {
			throw new PropertyException("Phone Number is not specified.");
		}
		this.phoneNo = phoneNo;
	}
	
	public boolean isValid(int id) {
		return this.id == id;
	}
	
	public int getId() {
		return id;
	}
	
	public void shortlistProperty(Property property) {
		shortlistedProperties.add(property);
	}
	
	public List<Property> getShortlistedProperties(){
		return shortlistedProperties;
	}
	
	public void listProperty(Property property) {
		listedProperties.add(property);
	}
	
	public List<Property> getListedProperties(){
		return listedProperties;
	}
	
	public boolean hasListedProperty(int propertyId) {
		return listedProperties.stream().anyMatch(property -> property.checkId(propertyId));
	}
	
	public String toString() {
		return "Id: " + id + ", Name: " + name + ", Gender: " + gender + ", PhoneNo: " + phoneNo;
	}
	
}
