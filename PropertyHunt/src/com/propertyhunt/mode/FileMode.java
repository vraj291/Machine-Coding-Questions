package com.propertyhunt.mode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.propertyhunt.commands.CommandDriver;
import com.propertyhunt.exceptions.CommandException;
import com.propertyhunt.exceptions.ModeException;

public class FileMode extends Mode{
	
	private FileReader fr;
	
	public FileMode(String filename, CommandDriver cmd) {
		super(cmd);
		try {
			File file=new File(filename);
			fr=new FileReader(file);
		}catch(FileNotFoundException e) {
			throw new ModeException("File can not be found.");
		}
	}

	public void run() {
		try {
			BufferedReader br=new BufferedReader(fr);   
			String line;  
			while((line=br.readLine())!=null)  
			{  
				try {
					cmd.execute(line);
				}catch(CommandException e) {
					System.out.println(e.getMessage());
				}
			} 
			br.close();
			fr.close();
			System.out.println("Program Exited.");
		}catch(FileNotFoundException e) {
			throw new ModeException("File can not be found.");
		}catch(IOException e) {
			throw new ModeException("An I/O Error occurred.");
		}
	}
	
}
