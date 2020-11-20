# Ex1-oop- weighted graph

### WGraph_DS-
1. getNode(int key)- return the node with this key.
2. hasEdge(int node1, int node2)- return true if there is an edge between node1 & node2, else return false.
3. getEdge(int node1, int node2)- if there is an edge between those two nodes than it returns the weight of the edge, else return -1.
4. addNode(int key)- add a node with this key to the graph.
5. connect(int node1, int node2, double w)- connect between those two nodes with this weight.
6. getV()-return a collection of the nodes in the graph.
7. getV(int node_id)-returns a collection of the neighbors nodes of this node.
8. removeNode(int key)- remove this node from the graph.
9. removeEdge(int node1, int node2)- remove the edge between those two nodes.
10. nodeSize()- return the number of nodes in this graph.
11. edgeSize()-return the number of edges in this graph.
12. getMC()-return the number of operations that we did on the graph.

### WGraph_Algo-
1. init(weighted_graph g)- put g in this.g.
2. getGraph()- return this g.
3. copy()- return a copy of this g.
4. isConnected()-return true if this graph is connected.
5. shortestPathDist(int src, int dest)-return the distance between src and dest.
6. shortestPath(int src, int dest)- return a list of nodes that represent the path from src to dest.
7. save(String file)-Saves this weighted (undirected) graph to the given file name.
8. load(String file)- load a graph to this graph algorithm.














