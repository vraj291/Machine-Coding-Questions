package com.propertyhunt.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.propertyhunt.models.Property;

public abstract class BaseCommand {
	
	protected HashMap<String,String> inputs = new HashMap<>();
	
	protected List<String> processQuery(String text,String delimiter) {
		List<String> result = new ArrayList<String>();
		if(text == null) return result;
		int start = 0;
		int end = text.indexOf(delimiter);
		while(end != -1) {
			result.add(text.substring(start, end));
			start = end + delimiter.length();
			end = text.indexOf(delimiter,start);
		}
		result.add(text.substring(start));
		return result;
	}
	
	protected HashMap<String,String> getParameters(String command){
		
		HashMap<String,String> inputs = new HashMap<>();		
		List<String> parameters = processQuery(command,", ");
		
		parameters.forEach(parameter -> {
			int index = parameter.indexOf(" ");
			String key = parameter.substring(0,index);
			String value = parameter.substring(index+1);
			inputs.put(key, value);
		});
		
		return inputs;
	}
	
	protected void printResults(List<Property> result) {
		if(result.size() == 0) {
			System.out.println("No Properties Found.");
		}else {
			System.out.println(String.format("%2s %25s %15s %10s %15s %5s %5s %10s","Id","Title","Location","Price","Size","Rooms","Type","Status"));
			result.forEach(property -> {
				System.out.println(property);
			});
		}
	}
	
	public abstract void execute(String text);
}
