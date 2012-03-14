/* Class to generate and handle peptide profiles 
 * Amino Acid weights are read from the specified amino acid file
 * and stored in a hash table based on the their AA letter.
 * 
 *  In the future, it may be more efficient to hardcode these weights
 */


public class Profile {
	
	private Modifier[] PermMod;
	private double[] profile;
	

	public Profile() {
		
	}
	
	
	// Permanent Modifiers are profile modifiers that are constant for this instantiation 
	// of this class.  Such things as the variable modification for 'M' at +16
	public void SetPermMod(Modifier M) {
		
		if (PermMod == null) {
			PermMod = new Modifier[1];
			PermMod[0] = M;
		} else {
			Modifier[] tempM = PermMod;
			PermMod = new Modifier[tempM.length+1];
			System.arraycopy(tempM, 0, PermMod, 0, tempM.length);
			PermMod[PermMod.length-1] = M;
		}
	}
	
	/* Generates a profile given the parameters
	 * 
	 * The profile is generated given the seq, ion and the charge, with the modifications
	 * given in the array of modifiers
	 * 
	 * This generates the full profile.  The overloaded function below only goes up to the 
	 * specified location
	 */
	public void CreateProfile(String Seq, char IonType, int IonCharge, Modifier[] M) {
		
		CreateProfile(Seq, IonType, IonCharge, M, Seq.length());
			
	}
	
	// Same as above but only goes up to int Max
	public void CreateProfile(String Seq, char IonType, int IonCharge, Modifier[] M, int Max) {
		
		profile = new double[Max];
		double water = (IonType == 'y') ? Globals.WATERWEIGHT + Globals.WEIGHTS.get("Cterm").doubleValue()
				: Globals.WEIGHTS.get("Nterm").doubleValue();
		
		for (int i=0; i< Max; i++) {
			if (i == 0) {
				profile[i] = water + Globals.WEIGHTS.get(Seq.substring(i, i+1)).doubleValue();
			} else {
				profile[i] = profile[i-1] + Globals.WEIGHTS.get(Seq.substring(i, i+1)).doubleValue();
			}
			
			if (M != null) {
				for (int j=0; j< M.length; j++) {
					if (M[j].pos == i) {
						profile[i] += M[j].val;
					}
				}
			}
			
			if (PermMod != null) {
				Modifier[] PermMod_t = new Modifier[PermMod.length];
				if (IonType == 'y')
					ReversePerm(Seq.length(), PermMod_t);
				else
					PermMod_t = PermMod;
				
				
				for (int j=0; j< PermMod_t.length; j++) {
					if (PermMod_t[j].pos == i) {
						profile[i] += PermMod_t[j].val;
					}
				}
			}
			
					
		}
		
		for (int i=0; i< Max; i++) {
			profile[i] = (profile[i] + IonCharge)/IonCharge;
		}
			
	}

	// Return the value of a profile at the given position
	public double GetProfile(int pos) {
		return profile[pos];
	}
	
	// Same as above but a block of a profile.
	public void GetProfile(int start, int stop, double data[]) {
		System.arraycopy(profile, start, data, 0, (stop-start)+1);
	}
	
	/* Calculates the cost of a portion of a profile against a MS spectrum.  The lower the cost
	 * the better since the graph functions look for the shortest path tree.
	 * 
	 * There are two parts to the cost. For a single amino acid, 
	 * Cost = (Alignment Weight) * Alignment Cost + (Intensity Weight) * Intensity Cost
	 * 
	 * Where alignment cost is the area under a normal curve between the difference
	 * between the expected m/z and the actual m/z, with a given std dev.
	 * 
	 * Intensity Cost is normalized intensity of this peak to the most intense peak on
	 * the spectrum, adjusted exponentially.  
	 * 
	 * The weights are currently based on discussion with investigators on the importance
	 * of intensity vs alignment when determining phospho sites.  Subject to change
	 * 
	 * The default cost is 2 if no match can be found.  This is geared to be slightly
	 * worse than a match that is noise.
	 * 
	 */
	
	public double Cost(int start, int stop, Spectrum spectrum) {
	
		int[] matches = new int[Globals.MAXMATCHES];
		double total_cost = 0;
		double MATCHTOL;
		
		for (int aa=start; aa <= stop; aa++) {
			
			int count = 0;
	        double cost = Globals.DEFAULTCOST;
	        double cost_t;
      
	       	// Match tolerance is a function of the mass to charge of the theoretical peak. 
		// 1 kDa/ppm	
		MATCHTOL = Globals.MATCHTOL; //(profile[aa]*Globals.MATCHTOL)/1000000;

	        // find all matches within stdev * 2.  If more than one match will take the
	        // one with the lowest cost
	        for (int i=0; i<spectrum.length(); i++) {
	        	
                if (java.lang.Math.abs(spectrum.mz(i) - profile[aa]) < MATCHTOL && i > spectrum.length() * Globals.THRESH) {
                        matches[count] = i;
                        count++;
                }
	        }

	        if (count > 0) {
                for (int i=0; i < count; i++) {
				// Match weight
                	cost_t = Globals.MATCHWEIGHT * 
				// Area of the square from the mean to the observed
				((2 *   normalPdf((double) MATCHTOL/2)* java.lang.Math.abs(spectrum.mz(matches[i])-profile[aa])) -

				// Area under the curve
				(2 *	java.lang.Math.abs
                			(.5 - ProbMath.normalCdf((double) (spectrum.mz(matches[i])-profile[aa])/(MATCHTOL/2)))));
                	
                	//double spec_diff = (double) java.lang.Math.abs(spectrum.mz(matches[i])-profile[aa]);
                	//double lower_bound = -Globals.MATCHTOL;
                	//double cdf = 2.0 * ProbMath.normalCdf((spec_diff+lower_bound)/(Globals.MATCHTOL/2));
                	//cost_t = Globals.MATCHWEIGHT * (cdf  - 0.0455);
                	
                
                	
                	// Use relative rank or relative intensity based on parameters
                	// if RELRANK mode, use rank/(# of peaks).  else use intensity/max intensity
                	if (Globals.INTMODE == Globals.INTENUM.RELRANK) {
                		cost_t = cost_t + (Globals.INTWEIGHT *
                			java.lang.Math.pow((double) 1 - (double) matches[i]/spectrum.max_index(), Globals.INTEXP));
                	}
                	else {
                		cost_t = cost_t + (Globals.INTWEIGHT * 
                			java.lang.Math.pow((double) 1 - (double) spectrum.intensity(matches[i])/spectrum.max(), Globals.INTEXP));
                	}
                    if (cost_t < cost) {
                            cost = cost_t;
                    }
                }
	        }
	        
	        total_cost += cost;
		}
		return total_cost;
	}
	
	// Reverse a perm modifier for Y ions
	private void ReversePerm(int length, Modifier PermMod_t[]) {
		for (int i=0; i<PermMod.length; i++) {
			PermMod_t[i] = new Modifier((length-PermMod[i].pos)-1, PermMod[i].val); 
		}
	}
	
	// Calculate the height of a normal curve at the mean with u=0 and sigma an input
	// ONLY SPECIFIC FOR THIS FUNCTION, NOT A GENERAL NORMAL PDF FUNCTION
	private double normalPdf(double sigma) {
		
		double cons = 1/(java.lang.Math.sqrt(2*java.lang.Math.PI)*sigma);
		return cons;	

	}

}
