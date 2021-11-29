package com.propertyhunt.commands;

import java.util.HashMap;

import com.propertyhunt.exceptions.CommandException;
import com.propertyhunt.models.Application;
import com.propertyhunt.models.User;

public class RegisterUserCommand extends BaseCommand{
	
	public static String commandName = "RegisterUser";
	
	private String help = "\nCommand ==> RegisterUser <name> <gender> <phoneNo>\n"
			+ "Example ==> RegisterUser name Vraj, gender M, phoneNo 124";
	
	protected Application app;
	
	public RegisterUserCommand(final Application app) {
		this.app = app;
	}
	
	@Override
	public void execute(String text) {
		HashMap<String,String> inputs = getParameters(text);
		try {
			User createdUser = app.createUser(inputs.get("name"),inputs.get("gender"),inputs.get("phoneNo"));
			System.out.println("Registered User => " + createdUser);
		}catch(Exception e) {
			throw new CommandException("Invalid Command. " + e.getMessage() + help);
		}
	}
}
