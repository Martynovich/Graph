package main.java.com.andersen.exceptions;

public class NegativeCicleException extends Exception {
	
	private Object shortestPath;
	 
    public Object getShortestPath(){
    	return shortestPath;
    }
    
    public NegativeCicleException(String message, Object shortestPath){
        super(message);
        this.shortestPath = shortestPath;
    }
}
