import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


/*
 * This class runs a Gibbs sampling optimization on PhosphoScore with the specified parameters and
 * optimization targets.
 * 
 * See usage and paper for description
 * 
 */


public class PhosphoGibbs {

	// Class that handles one of the different parameters you can use for gibbs sampling
	public static class Parameter {
		public String name;
		public double min;
		public double max;
		public double step;
		public double current = 0;
		public double prev = 0;
		
		
		public Parameter(String name, double min, double max, double step) {
			this.name = name;
			this.min = min;
			this.max = max;
			this.step = step;
		}
		
		public Parameter(String name, double c) {
			this.name = name;
			this.current = c;
		}
				
		public void random(boolean init) {
			int t;
			if (init) {
				t = param_random.nextInt((int) ((max-min)/step+1));
				current = prev = min + step * t;
			}
			else {
				prev = current;
				t = param_random.nextInt((int) ((max-min)/step+1));
				current = min + step * t;
			}
		}
		
		public ArrayList<String> toStringList() {
			ArrayList<String> StringList = new ArrayList<String>(4);
			StringList.add(name);
			if (name.equals("-INTMODE") && current == 1) {
				StringList.add("rank");
			} else if (name.equals("-INTMODE") && current == 0) {
				StringList.add("int");
			} else {
				StringList.add(String.valueOf(current));
			}
			return StringList;
		}
		
	}
	
	public static File infile, outfile;
	public static String outf;
	public static int iterations = 100;
	public static int iter_max_or_change;
	public static int iter;
	public static int change;
	public static Random param_random = new Random();
	
	// Parameters
	public static ArrayList<Parameter> Params;
	
	// switches that determine which values to optimize
	public static boolean dscore, zscore, dcdistance;
	public static double sensitivity;
	public static boolean sense_reached;
	
	public static PrintWriter outwriter;
	public static Date D = new Date();
	
	public static ArrayList<String> Constant_args;
	
	
	public static void main(String[] args) {
		
		String PhosphoScoreOutfile;		
		ArrayList<String> PhosphoScore_args;
		Constant_args =  new ArrayList<String>();
		iter = 0;
		outf = null;
		change = 0;
		// 0 = iterations, 1 = terminate after X iterations with no change
		iter_max_or_change = 0;
		Params = new ArrayList<Parameter>();
		dscore = zscore = dcdistance = sense_reached = false;
		
		
		if (ProcessCmdLine(args)) {
			return;
		}
		
		// Stores the best optimization's so far
		double best_zscore = -20, best_dcdistance = 1000;
		double best_dscore = 1;
		if (dscore && dcdistance) {
			best_dscore = 0;
		}
		
		// Don't print output from the scoring class
		PhosphoScore.suppress = true;
		
		// Print some header stuff to the outfile
		try {
			outwriter = new PrintWriter(outfile);
			outwriter.printf("\nGibbs Sampling Optimization\n");
			outwriter.printf("\nDirectory %s\nRun at %s\n", infile.toString(), D.toString());
			outwriter.printf("Run with Mode=%d\n\n", 3);
			outwriter.flush();	
		} catch (Exception e) {
			if(Globals.SysError("Error: writing to outfile " + outfile.toString())) {
				return;
			}
				
		}
		
		
		
		// This is the file the PhosphoScoring will output too
		PhosphoScoreOutfile = (outfile.getParent() == null) ? new String("PhosphoScoreGibbs") :
				new String(outfile.getParent() + File.separator + "PhosphoScoreGibbs"); 
		
		Constant_args.add("-s");
		Constant_args.add(infile.toString());
		Constant_args.add("-o");
		Constant_args.add(PhosphoScoreOutfile);
		
		
		// Initialize with random values
		for (Parameter P : Params) {
			P.random(true);
		}
		
		
		for (iter = 0; iter < iterations; iter++) {

			outwriter.printf("Iteration %d:  Best Dscore=%1.7f, Zscore=%1.7f, dcdistance=%1.7f\n", 
					iter, best_dscore,  best_zscore, best_dcdistance);
			outwriter.printf("----Parameters----\n");
			
			// Hold the arguments that are passed to PhosphoScore in a list
			PhosphoScore_args = new ArrayList<String>();
			
			// Append the constant args
			PhosphoScore_args.addAll(Constant_args);

			// change one parameter each iteration to a random value.  Will loop through the 
			// parameters in order
			Params.get(iter % Params.size()).random(false);
			
			// Append to the argument list
			for (Parameter P : Params) {
				if (P.name.equals("-TOTALWEIGHT")) {
					Parameter t1 = new Parameter("-MATCHWEIGHT", P.current);
					Parameter t2 = new Parameter("-INTWEIGHT", P.max-P.current);
					PhosphoScore_args.addAll(t1.toStringList());
					PhosphoScore_args.addAll(t2.toStringList());
				}
				else {
					PhosphoScore_args.addAll(P.toStringList());
				}
				outwriter.printf(" %s", P.toStringList().toString());
			}
			
			// Call the PhosphoScore's main function
			String[] T = PhosphoScore_args.toArray(new String[PhosphoScore_args.size()]);
			System.out.println(PhosphoScore_args.toString());
			PhosphoScore.main(T);
			
			
			outwriter.printf("\n----End Parameters----\n");
			outwriter.printf("New Scores: Dscore=%1.7f, Zscore=%1.7f, dcdistance=%1.7f\n", 
					PhosphoScore.Stats.Dmean(),  PhosphoScore.Stats.Zmean(), PhosphoScore.Stats.DDmean());
			
			// Check to see if the current optimization targets are better than the best found so far
			// There are three things that can be optimized, Dscore, Zscore and Pvalue.  Using multiple
			// ones is an AND operation.  So if the new Dscores, Zscores, Pvales are all better (lower, higher, 
			// and lower respectively) the replace the best params with the new ones.  If not, revert to 
			// the old parameters
			
			// New addition:  Accept the new parameters only if the number of non-ambiguous identifications
			// is greater than the user input sensitivity.
			
			
			if (dscore && dcdistance && PhosphoScore.Stats.Dmean() <= best_dscore) {
				Params.get(iter % Params.size()).current = Params.get(iter % Params.size()).prev;
			} else if (dscore  && !dcdistance && PhosphoScore.Stats.Dmean() >= best_dscore) {
				Params.get(iter % Params.size()).current = Params.get(iter % Params.size()).prev;
			} else if (zscore && PhosphoScore.Stats.Zmean() <= best_zscore) {
				Params.get(iter % Params.size()).current = Params.get(iter % Params.size()).prev;
			} else if (dcdistance && PhosphoScore.Stats.DDmean() >= best_dcdistance) {
				Params.get(iter % Params.size()).current = Params.get(iter % Params.size()).prev;
			} else {
				if (PhosphoScore.Stats.PercPass() > sensitivity ) {
					outwriter.printf("****Replacing the new best values\n");
					best_dscore = PhosphoScore.Stats.Dmean();
					best_zscore = PhosphoScore.Stats.Zmean();
					best_dcdistance = PhosphoScore.Stats.DDmean();
					if (iter_max_or_change == 1) {
						iter = -1;
					}
					sense_reached = true;
				} else {
					outwriter.printf("----Sensitivity Threshold Reached----\n");
					Params.get(iter % Params.size()).current = Params.get(iter % Params.size()).prev;
				}			
				
			}
			outwriter.printf("Percent Passed: %1.5f\n", PhosphoScore.Stats.PercPass());
			outwriter.printf("\n");
			
			outwriter.flush();
			
			
			
			
		}
		
		if (!sense_reached) {
			outwriter.printf("\nWARNING: Sensitivity Threshold Never Reached. Lower Threshold and Rerun\n");
		}
		outwriter.printf("\nFinal Parameters: ");
		PhosphoScore_args = new ArrayList<String>();
		PhosphoScore_args.addAll(Constant_args);
		for (Parameter P : Params) {
			if (P.name.equals("-TOTALWEIGHT")) {
				Parameter t1 = new Parameter("-MATCHWEIGHT", P.current);
				Parameter t2 = new Parameter("-INTWEIGHT", P.max-P.current);
				PhosphoScore_args.addAll(t1.toStringList());
				PhosphoScore_args.addAll(t2.toStringList());
			}
			else {
				PhosphoScore_args.addAll(P.toStringList());
			}
			outwriter.printf(" %s", P.toStringList().toString());
		}
		outwriter.printf("\n");
		outwriter.flush();

		System.out.println(PhosphoScore_args.toString());
		PhosphoScore.main(PhosphoScore_args.toArray(new String[PhosphoScore_args.size()]));
		iter++;
		outwriter.close();

	}
	
	
	
	private static boolean ProcessCmdLine(String[] args) {
		
		int i = 0;
		boolean total = false;
		boolean indiv = false;
		
		while (i < args.length) {
			
			/***** Basic Options ********/
			
			if (args[i].equals("-s")) {	
				infile = new File(args[++i]);								
			} else if (args[i].equals("-o")) {
				outfile = new File(args[++i] + File.separator + "GibbsResult.txt");
			} else if (args[i].equals("-imax")) {
				iter_max_or_change = 0;
				iterations = Integer.parseInt(args[++i]);
			} else if (args[i].equals("-ichange")) {
				iter_max_or_change = 1;
				iterations = Integer.parseInt(args[++i]);
			/***** Optimizer Options ********/
			} else if (args[i].equals("-dscore")) {
				dscore = true;
			} else if (args[i].equals("-zscore")) {
				zscore = true;
			} else if (args[i].equals("-dcdistance")) {
				dcdistance = true;
			} else if (args[i].equals("-sensitivity")) {
				try {
					sensitivity = Double.parseDouble(args[++i]);
				} catch (Exception e) {
					Globals.SysError("Error: Sensitivity must be a number");
					return true;
				}
				
			/***** Parameter Options ********/
				
			} else if (args[i].equals("-MATCHTOL")) {
				try {
					Constant_args.add(args[i++]);
					Constant_args.add(args[i]);
					i = i + 2;
					//Params.add(new Parameter("-MATCHTOL", Double.parseDouble(args[++i]),
					//		Double.parseDouble(args[++i]), Double.parseDouble(args[++i])));	
				} catch (Exception e) {
					Globals.SysError("Error: MATCHTOL must be three numbers");
					return true;
				}
			} else if (args[i].equals("-THRESH")) {
				try {
					Params.add(new Parameter("-THRESH", Double.parseDouble(args[++i]),
							Double.parseDouble(args[++i]), Double.parseDouble(args[++i])));	
				} catch (Exception e) {
					Globals.SysError("Error: THRESH must be three numbers");
					return true;
				}
			} else if (args[i].equals("-INTEXP")) {
				try {
					Params.add(new Parameter("-INTEXP", Double.parseDouble(args[++i]),
							Double.parseDouble(args[++i]), Double.parseDouble(args[++i])));	
				} catch (Exception e) {
					Globals.SysError("Error: INTEXP must be three numbers");
					return true;
				}
			} else if (args[i].equals("-MATCHWEIGHT")) {
				try {
					Params.add(new Parameter("-MATCHWEIGHT", Double.parseDouble(args[++i]),
							Double.parseDouble(args[++i]), Double.parseDouble(args[++i])));	
					indiv = true;
				} catch (Exception e) {
					Globals.SysError("Error: MATCHWEIGHT must be three numbers");
					return true;
				}
			} else if (args[i].equals("-INTWEIGHT")) {
				try {
					Params.add(new Parameter("-INTWEIGHT", Double.parseDouble(args[++i]),
							Double.parseDouble(args[++i]), Double.parseDouble(args[++i])));	
					indiv = true;
				} catch (Exception e) {
					Globals.SysError("Error: INTWEIGHT must be three numbers");
					return true;
				}
				// Randmizes matchweight, int weight is chosen as max-matchweight
			} else if (args[i].equals("-TOTALWEIGHT")) {
				try {
					Params.add(new Parameter("-TOTALWEIGHT", Double.parseDouble(args[++i]),
							Double.parseDouble(args[++i]), Double.parseDouble(args[++i])));	
					total = true;
				} catch (Exception e) {
					Globals.SysError("Error: TOTALWEIGHT must be three numbers");
					return true;
				}
			} else if (args[i].equals("-DEFAULTCOST")) {
				try {
					Params.add(new Parameter("-DEFAULTCOST", Double.parseDouble(args[++i]),
							Double.parseDouble(args[++i]), Double.parseDouble(args[++i])));	
				} catch (Exception e) {
					Globals.SysError("Error: DEFAULTCOST must be three numbers");
					return true;
				}
			} else if (args[i].equals("-INTMODE")) {
				try {
//					Params.add(new Parameter("-INTMODE", Double.parseDouble(args[++i]),
//							Double.parseDouble(args[++i]), Double.parseDouble(args[++i])));
					Constant_args.add(args[i++]);
					if (Double.parseDouble(args[i]) == 1) {
						Constant_args.add("rank");
					}					
					else {
						Constant_args.add("int");
					}
					i = i + 2;										
					
				} catch (Exception e) {
					Globals.SysError("Error: INTMODE must be three numbers");
					return true;
				}
			} else if (args[i].equals("-CONFMODE")) {
				Constant_args.add(args[i++]);
				Constant_args.add(args[i++]);
				Constant_args.add(args[i]);	
			} else {
				System.out.println("Error: Invalid Option " + args[i]);
				Usage();
				if (!Globals.INTERACTIVE) {
					System.exit(1);
				}
				return true;
				
			}
			i++;
					
		}
		
		if (infile == null || outfile == null) {			
			Globals.SysError("Error: -s and -o are required");
			return true;
		} 
		else if (!infile.exists()) {
			Globals.SysError("Error: No file or directory found with that name");
			return true;
		}
		if (dscore == false && zscore == false && dcdistance == false) {
			
			Globals.SysError("Error: -dscore, -zscore or -dcdistance are required");
			return true;
		}
		if (total && indiv) {
			Globals.SysError("Error: If TOTALWEIGHT given, MATCHWEIGHT AND INTWEIGHT cannot be used");
			return true;
		}
		
		return false;
		
	}
	
	
	private static void Usage() {
	
			
		System.out.printf("\n");
		System.out.printf("Gibbs Sampling Optimization for PhosphoScore\n");
		System.out.printf("Developed at the LKEM, NHLBI, NIH\n");
		System.out.printf("Contact Brian Ruttenberg (berutten@cs.ucsb.edu, ruttenberg@gmail.com) or\n");
		System.out.printf("Dr Mark Knepper (knepperm@nhlbi.nih.gov) for more information\n");
		
		System.out.printf("\n");
		System.out.printf("Usage: java PhosphoGibbs -s <directory> -o <outfile> <-zscore|-dscore|-dcdistance> <options>\n");
		System.out.printf("\n");
		System.out.printf("-s <dir>\t\t-Analyze all the turboSEQUEST outfiles in the specified directory.  sequest.params is searched in this directory\n");
		System.out.printf("\n");
		System.out.printf("-o <file>\t\t-Outfile to place results.  This directory will also be used for all PhosphoScore out files\n");

		System.out.printf("\n");
		System.out.printf("-imax <value>\t\t-Number of iterations for the sampling to go through.  Higher for more parameters\n\n");
		System.out.printf("-ichange <value>\t\t-Stops Gibbs if after <value> iterations no change in the results is found.  Higher for more parameters\n\n");
		System.out.printf("-dscore\t\t\t-Use dscore as the optimizing function and try to minimize the average dscore\n\n");
		System.out.printf("-zscore\t\t\t-Use zscore as the optimizing function and try to maximize the average zscore\n\n");
		System.out.printf("-dcdistance\t\t\t-Use the Default Cost distance as the optimizing function and try to minimize the average distance\n\n");
		System.out.printf("\t\t\t\t-Note:  using -dscore and -dcdistance at the same time will try to MAXIMIZE dscore and MINIMIZE dcdistance\n\n");
		System.out.printf("-h\t\t\t-Usage\n\n");
		
		System.out.println("----Parameter Options----\n");
			
		System.out.printf("All Parameter options are of the form: <Parameter> <min> <max> <step>, with the exception of INTMODE which is a single option\n");
		System.out.printf("The following options are available:\n");
		System.out.printf("MATCHTOL, THRESH, TOTALTWEIGHT, MATCHWEIGHT, INTWEIGHT, INTEXP, DEFAULTCOST, INTMODE\n");

		System.out.println("\nFor additional information, please see the readme");
				
	}


	
	
}
