package main.java.com.andersen.graph;

class Edge {
	
	private int startVertex;
	private int finishVertex;
	private int length;
	
	public Edge(int startVertex, int finishVertex, int length){
		this.startVertex = startVertex;
		this.finishVertex = finishVertex;
		this.length = length;
	}
	
	int getStartVertex() {
		return startVertex;
	}

	int getFinishVertex() {
		return finishVertex;
	}

	int getLength() {
		return length;
	}
	
	boolean isEmpty(){
		if(startVertex == 0 && finishVertex  == 0 && length == 0 ){
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		String s = "(" + startVertex + " " + finishVertex + " " + length + ")";
		return s;
	}
	

}
