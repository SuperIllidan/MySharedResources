package com.hidy.hdoa6.weeklyDiet.beans;

import java.util.Map;

public class Breakfast {
	private Map<String,Map<String,String>> dishesMap;
	public Breakfast() {
		super();
	}
	public Map<String, Map<String,String>> getDishesMap() {
		return dishesMap;
	}
	public void setDishesMap(Map<String, Map<String,String>> dishesMap) {
		this.dishesMap = dishesMap;
	}
	
}
