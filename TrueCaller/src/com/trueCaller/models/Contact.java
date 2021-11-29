package com.trueCaller.models;

public class Contact {
	
	private String phone;
	
	public Contact (String phone) {
		this.phone = phone;
	}
	
	public String getPhoneNumber() {
		return phone;
	}
	
	public boolean equals(Contact contact) {
		return this.phone == contact.getPhoneNumber();
	}
	
	public boolean contains(String phone) {
		return this.phone.contains(phone);
	}
}
