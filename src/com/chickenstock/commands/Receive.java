package com.chickenstock.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

public class Receive {

	public String handle(int line) throws JSONException, FileNotFoundException {
		JSONObject writer = new JSONObject();
		Scanner scan = new Scanner(new File("data/companyNames.txt"));
		for(int i = 0; i < line; i++)
			scan.nextLine();
		writer.put("name",scan.next());
		
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
			recColor = "#00FF00";
		}
		writer.put("price", 0.00);
		writer.put("rec", recColor);
		writer.put("recText", recSentence);
		
		writer.put("buzz", "#0000FF");
		writer.put("buzzText", "Stuff");
		return writer.toString();
		
		
	}

}
