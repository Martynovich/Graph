package main.java.com.andersen.graph;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import main.java.com.andersen.exceptions.EmptyGraphException;
import main.java.com.andersen.exceptions.NegativeCycleException;

public class Graph {
	private final Map<Integer, ArrayList<MatrixElement>> matrix;
	private final ArrayList<Edge> edges;
	private final static Logger logger = Logger.getLogger(Graph.class);

	// Create object Graph from Builder.
	public Graph(Builder builder) {
		matrix = builder.matrix;
		edges = builder.edges;
	}

	// Return shortest distance from vertex to vertex
	public int getShortestLength(int startVertex, int finishVertex) throws NegativeCycleException, EmptyGraphException {
		logger.info(this);
		logger.info("Start searching for shortest length between two vertex.");
		return new GraphHandler(matrix, edges).getShortestLength(startVertex, finishVertex);
	}

	// Return edges list of shortest path
	public ArrayList<Integer[]> getShortestPath(int startVertex, int finishVertex)
			throws NegativeCycleException, EmptyGraphException {
		logger.info(this);
		logger.info("Start searching for shortest path between two vertex.");
		return new GraphHandler(matrix, edges).getShortestPath(startVertex, finishVertex);
	}

	// Return ArrayList of edges.
	public ArrayList<Integer[]> getEdges() {
		ArrayList<Integer[]> edges = new ArrayList<Integer[]>();
		for (Edge edge : this.edges) {
			edges.add(new Integer[] { edge.getStartVertex(), edge.getFinishVertex(), edge.getLength() });
		}
		return edges;
	}

	// Return Set of vertexes.
	public Set<Integer> getVertexes() {
		Set<Integer> vertexes = new TreeSet<Integer>();
		for (Map.Entry<Integer, ArrayList<MatrixElement>> entry : matrix.entrySet()) {
			vertexes.add(new Integer(entry.getKey()));
		}

		return vertexes;
	}

	@Override
	public int hashCode() {
		int result = 17;
		for (Edge edge : edges) {
			result += edge.getStartVertex() * 37 + 13;
			result += edge.getFinishVertex() * 57 + 33;
			result += edge.getLength() * 77 + 53;
		}
		for (Map.Entry<Integer, ArrayList<MatrixElement>> entry : matrix.entrySet()) {
			result += entry.getKey() * 97 + 73;
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
		for (Edge edge : edges) {
			for (Integer[] edgeArr : graphEdges) {
				int edgeQuantityInGraph = edgeQuantityInCurrentList(edgeArr, graphEdges);
				if (!isExistInCurrentQuantity(edgeArr, edgeQuantityInGraph)) {
					return false;
				}
			}
		}
		Set<Integer> graphVertexes = graph.getVertexes();
		for (Integer vertex : graphVertexes) {
			if (!matrix.containsKey(vertex)) {
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
		graph.append("Vertexes:" + matrix.size() + " pcs");
		graph.append("\n");
		for (Map.Entry<Integer, ArrayList<MatrixElement>> entry : matrix.entrySet()) {
			graph.append(entry.getKey() + ", ");
		}
		graph.append("\n");
		return graph.toString();
	}

	// Return edge quantity in current list of Edges
	private int edgeQuantityInCurrentList(Integer[] edge, ArrayList<Integer[]> edgeList) {
		int quantityOfEdge = 0;
		for (Integer[] arr : edgeList) {
			if (arr[0] == edge[0] && arr[1] == edge[1] && arr[2] == edge[2]) {
				quantityOfEdge++;
			}
		}
		return quantityOfEdge;
	}

	// Answer edge is exist in edge array in current quantity.
	private boolean isExistInCurrentQuantity(Integer[] edge, int i) {
		boolean isPresent = false;
		int howManyEdgeInEdgeList = 0;
		for (Edge e : edges) {
			if (e.getStartVertex() == edge[0] && e.getFinishVertex() == edge[1] && e.getLength() == edge[2]) {
				isPresent = true;
				howManyEdgeInEdgeList++;
			}
		}
		if (isPresent && howManyEdgeInEdgeList == i) {
			return true;
		}
		return false;
	}

	// Builder class constructor.
	public static class Builder {
		Map<Integer, ArrayList<MatrixElement>> matrix = new TreeMap<Integer, ArrayList<MatrixElement>>();
		ArrayList<Edge> edges = new ArrayList<Edge>();

		// Create graph's vertex and add it to Map vertexMatrix as a key.
		public Builder vertex(int vertexNumber) {
			addVertex(vertexNumber);
			return this;
		}

		// Create edge and add it to list of edges.Create graph's vertex and add
		// them to Map vertexMatrix as a key.
		public Builder edge(int startVertex, int finishVertex, int length) {
			Edge edge = new Edge(startVertex, finishVertex, length);
			edges.add(edge);
			addVertex(startVertex);
			addVertex(finishVertex);
			return this;
		}

		// Add vertex to graph.
		private void addVertex(int vertexNumber) {
			if (!matrix.containsKey(vertexNumber)) {
				matrix.put(vertexNumber, new ArrayList<MatrixElement>());
			}
		}

		public Graph build() {
			return new Graph(this);
		}
	}
}
