package default_p;

public class Node {

	//Data
	private State state;
	private Node parent;
	private String parent_move;
	private double cost_to_here;
	
	//Constructor
	public Node( State s) { //For the root node
		state = s;
		parent = null;
		parent_move = null;
		cost_to_here = 0;
	}
	public Node( State s, Node n, String imove, double icost) { //For the rest of the nodes
		state = s;
		parent = n;
		parent_move = imove;
		cost_to_here = icost;
	}
	
	//Methods
	public double getCost() {
		return cost_to_here;
	}
	
	public State getState( ) {
		return state;
	}
	public Node getParent( ) {
		return parent;
	}
	public String getLastMove( ) {
		return parent_move;
	}
	

}
