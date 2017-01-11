package main.java.com.andersen.graph;

import java.util.ArrayList;

public class MatrixElement {
	 private int distan�eFromStartVertex = 1000*1000;
	 //private Edge edgeToPreviousNearestVertex = new Edge(0, 0, 0);
	 private int previousNearestVertex = 1000*1000;
	 private int edgesQuantity;
	 private ArrayList<Edge> pathToStart = new ArrayList<Edge>();
	 
	 MatrixElement(int edgesQuantity){
		 this.edgesQuantity = edgesQuantity;
	 }
	 
	int getDistan�eFromStartVertex() {
		return distan�eFromStartVertex;
	}
	void setDistan�eFromStartVertex(int distan�eFromStartVertex) {
		this.distan�eFromStartVertex = distan�eFromStartVertex;
	}
	
	int getPreviousNearestVertex() {
		return previousNearestVertex;
	}
	void setPreviousNearestVertex(int previousNearestVertex) {
		previousNearestVertex = previousNearestVertex;
	}
	int getEdgesQuantity() {
		return edgesQuantity;
	}
	void setEdgesQuantity(int edgesQuantity) {
		this.edgesQuantity = edgesQuantity;
	}
//	Edge getEdgeToPreviousNearestVertex() {
//		return edgeToPreviousNearestVertex;
//	}

//	void setEdgeToPreviousNearestVertex(Edge edgeToPreviousNearestVertex) {
//		this.edgeToPreviousNearestVertex = edgeToPreviousNearestVertex;
//	}

	public ArrayList<Edge> getPathToStart() {
		return pathToStart;
	}

	public void setPathToStart(ArrayList<Edge> pathToStart) {
		this.pathToStart = pathToStart;
	}
}
