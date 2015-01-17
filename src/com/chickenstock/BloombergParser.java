package com.chickenstock;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;

public class BloombergParser {
	
	public static double average(double valueOne, double valueTwo){
		return (valueOne + valueTwo) / 2;
	}
	public static void main(String[] args) {
		
		String f = "";
		double closingPrice = 0;
		double openingPrice = 0;
		Company currentCompany = null;
		boolean readFirstLine = false;
		File file = new File("bloomberg\\master.txt");
		LinkedList<Company> companies = new LinkedList<Company>();
		try {
			f = new String(Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			System.out.println("File not found!");
			e.printStackTrace();
		}
		String[] lines = f.split(",");
		for(String s: lines){
			if(s.contains("securityData")){
				if(readFirstLine){
					currentCompany = new Company(s.substring(29).split(" ")[0]); 
					companies.add(currentCompany);
					System.out.println(s.substring(29).split(" ")[0]);
				}
				else {
					readFirstLine = true;
					currentCompany = new Company(s.substring(38).split(" ")[0]);
					companies.add(currentCompany);
					System.out.println(s.substring(38).split(" ")[0]);
				}
			}
			if(s.contains("PX_LAST")){
				closingPrice = Double.parseDouble(s.substring(10));
			}
			if(s.contains("OPEN")){
				s = s.substring(7);
				s = s.replace("}", "");
				s = s.replace("]", "");
				openingPrice = Double.parseDouble(s);
				currentCompany.addDailyAverage(average(closingPrice, openingPrice));
			}

		}
	}

}