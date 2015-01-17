package com.chickenstock;

import java.util.ArrayList;

public class Company {

	String title;
	ArrayList<Double> dailyAverage;
	public Company(String title){
		this.title = title;
		dailyAverage = new ArrayList<Double>();
	}
	
	public void addDailyAverage(double avg){
		dailyAverage.add(avg);
	}
}
