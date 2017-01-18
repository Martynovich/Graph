package test.java.com.andersen.graph;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import main.java.com.andersen.exceptions.EmptyGraphException;
import main.java.com.andersen.exceptions.NegativeCycleException;
import main.java.com.andersen.graph.Graph;

public class GraphTest {

	/**
	 * Check shortest way length.
	 */
	@Test
	public void testGetShortestLength() throws NegativeCycleException, EmptyGraphException {
		Graph graph = new Graph.Builder().edge(1, 2, 10).edge(2, 3, 5).edge(3, 4, 5).edge(4, 5, 4).build();
		int shortestDistancesBetweenVertexOneAndVertexFive = graph.getShortestLength(1, 5);
		assertEquals(24, shortestDistancesBetweenVertexOneAndVertexFive);
		assertFalse(25 == shortestDistancesBetweenVertexOneAndVertexFive);
	}

	/**
	 * Check shortest path between three current.
	 */
	@Test
	public void getShortestPathTest() throws NegativeCycleException, EmptyGraphException {
		Graph graph = new Graph.Builder().edge(1, 2, 2).edge(2, 3, 3).edge(3, 6, 4).edge(1, 4, 2).edge(4, 6, 8)
				.edge(4, 5, 3).edge(5, 6, 4).build();
		ArrayList<Integer[]> edgesLinkingStartAndFinishVertexes = graph.getShortestPath(1, 6);
		assertFalse(edgesLinkingStartAndFinishVertexes.isEmpty());
		assertTrue(edgesLinkingStartAndFinishVertexes.size() == 3);
		assertTrue(edgeComparator(edgesLinkingStartAndFinishVertexes.get(0), new Integer[] { 1, 2, 2 })
				&& edgeComparator(edgesLinkingStartAndFinishVertexes.get(1), new Integer[] { 2, 3, 3 })
				&& edgeComparator(edgesLinkingStartAndFinishVertexes.get(2), new Integer[] { 3, 6, 4 }));
	}

	/**
	 * Check shortest path between two equals in length but different in edges
	 * quantity.
	 */
	@Test
	public void getShortestPathBetweenTwoEqualBranchesWithDifferentNumberOfVertexesTest()
			throws NegativeCycleException, EmptyGraphException {
		Graph graph = new Graph.Builder().edge(1, 2, 1).edge(2, 3, 2).edge(3, 5, 3).edge(1, 4, 2).edge(4, 5, 4).build();
		ArrayList<Integer[]> edgesLinkingStartAndFinishVertexes = graph.getShortestPath(1, 5);
		assertFalse(edgesLinkingStartAndFinishVertexes.isEmpty());
		assertTrue(edgesLinkingStartAndFinishVertexes.size() == 2);
		assertTrue(edgeComparator(edgesLinkingStartAndFinishVertexes.get(0), new Integer[] { 1, 4, 2 })
				&& edgeComparator(edgesLinkingStartAndFinishVertexes.get(1), new Integer[] { 4, 5, 4 }));
	}

	/**
	 * Check shortest path between two current.Graph has negative edges.
	 */
	@Test
	public void getShortestPathWithNegativEdgesTest() throws NegativeCycleException, EmptyGraphException {
		Graph graph = new Graph.Builder().edge(1, 2, 4).edge(2, 3, 5).edge(3, 5, -6).edge(1, 4, 2).edge(4, 5, 3)
				.build();
		ArrayList<Integer[]> edgesLinkingStartAndFinishVertexes = graph.getShortestPath(1, 5);
		assertFalse(edgesLinkingStartAndFinishVertexes.isEmpty());
		assertTrue(edgesLinkingStartAndFinishVertexes.size() == 3);
		assertTrue(edgeComparator(edgesLinkingStartAndFinishVertexes.get(0), new Integer[] { 1, 2, 4 })
				&& edgeComparator(edgesLinkingStartAndFinishVertexes.get(1), new Integer[] { 2, 3, 5 })
				&& edgeComparator(edgesLinkingStartAndFinishVertexes.get(2), new Integer[] { 3, 5, -6 }));
	}

	/**
	 * Check empty graph. Waiting for EmptyGraphException.
	 */
	@Test(expected = EmptyGraphException.class)
	public void emptyGraphTest() throws NegativeCycleException, EmptyGraphException {
		Graph graph = new Graph.Builder().build();
		graph.getShortestPath(1, 2);
	}

	/**
	 * Check graph. Waiting for IllegalArgumentException.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void absentVertexTest() throws NegativeCycleException, EmptyGraphException {
		Graph graph = new Graph.Builder().edge(1, 2, 3).edge(2, 3, 4).build();
		graph.getShortestPath(1, 8);
	}

	/**
	 * Check distance from vertex to itself.
	 */
	public void vertexPathToItself() throws NegativeCycleException, EmptyGraphException {
		Graph graph = new Graph.Builder().edge(1, 1, 3).edge(1, 1, 4).build();
		int length = graph.getShortestLength(1, 1);
		ArrayList<Integer[]> path = graph.getShortestPath(1, 1);
		assertTrue(length == 0 && path == null);
	}

	/**
	 * Check distance from vertex to itself when exist negative cycle.
	 */
	@Test(expected = NegativeCycleException.class)
	public void vertexPathToItselfNegativLoop() throws NegativeCycleException, EmptyGraphException {
		Graph graph = new Graph.Builder().edge(1, 1, -3).edge(1, 1, -4).build();
		ArrayList<Integer[]> path = graph.getShortestPath(1, 1);
	}

	/**
	 * Check graph in which linking edge between two vertices is absent.
	 */
	@Test
	public void linkingEdgeIsAbsent() throws NegativeCycleException, EmptyGraphException {
		Graph graph = new Graph.Builder().edge(1, 2, -5).edge(2, 3, -6).vertex(4).build();
		assertTrue(graph.getShortestLength(1, 4) == 1000000);
		assertTrue(graph.getShortestPath(1, 4).isEmpty());
	}

	/**
	 * Check graph in which repeating edges between two vertexes
	 */
	@Test
	public void repeatingEdgesBetweeTwoVertexesTest() throws NegativeCycleException, EmptyGraphException {
		Graph graph = new Graph.Builder().edge(1, 2, 2).edge(1, 2, 3).edge(2, 3, 5).edge(2, 3, 6).edge(3, 4, 1).build();
		ArrayList<Integer[]> edgesLinkingStartAndFinishVertexes = graph.getShortestPath(1, 4);
		assertFalse(edgesLinkingStartAndFinishVertexes.isEmpty());
		assertTrue(edgesLinkingStartAndFinishVertexes.size() == 3);
		assertTrue(graph.getShortestLength(1, 4) == 8);
		assertTrue(edgeComparator(edgesLinkingStartAndFinishVertexes.get(0), new Integer[] { 1, 2, 2 })
				&& edgeComparator(edgesLinkingStartAndFinishVertexes.get(1), new Integer[] { 2, 3, 5 })
				&& edgeComparator(edgesLinkingStartAndFinishVertexes.get(2), new Integer[] { 3, 4, 1 }));
	}

	/**
	 * Test for equals.
	 */
	@Test
	public void equalsTest() {
		Graph graph = new Graph.Builder().edge(1, 2, 2).edge(2, 3, 3).edge(3, 5, -4).edge(1, 4, 5).edge(4, 5, 6)
				.build();
		Graph graph2 = new Graph.Builder().edge(2, 3, 3).edge(1, 2, 2).edge(3, 5, -4).edge(1, 4, 5).edge(4, 5, 6)
				.build();
		Graph graph3 = new Graph.Builder().edge(2, 3, 3).edge(1, 2, 2).edge(1, 4, 5).edge(4, 5, 6).edge(3, 5, -4)
				.build();
		Graph graph4 = new Graph.Builder().edge(1, 2, 2).edge(2, 3, 3).edge(3, 5, -4).edge(1, 4, 5).edge(4, 5, 6)
				.vertex(8).build();
		assertTrue(graph.equals(graph));
		assertTrue(graph.equals(graph2));
		assertTrue(graph2.equals(graph));
		assertTrue(graph2.equals(graph3));
		assertTrue(graph.equals(graph3));
		assertFalse(graph.equals(graph4));
		assertFalse(graph.equals(null));
	}

	/**
	 * Test for hashCode
	 */
	@Test
	public void hashCodeTest() {
		Graph graph = new Graph.Builder().edge(1, 2, 2).edge(2, 3, 3).edge(3, 5, -4).edge(1, 4, 5).edge(4, 5, 6)
				.build();
		Graph graph2 = new Graph.Builder().edge(2, 3, 3).edge(1, 2, 2).edge(3, 5, -4).edge(1, 4, 5).edge(4, 5, 6)
				.build();
		Graph graph3 = new Graph.Builder().edge(1, 2, 2).edge(2, 3, 3).edge(3, 5, -4).edge(1, 4, 5).edge(4, 5, 6)
				.vertex(8).build();
		assertTrue(graph.equals(graph2) && graph.hashCode() == graph2.hashCode());
		assertFalse(graph.equals(graph3) && graph.hashCode() == graph3.hashCode());
	}

	private boolean edgeComparator(Integer[] shortestPathElement, Integer[] etalon) {
		if (shortestPathElement[0] == etalon[0] && shortestPathElement[1] == etalon[1]
				&& shortestPathElement[0] == etalon[0]) {
			return true;
		}
		return false;
	}

}