package main.java.com.andersen.graph;
import org.apache.log4j.Logger;

import main.java.com.andersen.exceptions.NegativeCicleException;

import java.util.*;

public class Graph {
	private final  Map<Integer, ArrayList<MatrixElement>> matrix;
	private final ArrayList<Edge> edges;
	private final static Logger logger = Logger.getLogger(Graph.class);
	
	//Create object Graph from Builder.
	public Graph(Builder builder){
		matrix = builder.matrix;
		edges = builder.edges;
	}
	
	//Return shortest distance from vertex to vertex
	public int getShortestLength(int startVertex, int finishVertex){
		initMatrix(startVertex);
		findAllShortestDistInMatr(startVertex);
		int shortestLength = findShortestWayFromSartToCurrent(finishVertex).getDistanñeFromStartVertex();
		return shortestLength;
	}	
	
	//Return edges list of shortest path
	public ArrayList<Integer[]> getShortestPath(int startVertex, int finishVertex) throws NegativeCicleException{
		initMatrix(startVertex);
		findAllShortestDistInMatr(startVertex);
		ArrayList<Edge> shortestPath = findShortestWayFromSartToCurrent(finishVertex).getPathToStart();
		ArrayList<Integer[]> shortestPathIntArr = new ArrayList<Integer[]>();
		for(int i = 0 ; i < shortestPath.size() ; i++){
			Integer startVertexInt = shortestPath.get(i).getStartVertex();
			Integer finishVertexInt = shortestPath.get(i).getFinishVertex();
			Integer length = shortestPath.get(i).getLength();
			Integer edge[] = {startVertexInt, finishVertexInt, length};
			shortestPathIntArr.add(edge);
		}
		if(isNegativeCicle()){
			throw new NegativeCicleException("Is negativ cicle in graph", shortestPathIntArr);
		}
		return shortestPathIntArr;
	}

	//Initialize matrix VertexMatrix 
	private void initMatrix(int startVertex){
		logger.info("Init matrix, start vertex:" + startVertex);
		for(Map.Entry<Integer, ArrayList<MatrixElement>> entry : matrix.entrySet()){
			ArrayList<MatrixElement> matrixRaw = entry.getValue();
			matrixRaw.clear();
			for(int i = 0 ; i <= matrix.size();i++){//Add one repeat
				MatrixElement matrixElement = new MatrixElement(i);
				matrixRaw.add(i, matrixElement);
			}
		}
		MatrixElement startMatrixElement = matrix.get(startVertex).get(0);
		startMatrixElement.setDistanñeFromStartVertex(0);
	}
	
	//Fill matrix with shortest distances and shortest paths between start and other vertexes.
	private void findAllShortestDistInMatr(int startVertex){
		logger.info("Filling matrix with shortest distances to start vertex and linking edges.Start vertex - " + startVertex);
		for(int i = 1 ; i <= matrix.size() ; i++){//Add one repeat
			for(Edge edge : edges){
				MatrixElement currentElement = matrix.get(edge.getFinishVertex()).get(i);
				MatrixElement previousElement = matrix.get(edge.getStartVertex()).get(i-1);
				MatrixElement currentRawPreviousElement = matrix.get(edge.getFinishVertex()).get(i-1);
				ArrayList<Edge> newPathToStart;
				if(currentElement.getDistanñeFromStartVertex() > 
						previousElement.getDistanñeFromStartVertex() + edge.getLength() && 
						previousElement.getDistanñeFromStartVertex()!=1000*1000){
					currentElement.setDistanñeFromStartVertex(previousElement.getDistanñeFromStartVertex() + edge.getLength());
					currentElement.setPreviousNearestVertex(edge.getStartVertex());	
					currentElement.setEdgesQuantity(previousElement.getEdgesQuantity() + 1);
					newPathToStart = new ArrayList<Edge>(previousElement.getPathToStart());
					newPathToStart.add(edge);
					currentElement.setPathToStart(newPathToStart);
				}
				if(currentElement.getDistanñeFromStartVertex() >= currentRawPreviousElement.getDistanñeFromStartVertex() && currentRawPreviousElement.getDistanñeFromStartVertex() != 1000*1000){
					currentElement.setDistanñeFromStartVertex(currentRawPreviousElement.getDistanñeFromStartVertex());
					currentElement.setPreviousNearestVertex(currentRawPreviousElement.getPreviousNearestVertex());
					newPathToStart = new ArrayList<Edge>(currentRawPreviousElement.getPathToStart());
					currentElement.setPathToStart(newPathToStart);
				}
			}
			if(matrix.get(startVertex).get(i).getDistanñeFromStartVertex() >= matrix.get(startVertex).get(i-1).getDistanñeFromStartVertex()){
				matrix.get(startVertex).get(i).setDistanñeFromStartVertex(matrix.get(startVertex).get(i-1).getDistanñeFromStartVertex());
			}
		}	
	}

	//Find shortest distances from vertex to start vertex and appropriate edge of current vertex.
	private MatrixElement findShortestWayFromSartToCurrent(int vertexNumber){
		logger.info("Searchin for shortest distances between start vertex and " + vertexNumber + " vertex. And correct edge for " + vertexNumber + " vertex");
		ArrayList<MatrixElement> vertexMatrixRaw = matrix.get(vertexNumber);
		MatrixElement nearestToStartVertexMatrixElement = vertexMatrixRaw.get(0);
		Integer minLength = nearestToStartVertexMatrixElement.getDistanñeFromStartVertex();
		for(int i = 1 ; i < vertexMatrixRaw.size(); i++){
			MatrixElement currentVertex = vertexMatrixRaw.get(i);
			if(currentVertex.getDistanñeFromStartVertex() < minLength){
				nearestToStartVertexMatrixElement = currentVertex;
			}
		}
		return nearestToStartVertexMatrixElement;
	}
	
	//Check presence of negative cicle.
	private boolean isNegativeCicle(){
		for(Map.Entry<Integer, ArrayList<MatrixElement>> entry : matrix.entrySet()){
			int lastElement = entry.getValue().get(edges.size()).getDistanñeFromStartVertex();
			int penultElement = entry.getValue().get(edges.size()-1).getDistanñeFromStartVertex();
			if(lastElement < penultElement){
				return true;
			}
		}
		return false;
	}

	//Builder class constructor.
	public static class Builder {
		Map<Integer, ArrayList<MatrixElement>> matrix = 
				new TreeMap<Integer, ArrayList<MatrixElement>>();
		ArrayList<Edge> edges = new ArrayList<Edge>();
		
		//Create graph's vertex and add it to Map vertexMatrix as a key.
		public Builder vertex(int vertexNumber){
			addVertexToMatrix(vertexNumber);
			return this;
		}
		
		//Create edge and add it to list of edges.Create graph's vertex and add them to Map vertexMatrix as a key.
		public Builder edge(int startVertex, int finishVertex, int length){
			if(startVertex == 0 && finishVertex == 0 && length == 0){
				logger.info("Ignor for empty edge.");
				return this;
			}
			Edge edge = new Edge(startVertex, finishVertex, length);
			edges.add(edge);
			addVertexToMatrix(startVertex);
			addVertexToMatrix(finishVertex);
			return this; 
		}
		
		private void addVertexToMatrix(int vertexNumber){
			if(!matrix.containsKey(vertexNumber)){
				matrix.put(vertexNumber, new ArrayList<MatrixElement>());
			}
		}
		
		public Graph build(){
			return new Graph(this);
		}
	}
}
