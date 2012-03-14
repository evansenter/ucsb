#include <iostream>
#include <vector>
#include "Edge.h"
#include "Vertex.h"
using namespace std;

#ifndef EDGES_H
#define EDGES_H

class EdgeHeap {
	public:
		EdgeHeap();
		EdgeHeap(int aSize);  
		void heapify();
		void sift(int pos);
		int isLeaf(int pos);
		int leftChild(int pos);
		int parent(int pos);
		Edge findSmallestEdge(int v1, int v2);
		int findEdge(Edge anEdge);
		int findEdge(int v1, int v2, int weight);
		int findVertex(int v1);
		void insertVertex(int v1); 
		void insertVertex(Vertex v1);
		void removeVertex(int v1);
		bool lastEdgeToVertex(Edge edge, int v1);
		void tryToRemoveVertices(Edge edge);
		bool insert(int v1, int v2, int weight); 
		int deleteEdge(Edge anEdge);  
		Edge deleteMax(); 
		bool containsVertices(vector <Vertex> aTree);
		bool containsAllVerticesUpTo(int topVertex);
		vector <Vertex> getVertices();
		vector <Vertex> getRemainingVertices(int verticesToKeep);
		int updateVertices(Vertex currentVertex);
		vector <Edge> shortestPath(Vertex *from, Vertex *to);
		int totalWeight();
		Edge at(int pos);
		int getElems() const;
		int getSize() const;
		void heapSwap(int a, int b);
		void print();
	private:
		Edge* array;
		int elements, size;
		vector <Vertex> vertices;
};

EdgeHeap::EdgeHeap() {
	size = 0;
	elements = 0;	
}

EdgeHeap::EdgeHeap(int aSize) { 
	size = aSize;	
	array = new Edge[size];
	elements = 0;
}

void EdgeHeap::heapify() {
	// For all parent nodes, sift their children into the proper position
	for (int i = elements/2 - 1; i >= 0; i--) sift(i);
}

void EdgeHeap::sift(int pos) {
	// For all parents, grab the smaller of the children and if smaller than the parent, swap and repeat
	while (!isLeaf(pos)) {
		int child = leftChild(pos);
		if (child < elements - 1 && array[child].getWeight() > array[child+1].getWeight()) child++;
		if (array[pos].getWeight() <= array[child].getWeight()) return;
		heapSwap(pos, child);
		pos = child;
	}
}

int EdgeHeap::isLeaf(int pos) { 
	if (pos >= elements/2 && pos < elements) return 1;
	else return 0; 
}

int EdgeHeap::leftChild(int pos) { return pos*2 + 1; }

int EdgeHeap::parent(int pos) { return (pos - 1)/2; }

Edge EdgeHeap::findSmallestEdge(int v1, int v2) {
  for (int i = 0; i < size && !array[i].isEmpty(); i++) if ((array[i].getV1() == v1 && array[i].getV2() == v2) || (array[i].getV1() == v2 && array[i].getV2() == v1)) return array[i];
  return Edge(-1, -1, -1);
}

int EdgeHeap::findEdge(Edge an_edge) {
	int loc = -1;	// Returns -1 if the edge doesn't exist and the index otherwise
	for (int i = 0; i < size; i++) if (!array[i].isEmpty() && array[i].getV1() == an_edge.getV1() && array[i].getV2() == an_edge.getV2() && array[i].getWeight() == an_edge.getWeight()) loc = i;
	return loc;
}

int EdgeHeap::findEdge(int v1, int v2, int weight) {
	int loc = -1;	// Returns -1 if the edge doesn't exist and the index otherwise
	for (int i = 0; i < size; i++) {
		if (!array[i].isEmpty() && array[i].getV1() == v1 && array[i].getV2() == v2 && array[i].getWeight() == weight) loc = i;
	}
	return loc;
}

int EdgeHeap::findVertex(int v1) {
	int loc = -1;	// Returns -1 if the edge doesn't exist and the index otherwise
	for (int i = 0; i < vertices.size(); i++) {
		if (vertices.at(i).getV1() == v1) loc = i;
	}
	return loc;
}

void EdgeHeap::insertVertex(int v1) {
	// If the vertex isn't in the vertices vector already, it gets added
	if (findVertex(v1) == -1) {
	  int i = 0;
	  while (i < vertices.size() && vertices.at(i).getV1() < v1) i++;
	  vertices.insert(vertices.begin() + i, Vertex(v1));
	}
}

void EdgeHeap::insertVertex(Vertex v1) {
	// If the vertex isn't in the vertices vector already, it gets added
	if (findVertex(v1.getV1()) == -1) {
	  int i = 0;
	  while (i < vertices.size() && vertices.at(i).getV1() < v1.getV1()) i++;
	  vertices.insert(vertices.begin() + i, v1);
	}
}

void EdgeHeap::removeVertex(int v1) {
	int loc;	// THIS ASSUMES YOU REALLY WANT THE VERTEX GONE, IT DOESN'T CARE HOW MANY EDGES GO TO IT!
	if ((loc = findVertex(v1)) != -1) vertices.erase(vertices.begin() + loc);
}

bool EdgeHeap::lastEdgeToVertex(Edge edge, int v1) {
	int edgeLoc = findEdge(edge);
	for (int i = 0; i < size; i++) if (i != edgeLoc && !array[i].isEmpty() && (array[i].getV1() == v1 || array[i].getV2() == v1)) return false;
	return true;
}

void EdgeHeap::tryToRemoveVertices(Edge edge) {
	// Method for managing the removal of vertices from the vertices vector as edges are removed, this version is clean
	// ie: If there are other edges that touch the vertex to be removed, it is skipped.
	if (lastEdgeToVertex(edge, edge.getV1())) removeVertex(edge.getV1());
	if (lastEdgeToVertex(edge, edge.getV2())) removeVertex(edge.getV2());
}

bool EdgeHeap::insert(int v1, int v2, int weight) { 	
	// Start by looking to see if the edge exists already, if not carry on with the insert
	int loc;
	if (findEdge(v1, v2, weight) == -1) {
		int pos = elements++;
		array[pos] = Edge(v1, v2, weight);	// Add in the new edge
		insertVertex(v1);			// Add in the new vertices
		insertVertex(v2);
		while (pos != 0 && array[pos].getWeight() < array[parent(pos)].getWeight()) {
			heapSwap(pos, parent(pos));
			pos = parent(pos);
		}
		return true;
	}
	return false;
}

int EdgeHeap::deleteEdge(Edge deadEdge) {
	// Find the node to delete (If it exists) and return the loc, otherwise return -1
	int loc;
	if ((loc = findEdge(deadEdge)) != -1) {
		tryToRemoveVertices(deadEdge);
		heapSwap(loc, --elements);	
		heapify();
		array[elements].setEmpty(true);
		return loc;
	}
	else return -1;
}

Edge EdgeHeap::deleteMax() {	
	// Returns -1 Edge if unsuccessful, the Edge otherwise:
	// Exchange the first element in the array (The maximum) with the last element, decrement the number
	// of elements by 1 and sift to retain the heap structure
	if (elements <= 0) { return Edge(-1, -1, -1); }	
	tryToRemoveVertices(array[0]);
	heapSwap(0, --elements);
	if (elements != 0) sift(0);
	array[elements].setEmpty(true);
	return array[elements];
}

bool EdgeHeap::containsVertices(vector <Vertex> aTree) {
  // Requires the vector provided to be sorted.
  int j = 0;
  for (int i = 0; i < aTree.size(); i++) {
    while (j < vertices.size() && vertices.at(j).getV1() < aTree.at(i).getV1()) j++;
    if (j == vertices.size()) return false;
  }
  return true;
}

bool EdgeHeap::containsAllVerticesUpTo(int topVertex) { 
  // If the vector of vertices is unique, sorted, and the last element is equal to the size, then all vertices up to that vertex are present
  return (vertices.size() == topVertex ? (vertices.size() == vertices.at(vertices.size() - 1).getV1() ? true : false) : false);
}

vector <Vertex> EdgeHeap::getVertices() { return vertices; }

vector <Vertex> EdgeHeap::getRemainingVertices(int verticesToKeep) {
  vector <Vertex> remaining; 
  for (int i = 0; i < verticesToKeep; i++) remaining.push_back(Vertex(i+1));
  int j = 0;
  for (int i = 0; i < vertices.size(); i++) {
    while (j < remaining.size() && remaining.at(j).getV1() < vertices.at(i).getV1()) j++;
    remaining.erase(remaining.begin() + j);
  }
  return remaining;
}

int EdgeHeap::updateVertices(Vertex currentVertex) {
  for (int i = 0; i < size; i++) {  // FOR EACH edge in the graph
    if (!array[i].isEmpty()) {	// If the edge at that position is not empty
      int alternateDistance = array[i].getWeight() + (currentVertex.getDistance() == -1 ? 0 : currentVertex.getDistance());
      if (array[i].getV1() == currentVertex.getV1()) {
	Vertex *newVertex = &vertices.at(findVertex(array[i].getV2()));	// The vertex on the other end of the edge connected to currentVertex
	if (newVertex -> getDistance() == -1) {
	  newVertex -> setPrevious(array[i].getV1()); 
          newVertex -> setDistance(alternateDistance);
	} else {
	  if (alternateDistance < newVertex -> getDistance()) {
	    newVertex -> setPrevious(currentVertex.getV1());
	    newVertex -> setDistance(alternateDistance);
	  }
	}
      }
      else if (array[i].getV2() == currentVertex.getV1()) {
	Vertex *newVertex = &vertices.at(findVertex(array[i].getV1()));
	if (newVertex -> getDistance() == -1) {
	  newVertex -> setPrevious(array[i].getV2());
	  newVertex -> setDistance(alternateDistance);
	} else {
	  if (alternateDistance < newVertex -> getDistance()) {
	    newVertex -> setPrevious(currentVertex.getV1());
	    newVertex -> setDistance(alternateDistance);
	  }
	}
      }
    }
  }
  int closestUnknown = -1, i = 0;
  while (i < vertices.size() && closestUnknown == -1) {
    if (!vertices.at(i).isKnown() && vertices.at(i).getDistance() > 0) closestUnknown = vertices.at(i).getDistance();
    i++;
  }
  for (int i = 0; i < vertices.size(); i++) if (!vertices.at(i).isKnown() && vertices.at(i).getDistance() > 0 && vertices.at(i).getDistance() < closestUnknown) closestUnknown = vertices.at(i).getDistance();
  for (int i = 0; i < vertices.size(); i++) if (!vertices.at(i).isKnown() && vertices.at(i).getDistance() == closestUnknown) return i;
}


vector <Edge> EdgeHeap::shortestPath(Vertex *from, Vertex *to) {
  vector <Edge> shortestPath;   
  if (findVertex(from -> getV1()) != -1 && findVertex(to -> getV1()) != -1) { // If vertices exist...
    for (int i = 0; i < vertices.size(); i++) vertices.at(i).reset(); // Initialize everything
    Vertex *currentVertex, *endVertex;
    currentVertex = &vertices.at(findVertex(from -> getV1()));
    currentVertex -> setDistance(0);	// Set distance to starting point = 0
    int done = 0; // We are not done
    endVertex = &vertices.at(findVertex(to -> getV1()));
    int counter = 1;
    while (!endVertex -> isKnown()) {
      counter ++;
      if (currentVertex == endVertex) break;
      currentVertex -> setKnown(true);
      int loc = updateVertices(*currentVertex); 
      if (loc == -1) {
	shortestPath.erase(shortestPath.begin(), shortestPath.end());
	shortestPath.push_back(Edge(-1, -1, -1));
	return shortestPath;
      }
      currentVertex = &vertices.at(loc); 
    } // Once this loop terminates, we should have a path
    // PATH FORMING PORTION
    EdgeHeap path = EdgeHeap(counter);
    while (endVertex -> getPrevious() != -1) {
      Edge pathEdge = findSmallestEdge(endVertex -> getV1(), endVertex -> getPrevious());
      if (pathEdge.getV1() == -1 && pathEdge.getV2() == -1 && pathEdge.getWeight() == -1 || (vertices.at(findVertex(endVertex -> getPrevious())).getPrevious() == endVertex -> getV1())) {
	// CATCHES NONEXISTENCE AND CYCLES
	shortestPath.erase(shortestPath.begin(), shortestPath.end());
	shortestPath.push_back(Edge(-1, -1, -1));
	return shortestPath;  // Returns the error version of shortestPath
      } else {
	shortestPath.push_back(pathEdge);
	endVertex = &vertices.at(findVertex(endVertex -> getPrevious()));
      }
    }
  } else {}
  return shortestPath;
}

int EdgeHeap::totalWeight() {
  int weight = 0;
  for (int i = 0; i < size; i++) if (!array[i].isEmpty()) weight += array[i].getWeight();
  return weight;
}

Edge EdgeHeap::at(int pos) { return array[pos]; }

int EdgeHeap::getElems() const { return elements; }

int EdgeHeap::getSize() const { return size; }

void EdgeHeap::heapSwap(int a, int b) {
	Edge temp = array[a];
	array[a] = array[b];
	array[b] = temp;
}

void EdgeHeap::print() {
	cout << "Heap of edges (array): [ ";
	for (int i = 0; i < size; i++) if (!array[i].isEmpty()) cout << "(" << array[i].getV1() << ", " << array[i].getV2() << ", " << array[i].getWeight() << "), ";
	cout << "]" << endl;
	cout << "Collection of vertices (vector): [ ";
	for (int i = 0; i < vertices.size(); i++) cout << vertices.at(i).getV1() << ", "; 
	cout << "]" << endl;
}

#endif
