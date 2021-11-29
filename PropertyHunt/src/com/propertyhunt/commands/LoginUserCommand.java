package com.propertyhunt.commands;

import com.propertyhunt.exceptions.CommandException;
import com.propertyhunt.models.Application;

public class LoginUserCommand extends BaseCommand{
	
	public static String commandName = "LoginUser";
	
	private String help = "\nCommand ==> LoginUser <user>\n"
			+ "Example ==> LoginUser 1";
	
	protected Application app;
	
	public LoginUserCommand(final Application app) {
		this.app = app;
	}
	
	@Override
	public void execute(String text) {
		try {
			boolean success = app.loginUser(Integer.parseInt(text));
			if(success) {
				System.out.println("Successfully Logged in as User with Id = " + text + ".");
			}else {
				System.out.println("Log in failed. No such User with Id = " + text + " exists.");
			}	
		}catch(Exception e) {
			throw new CommandException("Invalid Command. " + e.getMessage() + help);
		}
	}
}
