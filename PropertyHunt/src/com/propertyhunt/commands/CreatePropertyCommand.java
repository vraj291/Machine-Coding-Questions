package com.propertyhunt.commands;

import java.util.HashMap;

import com.propertyhunt.exceptions.CommandException;
import com.propertyhunt.models.Application;

public class CreatePropertyCommand extends BaseCommand{
	
	public static String commandName = "ListProperty";
	
	private String help = "\nCommand ==> ListProperty <title> <location> <price> <type> <size> <rooms> <nearby locations>\n"
			+ "Example ==> ListProperty title A 2BHK for Sale, location Bellandur, price 90k, type sell, size 1800sqft, rooms 2BHK, nearby marathahalli/akota";
	
	protected Application app;
	
	public CreatePropertyCommand(final Application app) {
		this.app = app;
	}

	@Override
	public void execute(String text) {
		HashMap<String,String> inputs = getParameters(text);
		try {
			if(inputs.get("nearby") == null) {
				inputs.put("nearby","");
			}
			int createdId = app.addProperty(inputs.get("title"),inputs.get("location"),inputs.get("price"),inputs.get("type"),inputs.get("size"),inputs.get("rooms"),processQuery(inputs.get("nearby"),"/"));
			System.out.println("Listed Property with Id = " + createdId);
		}catch(Exception e) {
			throw new CommandException("Invalid Command. " + e.getMessage() + help);
		}
	}
		
}
