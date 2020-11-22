package ex1.src;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class WGraph_DS implements weighted_graph, Serializable {

	private HashMap<Integer, node_info> graphMap;
	private HashMap<Integer, HashMap<Integer, Double>> neighborsMap;
	private int edgeSize;
	private int Mc;
	private static final long serialVersionUID = 1L; // for serializable

	/**
	 * Constractor
	 */
	public WGraph_DS() {
		graphMap = new HashMap<Integer, node_info>();
		neighborsMap = new HashMap<Integer, HashMap<Integer, Double>>();
		edgeSize = 0;
		Mc = 0;
	}

	/**
	 * Copy constructor
	 * @param g - another graph
	 */
	public WGraph_DS(weighted_graph g) {
		this();
		for (node_info node : g.getV()) { //copy the nodes
			int nodeKey = node.getKey();
			addNode(nodeKey);
		}
		for (node_info node : g.getV()) { //copy the edges
			int nodeKey = node.getKey();
			for (node_info nei : g.getV(nodeKey)) {
				int neiKey = nei.getKey();
				connect(nodeKey, neiKey, g.getEdge(nodeKey, neiKey));
			}
		}
	}

	@Override
	/**
	 * Return the node_data by the node_id
	 * return null if none
	 * @param key - the node_id
	 */
	public node_info getNode(int key) {
		return graphMap.get(key);
	}

	@Override
	/**
	 * Return true iff there is an edge between node1 and node2
	 * @param node1
	 * @param node2
	 */
	public boolean hasEdge(int node1, int node2) {
		if (!graphMap.containsKey(node1) || !graphMap.containsKey(node2)) {
			return false;
		}
		return neighborsMap.get(node1).containsKey(node2);
	}

	@Override
	/**
	 * Return the weight if the edge (node1, node1)
	 * in case there is no such edge -> should return -1
	 * @param node1
	 * @param node2
	 */
	public double getEdge(int node1, int node2) {
		if (!hasEdge(node1, node2)) {
			return -1;
		}
		return neighborsMap.get(node1).get(node2);
	}

	@Override
	/**
	 * Add a new node to the graph with the given key
	 * if there is already a node with such a key -> no action should be performed
	 * @param key
	 */
	public void addNode(int key) {
		if (graphMap.containsKey(key)) {
			return;
		}
		node_info n = new NodeInfo(key);
		graphMap.put(key, n);
		neighborsMap.put(key, new HashMap<Integer, Double>());
		Mc++;
	}

	@Override
	/**
	 * Connect an edge between node1 and node2, with an edge with weight >=0
	 * @param node1
	 * @param node2
	 * @param w - the weight
	 */
	public void connect(int node1, int node2, double w) {
		if (node1 == node2) {
			return;
		}

		if (!graphMap.containsKey(node1) || !graphMap.containsKey(node2)) {
			String nodes = "";
			if (!graphMap.containsKey(node1))
				nodes += node1;

			if (!graphMap.containsKey(node2)) {
				if(nodes.length() > 0)
					nodes += " and ";
				nodes += node2;
			}			
			throw new RuntimeException(
					"can't connect the nodes: " + node1 + " and " + node2 + ". " + nodes + " doesn't exsist");	
		}
		if (w < 0) {
			throw new RuntimeException("the weight is: " + w + " but it have to be positive");
		}

		if (!hasEdge(node1, node2)) {
			edgeSize++;
		}
		neighborsMap.get(node1).put(node2, w);
		neighborsMap.get(node2).put(node1, w);
		Mc++;
	}

	@Override
	/**
	 * This method return a pointer (shallow copy) for a Collection representing all
	 * the nodes in the graph
	 */
	public Collection<node_info> getV() {
		return graphMap.values();
	}

	@Override
	/**
	 * This method returns a Collection containing all the nodes connected to node_id
	 */
	public Collection<node_info> getV(int node_id) {
		Set<Integer> neighborsNumer = neighborsMap.get(node_id).keySet(); // set of nodes key
		Set<node_info> neighbors = new HashSet<node_info>(); // new set of noeds objects
		for (Integer node : neighborsNumer) {
			node_info n = graphMap.get(node);
			neighbors.add(n);
		}
		return neighbors;
	}

	@Override
	/**
	 * Delete the node (with the given ID) from the graph -and removes all edges
	 * which starts or ends at this node
	 * @return the data of the removed node (null if none).
	 * @param key
	 */
	public node_info removeNode(int key) {
		if (getNode(key) == null) {
			return null;
		}
		for (Integer node : neighborsMap.get(key).keySet()) {
			neighborsMap.get(node).remove(key);
		}
		edgeSize -= neighborsMap.get(key).size();
		neighborsMap.remove(key);

		Mc++;
		return graphMap.remove(key);
	}

	@Override
	/**
	 * Delete the edge from the graph
	 * @param node1
	 * @param node2
	 */
	public void removeEdge(int node1, int node2) {
		if (!hasEdge(node1, node2)) {
			return;
		}
		neighborsMap.get(node1).remove(node2);
		neighborsMap.get(node2).remove(node1);
		edgeSize--;
		Mc++;
	}

	@Override
	/**
	 * Return the number of vertices (nodes) in the graph
	 */
	public int nodeSize() {
		return getV().size();
	}

	@Override
	/**
	 * Return the number of edges (undirectional graph)
	 */
	public int edgeSize() {
		return edgeSize;
	}

	@Override
	/**
	 * Return the Mode Count - for testing changes in the graph
	 */
	public int getMC() {
		return Mc;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof WGraph_DS))
			return false;

		WGraph_DS g = (WGraph_DS) obj;

		if (nodeSize() != g.nodeSize()) {
			return false;
		}
		if (edgeSize() != g.edgeSize()) {
			return false;
		}
		if (!graphMap.equals(g.graphMap)) {
			return false;
		}
		if (!neighborsMap.equals(g.neighborsMap)) {
			return false;
		}
		return true;
	}

	public static class NodeInfo implements node_info, Serializable {

		private static final long serialVersionUID = 1L;
		private int key;
		private HashMap<Integer, node_info> Ni; // neighbors
		private String info;
		private double tag;
		private static int KeyCounter = 0; // for the default constractur

		//Constructor
		public NodeInfo() {
			this(KeyCounter);
			KeyCounter++;
		}

		public NodeInfo(int key) {
			this.key = key;
			Ni = new HashMap<Integer, node_info>();
			tag = -1;
		}

		/**
		 * Copy constructor
		 * @param n - another node data
		 */
		public NodeInfo(node_info n) {
			this.key = n.getKey();
			this.Ni = new HashMap<Integer, node_info>();
			this.info = n.getInfo();
			this.tag = n.getTag();
		}

		/**
		 * Return the key (id) associated with this node
		 */
		@Override
		public int getKey() {
			return this.key;
		}

		/**
		 * Return the remark (meta data) associated with this node
		 */
		@Override
		public String getInfo() {
			return this.info;
		}

		/**
		 * Allows changing the remark (meta data) associated with this node.
		 */
		@Override
		public void setInfo(String s) {
			info = s;
		}

		/**
		 * Temporal data (aka distance, color, or state) which can be used be algorithms
		 */
		@Override
		public double getTag() {
			return this.tag;
		}

		/**
		 * Allow setting the "tag" value for temporal marking an node - common practice for marking by algorithms
		 */
		@Override
		public void setTag(double t) {
			this.tag = t;
		}

		@Override
		public String toString () {
			return "" + this.key;
		}

		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof NodeInfo))
				return false;
			NodeInfo n = (NodeInfo) obj;
			return getKey() == n.getKey();
		}
	}
}