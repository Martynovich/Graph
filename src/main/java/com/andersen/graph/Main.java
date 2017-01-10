package main.java.com.andersen.graph;
import java.util.*;

public class Main {

	public static void main(String args[]){
		//Graph graph = new Graph.Builder().edge(1,2,1).edge(2,3,2).edge(3,4,3).edge(4,5,4).edge(3,2,-5).build();
		Graph graph = new Graph.Builder().edge(1,2,1).edge(2,3,2).edge(3,4,3).edge(4,5,4).edge(5,6,5).edge(2,1,-5).build();
		System.out.println(graph.getShortestLength(1, 4));
		for(Map.Entry<Integer, ArrayList<VertexMatrixElement>> e : graph.vertexMatrix.entrySet()){
			Integer i = e.getKey();
			ArrayList<VertexMatrixElement> arr = e.getValue();
			System.out.print(i + " ");
			for(int j = 0 ; j < e.getValue().size() ; j++){
				System.out.print(arr.get(j).getDistanñeFromStartVertex() + " " + 
						arr.get(j).getEdgeToPreviousNearestVertex() + " " + arr.get(j).getEdgesQuantity() + " ");
			}
			System.out.println("\n");
		}
		//System.exit(0);////////////////////
		ArrayList<Integer[]> arr = graph.getShortestPath(1, 4);
		System.out.println(arr.isEmpty());
		for(Integer[] i : arr){
			System.out.print(i[0] + " " + i[1] + " " + i[2] + "\n");
			
		}
		
	}
}
