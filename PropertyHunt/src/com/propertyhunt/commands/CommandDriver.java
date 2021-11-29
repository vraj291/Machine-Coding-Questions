package com.propertyhunt.commands;

import java.util.HashMap;

import com.propertyhunt.exceptions.CommandException;
import com.propertyhunt.models.Application;

public class CommandDriver {
	
	HashMap<String,BaseCommand> commandExecutors = new HashMap<>();
	
	public CommandDriver(Application app) {
		commandExecutors.put(CreatePropertyCommand.commandName, new CreatePropertyCommand(app));
		commandExecutors.put(SearchPropertyCommand.commandName, new SearchPropertyCommand(app));
		commandExecutors.put(ShortlistPropertyCommand.commandName, new ShortlistPropertyCommand(app));
		commandExecutors.put(ViewShortlistedPropertyCommand.commandName, new ViewShortlistedPropertyCommand(app));
		commandExecutors.put(ViewListedCommand.commandName, new ViewListedCommand(app));
		commandExecutors.put(SellPropertyCommand.commandName, new SellPropertyCommand(app));
		commandExecutors.put(RegisterUserCommand.commandName, new RegisterUserCommand(app));
		commandExecutors.put(LoginUserCommand.commandName, new LoginUserCommand(app));
	}
	
	public void execute(String text) {
		text = text.trim();
		String commandName = text;
		int index = text.indexOf(" ");
		try {
			if(index == -1) {
				commandExecutors.get(commandName).execute(null);
			}else {
				commandName = text.substring(0,index);
				commandExecutors.get(commandName).execute(text.substring(index+1));
			}
		}catch(NullPointerException e) {
			throw new CommandException("Invalid Command Name.");
		}
	}
	
}
