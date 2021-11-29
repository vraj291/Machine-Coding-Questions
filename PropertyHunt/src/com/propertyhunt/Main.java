package com.propertyhunt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.propertyhunt.commands.CommandDriver;
import com.propertyhunt.exceptions.ModeException;
import com.propertyhunt.mode.ConsoleMode;
import com.propertyhunt.mode.FileMode;
import com.propertyhunt.mode.Mode;
import com.propertyhunt.models.Application;

//FileName = D:\eclipse\eclipse-workspace\PropertyHunt\src\com\propertyhunt\input.txt

public class Main {
	
	public static void main(String[] args) throws IOException{
		
		Application a = new Application();
		CommandDriver cmd = new CommandDriver(a);
		Mode mode;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Welcome to PropertyHunt.");
		
		while(true) {
			System.out.println("Choose Mode: File(1) or Console(2)");
			int choice = Integer.parseInt(reader.readLine());
			if(choice == 1) {
				System.out.println("Enter File Name: ");
				String fileName = reader.readLine();
				try {
					mode = new FileMode(fileName,cmd);
					break;
				}catch(ModeException e) {
					System.out.println(e.getMessage());
				}
			}else if(choice == 2) {
				mode = new ConsoleMode(cmd);
				break;
			}else {
				System.out.println("Wrong Choice.");
			}
		}
		
		System.out.println();
		
		try {
			mode.run();
		}catch(ModeException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
}
