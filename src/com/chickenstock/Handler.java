package com.chickenstock;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;

import com.chickenstock.commands.*;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Handler implements HttpHandler {
	
	private List<String> messages = new LinkedList<String>();
	
	public void handle(HttpExchange t) throws IOException {
		System.out.print((Server.requests++) + "\t" + t.getRequestMethod() + ":");
//		System.out.println(Arrays.toString(t.getRequestHeaders().values().toArray()));
		String content = "";
		if (t.getRequestMethod().equalsIgnoreCase("POST")) {
			InputStream is = t.getRequestBody();
			byte[] buf = new byte[128];
			int len = is.read(buf);
			String command = new String(buf, 0, len);
			System.out.println(command);
			Headers responseHeaders = t.getResponseHeaders();
			responseHeaders.set("Content-Type", "text/plain");
			t.sendResponseHeaders(200, 0);
			content = handleCommand(parseCommand(command));
		} else if (t.getRequestMethod().equalsIgnoreCase("GET")) {
			String request = t.getRequestURI().toString().substring(1);
			System.out.print(request);
			String path = "404.html";
			int code = 200;
			if (request.equals("")) {
				System.out.println("getting index");
				path = "findex.html";
			} else {
				if (request.contains("src") || request.contains("data")) {
					System.out.println(" is a src or data file");
					code = 404;
				} else if (new File(request).exists()) {
					System.out.println(" exists");
					path = request;
				} else if (new File(request + ".html").exists()) {
					System.out.println(" exists but does not have extension");
					path = request + ".html";
				} else {
					System.out.println(" does not exist");
					code = 404;
				}
			}
			content = (code != 404) ? (Server.getFileContent(path)) : (Server.notFoundContent);
			t.sendResponseHeaders(code, content.length());
		}
		OutputStream os = t.getResponseBody();
		os.write(content.getBytes());
		os.close();
	}

	/**
	 * Rips apart string sent by browser into a usable command
	 * @param string Incoming command
	 * @return System usable command
	 */
	private String[][] parseCommand(String string) {
		String[] parts = string.split("&");
		String[][] command = new String[parts.length][2];
		for (int i = 0; i < command.length; i++) {
			command[i] = parts[i].split("=");
		}
		return command;
	}

	/**
	 * Directs input to an appropriate command
	 * @param input Input we can use for the command
	 * @return Output for the browser to handle
	 */
	private String handleCommand(String[][] input) {
		//String command = input[Command.COMMAND][Command.VAL];
		String output = "";
		try {
			try {
				output = new Receive().handle((int)(Math.random()*200));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return output;
	}
}