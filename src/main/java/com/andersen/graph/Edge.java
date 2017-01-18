package main.java.com.andersen.graph;

/**
 * @author Igor Graph's edge.
 */
class Edge {

	/**
	 * Start edge's vertex.
	 */
	private int startVertex;
	/**
	 * Finish edge's vertex.
	 */
	private int finishVertex;
	/**
	 * Edge's length.
	 */
	private int length;

	/**
	 * @param startVertex
	 *            Start edge's vertex.
	 * @param finishVertex
	 *            Finish edge's vertex.
	 * @param length
	 *            Edge's length.
	 */
	public Edge(int startVertex, int finishVertex, int length) {
		this.startVertex = startVertex;
		this.finishVertex = finishVertex;
		this.length = length;
	}

	/**
	 * @return Start edge's vertex.
	 */
	int getStartVertex() {
		return startVertex;
	}

	/**
	 * @return Finish edge's vertex.
	 */
	int getFinishVertex() {
		return finishVertex;
	}

	/**
	 * @return Edge's length.
	 */
	int getLength() {
		return length;
	}

	/**
	 * Check for all fields = 0.
	 * 
	 * @return Return true if edge is empty.
	 */
	boolean isEmpty() {
		if (startVertex == 0 && finishVertex == 0 && length == 0) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "(" + startVertex + " " + finishVertex + " " + length + ")";
	}
}
