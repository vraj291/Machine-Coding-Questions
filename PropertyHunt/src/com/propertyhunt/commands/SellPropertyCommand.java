package com.propertyhunt.commands;

import java.util.HashMap;
import java.util.List;

import com.propertyhunt.exceptions.CommandException;
import com.propertyhunt.exceptions.PropertyException;
import com.propertyhunt.models.Application;
import com.propertyhunt.models.Price;
import com.propertyhunt.models.Property;

public class SellPropertyCommand extends BaseCommand{

	public static String commandName = "SellProperty";
	
	private String help = "\nCommand ==> SellProperty <propertyId> <buyerId> <price>\n"
			+ "Example ==> SellProperty property 1, buyer 2, price 80k";
	
	protected Application app;
	
	public SellPropertyCommand(final Application app) {
		this.app = app;
	}
	
	@Override
	public void execute(String text) {
		try {
			HashMap<String,String> inputs = getParameters(text);
			Price price = new Price(inputs.get("price"));
			app.markAsSold(Integer.parseInt(inputs.get("property")),  Integer.parseInt(inputs.get("buyer")), price);
			System.out.println("Property with Id = "+inputs.get("property")+ " Sold to Buyer = " + inputs.get("buyer") + " at Price = " + price);
		}catch(PropertyException e) {
			throw new CommandException("Invalid Command. " + e.getMessage() + help);
		}catch(Exception e) {
			throw new CommandException("Invalid Command." + help);
		}
	}
	
}
