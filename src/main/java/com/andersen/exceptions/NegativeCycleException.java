package main.java.com.andersen.exceptions;

public class NegativeCycleException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2395423474152905124L;
	private  transient Object shortestPath;

	public final Object getShortestPath() {
		return shortestPath;
	}

	public NegativeCycleException(String message, Object shortestPath) {
		super(message);
		this.shortestPath = shortestPath;
	}
}
