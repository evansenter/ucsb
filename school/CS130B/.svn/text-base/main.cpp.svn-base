#include <stdio.h>
#include <vector>
#include "Methods.cpp"
using namespace std;

main() {
  int method, format, num_vertices, num_edges, vert_in_tree;
  scanf("%d %d %d %d %d", &method, &format, &num_vertices, &num_edges, &vert_in_tree);
  //printf("%d, %d, %d, %d, %d\n\n", method, format, num_vertices, num_edges, vert_in_tree);
  EdgeHeap heap = EdgeHeap(num_edges);
  // BRANCH HERE BASED ON OUTPUT FORMAT

  if (format != 3) { // Not SAMBA

  for (int i = 0; i < num_edges; i++) {
    int v1, v2, weight;
    scanf("%d %d %d", &v1, &v2, &weight);
    heap.insert(v1, v2, weight); 
  }
  Methods controller = Methods(heap, vert_in_tree); // CONTROLLER for getting the response tree.
  EdgeHeap response;
  if (method == 1) response = controller.shortestPath();
  else if (method == 2) response = controller.closestPair();
  else if (method == 3) response = controller.smallestEdge();
  else if (method == 4) response = controller.divideAndConquer(heap);
  else if (method == 5 || method == 6) response = controller.dynamicProgramming();
  else if (method == 7) response = controller.customMethod();
  
  // Print formatting
  
  if (format == 1) {
    cout << response.totalWeight() << endl;;
    for (int i = 0; i < vert_in_tree; i++)
      for (int j = 0; j < response.getSize() && !response.at(j).isEmpty(); j++)
	if (response.at(j).getV1() == i)
	  cout << response.at(j).getV1() << " " << response.at(j).getV2() << endl;
  } else { 
    cout << response.totalWeight() << endl;
    cout << response.getElems() << endl;
  }

  } else {  // SAMBA










    int minX = 0, maxX = 0, minY = 0, maxY = 0;
    vector <Edge> edges;
    for (int i = 0; i < num_edges; i++) {
      int v1, v2, weight, x, y;
      scanf("%d %d %d %d %d", &v1, &v2, &weight, &x, &y);
      heap.insert(v1, v2, weight);
      edges.push_back(Edge(v1, v2, x, y, weight));
      if (x > maxX) maxX = x;
      if (x < minX) minX = x;
      if (y > maxY) maxY = y;
      if (y < minY) minY = y;
    }
    Methods controller = Methods(heap, vert_in_tree); // CONTROLLER for getting the response tree.
    EdgeHeap response;
    if (method == 1) response = controller.shortestPath();
    else if (method == 2) response = controller.closestPair();
    else if (method == 3) response = controller.smallestEdge();
    else response = controller.customMethod();
  
    // Print formatting 
    cout << "%" << response.totalWeight() << endl;
    cout << "%" << response.getElems() << endl;
    cout << "coords " << minX << " " << minY << " " << maxX << " " << maxY << endl;
    for (int i = 0; i < response.getSize() && !response.at(i).isEmpty(); i++) {
      Edge sambaEdge;
      for (int j = 0; j < edges.size(); j++)
	if ((edges.at(j).getV1() == response.at(i).getV1() || edges.at(j).getV1() == response.at(i).getV2()) && (edges.at(j).getV2() == response.at(i).getV1() || (edges.at(j).getV2()) == response.at(i).getV2()))
	  cout << "pointline " << edges.at(j).getX() << " " << edges.at(j).getY() << " " << edges.at(j).getV1() << " " << edges.at(j).getV2() << " " << "black" << "1" << endl; 
    }
  }
}
