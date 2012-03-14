import java.io.*;
import java.util.*;

public class Converter {
  public static void main(String args[]) {
    System.out.println("Requested file is: " + args[0]);
    try {
      // Variable initialization
      long startTime = System.currentTimeMillis();
      ArrayList<Double> unique_values = new ArrayList<Double>();
      Scanner scanner = new Scanner(new File(args[0]));
      
      // Loop through to collect the scores
      while (scanner.hasNextLine()) {
	      String line = scanner.nextLine();
	      try {
	        double value = Double.valueOf(line);
	        if (!unique_values.contains(value))
	          unique_values.add(value);
	      } catch (NumberFormatException e) {
	        // Nulls catch here
	      }
      }
      scanner.close();
      
      System.out.println("File read complete, writing transform file");
      
      // Sort the scores
      double[] scores = new double[unique_values.size()];
      for (int i = 0; i < unique_values.size(); i++)
        scores[i] = unique_values.get(i);
      java.util.Arrays.sort(scores);
      
      // Write the transform file
      BufferedWriter out = new BufferedWriter(new FileWriter(args[0] + "_transform"));
      out.write("null -1\n");
      for (int i = 0; i < scores.length; i++)
        out.write(scores[i] + " " + i + "\n");
      out.close();
      
      System.out.println("Transform write complete, making the int file");
      
      // Reinitialize
      scanner = new Scanner(new File(args[0]));
      out = new BufferedWriter(new FileWriter("int_" + args[0]));
      
      // Write to the int file
      while (scanner.hasNextLine()) {
	      String line = scanner.nextLine();
	      try {
	        double value = Double.valueOf(line);
	        out.write(java.util.Arrays.binarySearch(scores, value) + "\n");
	      } catch (NumberFormatException e) {
	        out.write("-1\n");
	      }
      }
      
      // Close up
      scanner.close();
      out.close();

      System.out.println("Runtime is " + (System.currentTimeMillis() - startTime) / (1000 * 60) + " minutes");
      System.out.println(scores.length + " unique scores");
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
