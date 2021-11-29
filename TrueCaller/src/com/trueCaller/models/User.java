package com.trueCaller.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
	
	private String name;
	private String password;
	private Contact contact;
	private Map<Contact,User> contacts;
	private Map<Contact,User> blockedContacts;

	public User(String name,Contact contact) {
		this.name = name;
		this.contact = contact;
		this.contacts = new HashMap<>();
		this.blockedContacts = new HashMap<>();
	}
	
	public Contact getContact() {
		return contact;
	}
	
	public boolean verifyName(String name) {
		return this.name.contains(name);
	}
	
	public boolean verifyPassword(String password) {
		return this.password == password;
	}
	
	public void addContacts(List<User> users) {
		for(User user: users) {
			if(!contacts.containsKey(user.getContact())) {
				contacts.put(user.getContact(), user);
			}
		}
	}
	
	public void blockContact(Contact contact) {
		if(contacts.containsKey(contact) && !blockedContacts.containsKey(contact)) {
			blockedContacts.put(contact,contacts.get(contact));
			contacts.remove(contact);
		}
	}
	
	public void unblockContact(Contact contact) {
		if(!contacts.containsKey(contact) && blockedContacts.containsKey(contact)) {
			contacts.put(contact,blockedContacts.get(contact));
			blockedContacts.remove(contact);
		}
	}
	
	public int getContactsCount() {
		return contacts.size();
	}
	
	public int getBlockedContactsCount() {
		return blockedContacts.size();
	}
	
	public List<User> getContactsbyName(String name){
		List<User> result = new ArrayList<>();
		contacts.forEach((key,value) -> {
			if(value.verifyName(name)) {
				result.add(value);
			}
		});
		return result;
	}
	
	public List<User> getContactsbyPhone(String name){
		List<User> result = new ArrayList<>();
		contacts.forEach((key,value) -> {
			if(value.verifyName(name)) {
				result.add(value);
			}
		});
		return result;
	}
}
