package com.chickenstock.commands;
import java.util.List;

public class Refresh extends Command {
	
	private List<String> messages;
	
	public Refresh(String[][] input, List<String> messages) {	
		super(input);
		this.messages = messages;
	}

	@Override
	public String handle() {
		StringBuilder output = new StringBuilder();
		for (String message : messages) {
			output.append(message + "<br>");
		}
		return output.toString();
	}
}