Object-Oriented Programming (OOP)

In this assignment, I developed infrastructure of data structure, algorithms, and display system.
This assignment deals with the development of a data structure of weighted, undirected graph. 
A weighted graph is a graph in which each edge is given a numerical weight.
An undirected graph is a set of objects (called vertices or nodes) that are connected together, where all the edges are bidirectional.

After implement the data structure, I implement several algorithms on the graph including duplication ability, bonding test (to check if the graph is connected), short path calculation (minimum distance by the edge weight), finding the shortest path (a collection of vertices between the source and the destination) and the ability to save and restore the graph from a file.

At the beginning, I did WGraph_DS class which implements weighted_graph interface,
using an inner class NodeInfo that implement node_info interface (which describe the features of vertex in the graph).  
In WGraph_DS class I make functions such as:
hasEdge that returns true or false whether two nodes have edge between them.
connect that connect an edge between two nodes, with an edge with weight >=0
Also, other function such as: addNode, removeNode, removeEdge and more. 

Then, I did WGraph_Algo class that implements weighted_graph_algorithms interface (which represent a collection of algorithms on graphs). 
In WGraph_Algo class I make functions such as:
isConnected that returns true or false whether the graph is connected. A graph is connected if every pair of vertices in the graph is connected. This means that there is a path between every pair of vertices.
shortestPathDist that returns the length of the shortest path between src to dest.
save that saves the weighted (undirected) graph to the given file name and more.



