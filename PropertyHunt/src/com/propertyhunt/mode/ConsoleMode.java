package com.propertyhunt.mode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.propertyhunt.commands.CommandDriver;
import com.propertyhunt.exceptions.CommandException;
import com.propertyhunt.exceptions.ModeException;

//Available Commands ==> 
//RegisterUser name Vraj, gender M, phoneNo 124
//RegisterUser name qwerty, gender M, phoneNo 1254
//LoginUser 1
//ListProperty title A 2BHK for Sale, location Bellandur, price 40k, type sell, size 1800sqft, rooms 2BHK, nearby marathahalli/akota
//ListProperty title A 3BHK for Sale, location Akota, price 90L, type rent, size 1300sqft, rooms 3BHK, nearby marathahalli/akota
//SearchProperty location marathahalli
//ShortlistProperty 1
//ViewShortlistedProperties
//ViewListedProperties
//SellProperty property 1, buyer 2, price 80k
//ViewListedProperties

public class ConsoleMode extends Mode{
	
	public ConsoleMode(CommandDriver cmd) {
		super(cmd);
	}
	
	public void run() {
		 BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		 try {
			String input = "";
			while(true) {
				input = reader.readLine();
				if(input.equalsIgnoreCase("exit")) {
					System.out.println("Program Exited.");
					break;
				}
				try {
					cmd.execute(input);
				}catch(CommandException e) {
					System.out.println(e.getMessage());
				}
			}
			reader.close();
		} catch (IOException e) {
			throw new ModeException("An I/O Error occured.");
		}
	}
	
}
