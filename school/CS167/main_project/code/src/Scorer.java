

public class Scorer {

	Graph[] graph;
	int zstate;
	int mode;
	StringBuilder refseq;
	double mean, stddev;
	Modifier[] M;
			
	
	public Scorer(Spectrum S, String Seq, int IonCharge, double Parent, Modifier[] Perm, int gmode) {
		ScorerInit(S, Seq, IonCharge, Parent, Perm, gmode, false); 
		
	}
	
	public Scorer(Spectrum S, String Seq, int IonCharge, double Parent, Modifier[] Perm, int gmode, boolean debug) {
		ScorerInit(S, Seq, IonCharge, Parent, Perm, gmode, debug); 
	}
	
	
	private void ScorerInit(Spectrum S, String Seq, int IonCharge, double Parent, Modifier[] Perm, int gmode, boolean debug) {
		
		M = null;
		
		if (gmode < 2) {
			graph = new Graph[IonCharge*2];
			for (int i=0; i< IonCharge*2; i++) {
				graph[i] = new Graph(S, Parent, Perm);
			}
		} else {
			graph = new Graph[2];
			for (int i=0; i< 2; i++) {
				graph[i] = new Graph(S, Parent, Perm);
			}	
		}
			
		if (debug) 
			System.out.println("Initializing Scorer with " + String.valueOf(graph.length) + " graphs");
		
		refseq = new StringBuilder(Seq);
		zstate = IonCharge;
		mode = gmode;
		if (Perm != null) {
			M = new Modifier[Perm.length];
			System.arraycopy(Perm, 0, M, 0, M.length);
		}
			
	}
		
	public PhosphoScore.Score GetScore() {
		return GetScore(false);
	}
	
	public PhosphoScore.Score GetScore(boolean debug) {
		
		int zmin;
		// leaves for all trees
		double[][] leaves = (mode < 2) ? 
							new double[zstate*2][] :
							new double[2][];
		
		// holds the leaves after combining certain trees
		double[][] Finalleaves;
		
		// shortest path (0) is the tree, (1) is the index in that tree
		int[] shortest = new int[2];
		int[] shortest2 = new int[2];
		double shortest_score = 0;
		
		// phospho sites 
		int[] psites;
		
		int sites = 0;
		int[] rsites;
		
		double zscore, tail, diff;
		StringBuilder pepseq = new StringBuilder();
		int startp, dirp, starts, ends, dirs;
		
			
		// mode 2 & 3 do only one loop since they combine the zstates 
		// in the graph building
		if (mode > 1) {
			zmin = zstate;
		} else {
			zmin = 1;
		}
		
		// Do b series then y series
		for (int i=zmin; i <= zstate; i++) {
			
			// Set the graph parameters (seq, charge and the type)
			graph[(1 - (int) mode/2)*(i-1)*2].SetParams(refseq.toString(), i, 'b');
			// build the graph
			graph[(1 - (int) mode/2)*(i-1)*2].Build(mode, debug);
			// copy all the leaf nodes
			graph[(1 - (int) mode/2)*(i-1)*2].GetLeaves(leaves, (1 - (int) mode/2)*(i-1)*2); 
			
			// if not in debugging mode, dereference the graphs to save memory
			if (!debug)
				graph[(1 - (int) mode/2)*(i-1)*2] = null;
			
			// same except for y ions
			graph[(1 - (int) mode/2)*(i-1)*2+1].SetParams(new StringBuilder(refseq.toString()).reverse().toString(), i, 'y');
			graph[(1 - (int) mode/2)*(i-1)*2+1].Build(mode, debug);
			graph[(1 - (int) mode/2)*(i-1)*2+1].GetLeaves(leaves, (1 - (int) mode/2)*(i-1)*2+1); 
			
			// Get how many ion sites there were.  Technically not needed (can reconstruct 
			// from the leaves), but makes looping faster (no dynamically growing arrays).
			sites = graph[(1 - (int) mode/2)*(i-1)*2+1].GetIonSites(); 

			if (!debug)
				graph[(1 - (int) mode/2)*(i-1)*2] = null;
			
			if (debug)
				System.out.println("Done building graph from zstate " + String.valueOf(i));
			
			if (!debug && sites > 10)
				System.gc();
			
			
		}
		
		int effect_length = refseq.length();
		
		if (mode == 3) {
			/* mode 3, combine b and y ions for the combined z state tree.  Start with 2 trees,
			 * should end with 1 tree
			 * This might be REALLY slow...
			 */
			Finalleaves = new double[1][];
			rsites = new int[sites];
			
			// Loop through each charge and combine y and b
			for (int z=0; z < 1; z++) {
				Finalleaves[z] = new double[leaves[z].length];
				
				/* Need to reverse the Y ions first since they are encoded backwards
				 * Only after that can they be combined.  For each node, decode its sites,
				 * reverse the sites, then re-encode.
				 */
				for (int node=0, r=sites; node < leaves[z].length; node++, r=sites) {
					
					// reverse
					for (int j : DecodeLeaf(node, sites)) {
						rsites[--r] = j;
					}
					// add the two nodes of the tree together
					Finalleaves[z][node] = leaves[z*2][node] + leaves[z*2+1][EncodeLeaf(rsites)];
				}			
			}
			effect_length = effect_length * 2 * zstate;
			
		} else if (mode == 1) {
			/* mode 1, combine b and y ions for each z state tree.  Start with charge*2 trees,
			 * should end with charge # of trees
			 * This might be REALLY slow...
			 */
			Finalleaves = new double[zstate][];
			rsites = new int[sites];
			
			// Loop through each charge and combine y and b
			for (int z=0; z < zstate; z++) {
				Finalleaves[z] = new double[leaves[z].length];
				
				/* Need to reverse the Y ions first since they are encoded backwards
				 * Only after that can they be combined.  For each node, decode its sites,
				 * reverse the sites, then re-encode.
				 */
				for (int node=0, r=sites; node < leaves[z].length; node++, r=sites) {
					
					// reverse
					for (int j : DecodeLeaf(node, sites)) {
						rsites[--r] = j;
					}
					// add the two nodes of the tree together
					Finalleaves[z][node] = leaves[z*2][node] + leaves[z*2+1][EncodeLeaf(rsites)];
				}			
			}
			effect_length = effect_length * 2;
			
		} else if (mode == 2) {
			effect_length = effect_length  * zstate;
			Finalleaves = leaves;	
						
		} else {
			Finalleaves = leaves;	
		}
		
		// Dereference leaves to save space
		if (!debug)
			leaves = null;
		
		// Find the shortest path over all the trees
		shortest = ShortestPath(Finalleaves, debug, true);
		if (debug)
			System.out.println("Shortest path is " + String.valueOf(shortest[0]) + " " +  String.valueOf(shortest[1]));
		
		// convert the leaf node with the shortest path to a phospho sites
		psites = DecodeLeaf(shortest[1], sites);
		
		// Calculate the mean and std dev so can calc the zscore
		// mean already calc in the shortest path.  Also returns the number of nodes
		int Ecount = StdDev(Finalleaves);
		
		if (debug)
			System.out.println("Mean is " + String.valueOf(mean) + " and Std Dev is " + String.valueOf(stddev));
		
		

		
		// find the second shortest to compute the diff between the first and second scores
		shortest_score = Finalleaves[shortest[0]][shortest[1]];
		Finalleaves[shortest[0]][shortest[1]] = 0;
		shortest2 = ShortestPath(Finalleaves, debug, false);
		diff = (Ecount > 1) ? (Finalleaves[shortest2[0]][shortest2[1]]-shortest_score)/shortest_score : 1;

		zscore = (Ecount > 1 && stddev != 0) ? (shortest_score - mean) / stddev : 0;
		//tail = (double) ProbMath.normalCdf(zscore);
		tail = Globals.DEFAULTCOST - shortest_score/effect_length;
		//tail = Globals.DEFAULTCOST - Finalleaves[shortest2[0]][shortest2[1]]/effect_length;
		
		// Create a string version of the peptide seq
		// but first flip the phospho sites if it is a B ion
		// reason b not y is that in the DecodeLeaf function, it actually 
		// goes backwards, so y's end up being correct but b's are reversed
		if ((mode == 0 || mode == 2) && shortest[0] % 2 == 1 ) {
			startp = psites.length-1;
			dirp = -1;
		} else {
			startp = 0;
			dirp = 1;
		}
		starts = 0;
		ends   = refseq.length();
		dirs   = 1;
		
		// generate string peptide sequence
		int inserted = 0;
		for (int s=starts, p=startp; s != ends; s += dirs) {
			pepseq.append(refseq.charAt(s));
			
			if (refseq.charAt(s) == 'Y' | refseq.charAt(s) == 'T' | refseq.charAt(s) == 'S') {
				if (psites[p] == 2) {
					pepseq.append("#");
					p += dirp;
					inserted++;
				}
				else if (psites[p] == 1) { 
					pepseq.append("*");
					p += dirp;
					inserted++;
				} else {
					p += dirp;
				}						
			}

			for (int i = 0; M != null && i < M.length; i++) {
				if (M[i].pos == s) {
					pepseq.append('@');
				}
			}
			
		}
		

		int[] n = {shortest[1], shortest2[1]};
		double[] d = {shortest_score, Finalleaves[shortest2[0]][shortest2[1]]};
		// error handling might not be needed...(last argument)
		return new PhosphoScore.Score(pepseq.toString(), zscore, tail, Ecount, diff, 0, d, n);
		
		
	}
	
	
	/* Finds the shortest path for the given trees
	 * 
	 * First finds the shortest path for each tree, then the shortest
	 * path among the shortest paths for each tree.  It could be done
	 * faster (looking at them all at one) but this is easier for debugging
	 * purposes.  returns the tree (shortest[0]) and path (shortest[1]) that it found
	 * 
	 * also calculates the mean of the trees to use the looping efficiently
	 */
	private int[] ShortestPath(double[][] Leaves, boolean debug, boolean calcmean) {
	
		double[] path = new double[Leaves.length];
		int[] index = new int[Leaves.length];
		double sum_t = 0;
		int count = 0;
		int[] shortest = new int[2];
		double shortestp = 10000000.0;
		
		java.util.Arrays.fill(path, 10000000);
		
		// Find the shortest path for each of the given trees
		for (int j=0; j < Leaves.length; j++) {
			for (int i=0; i < Leaves[j].length; i++) {
				if (Leaves[j][i] > 0) {
					if (Leaves[j][i] < path[j]) {
						path[j] = Leaves[j][i];
						index[j] = i;
					}
					sum_t += Leaves[j][i];
					count++;
				}
			}
		}
	
		// find the shortest path of the shortest paths
		for (int j=0; j < Leaves.length; j++) {
			if (debug)
				System.out.println("Shortest path from graph " + String.valueOf(j) + 
						" is " + String.valueOf(path[j]) + " found at " + String.valueOf(index[j]));
			
			if (path[j] > 0) {
				if (path[j] < shortestp) {
					shortestp = path[j];
					shortest[0] = j;
					shortest[1] = index[j];
				}
			}
		}
		if (calcmean == true) {
			mean = sum_t/count;
		}
		return shortest;
		
	}
	
	/* Decodes the position of a leaf node into phosphorylation sites
	 * 
	 * A node is decoded based on that ordering of how the graph was constructed.
	 * The first site is 3^# of sites, the second site is 3^(# of sites-1) etc.
	 * Therefore the phosphorylation sites can be completed decoded from the
	 * position in the graph
	 * 
	 */
	
	private final int[] DecodeLeaf(int position, int numsites) {
		
		int site = 0;
		int[] decode = new int[numsites];
			
		for (int i=0; i < numsites; i++) {
			site = (int) java.lang.Math.floor(position / java.lang.Math.pow(3,numsites-i-1)) % 3;
			decode[i] = site;
		}
		
		return decode;
			
	}
	
	
	// Encode a leaf back into its node number based on the site encoding.  Just does the opposite
	// of the DecodeLeaf function
	private final int EncodeLeaf(int[] sites) {
		
		int node = 0;
					
		for (int i=0; i < sites.length; i++) {
			node += java.lang.Math.pow(3,sites.length-1-i) * sites[i];
		}
		return node;	
	}
	
	
	// Compute the Std Deviation of all the leaves.
	private int StdDev(double[][] Leaves) {
		
		double sum_t = 0;
		int count = 0;
				
		for (int j=0; j < Leaves.length; j++) {
			for (int i=0; i < Leaves[j].length; i++) {
				if (Leaves[j][i] > 0) {
					sum_t += java.lang.Math.pow(mean - Leaves[j][i], 2);
					count++;
				}
			}
		}
	
		stddev = (double) java.lang.Math.sqrt(sum_t/count);
		return count;
		
	}
	
}
	