package main.java.com.andersen.graph;

import java.util.ArrayList;

public class MatrixElement {
	 private int distanñeFromStartVertex = 1000*1000;
	 private int edgesQuantity;
	 private ArrayList<Edge> pathToStart = new ArrayList<>();
	 
	 MatrixElement(int edgesQuantity){
		 this.edgesQuantity = edgesQuantity;
	 }
	 
	int getDistanñeFromStartVertex() {
		return distanñeFromStartVertex;
	}
	void setDistanñeFromStartVertex(int distanñeFromStartVertex) {
		this.distanñeFromStartVertex = distanñeFromStartVertex;
	}
	
	int getEdgesQuantity() {
		return edgesQuantity;
	}
	void setEdgesQuantity(int edgesQuantity) {
		this.edgesQuantity = edgesQuantity;
	}

	public ArrayList<Edge> getPathToStart() {
		return pathToStart;
	}

	public void setPathToStart(ArrayList<Edge> pathToStart) {
		this.pathToStart = pathToStart;
	}
}
