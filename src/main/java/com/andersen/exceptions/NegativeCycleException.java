package main.java.com.andersen.exceptions;

public class NegativeCycleException extends Exception {
	
	private Object shortestPath;
	 
    public Object getShortestPath(){
    	return shortestPath;
    }
    
    public NegativeCycleException(String message, Object shortestPath){
        super(message);
        this.shortestPath = shortestPath;
    }
}
