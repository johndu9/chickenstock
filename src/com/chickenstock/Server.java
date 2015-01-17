package com.chickenstock;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetSocketAddress;
import java.util.Scanner;

import com.sun.net.httpserver.HttpServer;

public class Server {

	/** Content of the 404 page */
	public static String notFoundContent;
	/** Request number of the server */
	public static int requests = 0;
	/** Port that we'll be using */
	private static final int PORT = 8000;

	public static void main(String[] args) throws Exception {
		//Loads pages that we don't want to parse every time we load
		System.out.println("Loading pages . . .");
		notFoundContent = getFileContent("404.html");

		//Creates the server
		System.out.println("Creating server . . .");
		HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
		server.createContext("/", new Handler());
		server.setExecutor(null); // creates a default executor
		System.out.println("Starting server on " + server.getAddress() + " . . .");
		server.start();
	}
	
	/**
	 * Parses files and puts it into a string
	 * @param path File path of file we want
	 * @return Content of the file
	 */
	public static String getFileContent(String path) {
		File file = new File(path);
		StringBuilder builder = new StringBuilder();
		Scanner reader = null;
		try {
			reader = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return notFoundContent;
		}
		while (reader.hasNextLine()) {
			builder.append(reader.nextLine() + "\n");
		}
		reader.close();
		return builder.toString();
	}

}