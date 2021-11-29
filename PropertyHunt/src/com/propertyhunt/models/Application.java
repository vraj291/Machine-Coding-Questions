package com.propertyhunt.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.propertyhunt.enums.SORT_TYPE;
import com.propertyhunt.exceptions.PropertyException;

public class Application {
	
	private List<User> users = new ArrayList<User>();
	
	private List<Property> properties = new ArrayList<Property>();
	
	private User currentUser = null;
	
	public Application() {}
	
	private boolean isLoggedIn() {
		return currentUser != null;
	}
	
	public User getUser(int id) {
		return users.stream().filter(user -> user.isValid(id)).findFirst().orElse(null);
	}
	
	public Property getProperty(int id) {
		return properties.stream().filter(property -> property.checkId(id)).findFirst().orElse(null);
	}
	
	public User createUser(String name,String gender,String phoneNo) {
		User u1 = new User(users.size()+1,name,gender,phoneNo);
		users.add(u1);
		return u1;
	}
	
	public boolean loginUser(int id){
		User user = getUser(id);
		if(user != null) {
			currentUser = user;
			return true;
		}
		return false;
	}
	
	public int addProperty(String title, String location, String price,String listingType,String size,String rooms,List<String> nearby) {
		if(!isLoggedIn()) {
			throw new PropertyException("User is not logged in.");
		}
		Property p1 = new Property(properties.size()+1,title,location,price,listingType,size,rooms,nearby);
		properties.add(p1);
		currentUser.listProperty(p1);
		return p1.getId();
	}
	
	public List<Property> searchProperty(List<String> locations,Price minPrice, Price maxPrice,String type,Size minSize,Size maxSize,List<String> rooms,SortType sortType) {
		Stream<Property> result = properties.stream().filter(property -> {
			return property.isValid(locations,minPrice,maxPrice,type,minSize,maxSize,rooms) && currentUser.hasListedProperty(property.getId());
		});
		if(sortType.getValue().equals(SORT_TYPE.PRICE)) {
			result = result.sorted((property1,property2) -> 
				Integer.compare(property1.getPrice(), property2.getPrice())
			);
		}else if(sortType.getValue().equals(SORT_TYPE.SIZE)) {
			result = result.sorted((property1,property2) -> 
				Double.compare(property1.getSize(), property2.getSize())
			);
		}else{
			result = result.sorted((property1,property2) -> 
				property1.getTitle().compareToIgnoreCase(property2.getTitle())
			);
		}
		return result.collect(Collectors.toList());
	}
	
	public void shortlistProperty(int id) {
		if(!isLoggedIn()) {
			throw new PropertyException("User is not logged in.");
		}
		Property p1 = getProperty(id);
		if(p1 == null) {
			throw new PropertyException("Property does not exist.");
		}
		currentUser.shortlistProperty(p1);
	}

	public List<Property> viewShortlistedProperties() {
		if(!isLoggedIn()) {
			throw new PropertyException("User is not logged in.");
		}
		return currentUser.getShortlistedProperties();
	}
	
	public List<Property> viewListedProperties() {
		if(!isLoggedIn()) {
			throw new PropertyException("User is not logged in.");
		}
		return currentUser.getListedProperties();
	}
	
	public void markAsSold(int propertyId,int userId,Price price) {
		if(!isLoggedIn()) {
			throw new PropertyException("User is not logged in.");
		}
		Property p1 = getProperty(propertyId);
		if(p1 == null) {
			throw new PropertyException("Property does not exist.");
		}
		if(!currentUser.hasListedProperty(propertyId)) {
			throw new PropertyException("Property is not owned by current owner.");
		}
		User u1 = getUser(userId);
		if(u1 == null){
			throw new PropertyException("Buyer does not exist.");
		}
		p1.markAsSold(u1, price);
	}
}
