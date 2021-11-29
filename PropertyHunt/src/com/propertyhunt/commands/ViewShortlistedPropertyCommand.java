package com.propertyhunt.commands;

import java.util.List;

import com.propertyhunt.exceptions.CommandException;
import com.propertyhunt.models.Application;
import com.propertyhunt.models.Property;

public class ViewShortlistedPropertyCommand extends BaseCommand{

	public static String commandName = "ViewShortlistedProperties";
	
	protected Application app;
	
	public ViewShortlistedPropertyCommand(final Application app) {
		this.app = app;
	}
	
	@Override
	public void execute(String text) {
		try {
			System.out.println("Shortlisted Properties : ");
			List<Property> result = app.viewShortlistedProperties();
			printResults(result);
		}catch(Exception e) {
			e.printStackTrace();
			throw new CommandException("Invalid Command. " + e.getMessage());
		}
	}
		
}
