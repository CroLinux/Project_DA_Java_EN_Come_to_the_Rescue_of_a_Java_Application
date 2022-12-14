package com.hemebiotech.analytics;

/**
 * Modification of the existing Alex's code using ISymptomReader.java and 
 * ReadSymptomDataFromFile.java files provided by the company.
 * 
 * Goal: Read all the different entries(symptoms) from a file, order those one, 
 * count their occurences and generate a report.
 * 
 * Heme Biotech
 * Intern Ante D.
 * 
 * version 1.3
 * - Modification of the method name writeDataIntoTheFile()
 * 
 * November 2022
 * 
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;
import java.util.Collections;

public class AnalyticsCounter {
	// Declaration of the Input and Output files.
	private static String sourceFile = "Project02Eclipse/symptoms.txt";
	private static String outputFile = "results.out";

	// Table which is used to save the symptoms from the file.
	private static List<String> table;
	// Table to save the symptoms with their occurences.
	private static TreeMap<String, Integer> tabSorted = new TreeMap<>();

	/**
	 * Used to call the GetSymptoms() method from the Class ReadSymptomDataFromFile
	 * 
	 * @param Input file name
	 * @return List of the symptoms from the file stored into the table.
	 */
	public void readFileContents() {
		ReadSymptomDataFromFile readTable = new ReadSymptomDataFromFile(sourceFile);
		table = readTable.GetSymptoms();
	}

	/**
	 * Used to order by name the symptoms into the table.
	 */
	public void sortFileContents() {
		Collections.sort(table);
	}

	/**
	 * Used to save the ordered symptoms into a TreeMap + count and save their
	 * occurences.
	 */
	public void countOccurenciesContents() {
		for (String i : table) {
			Integer j = tabSorted.get(i);
			tabSorted.put(i, (j == null) ? 1 : j + 1);
		}
	}

	/**
	 * Used to write and save all the data from the TreeMap into the output file and
	 * call the displayProcessing() method.
	 * 
	 * @throws Exception Display a message if the program can't write the output
	 *                   file.
	 */
	public void writeDataIntoTheFile() throws Exception {
		try {
			BufferedWriter targetFile = new BufferedWriter(new FileWriter(outputFile, false));
			for (Map.Entry<String, Integer> entry : tabSorted.entrySet()) {
				targetFile.write(entry.getKey() + ": " + entry.getValue());
				targetFile.newLine();
			}
			targetFile.close();
			displayProcessing();
		} catch (IOException e) {
			System.out.println("Error : " + e.getMessage());
			System.out.println("The output file '" + outputFile + "' can't be generated!");
		}
	}

	/**
	 * Used to display a small report on the console and generate the output file.
	 * Here the 'sum' variable is used to verify if at the end of the processing, we
	 * have the same total number of entries (from the input file) and the final
	 * number of counted symptoms (into the TreeMap).
	 */
	public static void displayProcessing() {
		System.out.println("The provided file '" + sourceFile + "' has " + table.size() + " entries.");
		int sum = tabSorted.values().stream().mapToInt(Integer::intValue).sum();
		System.out.println("We found " + tabSorted.size() + " different symptoms for " + sum
				+ " records read, the output file '" + outputFile + "' is generated.");
	}

	/**
	 * Main method 'ligth' where we call all the separate methods.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {
		AnalyticsCounter AC = new AnalyticsCounter();

		AC.readFileContents();
		AC.sortFileContents();
		AC.countOccurenciesContents();
		AC.writeDataIntoTheFile();
	}
}