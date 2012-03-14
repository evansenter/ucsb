// Edge header file (With include guards and implementation)
#include <iostream>

#ifndef EDGE_H
#define EDGE_H

class Edge {	
	public:
		Edge();
		Edge(int v1, int v2, int weight);
		Edge(int v1, int v2, int x, int y, int weight);
		int getWeight() const;
		int getV1() const;
		int getV2() const;
		int getX() const;
		int getY() const;
		bool isEmpty() const;
		void setEmpty(bool truth); 
		void print();
	private:
		int v1, v2, x, y, weight;
		bool empty;
};

Edge::Edge() { empty = true; }

Edge::Edge(int a_v1, int a_v2, int a_weight) { 
	// Console version constructor
	v1 = a_v1;
	v2 = a_v2;
	weight = a_weight;	
	empty = false;
}

Edge::Edge(int a_v1, int a_v2, int a_x, int a_y, int a_weight) {
	// SAMBA version constructor
	v1 = a_v1;
	v2 = a_v2;
	x = a_x;
	y = a_y;
	weight = a_weight;
	empty = false;
}

int Edge::getWeight() const { return weight; }

int Edge::getV1() const { return v1; }

int Edge::getV2() const { return v2; }

int Edge::getX() const { return x; }

int Edge::getY() const { return y; }

bool Edge::isEmpty() const { return empty; }

void Edge::setEmpty(bool truth) { empty = truth; }

void Edge::print() { 
  std::cout << v1 << ", " << v2 << " - weight = " << weight;
  if (empty) std::cout << ", this edge is EMPTY." << std::endl;
  else std::cout << ", this edge is NOT EMTPY." << std::endl;
}

#endif
