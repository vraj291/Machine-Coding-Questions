package com.propertyhunt.commands;

import com.propertyhunt.exceptions.CommandException;
import com.propertyhunt.models.Application;

public class ShortlistPropertyCommand extends BaseCommand{

	public static String commandName = "ShortlistProperty";
	
	private String help = "\nCommand ==> ShortlistProperty <propertyId>\n"
			+ "Example ==> ShortlistProperty 1";
	
	protected Application app;
	
	public ShortlistPropertyCommand(final Application app) {
		this.app = app;
	}
	
	@Override
	public void execute(String text) {
		try {
			app.shortlistProperty(Integer.parseInt(text));
			System.out.println("Property with Id = "+text+ " Shortlisted.");
		}catch(Exception e) {
			throw new CommandException("Invalid Command. " + e.getMessage() + help);
		}
	}

}
