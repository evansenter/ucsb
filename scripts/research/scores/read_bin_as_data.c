#include <stdlib.h>
#include <stdio.h>
#include <time.h>

/**********************************************************************************************
File: read_bin_as_data.c

Description: This file's purpose is to provide console-level support for querying datasets. 
When run, pass in the number of nodes, maximum score threshold, and paths to all
datasets to be loaded into memory. After the scores have been loaded, the system will prompt
for a query. The query is made up of a center node (c) and maximum depth (d) for subtree generation.
From the center node, additional nodes (i) are added to the subgraph if and only if there is a 
path (p) no longer than (d) to (i) where every edge in (p) has a score higher than the current 
threshold.

Method-level documentation is included below.
**********************************************************************************************/

/* 
 * Method: get_line
 * 
 * Takes: Two node ids (integers) and the number of nodes in the graph (integer)
 * Returns: The line number (id) of the edge between those two nodes (integer)
 *
 * Description: This method assumes a fully connected graph that's zero indexed, so nodes (1, 2) are on 
 * line 0 and so on. It has error checking for node ids greater than the number of nodes and for cycles
 * (Calling get_line(1, 1, 10) for example). Errors print a message in the console and return -1.
*/
inline int get_line(int node_1, int node_2, int number_of_nodes) {
  if (node_1 > number_of_nodes || node_2 > number_of_nodes || node_1 == node_2) {
    printf("Argument error in get_line (%d, %d, %d) - returning -1\n", node_1, node_2, number_of_nodes);
    return -1;
  }
  if (node_1 > node_2) node_1 ^= (node_2 ^= (node_1 ^= node_2));
  int node_counter, line = 0;
  for (node_counter = 1; node_counter < node_1; node_counter++) line += number_of_nodes - node_counter;
  return line + node_2 - node_1 - 1;
}

/* 
 * Method: convert
 *
 * Takes: A score (character)
 * Returns: A score (integer)
 *
 * Description: Characters run from (-128 to 127), this shifts the range to (-1 (null score) to 254).
*/
inline int convert(char score) {
  return score < -1 ? 256 + score : score;
}

/* 
 * Method: get_score_from_nodes
 *
 * Takes: Pointer to an array of scores (pointer to character array), two node ids (integers), and the 
 *  number of nodes (integer)
 * Returns: The score of the edge between the two node ids (integer)
 *
 * Description: Computes the line the nodes are on, and looks up that position in the scores array and 
 *  converts it to an integer.
*/
inline int get_score_from_nodes(char *scores, int node_1, int node_2, int number_of_nodes) {
  return convert(scores[get_line(node_1, node_2, number_of_nodes)]);
}

/* 
 * Method: get_score_from_file
 *
 * Takes: Pointer to a score file (pointer to FILE), and the line to return (integer)
 * Returns: A score (character)
 *
 * Description: Zero indexed, looks in a file at a given line for the character score and returns
 *  it. This method is used while loading the scores into memory, whereas get_score_from_nodes is 
 *  used during queries.
*/
inline char get_score_from_file(FILE *stream, int line) {
  char score;
  fseek(stream, line, SEEK_SET);
  fread(&score, sizeof(char), 1, stream);
  return score;
}

/* 
 * Method: expand_graph
 *
 * Takes: Pointer to an array of scores (pointer to character array), pointer to an array of nodes
 *  in the graph (pointer to character array), the threshold for edges to include in the subgraph
 *  (pointer to character array), and the number of nodes in the graph (integer)
 * Returns: Void
 *
 * Description: This is the meat and bones method for generating subgraphs. It works as follows:
 *  Takes an array of length number_of_nodes + 1. For indices 1..number_of_nodes, if value == 0 
 *  the node is not in the graph. If value > 0, that node is known, if the value < 0 the node is 
 *  visited. More comments in the method.
*/
void expand_graph(char *scores, char *neighbors, int threshold, int number_of_nodes) {
  int i, j; 
  char neighbors_copy[number_of_nodes + 1];
  
  // ----- Make a copy of the graph -----
  for (i = 1; i <= number_of_nodes; i++) neighbors_copy[i] = neighbors[i];
    
  // ----- For each known (but not visited) node (n1), if there are neighbors (n2) that are not known
  // and the edge score is above the threshold, then mark the neighbor (n2) as known and the
  // node (n1) as visited (After all neighbors have been checked) -----
  for (i = 1; i <= number_of_nodes; i++) 
    if (neighbors_copy[i] > 0) {
      for (j = 1; j <= number_of_nodes; j++) 
        if (neighbors[j] == 0 && get_score_from_nodes(scores, i, j, number_of_nodes) >= threshold)
          neighbors[j] = 1;
      neighbors[i] *= -1;
    }
  // for (i = 1; i <= number_of_nodes; i++) printf("[%d]", neighbors[i]);
  // printf("\n");
}

/* 
 * Method: clear_graphs
 *
 * Takes: Pointer to an array of pointer to score arrays (pointer to pointer to character array), the
 *  number of files (integer), and the number of nodes (integer)
 * Returns: Void
 *
 * Description: Marks ALL nodes as not in the graph (0).
*/
inline void clear_graphs(char **graph, int number_of_files, int number_of_nodes) {
  int curr_file, curr_node;
  for (curr_file = 0; curr_file < number_of_files; curr_file++) 
    for (curr_node = 0; curr_node <= number_of_nodes; curr_node++) 
      graph[curr_file][curr_node] = 0;
}

/* 
 * Method: merge_graphs
 *
 * Takes: Pointer to an array of score arrays (pointer to pointer array), pointer to an array of doubles
 *  (pointer to double array), the number of files (integer), the number of nodes (integer), and the current
 *  threshold (double)
 * Returns: Pointer to the merged graph (Pointer to character array)
 *
 * Description: This method takes a collection of subgraphs and merges them together to create a composite graph.
 *  The method works by collecting the total weight for a node (The sum of the weights for datasets containing that node)
 *  and adding that node to the composite graph if the total weight is greater than the threshold (Defined in the main method
 *  as the current threshold / max threshold). All nodes are evaluated in this manner, and the resulting graph is returned.
*/
inline char* merge_graphs(char **graphs, double *weights, int number_of_files, int number_of_nodes, double threshold) {
  char *graph = malloc(number_of_nodes + 1);
  int curr_node, curr_file, curr_score;
  for (curr_node = 0; curr_node <= number_of_nodes; curr_node++) graph[curr_node] = 0;

  for (curr_node = 0; curr_node <= number_of_nodes; curr_node++) {
    curr_score = 0;
    for (curr_file = 0; curr_file < number_of_files; curr_file++) if (graphs[curr_file][curr_node]) curr_score += weights[curr_file];
    if (curr_score > threshold) graph[curr_node] = 1;
  }
  return graph;
}

/* 
 * Method: main
 *
 * Takes (Ordered): The number of nodes in the dataset graph (integer), the maximum threshold (integer), and
 *  an arbitrary number of paths to data files (Binary encoded with scores as characters).
 * Returns: Nothing (integer)
 *
 * Description: The order of execution is to load the files into memory, and then handle user queries until
 *  they decide to quit (By entering all 0's for the query). Additional comments in the method body.
*/
int main(int argc, char *argv[]) {
  int center_node, number_of_nodes, number_of_scores, curr_threshold, max_threshold, depth, number_of_files, combine_graphs, i, j;
  time_t start_time;
  
  // ----- Check the number of arguements -----
  time(&start_time);
  if (argc < 4) {
    printf("Wrong number of arguements.\n");
    return -1;
  }
  
  // ----- Initialize argument-dependent variables -----
  number_of_files = argc - 3;
  number_of_nodes = atoi(argv[1]);
  max_threshold = atoi(argv[2]);
  number_of_scores = get_line(number_of_nodes - 1 , number_of_nodes, number_of_nodes) + 1;
  FILE **binary_files = (FILE *) malloc(number_of_files * sizeof(FILE));
  
  // ----- Initialize scores array -----
  char **scores = malloc(number_of_files);
  for (i = 0; i < number_of_files; i++) {
    scores[i] = malloc(number_of_scores);
    printf("Space for file %d allocated from %u to %u.\n", i, scores[i], scores[i] + number_of_scores - 1);
    fflush(stdout);
  }

  // ----- Touch all score locations -----
  for (i = 0; i < number_of_files; i++) {
    for (j = 0; j < number_of_scores; j++) scores[i][j];
    printf("Touched scores for file %d.\n", i);
  }
    
  // ----- Initialize graphs array -----
  char **graphs = malloc(number_of_files);
  for (i = 0; i < number_of_files; i++) graphs[i] = malloc(number_of_nodes + 1);

  // ----- Start loading files -----
  for (i = 0; i < number_of_files; i++) binary_files[i] = fopen(argv[i + 3], "rb");

  // ----- Ensure the binary files loaded -----
  for (i = 0; i < number_of_files; i++) if (binary_files[i] == NULL) printf("File did not open properly.\n");
    
  // ----- Load binary file into array -----
  printf("Loading %d scores from %d files into memory, please wait.\n", number_of_scores * number_of_files, number_of_files);
  for (i = 0; i < number_of_files; i++) {
    printf("Starting to load file %d.", i);
    for (j = 0; j < number_of_scores; j++) {
      scores[i][j] = get_score_from_file(binary_files[i], j);
      if (j % 1000000 == 0) {
	printf(".");
	fflush(stdout);
      }
    }
    printf("file %d loaded.\n", i);
  }
  printf("Loading complete, (%f) seconds to run prep.\n", difftime(time(NULL), start_time));
    
  // ----- Assign threshold boundaries -----
  int *thresholds = (int *) malloc(3 * sizeof(int));
  thresholds[0] = max_threshold / 4;
  thresholds[1] = max_threshold / 2;
  thresholds[2] = 3 * max_threshold / 4;
  printf("Thresholds: low (%d), medium (%d), high (%d)\n", thresholds[0], thresholds[1], thresholds[2]);

  // ----- Setup weight vector -----
  double *weights = (double *) malloc(number_of_files * sizeof(double));

  // ----- Execution phase -----  
  printf("Please enter: center node, max depth, merge subgraphs? (1 = yes, 0 = no)\n");
  printf("To quit, enter 0 0 0\n");
  while (scanf("%d %d %d", &center_node, &depth, &combine_graphs) == 3) {
    if (center_node + depth + combine_graphs == 0) return 0;       
   
    if (combine_graphs && number_of_files > 1) {
      // ----- Get and prep weights -----
      for (i = 0; i < number_of_files; i++) weights[i] = -1;
      
      for (i = 0; i < number_of_files; i++) {
	do {
	  printf("Enter a weight greater than or equal to zero for dataset %d.\n", i);
	  scanf("%lf", &weights[i]);
	} while (weights[i] < 0);
      }

      // ----- Normalize weight vector -----
      double sum = 0;
      for (i = 0; i < number_of_files; i++) sum += weights[i];
      
      for (i = 0; i < number_of_files; i++) if (weights[i] != 0) weights[i] /= sum;

      printf("Normalized weights - ");
      for (i = 0; i < number_of_files - 1; i++) printf("dataset %d: %.4f, ", i, weights[i]);
      printf("dataset %d: %.4f\n", number_of_files - 1, weights[number_of_files - 1]);
    }

    // ----- Print output format -----
    if (combine_graphs && number_of_files > 1) printf("\n(center node, current threshold, current depth, subgraph size, min node score for composite, query time)\n");
    else {
      printf("(center node, current threshold, current depth) ");
      for (i = 0; i < number_of_files; i++)
	printf("[dataset id, subgraph size, query time] ");
      printf("\n");
    }
    
    for (curr_threshold = 0; curr_threshold < 3; curr_threshold++) {
      // ----- Create array for graph, set the centered node to known -----
      clear_graphs(graphs, number_of_files, number_of_nodes);
      
      for (i = 0; i < number_of_files; i++)
	graphs[i][center_node] = 1;
      
      // ----- Expand to specified depth -----
      int curr_data;
      for (i = 0; i < depth; i++) {
	time(&start_time);
	printf("%d, %d, %d, ", center_node, thresholds[curr_threshold], i);
	
	for (curr_data = 0; curr_data < number_of_files; curr_data++) {
	  if (!combine_graphs) time(&start_time);
	  
	  expand_graph(scores[curr_data], graphs[curr_data], thresholds[curr_threshold], number_of_nodes);
          
	  // ----- Print statistics -----
	  if (!combine_graphs) {
	    int size = 0;
	    for (j = 0; j <= number_of_nodes; j++) if (graphs[curr_data][j]) size++;
          
	    printf("%d, %d, %.1f", curr_data, size, difftime(time(NULL), start_time));
	    if (curr_data != number_of_files - 1)
	      printf(", ");
	    fflush(stdout);
	  }
	}
	if (combine_graphs) { 
	  // ----- Combine subgraphs into one -----
	  char *graph = merge_graphs(graphs, weights, number_of_files, number_of_nodes, (double)(thresholds[curr_threshold] / max_threshold));
	  int size = 0;
	  for (j = 0; j <= number_of_nodes; j++) if (graph[j]) size++;
	  printf("%d, %.1f", size, difftime(time(NULL), start_time));
	}
	printf("\n");
      }
      printf("\n");
    }
    printf("Please enter: center node, max depth, merge subgraphs? (1 = yes, 0 = no)\n");
    printf("To quit, enter 0 0 0\n");
  }
  printf("Done.\n");
}
