package main.java.com.andersen.graph;
import java.util.*;

public class Main {

	public static void main(String args[]){
		//Graph graph = new Graph.Builder().edge(1,2,10).edge(2,3,5).edge(3,4,5).edge(4,5,4).build();
		Graph graph = new Graph.Builder().edge(1,2,2).edge(2,3,3).edge(3,5,4).edge(1,4,3).edge(4,5,6).build();
		System.out.println(graph.getShortestLength(1, 5));
		//System.out.println(graph.vertexMatrix.size());
		for(Map.Entry entry : graph.vertexMatrix.entrySet()){
			System.out.print(entry.getKey() + " ");
		}
		System.out.println();
		for(VertexMatrixElement e : graph.vertexMatrix.get(1)){
			System.out.print(e.getDistanñeFromStartVertex() + " " + e.getEdgeToPreviousNearestVertex().toString());
		}		
		System.out.println();
		for(VertexMatrixElement e : graph.vertexMatrix.get(2)){
			System.out.print(e.getDistanñeFromStartVertex() + " "  + e.getEdgeToPreviousNearestVertex().toString());
		}		
		System.out.println();
		for(VertexMatrixElement e : graph.vertexMatrix.get(3)){
			System.out.print(e.getDistanñeFromStartVertex() + " "  + e.getEdgeToPreviousNearestVertex().toString());
		}		
		System.out.println();
		for(VertexMatrixElement e : graph.vertexMatrix.get(4)){
			System.out.print(e.getDistanñeFromStartVertex() + " "  + e.getEdgeToPreviousNearestVertex().toString());
		}		
		System.out.println();
		for(VertexMatrixElement e : graph.vertexMatrix.get(5)){
			System.out.print(e.getDistanñeFromStartVertex() + " "  + e.getEdgeToPreviousNearestVertex().toString());
		}		
		System.out.println();
		
		System.out.println(graph.edges.size());
		for(Edge e:graph.edges){
			System.out.println(e);
		}

		ArrayList<Integer[]> arr = graph.getShortestPath(1, 5);
		System.out.println(arr.isEmpty());
		for(Integer[] i : arr){
			System.out.print(i[0] + " " + i[1] + " " + i[2] + "\n");
			
		}
		
	}
}
