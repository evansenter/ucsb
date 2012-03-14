import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/*
 * Class SequestInterface
 * 
 * This class is used to interface between the PhosphoScoring scheme and the Sequest input files.
 * 
 * Ideally, this should be a super class, but that might come later.  If a different type of MS is used,
 * such as mascot, and interface class can be created for that MS that reads/interprets that MS's output
 * files.  All the new class needs to do is create the same exisiting function calls as this interface
 * and the PhosphoScoring won't know the difference.
 * 
 * The data that is necessary to run is found in the getXXX() functions. The init() and Process() function
 * can be commented out in PhosphoScore, as long as you can get the required information to the getXXX() 
 * functions.
 * 
 * 
 * 
 */


public class SequestInterface {

	String[] inlist;
	String path;
	int position;
	Spectrum.Peak[] peaks;
	boolean debug = false;
	
	double[] protein_terms = new double[2];
	
	double parent = 0;
	int charge = 0;
	StringBuilder refseq;
	Modifier[] perm;
	String Cterm = null, Nterm = null, ProtRef = null;
	int sites;
	
	
	public SequestInterface(boolean d) {
		debug = d;
	}
	
	
	public static class DTAFilter implements FilenameFilter {
		public boolean accept(File directory, String filename) {
			if (filename.endsWith(".dta")) return true;
			return false;	 
		}
 	}
	
	public static class ParamsFilter implements FilenameFilter {
		public boolean accept(File directory, String filename) {
			if (filename.endsWith(".params")) return true;
			return false;	 
		}
 	}
	
	
	// Intializes the file or list of files into the inlist array and sets the position to -1
	public void init(File infile) {
		
		// Process additional parameters that are in the sequest.params file, if found
		ProcessSequestParams(infile);
		
		// loop through the directory of files or one file
		if (!infile.isDirectory()) {	
			inlist = new String[1];	
			inlist[0] = infile.toString().substring(0, infile.toString().length()-3) + "dta";
			path = "";
		} else {
			inlist = infile.list(new DTAFilter());
			path = infile.getPath() + File.separator;
		}
		
		position = -1;
		
	}

		

	
	// Process the necessary information for this scan file.  This is needed for the SequestInterface, but
	// is optional if another one is implemented.
	public int Process() {
		
		perm = null;
		BufferedReader outreader = null;
		String line;
		boolean xon = false;
		
		double XCorr = 0;
		String origseq = null;
		Modifier[] perm_t;
		
		//Reads the .dta file into the Peaks[] array
		ReadSpectrum();
    	
			
		// Open the .out file to parse
		try {			
			outreader = new BufferedReader(new FileReader(path + inlist[position].substring(0, inlist[position].length()-3) + "out"));
	    } catch (FileNotFoundException e) {
	    	Globals.SysError("Error:  The .out file for " + path + inlist[position].toString() + " not found!");
	    	return 1;
	    }
    
	
	    /* Loop through the .out file to find the parent ion weight, and the peptide
	     * also filter based on XCorr.  
	     * 
	     *  This portion is VERY sensitive to the format of the turboSEQUEST file. 
	     *  May need to be changed if the file format changes.
	     */     
	    try {
	    	line = outreader.readLine();
	    	for (int i, j, xcorr, pep, ref; line != null; line = outreader.readLine()) {
	    		
	    		// Find parent ion
	    		i = line.indexOf("mass = ");
	    		if (i > -1) {
	    			j = line.indexOf(' ', i+7);
	    			parent = Double.parseDouble(line.substring(i+7, j));	
	    			if (debug)
	    				System.out.println("Parent ion of " + String.valueOf(parent) + " Found");
	    		}
	    		
	    		// Read the first line of the search results.  That is the 
	    		// candidate for Phospho searching.
	    		xcorr = line.indexOf("XCorr");
	    		pep = line.indexOf("Peptide");
	    		ref = line.indexOf("Reference");
	    		if (xcorr > -1 && pep > -1) {
	    			line = outreader.readLine();
	    			line = outreader.readLine();
	    			try {
	    				XCorr = Double.parseDouble(line.substring(xcorr-1, xcorr+5));
	    				while (line.substring(pep,pep+1).matches("[\t| ]")) {
	    					pep++;
	    				}
	    				origseq = line.substring(pep+2, line.length()-2);
	    				ProtRef = line.substring(ref, pep-1);
	    				Nterm = line.substring(pep, pep+2);
	    				Cterm = line.substring(line.length()-2, line.length());
	    			} catch (Exception e) {
	    				PhosphoScore.Error(inlist[position], charge, 3, "Data in .out file is incorrect!", 0);
	    				return 1;
	    			}
	    			if (debug)
	    				System.out.println("Sequence of " + origseq.toString() + " Found");
	    			
	    			break;
	    		}
	    		
	    		
	    	}
	    } catch (IOException e) {
	    	Globals.SysError("Error:  Reading the .out file for parsing");
	    	return 1;
	    }

		// Get charge state from file name
	    charge = Integer.parseInt(inlist[position].substring(inlist[position].length()-5, inlist[position].length()-4));
	    
	    // skip this peptide if the sequence was not found
	    if (origseq == null) {
	    	PhosphoScore.Error(inlist[position], charge, 3, "No Sequence Found", 0);
	    	return 1;
	    }
    
	    // skip this peptide if XCorr too low
	    if (XCorr < (Globals.XCORRCUTOFF + charge*.5) && xon) {
	    	PhosphoScore.Error(inlist[position], charge, 3, "XCorr too Low", 0);
	    	return 1;
	    }
	      
	    
		// pull the peptide sequence out (need to remove #, * and @)
	    // also determine if there are any @'s (a variable modification on M)
	    // THIS IS WHERE NEW MODIFICATIONS CAN BE ENTERED
	    
	    refseq = new StringBuilder();
	    int m = 0, v = 0;
	    boolean isphospho = false;
	    for (int i = 0; i<origseq.length(); i++) {
	    	
	        // M aa can have a variable modification of 16.  Only insert this in
	        // if turboSEQUEST says so
	        if (origseq.charAt(i) == 'M') {
	        	refseq.append(origseq.charAt(i));
	        	if (i+1 < origseq.length() && !Character.isLetter(origseq.charAt(i+1))) {
	        		v--;
	        		i++;
		        	if (perm == null) {
		        		perm = new Modifier[1];	
		        		perm[0] = new Modifier(i+v, Globals.VARMODWEIGHT[0]);
		        	} else {
			        	perm_t = perm;
			        	perm = new Modifier[perm_t.length+1];
			        	System.arraycopy(perm_t, 0, perm, 0, perm_t.length);
			        	perm[perm_t.length] = new Modifier(i+v, Globals.VARMODWEIGHT[0]);
		        	}
	        	}
	        	
	        /*
	         * New modifications can be inserted here.  Since we only want to check for STY 
	         * in the phospho tree, if you have other variable modifications (such as M) you can add them 
	         * here in the same manner as the 'M' above
	         * 
	         *  Future support may do this automatically
	         */
	        } else if (false) {
	        	/*
		        if (origseq.charAt(i) == CHARACTER) {
		        	refseq.append(origseq.charAt(i));
		        	if (!Character.isLetter(origseq.charAt(i+1))) {
		        		v--;
		        		i++;
			        	if (perm == null) {
			        		perm = new Modifier[1];	
			        		perm[0] = new Modifier(i+v, Globals.VARMODWEIGHT[?]);
			        	} else {
				        	perm_t = perm;
				        	perm = new Modifier[perm_t.length+1];
				        	System.arraycopy(perm_t, 0, perm, 0, perm_t.length);
				        	perm[perm_t.length] = new Modifier(i+v, Globals.VARMODWEIGHT[?]);
			        	}
		        	}
	        	*/
	        	
	        }
	        // only true phospho sites should go though this if clause
	        else if (!Character.isLetter(origseq.charAt(i))) {
	            v--;
	            isphospho = true;
	        } else if (origseq.charAt(i) == 'X') {
	        	PhosphoScore.Error(inlist[position], charge, 3, "Illegal Amino Acid", 0);
	        } else {
	        	refseq.append(origseq.charAt(i));
	        	if (origseq.charAt(i) == 'S' || origseq.charAt(i) == 'T' || origseq.charAt(i) == 'Y') {
	        		m++;
	        	}
	        }
	     
	        
	    }

	    sites = m;
	    
	    if (!isphospho) {
	    	PhosphoScore.Error(inlist[position], charge, 3, "Peptide is Not Phosphorylated", 0);
        	return 1;
        }
	
        if (debug) 
        	System.out.println("Ref Seq Found " + refseq.toString());
	    
        // Add permament modifiers if the sequest.params file specified that the 
        // N or C term of the protein are to have a modification
	    if (protein_terms[0] != 0 && Nterm.matches("-")) {
	    	if (perm == null) {
        		perm = new Modifier[1];	
        		perm[0] = new Modifier(0, protein_terms[0]);
        	} else {
	        	perm_t = perm;
	        	perm = new Modifier[perm_t.length+1];
	        	System.arraycopy(perm_t, 0, perm, 0, perm_t.length);
	        	perm[perm_t.length] = new Modifier(0, protein_terms[0]);
        	}
	    }
	    if (protein_terms[1] != 0 && Cterm.matches("-")) {
	    	if (perm == null) {
        		perm = new Modifier[1];	
        		perm[0] = new Modifier(refseq.length(), protein_terms[1]);
        	} else {
	        	perm_t = perm;
	        	perm = new Modifier[perm_t.length+1];
	        	System.arraycopy(perm_t, 0, perm, 0, perm_t.length);
	        	perm[perm_t.length] = new Modifier(refseq.length(), protein_terms[1]);
        	}
	    }
        
	return 0;
	}
	
	/* 
	 * The sequest.params file is optional.  If provided, it will use the amino acid modifications
	 * at the bottom of the file and the match tolerance from the file (which can still be overridden
	 * from the command line).  Right now the variable modifications are NOT used, rather they are hardcoded
	 * in.  SYT and always variable at -18 and +80, and in our experiments, M has a variable modification of +16. 
	 * Since we don't want our tree to build non-phosphorylated variable modifications (ie, let sequest decide
	 * if an M is +16), we can just have them as permament modifiers. 
	 * 
	 * If the sequest file specifies a modification on the N or C term of the protein, those values are returned
	 * and the main function will add those as permanent modifiers.  If the N or C term of a peptide has a modification
	 * on it, those are hashed in the amino acid weights hash table.
	 */
	private void ProcessSequestParams(File infile) {
		
		FileReader sequest_file = null;
		BufferedReader sequest_rd  = null;
		protein_terms[0] = protein_terms[1] = 0;
		String[] paramlist = null;
		File parent;
		
		// Look for the sequest file
		try {
			if (!infile.isDirectory()) {
				parent = new File(infile.getParent());
				paramlist = parent.list(new ParamsFilter());
				//System.out.printf("Using %s as sequest.params file", paramlist[0]);
				sequest_file = new FileReader(new File(infile.getParent() + File.separator + paramlist[0]));
				
			} else {
				paramlist = infile.list(new ParamsFilter());
				//System.out.printf("Using %s as sequest.params file", paramlist[0]);
				sequest_file = new FileReader(new File(infile.getPath() + File.separator + paramlist[0]));

			}
		} catch (Exception e) {
			System.out.println("Sequest params not found, using defaults");
		
			return;
		}
		
		sequest_rd = new BufferedReader(sequest_file);
        String line;
        String[] split_line, space_split;
        
        
        try {
        	for (line = sequest_rd.readLine(); line != null; line = sequest_rd.readLine()) {
        		
        		// Set the match tolerance to what is specified in the sequest file unless
        		// the match tolerance was set on the command line
        		if (line.matches("match_peak_tolerance.*") && !PhosphoScore.matchtol_set) {
        			split_line = line.split(" +", 4);
        			Globals.MATCHTOL = Double.parseDouble(split_line[2]);
        		} else if (line.matches("add.*")) {
        			
        			// put the variable modification into the hash table, unless it is on the C or N
        			// of a protein.
        			split_line = line.split("_");
        			space_split = line.split(" +");
        			
        			if (line.matches(".*Nterm_protein.*") && Double.parseDouble(space_split[2]) != 0) {
        				protein_terms[0] = Double.parseDouble(space_split[2]);
        			} else if (line.matches(".*Cterm_protein.*") && Double.parseDouble(space_split[2]) != 0) {
        				protein_terms[1] = Double.parseDouble(space_split[2]);
        			} else if (Globals.WEIGHTS.containsKey(split_line[1]) && Double.parseDouble(space_split[2]) != 0) {
        				Globals.WEIGHTS.put(split_line[1], Globals.WEIGHTS.get(split_line[1])+Double.parseDouble(space_split[2]));
        			}  			
        			
        		}
        		
        	}
        } catch (IOException e) {
        	Globals.SysError("Error: Reading from sequest.params file.");
        }
        
 	}
	
	// Reads the spectrum from the .dta file and stores it in a Peaks[] array.  The array will later
	// be put into the Spectrum class which handles spectrums.
	private void ReadSpectrum() {
		FileReader specreader = null;
		List<String> lines = new LinkedList<String>();
		String line;
		String temp_vals[] = new String[2];
		
		try {
			specreader = new FileReader(path + inlist[position]);
	    } catch (FileNotFoundException e) {
	    	Globals.SysError("Error: Spectrum file " + path + inlist[position] + " not found!");
	    	return;
	    }
	  	try {
	    	LineNumberReader reader = new LineNumberReader(specreader);
	    	while (reader.ready()) {
	    		lines.add(reader.readLine());
	    	}
	    	reader.close();
	    } catch (IOException e) {
	    	Globals.SysError("Error:  Could not read spectrum file");
	    	return;
	    }
	    
	    
	    peaks = new Spectrum.Peak[lines.size()];
	    
	    for (int i=0; i < lines.size(); i++) {
	    	line = lines.get(i);
	    	temp_vals = line.split(" +|\t+");
	    	peaks[i] = new Spectrum.Peak(Double.parseDouble(temp_vals[0]), Double.parseDouble(temp_vals[1]));	    
	    }
	    
	    // peaks are kept in sorted order
	    Arrays.sort(peaks);
	}
	
	// Accessor functions.  These functions MUST be implemented for another interface.
	
	public String getNext() { 
		if (++position == inlist.length) {
			return null;
		}
		return inlist[position]; 
	}
	
	public int getLength() { return inlist.length; }
	
	public String getPath() { return path; }
		
	public Spectrum.Peak[] getPeaks() { return peaks; }
		
	public int getCharge() { return charge; }
	
	public StringBuilder getSeq() { return refseq; }
	
	public double getParent() { return parent; }
    
	public Modifier[] getPerm() { return perm; }
	
	public int getSites() { return sites; }
	
	public String getCterm() { return Cterm; }
	
	public String getNterm() { return Nterm; }
	
	public String getProtRef() { return ProtRef; }
	
}
