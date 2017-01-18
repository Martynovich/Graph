package main.java.com.andersen.graph;

import java.util.ArrayList;

/**
 * @author Igor Element of Bellman–Ford algorithm matrix.
 *
 */
public class MatrixElement {
	/**
	 * Distance from start vertex to vertex which corresponds with current
	 * matrixElement. By default = 1000000 which means infinity.
	 */
	private int distanñeFromStartVertex = 1000 * 1000;
	/**
	 * Show how many edges between start vertex to vertex which corresponds with
	 * current matrixElement.
	 */
	private int edgesQuantity;
	/**
	 * Hold shortest path from start vertex to vertex which corresponds with
	 * current matrixElement.
	 */
	private ArrayList<Edge> pathToStart = new ArrayList<>();

	/**
	 * Constructor with fill edgesQuantity field.
	 * 
	 * @param edgesQuantity
	 */
	MatrixElement(int edgesQuantity) {
		this.edgesQuantity = edgesQuantity;
	}

	/**
	 * @return Distance from start vertex to vertex which corresponds with
	 *         current matrixElement.
	 */
	int getDistanñeFromStartVertex() {
		return distanñeFromStartVertex;
	}

	/**
	 * @param Distance
	 *            from start vertex to vertex which corresponds with current
	 *            matrixElement.
	 */
	void setDistanñeFromStartVertex(int distanñeFromStartVertex) {
		this.distanñeFromStartVertex = distanñeFromStartVertex;
	}

	/**
	 * @return edgesQuantity field.
	 */
	int getEdgesQuantity() {
		return edgesQuantity;
	}

	/**
	 * @param edgesQuantity
	 *            Set edgesQuantity field.
	 */
	void setEdgesQuantity(int edgesQuantity) {
		this.edgesQuantity = edgesQuantity;
	}

	/**
	 * @return pathToStart field.
	 */
	public ArrayList<Edge> getPathToStart() {
		return pathToStart;
	}

	/**
	 * @param pathToStart
	 *            Set pathToStart field.
	 */
	public void setPathToStart(ArrayList<Edge> pathToStart) {
		this.pathToStart = pathToStart;
	}
}
