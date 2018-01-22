import java.util.*;

public class Node {
	private int depth;
	private int pathCost;
	private boolean expanded;
	private State state;
	private Node parent;
	private ArrayList<Node> children;
	
	public Node(State s) {
		// root node
		depth = 0;
		pathCost = 0;
		expanded = false;
		state = s;
		parent = null;
		children = new ArrayList<Node>();
	}
	
	public Node(int d, int pc, boolean e, State s, Node p) {
		depth = d;
		pathCost = pc;
		expanded = e;
		state = s;
		parent = p;
		children = new ArrayList<Node>();
	}
	
	public void setChild(Node child) {
		children.add(child);
	}
	
	public int getDepth() {
		return depth;
	}
	
	public int getPathCost() {
		return pathCost;
	}
	
	public boolean isExpanded() {
		return expanded;
	}
	
	public State getState() {
		return state;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public ArrayList<Node> getChildren() {
		if (expanded) {
			return children;
		}
		else {
			return null;
		}
	}
}