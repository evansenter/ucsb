// Vertex header file (With include guards and implementation)
#include <iostream>

#ifndef VERTEX_H
#define VERTEX_H

class Vertex {
        public:
                Vertex();
                Vertex(int v1);
		void reset();
                int getV1() const;
                int getDistance() const;
                void setDistance(int aDistance);
                int getPrevious() const;
                void setPrevious(int aPrevious);
                bool isKnown() const;
                void setKnown(bool truth);
		void print();
        private:
                int v1, distance, previous;
                bool known;
};

Vertex::Vertex() {}

Vertex::Vertex(int aV1) {                         
  v1 = aV1;
  distance = -1;
  previous = -1;    
  known = false;
}

void Vertex::reset() {
  distance = -1;
  previous = -1;    
  known = false;
}

int Vertex::getV1() const { return v1; }

int Vertex::getDistance() const { return distance; }

void Vertex::setDistance(int aDistance) { distance = aDistance; }

int Vertex::getPrevious() const { return previous; }

void Vertex::setPrevious(int aPrevious) { previous = aPrevious; }

bool Vertex::isKnown() const { return known; }

void Vertex::setKnown(bool truth) { known = truth; }

void Vertex::print() { 
  std::cout << v1 << ", previous = " << previous << " and the distance = " << distance;
  if (known) std::cout << ", this vertex is known." << std::endl;
  else std::cout << ", this vertex is NOT known." << std::endl;
}

#endif

