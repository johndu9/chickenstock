package com.chickenstock;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
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
		Company[] companies = new Company[200];
		boolean readFirstLine = false;
		File stocks = new File("bloomberg\\master.txt");
		File names = new File("data\\companySymbols.txt");

		/** Parse names for Company names **/
		try {
			f = new String(Files.readAllBytes(names.toPath()));
		} catch (IOException e) {
			System.out.println("File not found!");
			e.printStackTrace();
		}

		String[] symbols = f.split("\n");

		/** Parse Stocks for Company ticker names and averages **/
		try {
			f = new String(Files.readAllBytes(stocks.toPath()));
		} catch (IOException e) {
			System.out.println("File not found!");
			e.printStackTrace();
		}
		String[] lines = f.split(",");
		for(String s: lines){

			if(s.contains("securityData")){
				if(s.contains("DONE"))
					readFirstLine = false;
				else if(readFirstLine){
					currentCompany = new Company(s.substring(29).split(" ")[0]);
					for(int i = 0; i < symbols.length; i++){
						if(currentCompany.title.equalsIgnoreCase(symbols[i].substring(0, symbols[i].length() -1))){
							companies[i] = currentCompany;
							break;
						}
					}
				}
				else {
					if(s.contains("data"))
						s = s.replace("{\"data\":[", "");
					readFirstLine = true;
					currentCompany = new Company(s.split("\"")[5].split(" ")[0]);
					for(int i = 0; i < symbols.length; i++){
						if(currentCompany.title.equalsIgnoreCase(symbols[i].substring(0, symbols[i].length() -1))){
							companies[i] = currentCompany;
							break;
						}
					}
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
		/** Calculate price fluctuation and last stock price **/
		String dataFile = "";
		int company = 0;
		for(Company c: companies){
			int days = 0;
			double previousDay = 0;
			double diff = 0;
			if(c != null){
				for(double currentDay: c.dailyAverage){
					days++;
					if(currentDay != c.dailyAverage.get(0))
						diff += Math.abs(currentDay - previousDay);
					previousDay = currentDay;
				}

				c.addVolatileIndex(diff / days);
				dataFile +=  c.title + " " + (diff / days) + " " + c.lastDailyPrice + "\n";
			}
			
			else
				dataFile += "Not found";
			company++;

		}
		try {
			NewsParser.writeToFile(dataFile, "bloomberg\\data.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
