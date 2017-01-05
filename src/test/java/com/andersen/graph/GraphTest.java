package test.java.com.andersen.graph;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import main.java.com.andersen.graph.Graph;

public class GraphTest {
	
	@Test
	public void testGetShortestLength(){
		Graph graph = new Graph.Builder().edge(1,2,10).edge(2,3,5).edge(3,4,5).edge(4,5,4).build();
		int shortestDistancesBetweenVertexOneAndVertexFive = graph.getShortestLength(1, 5);
		assertEquals(24, shortestDistancesBetweenVertexOneAndVertexFive);
		assertFalse(25 == shortestDistancesBetweenVertexOneAndVertexFive);
	}
	
	@Test
	public void testGetShortestPath(){
		Graph graph = new Graph.Builder().edge(1,2,10).edge(2,3,5).edge(3,4,5).edge(4,5,4).build();
		ArrayList<Integer[]> edgesLinkingStartAndFinishVertexes = graph.getShortestPath(1, 5);
		assertFalse(edgesLinkingStartAndFinishVertexes.isEmpty());
		assertTrue(edgesLinkingStartAndFinishVertexes.size() == 4);
		assertTrue(edgesLinkingStartAndFinishVertexes.get(0)[0] == 1 && edgesLinkingStartAndFinishVertexes.get(0)[1] == 2 &&
				edgesLinkingStartAndFinishVertexes.get(0)[2] == 10 && edgesLinkingStartAndFinishVertexes.get(1)[0] == 2 &&
				edgesLinkingStartAndFinishVertexes.get(1)[1] == 3 && edgesLinkingStartAndFinishVertexes.get(1)[2] == 5 &&
				edgesLinkingStartAndFinishVertexes.get(2)[0] == 3 && edgesLinkingStartAndFinishVertexes.get(2)[1] == 4 &&
				edgesLinkingStartAndFinishVertexes.get(2)[2] == 5 && edgesLinkingStartAndFinishVertexes.get(3)[0] == 4 &&
				edgesLinkingStartAndFinishVertexes.get(3)[1] == 5 && edgesLinkingStartAndFinishVertexes.get(3)[2] == 4);
	}

}