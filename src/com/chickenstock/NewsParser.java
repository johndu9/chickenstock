package com.chickenstock;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class NewsParser {
	
	private static List<String> companyNames = null;
	private static List<String> companySymbols = null;
	private static List<String> companyScores = null;
	private static List<String> words = null;
	private static List<Integer> wordScores = null;
	
	private static void initCompanies() throws FileNotFoundException {
		System.out.println("Initializing company data . . .");
		companyNames = new LinkedList<String>();
		companySymbols = new LinkedList<String>();
		companyScores = new LinkedList<String>();
		Scanner reader = new Scanner(new File("data/companySymbols.txt"));
		String query = "";
		while (reader.hasNextLine()) {
			query = reader.nextLine();
			String url = "http://www.reuters.com/finance/stocks/overview?symbol=" + query;
			String content = "";
			try {
				content = getSite(url, 25, 1000);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (content.contains("Quote| Reuters.com</title>")) {
				String companyName = content.substring("    <title>".length(), content.indexOf(" ("));
				companyNames.add(companyName);
				System.out.println("Initializing " + companyName + " data . . .");
				companySymbols.add(query);
				String description = getDescription(content);
				int score = 0;

				/*
				for (String word : words) {
					if (description.contains(word)) {
						score += wordScores.get(words.indexOf(word));
					}
				}
				*/
				String[] parts = description.split(" ");
				for (String part : parts) {
					for (String word : words) {
						if (part.contains(word)) {
							score += wordScores.get(words.indexOf(word));
						}
					}
				}
				companyScores.add("" + score);
			}
		}
		reader.close();
	}
	
	private static String getDescription(String content) {
		String descriptionContent = content.substring(content.indexOf("p>") + "p>".length());
//		System.out.println(descriptionContent);
		return descriptionContent;
	}
	
	private static void initWordScores() throws FileNotFoundException {
		System.out.println("Initializing word scores . . .");
		words = new ArrayList<String>();
		wordScores = new ArrayList<Integer>();
		Scanner reader = new Scanner(new File("data/wordScores.txt"));
		while (reader.hasNextLine()) {
			String[] parts = reader.nextLine().split("=");
			words.add(parts[0]);
			wordScores.add(new Integer(parts[1]));
		}
		reader.close();
	}
	
	private static String getSite(String url, int skip, int end) throws MalformedURLException, IOException {
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;
		reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
		String line = "";
		int counter = 0;
		boolean instory = false;
		while ((line = reader.readLine()) != null && counter < end) {
			if (counter > skip) {
				if (!instory && line.contains("2><p>")) {
					instory = true;
				}
				if (line.contains("<title>") || instory) {
					builder.append(line + "");
				}
				if (instory && line.contains("</p>")) {
					instory = false;
				}
			}
			counter++;
		}
		reader.close();
		return builder.toString();
	}
	
	private static void writeToFile(List<String> list, String path) throws IOException {
		StringBuilder builder = new StringBuilder();
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			builder.append(iterator.next() + "\n");
		}
		writeToFile(builder.toString(), path);
	}
	
	private static void writeToFile(String content, String path) throws IOException {
		System.out.println("Writing content to " + path + " . . .");
		Scanner reader = new Scanner(content);
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		while (reader.hasNextLine()) {
			writer.write(reader.nextLine());
			if (reader.hasNextLine()) {
				writer.newLine();
			}
		}
		reader.close();
		writer.close();
	}
	
	public static void main(String[] args) throws MalformedURLException, IOException {
		initWordScores();
		initCompanies();
		writeToFile(companyScores, "data/companyScores.txt");
		writeToFile(companyNames, "data/companyNames.txt");
	}
	
}