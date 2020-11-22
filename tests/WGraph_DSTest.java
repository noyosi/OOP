package ex1.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ex1.src.WGraph_DS;
//import ex1.src.node_info;
import ex1.src.weighted_graph;

class WGraph_DSTest {

	@Test
	void testConstracor () {
		WGraph_DS g = new WGraph_DS();
		g.addNode(3);
	}

	@Test
	void testCopyConstracor () {
		WGraph_DS g = new WGraph_DS();
		g.addNode(1);
		g.addNode(2);
		g.connect(1, 2, 0.5);
		WGraph_DS g1 = new WGraph_DS(g);
		assertEquals(0.5, g1.getEdge(1, 2));

		assertEquals(g, g1);
	}

	@Test
	void testGetNode() {
		weighted_graph g = new WGraph_DS();
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		assertNotEquals(null, g.getNode(1));
	}

	@Test
	void testHasEdge () {
		weighted_graph g = new WGraph_DS();
		g.addNode(1);
		g.addNode(2);
		assertFalse(g.hasEdge(1, 2));
		g.connect(1, 2, 0.5);
		assertTrue(g.hasEdge(1, 2));
	}

	@Test
	void testGetEdge() {
		weighted_graph g = new WGraph_DS();
		g.addNode(1);
		g.addNode(2);
		g.connect(1, 2, 0.2);
		assertEquals(0.2, g.getEdge(1, 2));

		g.connect(1, 2, 0.5);
		assertEquals(0.5, g.getEdge(1, 2));
	}

	@Test
	void testAddNode () {
		weighted_graph g = new WGraph_DS();
		g.addNode(1);
		assertEquals(1, g.nodeSize());
		g.addNode(1);
		assertEquals(1, g.nodeSize());
	}

	@Test
	void testConnect() {
		weighted_graph g = new WGraph_DS();
		g.addNode(1);
		g.addNode(2);

		g.connect(1, 2, 0.2);

		assertTrue(g.hasEdge(1, 2));
		assertTrue(g.hasEdge(2, 1));
		assertEquals(0.2, g.getEdge(1, 2));

		g.connect(1, 1, 0.5);
		assertFalse(g.hasEdge(1, 1));
	}

	@Test
	void testGetV() {
		weighted_graph g = new WGraph_DS();
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		assertEquals(3, g.getV().size());
	}

	@Test
	void testRemoveNode () {
		weighted_graph g = new WGraph_DS();
		g.addNode(1);
		assertEquals(null, g.removeNode(2));
		g.addNode(2);
		assertEquals(2, g.removeNode(2).getKey());

		g.addNode(2);
		g.connect(1, 2, 0.2);
		g.removeNode(2);
		assertFalse(g.hasEdge(1, 2));
		assertFalse(g.hasEdge(2, 1));
	}

	@Test
	void testRemoveEdge () {
		weighted_graph g = new WGraph_DS();
		g.addNode(1);
		g.addNode(2);
		g.connect(1, 2, 0.2);
		g.removeEdge(1, 2);
		assertFalse(g.hasEdge(1, 2));
	}

	@Test
	void testNodeSize () {
		weighted_graph g = new WGraph_DS();
		g.addNode(1);
		g.addNode(2);
		assertEquals(2, g.nodeSize());
	}

	@Test
	void testEdgeSize () {
		weighted_graph g = new WGraph_DS();
		g.addNode(1);
		g.addNode(2);
		g.connect(1, 2, 0.5);
		assertEquals(1, g.edgeSize());
	}
}