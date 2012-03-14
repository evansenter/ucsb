import java.io.*;
import java.util.*;


/* Command line :
 * 
	See usage
 */



public class PhosphoScore {

	public static File infile, outfile, errfile;
	public static String outf;
	public static int mode = 3;
	public static boolean debug, xon, matchtol_set;
	public static String[] inlist;
	public static PrintWriter outwriter, errwriter;
	public static Date D ;
	public static boolean suppress = false;
	public static int iterator;
	public static PhosphoStats Stats;
	
	// Declare an interface to use with this version. Currently only Sequest coded up
	public static SequestInterface Interface;
	
	
	/* Class Score
	 * 
	 * Stores a score for a given scan
	 */
	public static class Score {

		public StringBuilder peptide;
		public double zscore;
		public double dcdistance;
		public int Ecount;
		public double diff;
		public int error;
		public double[] cost;
		public int[] nodes;
		
		public Score(String p, double z, double t, int ev, double d, int e, double[] c, int[] n) {
			peptide = new StringBuilder(p);
			zscore = z;
			dcdistance = t;
			Ecount = ev;
			diff = d;
			error = e;
			cost = new double[2];
			cost[0] = c[0];
			cost[1] = c[1];
			nodes = new int[2];
			nodes[0] = n[0];
			nodes[1] = n[1];
			
		}
		
		public Score(StringBuilder p, double z, double t, int ev, double d, int e, double[] c, int[] n) {
			peptide = p;
			zscore = z;
			dcdistance = t;
			Ecount = ev;
			diff = d;
			error = e;
			cost = new double[2];
			cost[0] = c[0];
			cost[1] = c[1];
			nodes = new int[2];
			nodes[0] = n[0];
			nodes[1] = n[1];
		}
		
		
	}
	
	
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		debug = false;
		xon = false;
		matchtol_set = false;
		D = new Date();
		iterator = 0;
		
		Globals.INIT_WEIGHTS();
		
		// process command line arguments
		if (ProcessCmdLine(args)) {
			return;
		}
		
		// Open the error file
		try {
			errwriter = new PrintWriter(errfile);
		} catch (Exception e) {
			Globals.SysError("Error: Unable to open " + errfile.toString() + " for writing");
			return;
		}
		
		// Initialize the interface
		Interface = new SequestInterface(debug);
		Interface.init(infile);
		
		
		// Retrieve the next MS Spectrum/outfile to process
		String scan = Interface.getNext();

		// Print some header stuff to the outfile
		try {
			int index = scan.lastIndexOf(File.separatorChar);
			index = index < 0 ? 0 : index+1;
			//char[] spacing = new char[scan.length()-8-index];
			char[] spacing = new char[26];
			Arrays.fill(spacing, ' ');
			outwriter = new PrintWriter(outfile);
			outwriter.printf("\nDirectory %s\nRun at %s\n", outf, D.toString());
			outwriter.printf("Run with Mode=%d\n\n", mode);
			outwriter.printf("Scan%s\tCharge\tZscore\t\tDCDistance\t\tDscore\t\tConfidence\tPeptideSeq\n", String.valueOf(spacing));
			outwriter.flush();	
		} catch (Exception e) {
			Globals.SysError("Error: writing to outfile " + outfile.toString());
			return;
		}
		
		// Initialize the stats data
		Stats = new PhosphoStats(Interface.getLength());
		
		// Loop through the scan files (only one if -s was given as input)
		for (; scan != null; scan = Interface.getNext()) {
			iterator++;
			if (!suppress) {
				System.out.println("Starting Scan " + scan);
			}
						
			if (ProcessOneScan(scan, Interface.getPath()) == 0) { 
				if (!suppress) {
					System.out.println("Scan " + scan.substring(0, scan.length()-4) + " Successfully Completed");
				}
				System.gc();	
			}
			else {
				if (!suppress) {
					System.out.println("Scan " + " Did Not Finish Correctly.  See the error file");
				}
			}
			
			
		}
		
		Stats.PrintStats(outwriter);
		outwriter.close();
				
	}

	
	
	private static boolean ProcessCmdLine(String[] args) {
		
		int i = 0;
		int confcnt = 0;
		while (i < args.length) {
			
			/***** Basic Options ********/
			
			if (args[i].equals("-s")) {	
				infile = new File(args[++i]);								
			} else if (args[i].equals("-m")) {
				try {
					mode = Integer.parseInt(args[++i]);
				} catch (Exception e) {
					Globals.SysError("Error: Mode must be an integer from 0 to 3");
					return true;
				}
			} else if (args[i].equals("-o")) {
				outf = new String(args[++i]);
			} else if (args[i].equals("-xon")) {
				xon = true;
			} else if (args[i].equals("-d")) {
				debug = true;
			} else if (args[i].equals("-h")) {
				Usage();

			/***** Advanced Scoring Options ********/
				
			} else if (args[i].equals("-MATCHTOL")) {
				try {
					Globals.MATCHTOL = Double.parseDouble(args[++i]);
					matchtol_set = true;
				} catch (Exception e) {
					Globals.SysError("Error: MATCHTOL must be a number");
					return true;
				}
			} else if (args[i].equals("-THRESH")) {
				try {
					Globals.THRESH = Double.parseDouble(args[++i]);
				} catch (Exception e) {
					Globals.SysError("Error: THRESH must be a number");
					return true;
				}
			} else if (args[i].equals("-INTEXP")) {
				try {
					Globals.INTEXP = Double.parseDouble(args[++i]);
				} catch (Exception e) {
					Globals.SysError("Error: INTEXP must be a number");
					return true;
				}
			} else if (args[i].equals("-MATCHWEIGHT")) {
				try {
					Globals.MATCHWEIGHT = Double.parseDouble(args[++i]);
				} catch (Exception e) {
					Globals.SysError("Error: MATCHWEIGHT must be a number");
					return true;
				}
			} else if (args[i].equals("-INTWEIGHT")) {
				try {
					Globals.INTWEIGHT = Double.parseDouble(args[++i]);
				} catch (Exception e) {
					Globals.SysError("Error: INTWEIGHT must be a number");
					return true;
				}
			} else if (args[i].equals("-DEFAULTCOST")) {
				try {
					Globals.DEFAULTCOST = Double.parseDouble(args[++i]);
				} catch (Exception e) {
					Globals.SysError("Error: DEFAULTCOST must be a number");
					return true;
				}
			} else if (args[i].equals("-INTMODE")) {
				try {
					i++;
					if (args[i].equalsIgnoreCase("rank")) {
						Globals.INTMODE = Globals.INTENUM.RELRANK;
					}
					else if (args[i].equalsIgnoreCase("int")) {
						Globals.INTMODE = Globals.INTENUM.RELINT;
					}
					else {
						Globals.SysError("Error: INTMODE must be RELRANK or RELINT");
						return true;
					}
				} catch (Exception e) {
					Globals.SysError("Error: INTMODE must be RELRANK or RELINT");
					return true;
				}
				
			/***** Advanced Confidence Options ********/
			} else if (args[i].equals("-CONFMODE")) {
				try {
					i++;
					if (args[i].equalsIgnoreCase("dscore")) {
						Globals.CONFMODE[confcnt] = Globals.CONF.DSCORE;
						Globals.CONFCUT[confcnt] = Double.parseDouble(args[++i]);
					}
					else if (args[i].equalsIgnoreCase("zscore")) {
						Globals.CONFMODE[confcnt] = Globals.CONF.ZSCORE;
						Globals.CONFCUT[confcnt] = Double.parseDouble(args[++i]);
					}
					else if (args[i].equalsIgnoreCase("dcdistance")) {
						Globals.CONFMODE[confcnt] = Globals.CONF.DCDISTANCE;
						Globals.CONFCUT[confcnt] = Double.parseDouble(args[++i]);
					}
					else {
						Globals.SysError("Error: CONFMODE must be DSCORE, ZSCORE or DCDISTANCE");
						return true;
					}
					confcnt++;
				} catch (NumberFormatException e) {
					Globals.SysError("Error: The confidence value must follow the confidence mode");
					return true;
				} catch (Exception e) {
					Globals.SysError("Error: CONFMODE must be DSCORE, ZSCORE or DCDISTANCE, or too many CONFMODE arguments");
					return true;
				}			
			} else {
				Globals.SysError("Error: Invalid Option " + args[i]);
				Usage();
				return true;
			}
			i++;
					
		}
		
		if (infile == null || outf == null) {
			
			Globals.SysError("Error: -s and -o are required");
			return true;
		}
		outfile = new File(outf);
		if (!infile.exists()) {
			Globals.SysError("Error: No file or directory found with that name");
			return true;
		}
		else if (outfile.isDirectory()) {
			outfile = new File(outf + File.separator + "PhosphoScore.txt");
			errfile = new File(outf + File.separator + "PhosphoScore.err");
		} else {
			outfile = new File(outf + ".txt");
			errfile = new File(outf + ".err");
		}
	
		return false;
	}
	

	
	// Processes one Scan file at a time
	private static int ProcessOneScan(String scan, String path) {
		
		
		Spectrum S;
		
		double parent = 0, nlpeak = 0;
		int charge = 0;
		StringBuilder refseq = null;;
		Modifier[] perm=null;
				
		Scorer Pscorer;
		Score  Pscore;
		
		// Process the information for this spectrum/scan/outfile.  The current file is kept in the interface
		// class
		if (Interface.Process() > 0) {
			return 1;
		}
		
		S = new Spectrum(Interface.getPeaks());
		
		charge = Interface.getCharge();
		
		refseq = Interface.getSeq();
		
		parent = Interface.getParent();
	    
		perm   = Interface.getPerm();
		
		int sites = Interface.getSites();
		
		// Error conditions.  If the interface did not return the necessary data to run PhosphoScore, 
		// write an error.
	    if (S == null) {
	    	Error(scan, charge, mode, "UNable to create spectrum", 0);
	    	return 1;
	    }
	    if (charge == 0 || charge > Globals.MAXZSTATES) {
	    	Error(scan, charge, mode, "Charge state not found or Charge state is greater than " + String.valueOf(Globals.MAXZSTATES), 0);
	    	return 1;
	    }
	    if (parent == 0) {
	    	Error(scan, charge, mode, "Parent ion weight not found", 0);
	    	return 1;
	    }
	    if (refseq == null) {
	    	Error(scan, charge, mode, "Ref Seq not found", 0);
	    	return 1;
	    }

	    // The # of phospho sites is limited by the MAXIONS.
        if (sites > Globals.MAXIONS) {
        	Error(scan, charge, mode, "Too Many Phosphorylation Sites", 0);
        	return 1;
        } 	  
        
        // don't do this one if there is not more than 1 site
	    if (sites < 1) {
        	Error(scan, charge, mode, "No/Not Enough Phosphorylated Sites Found", 0);
        	return 1;
        }

	
        if (debug) 
        	System.out.println("Ref Seq Found " + refseq.toString());
		
	    // Determine if there is a neutral loss peak.  If there is a peak at the m/z of
	    // the NL peak, remove it (and it is the top peak)
	    nlpeak = (((parent+charge)-1)-(Globals.NLWEIGHT))/charge;
	    if (java.lang.Math.abs(nlpeak - S.mz(S.max_index())) < Globals.MATCHTOL) {
	    	S.set(S.max_index(), 0);
	    	if (debug) 
	    		System.out.println("Removing NL peak at " + String.valueOf(nlpeak));
	    }

        
	    // Should be all set to run now.
	    Pscorer = new Scorer(S, refseq.toString(), charge, parent, perm, mode, debug);
	    Pscore = Pscorer.GetScore(debug);
	    if (Pscore.error == 0) {
	    	Pscore.peptide.append(Interface.getCterm());
	    	Pscore.peptide.insert(0, Interface.getNterm());
	    	Stats.add(scan, charge, Interface.getProtRef(), Pscore);
	    } else {
	    	Error(scan, charge, mode, "Scorer Returned an Error", 0);
	    }
	   
	    return 0;
	    
		
	}
	
	
	private static void Usage() {
		
		System.out.printf("\n");
		System.out.printf("Phosph-Proteomic Mass Spec Scoring Scheme (v1.0)\n");
		System.out.printf("Developed at the LKEM, NHLBI, NIH\n");
		System.out.printf("Contact Brian Ruttenberg (berutten@cs.ucsb.edu, ruttenberg@gmail.com) or\n");
		System.out.printf("Dr Mark Knepper (knepperm@nhlbi.nih.gov) for more information\n");
		
		System.out.printf("\n");
		System.out.printf("Usage: java PhosphoScore -s|-d <file|directory> -o <outfile> <options>\n");
		System.out.printf("\n");
		System.out.printf("-s <file>\t\t-The scan file name to analyze. The standard turboSEQUEST outfile format\n");
		System.out.printf("\t\t\tis expected (ex: sample2_051220.2784.2784.3).  (Required if no -d)\n");
		System.out.printf("\n");
		System.out.printf("-d <dir>\t\t-Analyze all the turboSEQUEST outfiles in the specified directory (Required if no -s)\n");
		System.out.printf("\n");
		System.out.printf("-o <file>\t\t-Outfile to place results.  The mode will be appended to the name of the file (Required)\n");
		System.out.printf("\n");
		System.out.printf("-m <MODE>\t\t-Determines the method that the phospho trees are analyzed (optional)\n");
		System.out.printf("\t\t\t\t0:  All charges and ions have independent trees and the best path is selected from\n");
		System.out.printf("\t\t\t\tall the trees\n");
		System.out.printf("\t\t\t\t1:  All ions are aggregated into one tree and the best path is selected from the\n");
		System.out.printf("\t\t\t\taggregated trees\n");
		System.out.printf("\t\t\t\t2:  All charges are aggregated into one tree and the best path is selected from the\n");
		System.out.printf("\t\t\t\taggregated trees\n");
		System.out.printf("\t\t\t\t3:  Charges and ions are aggregated into one tree and the best path is selected\n");
		System.out.printf("\t\t\t\tfrom the aggregated tree\n");
		System.out.printf("\n");
		System.out.printf("-aaweights <file>\t-Changes the default file name with the Amino Acid weights (default AAWeights.txt)\n\n");
		System.out.printf("-d\t\t\t-Debug mode.  Lots of extra output (optional)\n");
		System.out.printf("\n");
		System.out.printf("-h\t\t\t-Usage\n\n");
		
		System.out.println("----Advanced Scoring Options----\n");
			
		System.out.printf("-MATCHTOL <value>\t-Sets the match tolerance from the spectrum to the ideal m/z.\n");
		System.out.printf("\t\t\t The Standard deviation of the normal curve is MATCHTOL/2 (default 1.0)\n\n");
		System.out.printf("-THRESH <value>\t\t-Sets the cutoff value for peaks.  Should be a decimal such as .05/.1 (default 0)\n\n");
		System.out.printf("-INTEXP <value>\t\t-Sets the exponent on the intensity cost function (default 2)\n\n");
		System.out.printf("-MATCHWEIGHT <value>\t-Sets the weight to place on matching for the cost function (default .5)\n\n");
		System.out.printf("-INTWEIGHT <value>\t-Sets the weight to place on intensity for the cost function (default 1.5)\n\n");
		System.out.printf("-DEFAULTCOST <value>\t-Sets the default cost of a non match in the tree (default 2)\n\n");
		System.out.printf("-INTMODE <rank|int>\t-Sets the mode to use for the intensity. rank is the relative rank\n" +
							"\t\t\t of a peak, int is the relative intensity(default rank)\n\n");
		
		System.out.println("For additional information, please see the readme");
		
	}
	
	public static void Error(String scan, int charge, int mode, String err, int exit) {
		
		errwriter.printf("%s\t%d\t%d\t%s\n", scan.substring(0, scan.length()-4), charge, mode, err);
		errwriter.flush();
		if (exit == 1) {
			System.exit(1);
		}
		
	}
	
	
	
	
	
}

