package main.java.com.andersen.graph;
import java.util.*;

import main.java.com.andersen.exceptions.EmptyGraphException;
import main.java.com.andersen.exceptions.NegativeCycleException;

public class Main {

	public static void main(String args[]) throws NegativeCycleException, EmptyGraphException{
		/*Graph graph = new Graph.Builder().edge(1,2,2).edge(2,3,3).edge(3,6,4)
				.edge(1,4,2).edge(4,6,8).edge(4,5,3).edge(5,6,4).build();
		System.out.println(graph.getShortestLength(1, 6));
		for(Map.Entry<Integer, ArrayList<MatrixElement>> e : graph.matrix.entrySet()){
			Integer i = e.getKey();
			ArrayList<MatrixElement> arr = e.getValue();
			System.out.print(i + " ");
			for(int j = 0 ; j < e.getValue().size() ; j++){
				ArrayList<Edge> list = arr.get(j).getPathToStart();
				String string;
				try{
					string = list.get(list.size()-1).toString();
				}catch(Exception w){
					string = "(0,0,0)";
				}
				System.out.print(arr.get(j).getDistanñeFromStartVertex() + " " + string + " " + list.size() + " ");
			}
			System.out.println("\n");
		}

		ArrayList<Integer[]> list;
		try {
			list = graph.getShortestPath(1, 6);
			for(Integer[] edge : list){
				System.out.print(edge[0] + ",");
				System.out.print(edge[1] + ",");
				System.out.println(edge[2]);
			}
		} catch (NegativeCycleException e1) {
			System.out.println(e1.getMessage());
		}*/
		Graph graph = new Graph.Builder().edge(1,2,2).edge(1,2,3).edge(2,3,5).edge(2,3,6).edge(3,4,1).build();
		ArrayList<Integer[]> arr = graph.getShortestPath(1, 4);
		int a = graph.getShortestLength(1, 4);
		System.out.println(a);
		System.out.println(arr.isEmpty());
		for(Integer[] i : arr) {
			System.out.println(i[0] + " " + i[1] + " " + i[2]);
		}
	}
}
