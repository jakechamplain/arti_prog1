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
}