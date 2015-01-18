package com.chickenstock.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

public class Receive {

	public String handle(int line) throws JSONException, FileNotFoundException {
		JSONObject writer = new JSONObject();
		Scanner scan = new Scanner(new File("data/companyNames.txt"));
		for(int i = 0; i < line; i++)
			scan.nextLine();
		writer.put("name",scan.nextLine());
		
		scan = new Scanner(new File("data/companySymbols.txt"));
		for(int i = 0; i < line; i++)
			scan.nextLine();
		writer.put("abbrv",scan.next());
		
		scan = new Scanner(new File("data/companyScores.txt"));
		for(int i = 0; i < line; i++)
			scan.nextLine();
		int score = scan.nextInt();
		String recColor ="FFFFFF";
		String recSentence = "NOW YA FUCKED UP";
		if(score < -5){
			recSentence = "Probably a bad idea";
			recColor = "#FF0000";
		}
		else if(score < 0){
			recSentence = "Not looking good";
			recColor = "#770000";
		}
		else if(score == 0){
			recSentence = "No insight available";
			recColor = "#000000";
		}
		else if(score < 5){
			recSentence = "Looks alright";
			recColor = "#007700";
		}
		else if(score > 4){
			recSentence = "Great deal!";
			recColor = "#42ff23";
		}
		scan = new Scanner(new File("bloomberg/data.txt"));
		for(int i = 0; i < line; i++){
			scan.nextLine();
		}

		String temp = scan.nextLine();
		String[] sArray = temp.split(" ");
		System.out.println(temp);

		double volatility = Double.parseDouble(sArray[1]);

		double price = Double.parseDouble(sArray[2]);

		writer.put("price", price);
		writer.put("rec", recColor);
		writer.put("recText", recSentence);
		

		
		String buzzText = "Unable to get data";
		String buzzColor = "#000000";

		if(volatility < .5){
			buzzText = "Very stable";
			buzzColor = "#0000FF";
		} else if(volatility < 1.0){
			buzzText = "Relatively level";
			buzzColor = "#B273FF";
		} else if(volatility == 1.5){
			buzzText = "Meh";
			buzzColor = "#FF87EF";
		} else if(volatility < 2){
			buzzText = "A bit active";
			buzzColor = "#FF655E";
		} else {
			buzzText = "Crraaaaaazzzzzyyyyy!";
			buzzColor = "#FF9812";
		}
		
		writer.put("buzz", buzzColor);
		writer.put("buzzText", buzzText);
		System.out.println(writer.toString());
		scan.close();
		return writer.toString();
		
		
	}

}
