package main.java.com.andersen.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import main.java.com.andersen.exceptions.EmptyGraphException;
import main.java.com.andersen.exceptions.NegativeCycleException;

/**
 * @author Igor Class for finding shortest length and shortest path between two
 *         vertices in immutable Graph object.
 */
public class GraphHandler {
	/**
	 * Hold matrix for Bellman–Ford algorithm. Keys is vertices. Value is List
	 * with shortest distances and paths between start vertex and current vertex
	 * with max number of edges <= List element's number.
	 */
	private Map<Integer, ArrayList<MatrixElement>> matrix = new TreeMap<>();
	/**
	 * Hold graph's edges.
	 */
	private final ArrayList<Edge> edges = new ArrayList<>();
	private final static Logger logger = Logger.getLogger(Graph.class);

	/**
	 * Constructor for filling matrix and edges.
	 * 
	 * @param vertices
	 *            Graph's vertices.
	 * @param edges
	 *            Graph's edges.
	 */
	GraphHandler(ImmutableSet<Integer> vertices, ImmutableList<Edge> edges) {
		setMatrix(vertices);
		setEdges(edges);
	}

	/**
	 * Return shortest distance from vertex to vertex
	 * 
	 * @param startVertex
	 *            Start vertex.
	 * @param finishVertex
	 *            Finish vertex.
	 * @return Shortest distance between start and finish vertices.
	 * @throws NegativeCycleException
	 *             Throws when graph has negative cycles.
	 * @throws EmptyGraphException
	 *             Throws when graph does not have any edges or vertices.
	 */
	public int getShortestLength(int startVertex, int finishVertex) throws NegativeCycleException, EmptyGraphException {
		if (isEmpty()) {
			throw new EmptyGraphException("Graph is Empty!");
		}
		if (!vertexesArePresent(startVertex, finishVertex)) {
			throw new IllegalArgumentException("Some vertex are not present in graph.");
		}
		initMatrix(startVertex);
		findAllShortestDistInMatr(startVertex);
		int shortestLength = findShortestWayFromSartToCurrent(finishVertex).getDistanñeFromStartVertex();
		if (isNegativeCycle()) {
			throw new NegativeCycleException("Is negativ cicle in graph", shortestLength);
		}
		return shortestLength;
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
		if (isEmpty()) {
			throw new EmptyGraphException("Graph is Empty!");
		}
		if (!vertexesArePresent(startVertex, finishVertex)) {
			throw new IllegalArgumentException("Some vertex are not present in graph.");
		}
		initMatrix(startVertex);
		findAllShortestDistInMatr(startVertex);
		ArrayList<Edge> shortestPath = findShortestWayFromSartToCurrent(finishVertex).getPathToStart();
		ArrayList<Integer[]> shortestPathIntArr = new ArrayList<>();
		for (int i = 0; i < shortestPath.size(); i++) {
			Integer startVertexInt = shortestPath.get(i).getStartVertex();
			Integer finishVertexInt = shortestPath.get(i).getFinishVertex();
			Integer length = shortestPath.get(i).getLength();
			Integer edge[] = { startVertexInt, finishVertexInt, length };
			shortestPathIntArr.add(edge);
		}
		if (isNegativeCycle()) {
			throw new NegativeCycleException("Is negativ cicle in graph", shortestPathIntArr);
		}
		return shortestPathIntArr;
	}

	/**
	 * Initialize matrix for Bellman–Ford algorithm.
	 * 
	 * @param startVertex
	 *            Start vertex for finding shortest distances and paths between
	 *            it and all other vertices.
	 */
	private void initMatrix(int startVertex) {
		logger.info("Init matrix, start vertex:" + startVertex);
		for (Map.Entry<Integer, ArrayList<MatrixElement>> entry : matrix.entrySet()) {
			ArrayList<MatrixElement> matrixRaw = entry.getValue();
			matrixRaw.clear();
			for (int i = 0; i <= matrix.size(); i++) {
				MatrixElement matrixElement = new MatrixElement(i);
				matrixRaw.add(i, matrixElement);
			}
		}
		MatrixElement startMatrixElement = matrix.get(startVertex).get(0);
		startMatrixElement.setDistanñeFromStartVertex(0);
	}

	/**
	 * Fill matrix with shortest distances and shortest paths between start and
	 * other vertices.
	 * 
	 * @param startVertex
	 *            Start vertex.
	 */
	private void findAllShortestDistInMatr(int startVertex) {
		logger.info("Filling matrix with shortest distances to start vertex and linking edges.Start vertex - "
				+ startVertex);
		for (int i = 1; i <= matrix.size(); i++) {
			for (Edge edge : edges) {
				MatrixElement currentElement = matrix.get(edge.getFinishVertex()).get(i);
				MatrixElement previousElement = matrix.get(edge.getStartVertex()).get(i - 1);
				MatrixElement currentRawPreviousElement = matrix.get(edge.getFinishVertex()).get(i - 1);
				ArrayList<Edge> newPathToStart;
				if (currentElement.getDistanñeFromStartVertex() > previousElement.getDistanñeFromStartVertex()
						+ edge.getLength() && previousElement.getDistanñeFromStartVertex() != 1000 * 1000) {
					currentElement.setDistanñeFromStartVertex(
							previousElement.getDistanñeFromStartVertex() + edge.getLength());
					currentElement.setEdgesQuantity(previousElement.getEdgesQuantity() + 1);
					newPathToStart = new ArrayList<>(previousElement.getPathToStart());
					newPathToStart.add(edge);
					currentElement.setPathToStart(newPathToStart);
				}
				if (currentElement.getDistanñeFromStartVertex() >= currentRawPreviousElement
						.getDistanñeFromStartVertex()
						&& currentRawPreviousElement.getDistanñeFromStartVertex() != 1000 * 1000) {
					currentElement.setDistanñeFromStartVertex(currentRawPreviousElement.getDistanñeFromStartVertex());
					newPathToStart = new ArrayList<>(currentRawPreviousElement.getPathToStart());
					currentElement.setPathToStart(newPathToStart);
				}
			}
			if (matrix.get(startVertex).get(i).getDistanñeFromStartVertex() >= matrix.get(startVertex).get(i - 1)
					.getDistanñeFromStartVertex()) {
				matrix.get(startVertex).get(i)
						.setDistanñeFromStartVertex(matrix.get(startVertex).get(i - 1).getDistanñeFromStartVertex());
			}
		}
	}

	/**
	 * Find shortest distances shortest path from finish vertex to start vertex.
	 * 
	 * @param finishVertex
	 *            Vertex for which we find shortest distance and path to start
	 *            vertex.
	 * @return MartixElement object which contains shortest length and path
	 *         between start and finish vertices.
	 */
	private MatrixElement findShortestWayFromSartToCurrent(int finishVertex) {
		logger.info("Searchin for shortest distances between start vertex and " + finishVertex
				+ " vertex. And correct edge for " + finishVertex + " vertex");
		ArrayList<MatrixElement> vertexMatrixRaw = matrix.get(finishVertex);
		MatrixElement nearestToStartVertexMatrixElement = vertexMatrixRaw.get(0);
		Integer minLength = nearestToStartVertexMatrixElement.getDistanñeFromStartVertex();
		for (int i = 1; i < vertexMatrixRaw.size(); i++) {
			MatrixElement currentVertex = vertexMatrixRaw.get(i);
			if (currentVertex.getDistanñeFromStartVertex() < minLength) {
				nearestToStartVertexMatrixElement = currentVertex;
			}
		}
		return nearestToStartVertexMatrixElement;
	}

	/**
	 * Check does graph is empty.
	 * 
	 * @return Return true if graph is empty.
	 */
	private boolean isEmpty() {
		if (matrix.isEmpty() && edges.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * Check presence of negative cycle.
	 * 
	 * @return Return true if graph has negative cycles.
	 */
	private boolean isNegativeCycle() {
		for (Map.Entry<Integer, ArrayList<MatrixElement>> entry : matrix.entrySet()) {
			int lastElement = entry.getValue().get(matrix.size()).getDistanñeFromStartVertex();
			int penultElement = entry.getValue().get(matrix.size() - 1).getDistanñeFromStartVertex();
			if (lastElement < penultElement) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check edge in graph.
	 * 
	 * @param startVertex
	 *            Start edge's vertex.
	 * @param finishVertex
	 *            Finish edge's vertex.
	 * @return return true if edge is present in graph.
	 */
	private boolean vertexesArePresent(int startVertex, int finishVertex) {
		if (matrix.containsKey(startVertex) && matrix.containsKey(finishVertex)) {
			return true;
		}
		return false;
	}

	/**
	 * Fill matrix with vertices.
	 * 
	 * @param vertexes
	 *            Set of graph's vertices.
	 */
	private void setMatrix(Set<Integer> vertexes) {
		Iterator<Integer> iterator = vertexes.iterator();
		while (iterator.hasNext()) {
			this.matrix.put(new Integer(iterator.next()), new ArrayList<MatrixElement>());
		}
	}

	/**
	 * Create copy of edges array. Need for keeping graph's object immutable.
	 * 
	 * @param edges
	 *            Graph's edges.
	 */
	private void setEdges(List<Edge> edges) {
		for (Edge edge : edges) {
			Edge copyEdge = new Edge(edge.getStartVertex(), edge.getFinishVertex(), edge.getLength());
			this.edges.add(copyEdge);
		}
	}
}
