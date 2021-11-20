package document;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class DocumentBenchmarking {

	
	public static void main(String [] args) {
	        int trials = 100;
	        String textfile = "data/warAndPeace.txt";
	        int increment = 20000;
		int numSteps = 12; 
		int start = 50000;
		
		System.out.print("NumberOfChars"+ '\t');	
		System.out.print("BasicTime" + '\t');
		System.out.print("EfficientTime" + '\n');
		for (int numToCheck = start; numToCheck < numSteps*increment + start; 
				numToCheck += increment)
		{
			System.out.print(numToCheck + "\t");
			final String str = getStringFromFile(textfile, numToCheck);
                        System.out.print(measureTime(trials,new BasicDocument(str))+"\t");
			
			System.out.print(measureTime(trials,new EfficientDocument(str)));
			System.out.println();
		}
	
	}
	
	private static double measureTime(int trials, Document doc) {
		long startTime = System.nanoTime();
		
		for (int i = 0; i < trials; i++) {
			doc.getFleschScore();
		}
		long endTime = System.nanoTime();
		double elapsedTime = (endTime-startTime)/1000000000.0;

		return elapsedTime;
	}
	
	public static String getStringFromFile(String filename, int numChars) {
		
		StringBuffer s = new StringBuffer();
		try {
			FileInputStream inputFile= new FileInputStream(filename);
			InputStreamReader inputStream = new InputStreamReader(inputFile);
			BufferedReader bis = new BufferedReader(inputStream);
			int val;
			int count = 0;
			while ((val = bis.read()) != -1 && count < numChars) {
				s.append((char)val);
				count++;
			}
			if (count < numChars) {
				System.out.println("Warning: End of file reached at " + count + " characters.");
			}
			bis.close();
		}
		catch(Exception e)
		{
		  //System.out.println(e);
		  //System.exit(0);
		}
		
		
		return s.toString();
	}
	
}
