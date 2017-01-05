package main.java.com.andersen.graph;
import org.apache.log4j.Logger;
import java.util.*;

public class Graph {
	Map<Integer, ArrayList<VertexMatrixElement>> vertexMatrix;
	final ArrayList<Edge> edges;
	final static Logger logger = Logger.getLogger(Graph.class);
	
	//Create object Graph from Builder.
	public Graph(Builder builder){
		vertexMatrix = builder.vertexMatrix;
		edges = builder.edges;
	}
	//Return shortest distance from vertex to vertex
	public int getShortestLength(int startVertex, int finishVertex){
		initVertexMatrix(startVertex);
		findAllShortestDistInVertMatr(startVertex);
		int shortestLength = findShortestWayFromSartToCurrent(finishVertex).getDistanñeFromStartVertex();
		return shortestLength;
	}	
	//Return edges list of shortest path
	public ArrayList<Integer[]> getShortestPath(int startVertex, int finishVertex){
		initVertexMatrix(startVertex);
		findAllShortestDistInVertMatr(startVertex);
		ArrayList<Edge> edgesFromStartToFinish = findShortestWayFromSartToFinish(startVertex, finishVertex);
		ArrayList<Integer[]> edgesFromStartToFinishArray = new ArrayList<Integer[]>();
		Collections.reverse(edgesFromStartToFinish);
		for(int i = 0; i < edgesFromStartToFinish.size(); i++){
			Integer[] edgeCopy = new Integer[3];
			edgeCopy[0] = edgesFromStartToFinish.get(i).getStartVertex();
			edgeCopy[1] = edgesFromStartToFinish.get(i).getFinishVertex();
			edgeCopy[2] = edgesFromStartToFinish.get(i).getLength();
			edgesFromStartToFinishArray.add(edgeCopy);
		}
		
		
		return edgesFromStartToFinishArray;
	}
	//Initialize matrix VertexMatrix 
	private void initVertexMatrix(int startVertex){
		logger.info("Init matrix, start vertex:" + startVertex);
		for(Map.Entry<Integer, ArrayList<VertexMatrixElement>> entry : vertexMatrix.entrySet()){
			ArrayList<VertexMatrixElement> matrixRaw = entry.getValue();
			matrixRaw.clear();
			for(int i = 0 ; i < vertexMatrix.size();i++){
				VertexMatrixElement vertexMatrixElement = new VertexMatrixElement(i);
				//logger.warn("Edges quantiti to start vertex = " + vertexMatrixElement.getEdgesQuantity());
				matrixRaw.add(i, vertexMatrixElement);
			}
		}
		VertexMatrixElement startVertexMatrixElement = vertexMatrix.get(startVertex).get(0);
		startVertexMatrixElement.setDistanñeFromStartVertex(0);
		startVertexMatrixElement.setPreviousNearestVertex(startVertex);
	}
	//Fill matrix with shortest distances and edges between vertexes.
	private void findAllShortestDistInVertMatr(int startVertex){
		logger.info("Filling matrix with shorted distances to start vertex and linking edges.Start vertex - " + startVertex);
		for(int i = 1 ; i < vertexMatrix.size() ; i++){
			for(Edge edge : edges){
				VertexMatrixElement currentElement = vertexMatrix.get(edge.getFinishVertex()).get(i);
				VertexMatrixElement previousElement = vertexMatrix.get(edge.getStartVertex()).get(i-1);
				VertexMatrixElement currentRawPreviousElement = vertexMatrix.get(edge.getFinishVertex()).get(i-1);
				
				if(currentElement.getDistanñeFromStartVertex() > 
						previousElement.getDistanñeFromStartVertex() + edge.getLength()&&
						previousElement.getDistanñeFromStartVertex()!=1000*1000){
					currentElement.setDistanñeFromStartVertex(previousElement.getDistanñeFromStartVertex() + edge.getLength());
					currentElement.setPreviousNearestVertex(edge.getStartVertex());	
					currentElement.setEdgeToPreviousNearestVertex(edge);
					currentElement.setEdgesQuantity(previousElement.getEdgesQuantity() + 1);
				}
				if(currentElement.getDistanñeFromStartVertex() >= currentRawPreviousElement.getDistanñeFromStartVertex()){
					currentElement.setDistanñeFromStartVertex(currentRawPreviousElement.getDistanñeFromStartVertex());
					currentElement.setPreviousNearestVertex(currentRawPreviousElement.getPreviousNearestVertex());
					currentElement.setEdgeToPreviousNearestVertex(currentRawPreviousElement.getEdgeToPreviousNearestVertex());
					currentElement.setEdgesQuantity(currentRawPreviousElement.getEdgesQuantity());
				}
			}
		}	
	}
	//Return ArrayList of Edges from finish to start, return empty ArrayList if there is no link between vertexes.
	private ArrayList<Edge> findShortestWayFromSartToFinish(int startVertex, int finishVertex){
		logger.info("Searchin for shortest path from " + startVertex + " vertex to " + finishVertex + " vertex");
		int currentVertex = finishVertex;
		ArrayList<Edge> edgeWayFromFinishToStart = new ArrayList<Edge>();
		VertexMatrixElement currentVertexMatrixElement = findShortestWayFromSartToCurrent(finishVertex);
		logger.warn("Edges quantiti to start vertex = " + currentVertexMatrixElement.getEdgesQuantity());
		if(!currentVertexMatrixElement.getEdgeToPreviousNearestVertex().isEmpty()){
		edgeWayFromFinishToStart.add(currentVertexMatrixElement.getEdgeToPreviousNearestVertex());
		}else{
			edgeWayFromFinishToStart.clear();
			logger.info("No links between start and finish.");
			return edgeWayFromFinishToStart;
		}
		do{
			currentVertexMatrixElement = findShortestWayFromSartToCurrent(currentVertexMatrixElement.getEdgeToPreviousNearestVertex().getStartVertex());
			logger.warn("Edges quantiti to start vertex = " + currentVertexMatrixElement.getEdgesQuantity());
			edgeWayFromFinishToStart.add(currentVertexMatrixElement.getEdgeToPreviousNearestVertex());	
			if(currentVertexMatrixElement.getEdgeToPreviousNearestVertex().isEmpty()){				
				edgeWayFromFinishToStart.clear();
				logger.info("No links between start and finish.");
				return edgeWayFromFinishToStart;
			}			
		}while(currentVertexMatrixElement.getEdgeToPreviousNearestVertex().getStartVertex() != startVertex);
		return edgeWayFromFinishToStart;
	}
	//Find shortest distances from vertex to start vertex and appropriate edge of current vertex.
	private VertexMatrixElement findShortestWayFromSartToCurrent(int vertexNumber){
		logger.info("Searchin for shortest distances between start vertex and " + vertexNumber + " vertex. And correct edge for " + vertexNumber + " vertex");
		ArrayList<VertexMatrixElement> vertexMatrixRaw = vertexMatrix.get(vertexNumber);
		VertexMatrixElement nearestToStartVertexMatrixElement = vertexMatrixRaw.get(0);
		Integer minLength = nearestToStartVertexMatrixElement.getDistanñeFromStartVertex();
		int edgesEquantity = vertexMatrix.size();
		for(int i = 1 ; i < vertexMatrixRaw.size(); i++){
			VertexMatrixElement currentVertex = vertexMatrixRaw.get(i);
			if(currentVertex.getDistanñeFromStartVertex() < minLength){
				nearestToStartVertexMatrixElement = currentVertex;
			}
		}
		return nearestToStartVertexMatrixElement;
	}

	//Builder class constructor.
	public static class Builder {
		Map<Integer, ArrayList<VertexMatrixElement>> vertexMatrix = 
				new TreeMap<Integer, ArrayList<VertexMatrixElement>>();
		ArrayList<Edge> edges = new ArrayList<Edge>();
		
		//Create graph's vertex and add it to Map vertexMatrix as a key.
		public Builder vertex(int vertexNumber){
			addVertexToVertexMatrix(vertexNumber);
			return this;
		}
		
		//Create edge and add it to list of edges.Create graph's vertex and add them to Map vertexMatrix as a key.
		public Builder edge(int startVertex, int finishVertex, int length){
			if(startVertex == 0 && finishVertex == 0 && length == 0){
				logger.info("Ignor for empty edge.");
				return this;
			}
			if(length == 0){
				logger.info("Ignor for edge without length.");
				return this;
			}
			Edge edge = new Edge(startVertex, finishVertex, length);
			edges.add(edge);
			addVertexToVertexMatrix(startVertex);
			addVertexToVertexMatrix(finishVertex);
			return this; 
		}
		
		private void addVertexToVertexMatrix(int vertexNumber){
			if(!vertexMatrix.containsKey(vertexNumber)){
				vertexMatrix.put(vertexNumber, new ArrayList<VertexMatrixElement>());
			}
		}
		
		public Graph build(){
			return new Graph(this);
		}
		
		
		
	}
}
