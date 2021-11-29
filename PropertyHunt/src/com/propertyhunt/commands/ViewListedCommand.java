package com.propertyhunt.commands;

import java.util.List;

import com.propertyhunt.exceptions.CommandException;
import com.propertyhunt.models.Application;
import com.propertyhunt.models.Property;

public class ViewListedCommand extends BaseCommand{
	
	public static String commandName = "ViewListedProperties";
	
	protected Application app;
	
	public ViewListedCommand(final Application app) {
		this.app = app;
	}
	
	@Override
	public void execute(String text) {
		try {
			System.out.println("Listed Properties : ");
			List<Property> result = app.viewListedProperties();
			printResults(result);
		}catch(Exception e) {
			throw new CommandException("Invalid Command. " + e.getMessage());
		}
	}
	
}
