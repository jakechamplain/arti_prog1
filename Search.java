import java.util.*;

public class Search {
	private Node root;
	private Node temp;
	private State goal;
	private ArrayList<Node> frontier = new ArrayList<Node>();
	
	public Search(State stateZero) {
		root = new Node(stateZero);
		temp = root;
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
		while(!frontier.isEmpty()) {
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
			if (success) {
				break;
			}
			
			frontier.remove(0);
			frontier.get(0).getState().giveLegalOptions();
			num_moves = frontier.get(0).getState().moves.size();
			System.out.println("NUMBER OF MOVES: " + num_moves);
			temp = frontier.get(0);
		}
	}
	
	
	public void breadthFirstSearch() {
		int num_moves = 0;
		int depth = 1;
		Node child;
		frontier.add(root);
		frontier.get(0).getState().giveLegalOptions();
		//while(frontier.get(0).getState().moves.size() > 0) {
		while(!frontier.isEmpty()) {
			num_moves = frontier.get(0).getState().moves.size();
			temp = frontier.get(0);
			for (int i = 0; i < num_moves; i++) {
				child = new Node(depth, 1, false, temp.getState().moves.get(i), temp);
				frontier.add(child);
			}
			
		}
	}
	
	public boolean checkGoal(State current) { //probably not void
		//There is no dirt
		//Robot is at home
		if((current.getRobotPoint().equals(goal.getRobotPoint())) && (current.dirtPresent() == -1)) {
			return true;
		}
		else {
			return false;
		}
	}
}