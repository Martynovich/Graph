package main.java.com.andersen.graph;

public class VertexMatrixElement {
	 private int distan�eFromStartVertex = 1000*1000;
	 private Edge edgeToPreviousNearestVertex = new Edge(0, 0, 0);
	 private int PreviousNearestVertex = 1000*1000;
	 private int edgesQuantity;
	 
	 VertexMatrixElement(int edgesQuantity){
		 this.edgesQuantity = edgesQuantity;
	 }
	 
	int getDistan�eFromStartVertex() {
		return distan�eFromStartVertex;
	}
	void setDistan�eFromStartVertex(int distan�eFromStartVertex) {
		this.distan�eFromStartVertex = distan�eFromStartVertex;
	}
	
	int getPreviousNearestVertex() {
		return PreviousNearestVertex;
	}
	void setPreviousNearestVertex(int previousNearestVertex) {
		PreviousNearestVertex = previousNearestVertex;
	}
	int getEdgesQuantity() {
		return edgesQuantity;
	}
	void setEdgesQuantity(int edgesQuantity) {
		this.edgesQuantity = edgesQuantity;
	}
	Edge getEdgeToPreviousNearestVertex() {
		return edgeToPreviousNearestVertex;
	}

	void setEdgeToPreviousNearestVertex(Edge edgeToPreviousNearestVertex) {
		this.edgeToPreviousNearestVertex = edgeToPreviousNearestVertex;
	}

	
	
	

}
