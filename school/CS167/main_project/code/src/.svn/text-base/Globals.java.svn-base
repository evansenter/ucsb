import java.util.Hashtable;


/* Contains globals and constants that will be used throughout
 * 
 * Some stuff can be changed at the command line,
 * 
 *  The intent is that these are advanced command line options that 
 *  will not be changed often
 *  
 *  Anything with a final in it should not (and will not) be changed by the command line   
 * 
 */


public class Globals {

	enum INTENUM {
		RELINT,
		RELRANK
	}
	
	enum CONF {
		DSCORE,
		DCDISTANCE,
		ZSCORE
	}
	
	/***** Advanced Scoring Options ********/
	
	// The tolerance of m/z for a match in the spectrum to occur.  The
	// Standard deviation of the normal curve will be MATCHTOL/2
	// This usually set by the sequest params file but can be overriden on the command line
	public static double MATCHTOL      	= 1; //1000;

	// Threshold to cut matches below.  SHould be a fractional number, such as .05 or .1.
	// Will not match with any peak in the bottom THRESH of peaks
	public static double THRESH      	= 0;
	
	// The exponent to apply to the normalized intensity of a match 
	public static double INTEXP	       	= 1.0;
	
	// The weights given to the match and intensity in calculating the cost
	public static double MATCHWEIGHT   	= .725; 
	public static double INTWEIGHT     	= 1.275;
	
	// The default cost of a non-match
	public static double DEFAULTCOST   	= 1.8;
	
	// Determines what value to use for the intensity.  Default is to use
	// the relative ranking of the intensity (rank/(total # of peaks)).  Other 
	// mode is to use the relative intensity (intensity/max intensity).
	public static INTENUM INTMODE		= INTENUM.RELRANK; 
	
	
	/***** Advanced Confidence Options ********/
	
	// The CONFMODE and CONFCUT are used to define ambiguous peptides.
	// There are three CONFMODEs: dscore, pvalue and zscore (though zscore and pvalue are generally equivalent)
	//  You can define up to 2 confmodes and they are interpreted as ANDs.  Example:
	
	// -CONFMODE DSCORE .03 - will mark all peptides with a dscore < .03 as ambiguous
	// -CONFMODE ZSCORE -1.2 - will mark all peptides with a zscore > -1.2 as ambiguous
	// -CONFMODE PVALUE .05 -CONFMODE DSCORE .02 - will AND the two conditions
	public static CONF[] CONFMODE		= new CONF[3];
	
	public static double[] CONFCUT		= new double[3];
	
	
	/***** Advanced General Options ********/
	
	public static boolean INTERACTIVE	= false;
	
	/***** Global Constants.  These are set by the sequest params file *****/
	
	// The encoding of nodes/modifiers to atomic weights.  Should not be 
	// changed unless the turboSEQUEST results change.
	// Right now this is HARDCODED for SYT phosphorylation
	public static final double[] MODENC = {0.0, -18, 79.9663};
	
	// Weight of amino acid M in the variable modification.
	// To add new variable modifications  you can put them in here and 
	// add a section in PhosphoScore.java to make a permanent modifier for it
	public static final double[] VARMODWEIGHT = {15.9949};
	
	// Amino Acid  Monoisotopic Hash table and weights.
	public static Hashtable<String, Double> WEIGHTS = new Hashtable<String, Double>(22,22);
	
	public static void INIT_WEIGHTS() {
		
		WEIGHTS.put("G", 57.021464);
		WEIGHTS.put("D", 115.02694);
		WEIGHTS.put("A", 71.037114);
		WEIGHTS.put("Q", 128.05858);
		WEIGHTS.put("S", 87.032029);
		WEIGHTS.put("K", 128.09496);
		WEIGHTS.put("P", 97.052764);
		WEIGHTS.put("E", 129.04259);
		WEIGHTS.put("V", 99.068414);
		WEIGHTS.put("M", 131.04048);
		WEIGHTS.put("T", 101.04768);
		WEIGHTS.put("H", 137.05891);
		WEIGHTS.put("C", 103.00919);
		WEIGHTS.put("F", 147.06841);
		WEIGHTS.put("L", 113.08406);
		WEIGHTS.put("R", 156.10111);
		WEIGHTS.put("I", 113.08406); 
		WEIGHTS.put("N", 114.04293); 
		WEIGHTS.put("Y", 163.06333);
		WEIGHTS.put("W", 186.07931);
		WEIGHTS.put("Cterm", 0.0);
		WEIGHTS.put("Nterm", 0.0);
		
	}
		
	
	
	/***** Static Global Constants (No option to change at run time) ********/
	
	// Maximum number of ion sites supported.  Any more than this will
	// overflow the integer precision
	public static final int	MAXIONS		   	= 13;
		
	// Max number of charge states supported
	public static final int MAXZSTATES 	= 4;
	
	// Max number of matches for an ideal m/z to the spectrum
	public static final int MAXMATCHES 	= 30;
	
	// Weight of water
	public static final double WATERWEIGHT = 18.0;
	
	// Neutral loss weight
	public static final double NLWEIGHT = WATERWEIGHT + 79.9663;
		
	// XCORR cutoff for each charge state 
	public static final double XCORRCUTOFF = 1.0;
	
	public static boolean SysError (String msg) {
		System.out.println(msg);
		if (!INTERACTIVE) {
			System.exit(1);
		}
		return INTERACTIVE;
	}

}
