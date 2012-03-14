import java.util.Hashtable;
import java.util.ArrayList;

public class Graph {
			
	private static Spectrum spectrum;
	private static double parent;	
	private Profile[] profiles = new Profile[Globals.MAXZSTATES];
	private String refseq;
	private int zstate;
	private char ion;
	private double[] Nodes;
	private int[] ionsites;
	private int[] Yionsites;
	private int startnode;
	
	/* Initialize the graph with the spectrum, the parent and the perm modifiers
	 * 
	 */
	public Graph(Spectrum S, double p, Modifier[] perm) {
		
		spectrum = S;
		parent = p;
				
		for (int i=0; i<Globals.MAXZSTATES; i++) {
			profiles[i] = new Profile();
			if (perm != null) {
				for (int j=0; j<perm.length; j++) {	
					profiles[i].SetPermMod(perm[j]);
				}
			} else {

			}
		}	
		
	}
	
	/* Sets parameters for this building of a graph
	 * 
	 * The graph can be re-used by changing these parameters
	 * 
	 */
	public void SetParams(String seq, int z, char iontype) {
		refseq = seq;
		zstate = z;
		ion = iontype;
		
		// Determine the ion sites and the Y ion sites
		// Y sites needed because they cannot undergo neutral loss
		int[] sites_t = new int[Globals.MAXIONS+1];
		int count = 0;
		int[] Ysites_t = new int[Globals.MAXIONS];
		int Ycount = 0;
		for (int i=0; i< seq.length(); i++) {
			if (seq.charAt(i) == 'S' || seq.charAt(i) == 'T' || seq.charAt(i) == 'Y') {
				sites_t[count] = i;
				count++;
				if (seq.charAt(i) == 'Y') {
					Ysites_t[Ycount] = i;
					Ycount++;
				}
			}			
		}
		
		sites_t[count++] = seq.length();
		ionsites = new int[count];
		System.arraycopy(sites_t, 0, ionsites, 0, count);
		if (Ycount > 0) {
			Yionsites = new int[Ycount];
			System.arraycopy(Ysites_t, 0, Yionsites, 0, Ycount);
		}
			
		
	}
	
	// Converts a node # to a modifier to be used to generate a profile
	private void ConvertNodeToModifier(int position, Modifier[] M) {
		
		for (int i=0; i<M.length; i++) {
			int mod = (int) java.lang.Math.floor(position / java.lang.Math.pow(3, M.length-1-i)) % 3;
			M[i] = new Modifier(ionsites[i], Globals.MODENC[mod]);			
		}
	}

	// Finds the cost of the parent of a node
	private double FindParentCost(int level, int node) {
		int parentnode = 0;
		
		if (level == 1) {
			return 0.0;
		} else if (level > 2) {
			for (int i=1; i<=level-2; i++) {
				parentnode += (int) java.lang.Math.pow(3, i);
			}
			 
		}

		return Nodes[parentnode + ((int) node/3 )];
		
	}
	
	/* The number of the nodes is as follows:  The first child of a node is 
	 * always non modified, so 0.  The second child is always -18, and the
	 * third child is always +80.  
	 * So 001 = 0, 0, -18
	 * 210 = +80, -18, 0
	 * etc...
	 */
	
	public void BuildDepth(int mode, boolean debug) {
		// Mode 0 and 1 combine the ion data outside this function. Mode 2 and 3 combine charge states within this function.
		double cost = 0;
		double best_cost = -1;
		double parent_cost = 0;
		double parent_t;
		double mass;
		boolean[] touched = new boolean[3];
		
		for (int i = 0; i < 3; i++)
		  touched[i] = false;

		// There better not be more than 4 million nodes, or trouble ensues. This means the max number of phosphorylation sites 
		//   we can handle is 13 (3^13 ~ 1.5 million).
		int numnodes = 0;
		for (int i = 1; i <= ionsites.length - 1; i++) {
			numnodes += (int) java.lang.Math.pow(3, i);
		}
		Nodes = new double[numnodes];

		// Variables for a depth first traversal.
		int[] current_path = new int[ionsites.length - 1];
    int first_leaf = numnodes - (int)(java.lang.Math.pow(3, current_path.length) - 1) - 1;
    int last_leaf = numnodes - 1;
    int number_of_leaves = last_leaf - first_leaf + 1;
    int level, node_index;

    // Pruning variables.
    Hashtable<Integer, Boolean> blacklist = new Hashtable<Integer, Boolean>(number_of_leaves, number_of_leaves);
  	for (int i = first_leaf; i <= last_leaf; i++)
  	  blacklist.put(i, false); 	  
  	ArrayList<Integer> black_nodes = new ArrayList<Integer>();

		// ES - Changing this to -1 for a sentinel value.
		java.util.Arrays.fill(Nodes, -1.0);

		// Compute initial node weight (ie, cost of everything before the first ion).
		if (mode < 2 && ionsites[0] > 0) {
			profiles[zstate-1].CreateProfile(refseq, ion, zstate, null, ionsites[0]);
			cost = profiles[zstate-1].Cost(0, ionsites[0]-1, spectrum);
		} else if (mode >=2 && ionsites[0] > 0)  {
			for (int i=zstate-1; i >= ((zstate - 1) * (1 - (int) mode/2) ); i--) {
				profiles[i].CreateProfile(refseq, ion, i, null, ionsites[0]);
				cost = profiles[i].Cost(0, ionsites[0]-1, spectrum);
			}
		}		

		// Store the initial weight in each of the three top nodes.
		if (debug)
			System.out.println("Initializing 3 nodes to " + String.valueOf(cost));

		if (ionsites.length > 2) {
			Nodes[0] = Nodes[1] = Nodes[2] = cost;
		}

		// Adjust the parent ion weight for the charge state. Doesn't matter if we are going through all charge states since 
		//   if it doesn't match with one all 2 or 3 are rejected.
		if (ion == 'y')
		    parent_t = (parent) / zstate;
		else
		    parent_t = (parent - Globals.WATERWEIGHT) / zstate;

    // This is a depth first search of the tree, which allows us to keep track of the global
    //   best path. If we reach a point in the tree where the current score exceeds the best
    //   cost for a complete path, we can blacklist that subtree and skip any computation on it.
    PathLoop:
    for (int current_leaf = first_leaf; current_leaf <= last_leaf && !blacklist.get(current_leaf); current_leaf++) {
      // Setup the path to be explored based on the leaf node we are at.
      current_path[current_path.length - 1] = current_leaf;
      for (int i = current_path.length - 1; i > 0; i--)
        current_path[i - 1] = (int)(current_path[i] / 3.0) - 1;

      // Traverse the path.
      for (int i = 0; i < current_path.length; i++) {
        // Ensure we haven't already calculated this node (Since we will visit the root node many times).
        //   If it's a root node we need to pull some tricks to get it computed once, that happens as well with the 'touched' boolean array.
        if (Nodes[current_path[i]] == -1 || ((current_path[i] == 0 || current_path[i] == 1 || current_path[i] == 2) && !touched[current_path[i]])) {
          
          // Updated the 'touched' array if we're looking at nodes 0, 1, or 2.
          if (Nodes[current_path[i]] != -1)
            touched[current_path[i]] = true;
          else Nodes[current_path[i]] = 0;
          
          // These level and node index variables are used in preexisting method calls, so I'm retaining them.
          //   level      = the current depth of the tree, index starts at 1.
          //   node_index = the index of the current node, index starts at 0, relative to that depth only.
          level = i + 1;
          node_index = 0;
          for (int j = 1; j < level; j++)
            node_index += java.lang.Math.pow(3, j);
          node_index = current_path[i] - node_index;

          // Represents a modification to a peptide profile.
          Modifier[] modifier = new Modifier[level];
          ConvertNodeToModifier(node_index, modifier);

          // Creates a peptide profile.
          for (int j = zstate - 1; j >= ((zstate - 1) * (1 - (int)(mode / 2))); j--)
            profiles[j].CreateProfile(refseq, ion, j + 1, modifier, ionsites[level]);

          mass = profiles[zstate - 1].GetProfile(ionsites[level] - 1);

          // Skip to the next path under the conditions below.
          if (level == ionsites.length - 1 && java.lang.Math.abs(mass - parent_t) > 2) {
            continue;
          } else if (Yionsites != null)
            for (int j : Yionsites)
              for (Modifier k : modifier)
                if (k.pos == j && k.val == Globals.MODENC[1]) {
                  continue PathLoop;
                }

          cost = 0;
          parent_cost = FindParentCost(level, node_index);
          
          for (int j = zstate - 1; j >= ((zstate - 1) * (1 - (int)(mode / 2))); j--) {
            cost += profiles[j].Cost(ionsites[level - 1], ionsites[level] - 1, spectrum);
          }
          Nodes[current_path[i]] += parent_cost + cost;

          // Pruning section (Note on optimization...).
          //   If we have a best cost path, the node we are at has already exceeded the best cost and it is not a leaf node...
          if (best_cost != -1 && Nodes[current_path[i]] >= best_cost && level + 1 < ionsites.length) {
            // Add the root of the subtree to be pruned.
            black_nodes.add(current_path[i]);
            // For each level from the current level to directly above the leaves...
            for (int j = level; j + 1 < ionsites.length; j++) {
              // Track the size so we can pull parent nodes from the front (This saves using two lists).
              int size = black_nodes.size();
              // For each node we'll add its children to the list...
              for (int k = 0; k < size; k++) {
                for (int l = 0; l < 3; l++) {
                  black_nodes.add((black_nodes.get(k) + 1) * 3 + l);
                }
              }
              // Then we'll remove the parents of the new nodes from the list.
              for (int k = 0; k < size; k++)
                black_nodes.remove(0);   
            }
            // Now we'll add the list of nodes to the actual blacklist and clean up.
            for (int j = 0; j < black_nodes.size(); j++)
              blacklist.put(black_nodes.get(j), true);
            black_nodes.clear();
          }
        }
      }
    }
	}
		
  public void Build(int mode, boolean debug) {
    // mode 0 and 1 combine the ion data outside this function.  Mode 
    // 2 and 3 combine charge states within this function   
    
    double cost = 0;
    double parent_t;
    double mass;
    double best_score = -1;
    int number_skipped = 0;
        
    // better not be more than 4 million nodes, or trouble
    // Means the max number of phosphorylation sites we can handle
    // is 13 (3^13 ~ 1.5million)
    int numnodes = 0;
    for (int i=1; i<=ionsites.length-1; i++) {
      numnodes += (int) java.lang.Math.pow(3, i);
    }
    Nodes = new double[numnodes];
    java.util.Arrays.fill(Nodes, 0.0);
    
    // Compute initial node weight (ie, cost of everything
    // before the first ion)
    if (mode < 2 && ionsites[0] > 0) {
      profiles[zstate-1].CreateProfile(refseq, ion, zstate, null, ionsites[0]);
      cost = profiles[zstate-1].Cost(0, ionsites[0]-1, spectrum);
    } else if (mode >=2 && ionsites[0] > 0)  {
      //profiles[0].CreateProfile(refseq, ion, 0, null, ionsites[0]);
      //profiles[1].CreateProfile(refseq, ion, 1, null, ionsites[0]);
      //profiles[2].CreateProfile(refseq, ion, 2, null, ionsites[0]);
      
      //for (int i=0; i<Globals.MAXZSTATES; i++) {
      for (int i=zstate-1; i >= ((zstate - 1) * (1 - (int) mode/2) ); i--) {
        profiles[i].CreateProfile(refseq, ion, i, null, ionsites[0]);
        cost = profiles[i].Cost(0, ionsites[0]-1, spectrum);
      }
    }   
    // store the initial weight in each of the three top nodes
    if (debug)
      System.out.println("Initializing 3 nodes to " + String.valueOf(cost));
    
    if (ionsites.length > 2) {
      Nodes[0] = Nodes[1] = Nodes[2] = cost;
    }
    
    // adjust the parent ion weight for the charge state.  Doesn't
    // matter if we are going through all charge states since if it doesn't
    // match with one all 2 or 3 are rejected
    if (ion == 'y')
        parent_t = (parent)/zstate;
    else
        parent_t = (parent - Globals.WATERWEIGHT)/zstate;
    
        
    // iterate through the tree. This is a breadth first traversal of the tree 
    // (not the most efficient but most programming friendly)
    //  The path lengths are calculated as the tree is formed.  
    for (int level=1; level < ionsites.length; level++) {
      startnode = 0;
      int nodes_at_level = 0;
                  
      for (int i=1; i<=level-1; i++) {
        startnode += (int) java.lang.Math.pow(3, i);
      } 
      nodes_at_level = (int) java.lang.Math.pow(3, level);
      
      // calculate the cost and at up the path length for
      // each node at this level  
      NodeLoop:
      for (int node = 0; node < nodes_at_level; node++) {
        
        Modifier[] M = new Modifier[level];
        
        // calc the modification sites for this node
        // function converts a numerical node # into an
        // array of modifiers
        ConvertNodeToModifier(node, M);
        
        
        // calc the profiles.  In mode 2 + 3, all the zstates are calc
        for (int i=zstate-1; i >= ((zstate - 1) * (1 - (int) mode/2) ); i--) {
          profiles[i].CreateProfile(refseq, ion, i+1, M, ionsites[level]);
        }
        mass = profiles[zstate-1].GetProfile(ionsites[level]-1);
        
        // determine if we skip this node or calc. Skipped when 
        // the mass of the node is not equal to the parent node (only in the final leafs),
        // or this node has a Y being modified by -18.
        if (level == ionsites.length-1 && java.lang.Math.abs(mass-parent_t) > 2) {
          continue;
        } else if (Yionsites != null) {
          for (int i : Yionsites) {
            for (Modifier j : M) {
              if (j.pos == i && j.val == Globals.MODENC[1])
                continue NodeLoop;
            }
          }
        }
        
        // Calc parent cost, and add up the cost for the charge states 
        // (if indicated by the mode)
        cost = 0;
        double parentcost = FindParentCost(level, node);
        
        // Skipping leaf conditions - If we are at a leaf and there has been one best score computed 
        //   and the parent cost of this leaf is > the best score...
        if (level + 1 == ionsites.length && parentcost >= best_score && best_score != -1 ) {
          // Set this leaf's score worse than the best score and skip actually computing it.
          number_skipped++;
          Nodes[startnode + node] = best_score + 1;
          continue;
        }        
        // Right now the last amino acid is checked - DIFFERENT THAN BIOWORKS!
        // Leaving for now
        for (int i=zstate-1; i >= ((zstate - 1) * (1 - (int) mode/2) ); i--) {
          cost += profiles[i].Cost(ionsites[level-1], ionsites[level]-1, spectrum);
        }
        Nodes[startnode+node] += parentcost + cost;
                
        // Hook to set the best score the first time. If we're at a leaf and there isn't a best score, or the one just calculated is better, commit it.
        if (level + 1 == ionsites.length && (Nodes[startnode + node] < best_score || best_score == -1))
          best_score = Nodes[startnode + node];
      }            
    }

    if (debug && number_skipped > 0)    
      System.out.println("Skipped " + number_skipped + " nodes.");
  }
	
	// Copies all the leaves of the tree to a new array
	public void GetLeaves(double[][] Leaves, int index) {
		
		Leaves[index] = new double[Nodes.length-startnode];
		System.arraycopy(Nodes, startnode, Leaves[index], 0, Nodes.length-startnode);
				
	}
	
	public int Count() {
		return (Nodes.length - startnode);
	}

	public int GetStart() {
		return startnode;
	}
	
	public int GetIonSites() {
		return ionsites.length-1;
	}
	
	
}
