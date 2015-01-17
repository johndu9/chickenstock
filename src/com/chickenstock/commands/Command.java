package com.chickenstock.commands;
public abstract class Command {
	
	public static final int KEY = 0;
	public static final int VAL = 1;

	//key indices
	public static final int COMMAND = 0;
	
	/** Input that we get from the browser */
	protected String[][] input;
	
	/**
	 * Stores the input in the command
	 * @param input Input from browser
	 */
	public Command(String[][] input) {
		this.input = input;
	}
	
	/**
	 * Handles input and turns it into something we can send back to the browser
	 * @return Browser usable string
	 */
	public abstract String handle();
	
}