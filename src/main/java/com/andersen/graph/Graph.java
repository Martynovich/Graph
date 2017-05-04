package main.java.com.andersen.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import org.apache.log4j.Logger;

import main.java.com.andersen.exceptions.EmptyGraphException;
import main.java.com.andersen.exceptions.NegativeCycleException;

/**
 * @author Igor Create immutable oriented graph object in where we can find
 *         shortest distance and shortest path between any two vertic
 *
 */
public final class Graph {
	/**
	 * Hold all graph's vertices.
	 */
	private final ImmutableSet<Integer> vertices;

	/**
	 * Hold all graph's edges.
	 */
	private final ImmutableList<Edge> edges;

	private static final Logger logger = Logger.getLogger(Graph.class);

	/**
	 * @param builder
	 *            Create object Graph from Builder.
	 */
	public Graph(Builder builder) {
		vertices = ImmutableSet.copyOf(builder.vertices);
		edges = ImmutableList.copyOf(builder.edges);
		logger.info(this);
	}

	/**
	 * Return shortest distance from vertex to vertex
	 * 
	 * @param startVertex
	 *            Start vertex.
	 * @param finishVertex
	 *            Finish vertex.
	 * @return Shortest distance between start and finish vertex. Return 0 if we
	 *         finding distance from vertex to itself. Return 1000000 if path
	 *         between to vertices does not exist.
	 * @throws NegativeCycleException
	 *             Throws when graph has negative cycles.
	 * @throws EmptyGraphException
	 *             Throws when graph does not have any edges or vertices.
	 */
	public int getShortestLength(int startVertex, int finishVertex) throws NegativeCycleException, EmptyGraphException {
		logger.info("Start searching for shortest length between two vertex.");
		return new GraphHandler(vertices, edges).getShortestLength(startVertex, finishVertex);
	}

	/**
	 * Return edges list of shortest path.Edge represented as array of Integer
	 * objects where array[0] - edge's start vertex, array[1] - edge's finish
	 * vertex, array[2] - distance between start and finish vertices.
	 * 
	 * @param startVertex
	 *            Start vertex.
	 * @param finishVertex
	 *            Finish vertex.
	 * @return Shortest path between start and finish vertices. Return empty
	 *         List<Integer[]> if we finding path from vertex to itself. Return
	 *         null if path between to vertices does not exist.
	 * @throws NegativeCycleException
	 *             Throws when graph has negative cycles.
	 * @throws EmptyGraphException
	 *             Throws when graph does not have any edges or vertices.
	 */
	public ArrayList<Integer[]> getShortestPath(int startVertex, int finishVertex)
			throws NegativeCycleException, EmptyGraphException {
		logger.info(this);
		logger.info("Start searching for shortest path between two vertex.");
		return new GraphHandler(vertices, edges).getShortestPath(startVertex, finishVertex);
	}

	/**
	 * Return all edges. Edge represented as array of Integer objects where
	 * array[0] - edge's start vertex, array[1] - edge's finish vertex, array[2]
	 * - distance between start and finish vertices.
	 * 
	 * @return ArrayList of edges
	 */
	public ArrayList<Integer[]> getEdges() {
		ArrayList<Integer[]> allEdges = new ArrayList<>();
		for (Edge edge : edges) {
			allEdges.add(new Integer[] { edge.getStartVertex(), edge.getFinishVertex(), edge.getLength() });
		}
		return allEdges;
	}

	/**
	 * Return all vertices.
	 * 
	 * @return Set of vertices.
	 */
	public Set<Integer> getVerices() {
		return new TreeSet<Integer>(vertices);
	}

	@Override
	public int hashCode() {
		int result = 17;
		for (Edge edge : edges) {
			result += edge.getStartVertex() * 37 + 13;
			result += edge.getFinishVertex() * 57 + 33;
			result += edge.getLength() * 77 + 53;
		}
		Iterator<Integer> iterator = vertices.iterator();
		while (iterator.hasNext()) {
			result += iterator.next() * 97 + 73;
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Graph)) {
			return false;
		}
		Graph graph = (Graph) obj;
		ArrayList<Integer[]> graphEdges = graph.getEdges();
		for (int i = 0; i < edges.size(); i++) {
			for (Integer[] edgeArr : graphEdges) {
				int edgeQuantityInGraph = edgeQuantityInCurrentList(edgeArr, graphEdges);
				if (!isExistInCurrentQuantity(edgeArr, edgeQuantityInGraph)) {
					return false;
				}
			}
		}
		Set<Integer> graphVertexes = graph.getVerices();
		for (Integer vertex : graphVertexes) {
			if (!vertices.contains(vertex)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder graph = new StringBuilder();
		graph.append("\n" + "\n");
		graph.append("Graph: hashCode = " + this.hashCode());
		graph.append("\n");
		graph.append("Edges:" + edges.size() + " pcs");
		graph.append("\n");
		int iterator = 0;
		for (Edge edge : edges) {
			graph.append(edge);
			iterator++;
			if (iterator % 10 == 0) {
				graph.append("\n");
			}
		}
		graph.append("\n");
		graph.append("Vertexes:" + vertices.size() + " pcs");
		graph.append("\n");
		Iterator<Integer> it = vertices.iterator();
		while (it.hasNext()) {
			graph.append(it.next() + ", ");
		}
		graph.append("\n");
		return graph.toString();
	}

	/**
	 * Return edge quantity in current list of Edges. Method-helper for equals.
	 * 
	 * @param edge
	 *            The vertex that we are looking for.
	 * @param edgeList
	 *            List in which we are looking for our vertex.
	 * @return Edge quantity in current list of Edges
	 */
	private int edgeQuantityInCurrentList(Integer[] edge, ArrayList<Integer[]> edgeList) {
		int quantityOfEdge = 0;
		for (Integer[] arr : edgeList) {
			if (arr[0] == edge[0] && arr[1] == edge[1] && arr[2] == edge[2]) {
				quantityOfEdge++;
			}
		}
		return quantityOfEdge;
	}

	/**
	 * Answer edge is exist in graph's edges in current quantity.
	 * 
	 * @param edge
	 *            Finding edge
	 * @param currentQuantity
	 *            Current quantity
	 * @return Edge is exist in graph's edges in current quantity
	 */
	private boolean isExistInCurrentQuantity(Integer[] edge, int currentQuantity) {
		boolean isPresent = false;
		int howManyEdgeInEdgeList = 0;
		for (Edge e : edges) {
			if (e.getStartVertex() == edge[0] && e.getFinishVertex() == edge[1] && e.getLength() == edge[2]) {
				isPresent = true;
				howManyEdgeInEdgeList++;
			}
		}
		if (isPresent && howManyEdgeInEdgeList == currentQuantity) {
			return true;
		}
		return false;
	}

	/**
	 * @author Igor Builder class constructor. Need for create immutable graph
	 *         object without Graph class constructor
	 */
	public static class Builder {
		/**
		 * Hold graph's vertices.
		 */
		private Set<Integer> vertices = new TreeSet<>();
		/**
		 * Hold graph's edges.
		 */
		private ArrayList<Edge> edges = new ArrayList<>();

		/**
		 * Create graph's vertex and add it to vertices.
		 * 
		 * @param vertexNumber
		 * @return Builder object in which vertices have new vertex.
		 * @see vertices
		 */
		public Builder vertex(int vertexNumber) {

			addVertex(vertexNumber);
			return this;
		}

		/**
		 * Create edge and add it to list of edges.Create graph's vertices and
		 * add them to edges.
		 * 
		 * @param startVertex
		 * @param finishVertex
		 * @param length
		 * @return Builder object in which vertices have new vertices and new
		 *         edge.
		 */
		public Builder edge(int startVertex, int finishVertex, int length) {
			Edge edge = new Edge(startVertex, finishVertex, length);
			edges.add(edge);
			addVertex(startVertex);
			addVertex(finishVertex);
			return this;
		}

		/**
		 * Check vertex is present in vertices, and if does not present, add it
		 * to vertices.
		 * 
		 * @param vertexNumber
		 *            Number of adding vertex.
		 */
		private void addVertex(int vertexNumber) {
			if (!vertices.contains(vertexNumber)) {
				vertices.add(vertexNumber);
			}
		}

		/**
		 * Return Graph object after adding all edges and vertices.
		 * 
		 * @return New Graph object.
		 */
		public Graph build() {
			return new Graph(this);
		}
	}
}
