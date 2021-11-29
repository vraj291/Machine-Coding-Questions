package com.codingblox.models;

import com.codingblox.enums.SORT_TYPE;

public class SortType {
	
	private SORT_TYPE value;
	
	public SortType(String input) {
		if(input.equalsIgnoreCase("ASC")) {
			value = SORT_TYPE.ASC;
		}else{
			value = SORT_TYPE.DESC;
		}
	}
	
	public SORT_TYPE getValue() {
		return this.value;
	}

}
