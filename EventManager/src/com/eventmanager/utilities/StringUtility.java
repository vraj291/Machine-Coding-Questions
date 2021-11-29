package com.eventmanager.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.eventmanager.exceptions.DateException;

public class StringUtility {

	private static List<String> processQuery(String text,String delimiter) {
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
	
	public static List<Integer> getIds(String ids){
		List<String> result = processQuery(ids,",");
		return result.stream().map(value -> Integer.parseInt(value)).collect(Collectors.toList());
	}
	
	public static Date toDate(String date) {
		try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(date);
		} catch (ParseException e) {
			throw new DateException("Invalid Date.");
		}
	}
	
}
