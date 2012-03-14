#include <iostream>
#include <vector>
#include "Edges.h"
using namespace std;

class Methods {
  public:
    Methods();
    Methods(EdgeHeap tree, int theVerticesToKeep);
    vector <Edge> appendDistance(vector <Edge>);
    EdgeHeap shortestPath(); 
    EdgeHeap closestPair();
    EdgeHeap smallestEdge();
    EdgeHeap divideAndConquer(EdgeHeap tree);
    EdgeHeap dynamicProgramming();
    EdgeHeap customMethod();
  private:
    EdgeHeap fullTree;
    int verticesToKeep;
};

Methods::Methods() {}

Methods::Methods(EdgeHeap tree, int theVerticesToKeep) {
  fullTree = tree;
  verticesToKeep = theVerticesToKeep;
}

vector <Edge> Methods::appendDistance(vector <Edge> edges) {
  int distance = 0;
  for (int i = 0; i < edges.size(); i++) distance += edges.at(i).getWeight();
  edges.push_back(Edge(-1, -1, distance));
  return edges;
}

EdgeHeap Methods::shortestPath() {
  // Initialize with the size of the largest vertex that's going to be kept
  // Insert the smallest vertex in the fullTree into the newTree
  // WHILE the newTree doesn't have all the vertices it needs..
  // FOR EACH vertex (i) that needs to be in the newTree and isn't yet...
  // MAP the shortest l-path from vertex (i) to A vertex in newTree
  // END FOR
  // REDUCE to the smallest (weight) path from the MAP and add to newTree
  // -> Break ties by selecting the path that STARTS at the smallest numbered vertex
  // END WHILE
  EdgeHeap newTree = EdgeHeap(fullTree.getSize());
  newTree.insertVertex(fullTree.getVertices().at(0));
  while(!newTree.containsAllVerticesUpTo(verticesToKeep)) {
    vector <Vertex> remainingVertices = newTree.getRemainingVertices(verticesToKeep);
    vector <Vertex> verticesInNewTree = newTree.getVertices();
    vector < vector <Edge> > edgeList;
    for (int i = 0; i < remainingVertices.size(); i++) 
      for (int j = 0; j < verticesInNewTree.size(); j++) {
	int vert1 = remainingVertices.at(i).getV1();	
	int vert2 = verticesInNewTree.at(j).getV1();
	if (vert1 != -1 && vert2 != -1) {
	  vector <Edge> thePath = fullTree.shortestPath(&fullTree.getVertices().at(fullTree.findVertex(vert1)), &fullTree.getVertices().at(fullTree.findVertex(vert2)));
	  edgeList.push_back(appendDistance(thePath));
	}
      }
    int smallestWeight = edgeList.at(0).at(edgeList.at(0).size() - 1).getWeight();
    for (int i = 0; i < edgeList.size(); i++) 
      if (smallestWeight > edgeList.at(i).at(edgeList.at(i).size() - 1).getWeight()) 
	smallestWeight = edgeList.at(i).at(edgeList.at(i).size() - 1).getWeight();
    int done = 0;
    for (int i = 0; i < edgeList.size(); i++)
      if (edgeList.at(i).at(edgeList.at(i).size() - 1).getWeight() == smallestWeight && !done) {
	done++;
	vector <Edge> bestList = edgeList.at(i);
	for (int j = 0; j < bestList.size() - 1; j++) // Skip over the appended entry at the end
	  newTree.insert(bestList.at(j).getV1(), bestList.at(j).getV2(), bestList.at(j).getWeight());
      }
  }
  return newTree;
}

EdgeHeap Methods::closestPair() {
  vector <EdgeHeap> forest;
  vector <Vertex> allVertices = fullTree.getVertices();
  for (int i = 0; i < allVertices.size(); i++) {
    EdgeHeap tree = EdgeHeap(allVertices.size());
    tree.insertVertex(allVertices.at(i));
    forest.push_back(tree);
  }
  while (forest.size() > 1) {
    vector < vector <Edge> > edgeList;
    for (int j = forest.size() - 1; j >= 0; j--)
      for (int i = 0; i < j; i++) {
	// i, j are the indexes of the trees in the forest, now need to collect all shortest paths between i and j
	vector < vector <Edge> > pathsFromItoJ;
	for (int k = 0; k < forest.at(i).getSize(); k++)
	  for (int l = 0; l < forest.at(j).getSize(); l++)
	    pathsFromItoJ.push_back(appendDistance(fullTree.shortestPath(&fullTree.getVertices().at(fullTree.findVertex(forest.at(i).at(k).getV1())), &fullTree.getVertices().at(fullTree.findVertex(forest.at(j).at(l).getV1())))));
	
      int smallestWeight = pathsFromItoJ.at(0).at(edgeList.at(0).size() - 1).getWeight();
      for (int k = 0; k < edgeList.size(); k++) 
	if (smallestWeight > edgeList.at(k).at(edgeList.at(k).size() - 1).getWeight()) 
	  smallestWeight = edgeList.at(k).at(edgeList.at(k).size() - 1).getWeight();
      int done = 0;
      for (int k = 0; k < edgeList.size(); k++)
	if (edgeList.at(k).at(edgeList.at(k).size() - 1).getWeight() == smallestWeight && !done) {
	  done++;
	  for (int l = 0; l < forest.at(j).getSize(); l++) 
	    if (!forest.at(j).at(l).isEmpty())
	      forest.at(i).insert(forest.at(j).at(l).getV1(), forest.at(j).at(l).getV2(), forest.at(j).at(l).getWeight());
	  vector <Vertex> vertices = forest.at(j).getVertices();
	  for (int l = 0; l < vertices.size(); l++)
	    forest.at(i).insertVertex(vertices.at(l).getV1());
	  forest.erase(forest.begin() + j);  
	}
      }
  }
  return forest.at(0);
}

EdgeHeap Methods::smallestEdge() {
  EdgeHeap newTree = EdgeHeap(verticesToKeep);
  int done = 0;
  int smallestEdgeLoc = 0;
  while (!done) {
    Edge smallestEdge = fullTree.at(smallestEdgeLoc++);	      // Grab the smallest edge
    int v1 = smallestEdge.getV1(), v2 = smallestEdge.getV2();
    if (v1 == -1 || v2 == -1) continue;
    if (newTree.shortestPath(&newTree.getVertices().at(newTree.findVertex(v1)), &newTree.getVertices().at(newTree.findVertex(v2))).at(0).getWeight() != -1)
      newTree.insert(v1, v2, smallestEdge.getWeight());	      // Check if there are a pair of vertices in verticesToKeep without a path between them
    int allPathsPresent = 1;				      // Assume all paths present
    if (newTree.containsAllVerticesUpTo(verticesToKeep)) {    // If all the vertices are present we might be done, let's check...
      for (int i = verticesToKeep; i > 0; i--)
	for (int j = 1; j < i; j++)			      // For all pairs of vertices (Order not imporant)
	  if (newTree.shortestPath(&newTree.getVertices().at(newTree.findVertex(i)), &newTree.getVertices().at(newTree.findVertex(j))).at(0).getWeight() == -1) // If a path doesn't exist IN the newTree
	    allPathsPresent--;				      // Then we aren't done
      if (allPathsPresent) done++;			      // Otherwise we are done
    }
  }
  for (int eCounter = 0; eCounter < newTree.getSize() && !newTree.at(eCounter).isEmpty(); eCounter++) {
    newTree.at(eCounter).setEmpty(true);
    int allPathsPresent = 1;
    for (int i = verticesToKeep; i > 0; i--)
      for (int j = 1; j < i; j++)                           // For all pairs of vertices (Order not imporant)
        if (newTree.shortestPath(&newTree.getVertices().at(newTree.findVertex(i)), &newTree.getVertices().at(newTree.findVertex(j))).at(0).getWeight() == -1) // If a path doesn't exist IN the newTree
	  allPathsPresent--;
    if (!allPathsPresent) newTree.at(eCounter).setEmpty(false);
  }
  return newTree;
}

EdgeHeap Methods::divideAndConquer(EdgeHeap tree) {
  if (tree.getVertices().size() == 1) {	// BASE CASE: 1 vertex
    EdgeHeap singleVertex = EdgeHeap(fullTree.getSize());
    singleVertex.insertVertex(tree.getVertices().at(0));
    return singleVertex;
  }
  if (tree.getVertices().size() == 2) {	// BASE CASE: 2 vertices
    int v1, v2;
    if (tree.getVertices().at(0).getV1() < tree.getVertices().at(1).getV1()) {
      v1 = tree.getVertices().at(0).getV1();
      v2 = tree.getVertices().at(1).getV1();
    } else {
      v1 = tree.getVertices().at(1).getV1();
      v2 = tree.getVertices().at(0).getV1();
    }
    EdgeHeap doubleVertex = EdgeHeap(fullTree.getSize());
    vector <Edge> path = fullTree.shortestPath(&fullTree.getVertices().at(fullTree.findVertex(v1)), &fullTree.getVertices().at(fullTree.findVertex(v2)));
    for (int i = 0; i < path.size(); i++)
      doubleVertex.insert(path.at(i).getV1(), path.at(i).getV2(), path.at(i).getWeight());
    return doubleVertex;
  } 
  // DIVIDE STEP
  int partition = tree.getVertices().at(tree.getVertices().size() / 2).getV1();
  EdgeHeap lowerHalf = EdgeHeap(fullTree.getSize());
  EdgeHeap upperHalf = EdgeHeap(fullTree.getSize());
  for (int i = 0; i < tree.getVertices().size(); i++)
    if (tree.getVertices().at(i).getV1() < partition) lowerHalf.insertVertex(tree.getVertices().at(i));
    else upperHalf.insertVertex(tree.getVertices().at(i));
  EdgeHeap lowerReturn = divideAndConquer(lowerHalf);
  EdgeHeap upperReturn = divideAndConquer(upperHalf);
  // CONQUER STEP
  vector < vector <Edge> > paths;
  for (int i = 0; i < lowerReturn.getVertices().size(); i++) 
    for (int j = 0; j < upperReturn.getVertices().size(); j++) {
      int vert1 = lowerReturn.getVertices().at(i).getV1();	
      int vert2 = upperReturn.getVertices().at(j).getV1();
      if (vert1 != -1 && vert2 != -1) {
        vector <Edge> thePath = fullTree.shortestPath(&fullTree.getVertices().at(fullTree.findVertex(vert1)), &fullTree.getVertices().at(fullTree.findVertex(vert2)));
        paths.push_back(appendDistance(thePath));
      }
    }
  Edge smallestEdge = Edge(1, 1, 0);
  int smallestWeight = paths.at(0).at(paths.at(0).size() - 1).getWeight();
  for (int i = 0; i < paths.size(); i++)
    if (smallestWeight > paths.at(i).at(paths.at(i).size() - 1).getWeight()) {
      smallestWeight = paths.at(i).at(paths.at(i).size() - 1).getWeight();
      smallestEdge = paths.at(i).at(paths.at(i).size() - 1);
    }
  EdgeHeap mergeTree = EdgeHeap(fullTree.getSize());
  for (int i = 0; i < lowerReturn.getSize(); i++)
    mergeTree.insert(lowerReturn.at(i).getV1(), lowerReturn.at(i).getV2(), lowerReturn.at(i).getWeight());
  for (int i = 0; i < upperReturn.getSize(); i++)
    mergeTree.insert(upperReturn.at(i).getV1(), upperReturn.at(i).getV2(), upperReturn.at(i).getWeight());
  mergeTree.insert(smallestEdge.getV1(), smallestEdge.getV2(), smallestEdge.getWeight());
  for (int eCounter = 0; eCounter < mergeTree.getSize() && !mergeTree.at(eCounter).isEmpty(); eCounter++) {
    mergeTree.at(eCounter).setEmpty(true);
    int allPathsPresent = 1;
    for (int i = mergeTree.getVertices().size() - 1; i > 0; i--)
      for (int j = 1; j < i; j++)                           // For all pairs of vertices (Order not imporant)
        if (mergeTree.shortestPath(&mergeTree.getVertices().at(mergeTree.findVertex(mergeTree.getVertices().at(i).getV1())), &mergeTree.getVertices().at(mergeTree.findVertex(mergeTree.getVertices().at(j).getV1()))).at(0).getWeight() == -1)
	  allPathsPresent--;
    if (!allPathsPresent) mergeTree.at(eCounter).setEmpty(false);
  }
  return mergeTree;
}

EdgeHeap Methods::dynamicProgramming() {
  // No cycle support in trees
}

EdgeHeap Methods::customMethod() {
  // Variant on shortestPath that stores the intermediary paths as EdgeHeaps rather than a vector of (vectors of Edges).
  EdgeHeap newTree = EdgeHeap(verticesToKeep);
  newTree.insertVertex(fullTree.getVertices().at(0));
  while(!newTree.containsAllVerticesUpTo(verticesToKeep)) {
    vector <Vertex> remainingVertices = newTree.getRemainingVertices(verticesToKeep);
    vector <Vertex> verticesInNewTree = newTree.getVertices();
    vector <EdgeHeap> edgeList;
    for (int i = 0; i < remainingVertices.size(); i++) 
      for (int j = 0; j < verticesInNewTree.size(); j++) {
	vector <Edge> aPath = fullTree.shortestPath(&fullTree.getVertices().at(fullTree.findVertex(remainingVertices.at(i).getV1())), &fullTree.getVertices().at(fullTree.findVertex(verticesInNewTree.at(j).getV1())));
	EdgeHeap pathAsTree = EdgeHeap(fullTree.getSize());
	for (int k = 0; k < aPath.size(); k++)
	  pathAsTree.insert(aPath.at(k).getV1(), aPath.at(k).getV2(), aPath.at(k).getWeight());
	edgeList.push_back(pathAsTree);
      }
    int smallestWeight = edgeList.at(0).totalWeight();
    for (int i = 0; i < edgeList.size(); i++) 
      if (smallestWeight > edgeList.at(i).totalWeight()) 
	smallestWeight = edgeList.at(i).totalWeight();
    for (int i = 0; i < edgeList.size(); i++)
      if (edgeList.at(i).totalWeight() == smallestWeight) {
	EdgeHeap smallestPath = edgeList.at(i);
	for (int j = 0; j < smallestPath.getSize(); j++)
	  if (!smallestPath.at(j).isEmpty())
	    newTree.insert(smallestPath.at(j).getV1(), smallestPath.at(j).getV2(), smallestPath.at(j).getWeight());
      }
  }
  return newTree;
}
