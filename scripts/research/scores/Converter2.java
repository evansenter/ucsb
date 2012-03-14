import java.io.*;
import java.util.*;

public class Converter2 {
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

      // More prep
      scanner = new Scanner(new File(args[0]));
      BufferedWriter out = new BufferedWriter(new FileWriter(args[0] + "_scores_transform"));
      double difference = scores[scores.length - 1] - scores[0], min = scores[0];
      ArrayList<ArrayList> adjusted_scores = new ArrayList<ArrayList>();
      for (int i = 0; i < 255; i++)
	      adjusted_scores.add(new ArrayList<Double>());
      
      // Branch based on the number of scores
      if (scores.length > 254) {
	      // If more than 254...
	      int current_score = 0;
	      for (int i = 0; i < 255; i++)
	        while (current_score < scores.length && scores[current_score] < ((difference / 254) * (i + 1) + min))
	          adjusted_scores.get(i).add(scores[current_score++]);
	
      // Write data
      out.write("null -1\n");
      for (int i = 0; i < adjusted_scores.size(); i++) {
        ArrayList<Double> this_list = adjusted_scores.get(i);
        for (int j = 0; j < this_list.size(); j++)
          out.write(this_list.get(j) + " " + i + "\n");
      }
      out.close();
          
      // Write to the int file
      System.out.println("Transform complete, writing the int file");
      out = new BufferedWriter(new FileWriter("int_" + args[0]));
      
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        try {
          double value = Double.valueOf(line);
          int current_bin = 0;
          while ((difference / 254) * (current_bin + 1) + min < value)
            current_bin++;  
          out.write(current_bin + "\n");
        } catch (NumberFormatException e) {
          out.write("-1\n");
        }
      }
    } else { 
      // Else if less than or equal to 254...
      out = new BufferedWriter(new FileWriter(args[0] + "_transform"));
      
      // Write data
      out.write("null -1\n");
      for (int i = 0; i < scores.length; i++)
        out.write(scores[i] + " " + i + "\n");
      out.close();
      
      // Write to the int file
      System.out.println("Transform complete, writing the int file");
      out = new BufferedWriter(new FileWriter("int_" + args[0]));
      
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        try {
          double value = Double.valueOf(line);
          out.write(java.util.Arrays.binarySearch(scores, value) + "\n");
        } catch (NumberFormatException e) {
          out.write("-1\n");
        }
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
