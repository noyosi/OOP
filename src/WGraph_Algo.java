package ex1.src;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

public class WGraph_Algo implements weighted_graph_algorithms { 
	
	private final int WHITE = 0, GREY = 1;
	private weighted_graph mGraph;
	private HashMap<Integer, Double> distances;

	@Override
	/**
	 * Init the graph on which this set of algorithms operates on
	 * @param g 
	 */
	public void init(weighted_graph g) {
		mGraph = g;
	}

	@Override
	/**
	 * Return the underlying graph of which this class works
	 */
	public weighted_graph getGraph() {
		return mGraph;
	}

	@Override
	/**
	 * Compute a deep copy of this weighted graph
	 */
	public weighted_graph copy() {
		return mGraph == null ? null : new WGraph_DS(mGraph);
	}

	@Override
	/**
	 * Returns true iff there is a valid path from EVREY node to each other node
	 */
	public boolean isConnected() {
		if (mGraph.getV().size() < 2) {
			return true;
		}
		//set all the nodes to white
		for (node_info n : mGraph.getV()) {
			n.setTag(WHITE);
		}
		node_info first = mGraph.getV().iterator().next(); //some arbitrary node
		first.setTag(GREY); //set the arbitrary node tag to grey
		Queue<node_info> q = new LinkedList<node_info>();
		q.add(first);
		//the bfs main loop
		while (!q.isEmpty()) {
			node_info v = q.poll();
			for (node_info u : mGraph.getV(v.getKey())) {
				if (u.getTag() == WHITE) {
					u.setTag(GREY);
					q.add(u);
				}
			}
		}
		//check if there is an unreachable node
		for (node_info n : mGraph.getV()) {
			if (n.getTag() == WHITE) {
				return false;
			}
		}
		return true;
	}

	/**
	 * At the end - the dest node will save the shortest distacne from the src, and
	 * each node will point to it's ancestor (in the info field)
	 * @param src  - the source
	 * @param dest - the destination
	 */
	private void dijkstra(int src, int dest) {
		if (mGraph.getNode(src) == null || mGraph.getNode(dest) == null) {
			throw new RuntimeException("the node " + src + " or " + dest + " doesn't exsist");
		}
		
		distances = new HashMap<>();

		//set the tag of the nodes to infinity
		for (node_info n : mGraph.getV()) {
			distances.put(n.getKey(), Double.POSITIVE_INFINITY);
			n.setTag(-1);
		}

		node_info first = mGraph.getNode(src);
		distances.put(first.getKey(), 0.0);
		if (src == dest)
			return;

		Comparator<node_info> comparator = new Comparator<node_info>() {

			@Override
			public int compare(node_info o1, node_info o2) {
				return Double.compare(distances.get(o1.getKey()), distances.get(o2.getKey()));
			}
		};
		
		PriorityBlockingQueue<node_info> q = new PriorityBlockingQueue<node_info>(mGraph.getV().size(), comparator);

		q.addAll(mGraph.getV());

		//the bfs main loop
		while (!q.isEmpty()) {
			node_info v = q.poll();
			double distV = distances.get(v.getKey()); //the distance of v from the src
			if (distV == Double.POSITIVE_INFINITY) //there is no more nodes that are reachable from src
				break;
			for (node_info u : mGraph.getV(v.getKey())) {
				double distU = distances.get(u.getKey()); //the distance of u from the src
				double uv = mGraph.getEdge(u.getKey(), v.getKey());
				if (distU > distV + uv) {
					//the distance fron the src
					distances.put(u.getKey(), distV + uv);
					//the ancestor of u node is v
					u.setTag(v.getKey());
					q.remove(u);
					q.add(u);
				}
			}
		}
	}

	@Override
	/**
	 * Returns the length of the shortest path between src to dest 
	 *  if no such path -> returns -1
	 * @param src - start node
	 * @param dest - end (target) node
	 */
	public double shortestPathDist(int src, int dest) {
		dijkstra(src, dest);
		if (distances.get(dest) == Double.POSITIVE_INFINITY) {
			return -1;
		}
		return distances.get(dest);
	}

	@Override
	/**
	 * Returns the shortest path between src to dest - as an ordered List of nodes
	 * if no such path -> returns null
	 * @param src - start node
	 * @param dest - end (target) node
	 */
	public List<node_info> shortestPath(int src, int dest) {
		dijkstra(src, dest);
		if (src != dest && mGraph.getNode(dest).getTag() == -1) {
			return null;
		}
		LinkedList<node_info> list = new LinkedList<>();
		int nodeKey = dest;
		do {
			node_info node = mGraph.getNode(nodeKey);
			list.addFirst(node);
			nodeKey = (int) node.getTag();
		} while (nodeKey != -1);
		return list;
	}

	@Override
	/**
	 * Saves this weighted (undirected) graph to the given file name
	 * return true - iff the file was successfully saved
	 * @param file - the file name (may include a relative path).
	 */
	public boolean save(String file) {
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(mGraph);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			return false;
		}
		return true;
	}

	@Override
	/**
	 * This method load a graph to this graph algorithm
	 * if the file was successfully loaded - the underlying graph of this class will be changed (to the loaded one),
	 * in case the graph was not loaded the original graph should remain "as is".
	 * return true - iff the graph was successfully loaded
	 * @param file - the file name
	 */
	public boolean load(String file) {
		try {
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			mGraph = (WGraph_DS) in.readObject();
			in.close();
			fileIn.close();
		} catch (Exception i) {
			return false;
		}
		return true;
	}
}