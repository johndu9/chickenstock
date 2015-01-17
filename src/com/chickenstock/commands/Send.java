package com.chickenstock.commands;
import java.util.List;

public class Send extends Refresh {

	//key names
	public static final int MESSAGE = 1;
	
	public Send(String[][] input, List<String> messages) {
		super(input, messages);
		messages.add(input[MESSAGE][VAL]);
	}
	
}