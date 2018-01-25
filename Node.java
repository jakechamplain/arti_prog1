import java.util.*;

public class Node {
	private int depth;
	private int pathCost;
	private boolean expanded;
	private String move;
	private State state;
	private Node parent;
	private ArrayList<Node> children;
	
	public Node(State s) {
		// root node
		depth = 0;
		pathCost = 0;
		expanded = false;
		move = "";
		state = s;
		parent = null;
		children = new ArrayList<Node>();
	}
	
	public Node(int d, int pc, boolean e, String m, Node p) {
		depth = d;
		pathCost = pc;
		expanded = e;
		move = m;
		state = new State(p.getState());
		parent = p;
		children = new ArrayList<Node>();
		if(this.move != "") {
			this.setState();
		}
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
	
	public String getMove() {
		return move;
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
	
	public void setExpanded(boolean e) {
		expanded = e;
	}
	
	public void setState() {
		String orientation = "";
		int dirt_index;
		
		if(this.move == "SUCK") {
			dirt_index = state.dirtPresent();
			state.removeDirt(dirt_index);
		}
		else if(this.move == "TURN_RIGHT") {
			switch(this.state.getRobotO()) {
			case "NORTH":
				orientation = "EAST";
				break;
			case "EAST":
				orientation = "SOUTH";
				break;
			case "SOUTH":
				orientation = "WEST";
				break;
			case "WEST":
				orientation = "NORTH";
				break;
			}
			this.state.setRobotO(orientation);
		}
		else if(this.move == "TURN_LEFT") {
			switch(this.state.getRobotO()) {
			case "NORTH":
				orientation = "WEST";
				break;
			case "WEST":
				orientation = "SOUTH";
				break;
			case "SOUTH":
				orientation = "EAST";
				break;
			case "EAST":
				orientation = "NORTH";
				break;
			}
			this.state.setRobotO(orientation);
		}
		else if(this.move == "GO") {
			this.state.robotGo();
		}
	}
}