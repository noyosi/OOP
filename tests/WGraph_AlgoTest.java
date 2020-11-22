package ex1.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

	import java.util.Collection;
	import java.util.Iterator;

	import ex1.src.WGraph_Algo;
	import ex1.src.WGraph_DS;
	import ex1.src.node_info;
	import ex1.src.weighted_graph;
	import ex1.src.weighted_graph_algorithms;

	class WGraph_AlgoTest {
		
		@Test
		void testCopy () {
			WGraph_DS g = new WGraph_DS();
			g.addNode(1);
			g.addNode(2);
			g.connect(1, 2, 0.5);
			WGraph_Algo ga = new WGraph_Algo();
			ga.init(g);
			weighted_graph g1 = ga.copy();
			assertEquals(0.5, g1.getEdge(1, 2));
			
			assertEquals(g, g1);
		}
		
		@Test
		void testIsConnected () {
			weighted_graph g = new WGraph_DS();
			WGraph_Algo g1 =  new WGraph_Algo();
			g1.init(g);
			g.addNode(1);
			g.addNode(2);
			g.addNode(3);
			g.connect(1, 2, 0.5);
			g.connect(2, 3, 0.5);
			g.connect(1, 3, 0.5);
			assertTrue(g1.isConnected());
		}
		
		@Test
		void testShortestPathDist () {
			weighted_graph g = new WGraph_DS();
			WGraph_Algo g1 =  new WGraph_Algo();
			g1.init(g);
			g.addNode(1);
			g.addNode(2);
			g.addNode(3);
			g.addNode(4);
			g.connect(1, 2, 3);
			g.connect(2, 3, 2);
			g.connect(3, 4, 3);
			g.connect(4, 1, 2.5);
			assertEquals(5.0,(g1.shortestPathDist(1, 3)));
		}
		
		@Test
		void testShortestPath () {
			weighted_graph g = new WGraph_DS();
			WGraph_Algo g1 =  new WGraph_Algo();
			g1.init(g);
			g.addNode(1);
			g.addNode(2);
			g.addNode(3);
			g.addNode(4);
			g.connect(1, 2, 3);
			g.connect(2, 3, 2);
			g.connect(3, 4, 3);
			g.connect(4, 1, 2.5);
			
			Collection<node_info> c = g1.shortestPath(1, 3);
			Iterator<node_info> it = c.iterator();
			for (int i = 1; i <= 3 ; i++) {
				node_info n = it.next();
				assertEquals(i, n.getKey());
			}
		}
		
		@Test
		void testSaveLoad () {
			 weighted_graph g = new WGraph_DS();
		        weighted_graph_algorithms g1 = new WGraph_Algo();
		        g1.init(g);
		        String str = "GraphName.obj";
		        g1.save(str);
		        g1.load(str);
		        weighted_graph g2 = g1.getGraph();
		        assertEquals(g,g2);
		}
}