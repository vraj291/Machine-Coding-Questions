package com.propertyhunt.commands;

import java.util.HashMap;
import java.util.List;

import com.propertyhunt.exceptions.CommandException;
import com.propertyhunt.models.Application;
import com.propertyhunt.models.Price;
import com.propertyhunt.models.Property;
import com.propertyhunt.models.Size;
import com.propertyhunt.models.SortType;

public class SearchPropertyCommand extends BaseCommand{
	
	public static String commandName = "SearchProperty";
	
	private String help = "\nCommand ==> SearchProperty <locations> <pricerange> <type> <sizerange> <rooms> <sort>\n"
			+ "Example ==> SearchProperty locations Bellandur/Akota, pricerange 90k-120k, type sell, sizerange 1800sqft-2000sqft, rooms 2BHK/3BHK, sort price";
	
	protected Application app;
	
	public SearchPropertyCommand(final Application app) {
		this.app = app;
	}

	@Override
	public void execute(String text) {
		HashMap<String,String> inputs = getParameters(text);
		List<String> values;
		try {
			
			values = processQuery(inputs.get("pricerange"),"-");
			Price minPrice = new Price(values.isEmpty()? "0" : values.get(0));
			Price maxPrice = new Price(values.size() == 2? values.get(1) : String.valueOf(Integer.MAX_VALUE));
			
			values = processQuery(inputs.get("sizerange"),"-");
			Size minSize = new Size(values.isEmpty()? "0sqmt" : values.get(0));
			Size maxSize = new Size(values.size() == 2? values.get(1) : String.valueOf(Integer.MAX_VALUE) + "sqmt"); 
			
			SortType sortType = new SortType(inputs.get("sort"));
			
			System.out.println("Search Results : ");
			List<Property> result = app.searchProperty(processQuery(inputs.get("location"),"/"),minPrice,maxPrice,inputs.get("type"),minSize,maxSize,processQuery(inputs.get("rooms"),"/"),sortType);
			printResults(result);
			
		}catch(Exception e) {
			throw new CommandException("Invalid Command. " + e.getMessage() + help);
		}
	}

}
