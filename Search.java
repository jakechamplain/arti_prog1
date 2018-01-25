import java.util.*;
import java.io.IOException;

public class Search {
	private Node root;
	private Node temp;
	private State goal;
	private ArrayList<Node> frontier = new ArrayList<Node>();
	
	public Search(State stateZero) {
		root = new Node(stateZero);
		temp = null;
		goal = root.getState();
	}

	public void test() {
		int num_moves = 0;
		int depth = 1;
		Node child;
		boolean success = false;
		frontier.add(root);
		frontier.get(0).getState().giveLegalOptions();
		num_moves = frontier.get(0).getState().moves.size();
		System.out.println("NUMBER OF MOVES: " + num_moves);
		temp = frontier.get(0);
		while(!success) {
			for (int i = 0; i < num_moves; i++) {
				child = new Node(depth, 1, false, temp.getState().moves.get(i), temp);
				frontier.add(child);
				System.out.println("Orientation of robot " + i + ": " + child.getState().getRobotO());
				System.out.println("	Coordinates of robot: " + child.getState().getRobotPoint().getX() + ", " + child.getState().getRobotPoint().getY());
				System.out.println("	Move made: " + child.getMove());
				System.out.println("*************************************************");
				// something wrong with dirtPresent() 
				success = checkGoal(child.getState());
			}
			frontier.remove(0);
			frontier.get(0).getState().giveLegalOptions();
			num_moves = frontier.get(0).getState().moves.size();
			System.out.println("NUMBER OF MOVES: " + num_moves);
			temp = frontier.get(0);
		}
		System.out.println("* * * * * * * * * * * * SUCCESS * * * * * * * * * * * * ");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	public boolean repeatedState(ArrayList<State> state_list, Node node) {
		for(int i = 0; i < state_list.size(); i++) {
			//if((node.getState().getRobotPoint().equals(state_list.get(i).getRobotPoint()))
			//	&& (node.getState().getRobotO().equals(state_list.get(i).getRobotO()))
			//	&& (node.getState().getDirtPoints().size() == state_list.get(i).getDirtPoints().size())) {
			//	System.out.println("* * * * * * REPEATED STATE * * * * * *");
			if((node.getState().getRobotPoint().getX() == state_list.get(i).getRobotPoint().getX())
				&& (node.getState().getRobotPoint().getY() == state_list.get(i).getRobotPoint().getY())
				&& (node.getState().getRobotO().equals(state_list.get(i).getRobotO()))
				&& (node.getState().getDirtPoints().equals(state_list.get(i).getDirtPoints()))) {
				System.out.println("* * * * * * REPEATED STATE * * * * * *");
				return true;
			}
		}
		
		return false;
	}
	
	public ArrayList<String> makePath(Node start) {
		ArrayList<String> path = new ArrayList<String>();
		Node temp = start;
		while(temp != null) {
			path.add(temp.getMove());
			temp = temp.getParent();
		}
		
		return path;
	}
	
	
	
	public ArrayList<String> breadthFirstSearch() {
		int num_moves = 0;
		int depth = 1;
		Node child;
		ArrayList<State> visitedStates = new ArrayList<State>();
		ArrayList<String> path = new ArrayList<String>();
		frontier.add(root);
		frontier.get(0).getState().giveLegalOptions();
		visitedStates.add(frontier.get(0).getState());
		while(!frontier.isEmpty()) {
			temp = frontier.get(0);
			temp.getState().giveLegalOptions();
			num_moves = temp.getState().moves.size();
			// expand children and add each child node to the end of the frontier
			for (int i = 0; i < num_moves; i++) {
				child = new Node(depth, 1, false, temp.getState().moves.get(i), temp);
				if(checkGoal(child.getState())) {
					System.out.println("* * * * * * * * * * * * SUCCESS * * * * * * * * * * * * ");
					return makePath(child);
				}
				if(!repeatedState(visitedStates, child)) {
					frontier.add(child);
					visitedStates.add(child.getState());
					//System.out.println("* * * * * REMOVED REPEATED STATE * * * * *");
				}
				
				System.out.println("Move made: " + child.getMove());
				System.out.println("	Coordinates of robot: " + child.getState().getRobotPoint().getX() + ", " + child.getState().getRobotPoint().getY());
				System.out.println("	New orientation of robot: " + child.getState().getRobotO());
				System.out.println("	Number of dirt: " + child.getState().getDirtPoints().size());
				System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * *");
			}
			temp.setExpanded(true);
			frontier.remove(0);
			depth++;
		}
		return null;
	}
	
	public boolean checkGoal(State current) { //probably not void
		//There is no dirt
		//Robot is at home
		if((current.getRobotPoint().equals(goal.getRobotPoint())) && (current.getDirtPoints().isEmpty())) {
			return true;
		}
		else {
			return false;
		}
	}
}