package com.propertyhunt.models;

import java.util.ArrayList;
import java.util.List;

import com.propertyhunt.enums.LISTING_TYPE;
import com.propertyhunt.enums.ROOM_SIZE;
import com.propertyhunt.exceptions.PropertyException;

public class Property {
	
	private int id;
	
	private String title;
	 
	private String location;
	
	private Price price;

	private ListingType listingType;

	private Size size;
	
	private Rooms rooms;
	
	private List<String> nearbyLocations = new ArrayList<String>();
	
	private boolean available = true;
	
	private User buyer;
	
	private Price sellingPrice;
	
	public Property(int id,String title, String location, String price,String listingType,String size,String rooms, List<String> nearbyLocations) {
		this.id = id;
		this.title = title;
		this.location = location;
		this.price = new Price(price);
		this.listingType = new ListingType(listingType);
		this.size = new Size(size);
		this.rooms = new Rooms(rooms);
		this.nearbyLocations = nearbyLocations;
	}
	
	public int getId() {
		return id;
	}
	
	public int getPrice() {
		return price.getValue();
	}
	
	public double getSize() {
		return size.getValue();
	}
	
	public String getTitle() {
		return title;
	}
	
	public boolean checkId(int id) {
		return this.id == id;
	}
	
	private boolean checkLocations(List<String> locations) {
		if(locations.size() == 0) return true;
		return locations.stream().anyMatch(location -> location.equalsIgnoreCase(this.location)) || locations.stream().anyMatch(location -> this.nearbyLocations.stream().anyMatch(nearbyLocation -> location.equalsIgnoreCase(nearbyLocation)));
	}
	
	private boolean checkPrice(Price min,Price max) {
		return (price.getValue() >= min.getValue() && price.getValue() <= max.getValue());
	}
	
	private boolean checkSize(Size min,Size max) {
		return (size.getValue() >= min.getValue() && size.getValue() <= max.getValue());
	}
	
	private boolean checkListingType(String type) {
		if(type == null) return true;
		return listingType.check(type);
	}
	
	private boolean checkRooms(List<String> rooms) {
		if(rooms.size() == 0) return true;
		return this.rooms.check(rooms);
	}
	
	public boolean isAvailable() {
		return available;
	}
	
	public boolean isValid(List<String> locations,Price minPrice, Price maxPrice,String type,Size minSize, Size maxSize,List<String> rooms) {
		return (isAvailable() && checkLocations(locations) && checkPrice(minPrice, maxPrice) && checkListingType(type) && checkSize(minSize,maxSize) && checkRooms(rooms));
	}
	
	public void markAsSold(User user,Price price) {
		available = false;
		buyer = user;
		sellingPrice = price;
	}
	
	public String toString() {
		return String.format("%2s %25s %15s %10s %15s %5s %5s %10s",String.valueOf(id),title,location,price,size,rooms,listingType,(available? "Available" : "Sold"));
	}
}
