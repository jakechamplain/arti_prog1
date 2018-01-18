import java.util.*;

public class Node {
	private int depth;
	private int path_cost;
	private boolean expanded;
	private State state;
	private Node parent;
	private ArrayList<Node> children;
	
	public Node(int d, int p_c, boolean e, State s, Node p) {
		depth = d;
		path_cost = p_c;
		expanded = e;
		state = s;
		parent = p;
		children = new ArrayList<Node>();
	}
	
	public void setChild(Node child) {
		children.add(child);
	}
}