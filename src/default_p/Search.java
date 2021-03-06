package default_p;

import java.util.ArrayList;

public class Search {

	public enum SearchType {
		BREADTH, DEPTH, U_COST, A_STAR;
	}
	
	//Data	
	private State first_state; //State of the root Node
	private State goalState; //State of the goal Node
	private Node rootNode; //Root Node
	private ArrayList<Node> frontier;
	private ArrayList<State> visitedStates; //List of already visited states
	private Node next_node;
	private int expansion_count = 0; //Count of expansions (For the report)
	private int max_frontier_size = 0; //Maximum size of the frontier (for the report)
	public ArrayList<String> correct_move_list; //List of moves that lead to Goal State - BEWARE, IT'S BACKWARDS (reverted in NewAgent)
	public Node goalNode;
	private SearchType search_type;
	
	//Constructor
	public Search(State istate, State igstate) {
		first_state = istate;
		goalState = igstate;
		rootNode = new Node(first_state);
		frontier = new ArrayList<Node>();
		visitedStates = new ArrayList<State>();
		frontier.add(rootNode);
		visitedStates.add(first_state);

	}
	
	//Methods
	public void breadthFirstSearch() {
		search_type = SearchType.BREADTH;
		expand(rootNode);
		expansion_count++;
		frontier.remove(0);
		
		while (!frontier.isEmpty()) { //The Frontier is emptied when the Goal Node is reached
			if (max_frontier_size < frontier.size()) { //Store the biggest seen size of the frontier
				max_frontier_size = frontier.size();
			}
			expansion_count++;
			//System.out.println(" EXPANSION NUMBER: " + expansion_count);
			next_node = frontier.get(0);
			frontier.remove(0);
			expand(next_node);

		
		}
	}
	
	public void depthFirstSearch() {
		search_type = SearchType.DEPTH;
		expand(rootNode);
		expansion_count++;
		frontier.remove(0);
		
		while(!frontier.isEmpty()) {
			if (max_frontier_size < frontier.size()) { //Store the biggest seen size of the frontier
				max_frontier_size = frontier.size();
			}
			expansion_count++;
			next_node = frontier.get(0);
			frontier.remove(0);
			expand(next_node);
		}
	}
	
	public void uniformCostSearch() {
		search_type = SearchType.U_COST;
		expand(rootNode);
		expansion_count++;
		frontier.remove(0);
		
		while (!frontier.isEmpty()) { //The Frontier is emptied when the Goal Node is reached
			if (max_frontier_size < frontier.size()) { //Store the biggest seen size of the frontier
				max_frontier_size = frontier.size();
			}
			expansion_count++;
			//System.out.println(" EXPANSION NUMBER: " + expansion_count);
			next_node = frontier.get(0);
			frontier.remove(0);
			expand(next_node);
		}
	}
	
	public void aStarSearch() {
		search_type = SearchType.A_STAR;
		expand(rootNode);
		expansion_count++;
		frontier.remove(0);
		
		int i = 0;
		while(!frontier.isEmpty()) {
			
			if (max_frontier_size < frontier.size()) { //Store the biggest seen size of the frontier
				max_frontier_size = frontier.size();
			}
			expansion_count++;
			//System.out.println("Expansion count: " + expansion_count);
			next_node = frontier.get(0);
			frontier.remove(0);
			expand(next_node);
		}
		System.out.println("Expansion count: " + expansion_count);
	}
	
	public void expand( Node currentNode) {
		State state_of_cn = currentNode.getState();
		State state_of_child;
		Node child;
		ArrayList<String> lmoves = state_of_cn.giveLegalOptions();

		//Testing
		//System.out.println("Parent node with State in which the Robot is at                       " + state_of_cn.robot_point + " looking "  + state_of_cn.robot_o);
		//System.out.println("has been EXPANDED. These are its Children Nodes: ");
		double child_eval = 0; //Temporary location
		for (int i = 0; i < lmoves.size(); i++) {
			state_of_child = state_of_cn.predictState(lmoves.get(i));
			if (unseenState(state_of_child)) { //If the State hasn't been visited yet, add the Node to the Frontier (last position)
				visitedStates.add(state_of_child);
				child = new Node(state_of_child,currentNode, lmoves.get(i), currentNode.getCost()+1); //Only works with cost 1!!
				if (state_of_child.robot_point.equals(goalState.robot_point) && state_of_child.robot_o.equals(goalState.robot_o) && state_of_child.getDirtPoints().equals(goalState.getDirtPoints())) {
					/*	
					System.out.println("%n");
					System.out.println("SOLUTION FOUND");
					System.out.println("%n");
					*/	
					goalNode = child;
					correct_move_list = goalReached(child);
					frontier.clear(); //Empty the Frontier
				}
				switch(search_type) {
				case BREADTH:
					frontier.add(child); //BEWARE, only works for breadth-first. The location of storage has to change for other methods
					break;
				case DEPTH:
					frontier.add(0, child);
					break;
				case U_COST:
					int size = frontier.size();
					int index = size;
					for (int k = i; k > 0; k--) {
						if (frontier.get(size - k).getCost() > child.getCost()) {
							index = size - k;
							break;
						}
					}
					frontier.add(index, child);
					break;
				case A_STAR:
					child_eval = evaluationFunction(child);
					int child_index = 0;
					
					//Test
					//System.out.println(" -- A new child was created");
					//System.out.println(" The size of the Frontier is " + frontier.size());
					for (int j = 0; j < frontier.size(); j++) {
						if (child_eval >= evaluationFunction(frontier.get(j))) {
							child_index = j+1;
						}
					}
					//Test
					//System.out.print("The child will be stored at index " + child_index );
										
					frontier.add(child_index, child);
					break;
				}
				//Testing
				//System.out.println("     CHILD NUMBER " + (i+1) + " CREATED, with a STATE where Robot is at " + state_of_child.robot_point + " looking "  + state_of_child.robot_o);
				//System.out.println("It's Evaluation Score is: " + child_eval);
			}
		}
	}
	
	private double evaluationFunction( Node n) {
		double eval = 0.0;
		Heuristic h = new Heuristic(n);
		eval = n.getCost() + h.getHeuristic();
		
		return eval;
	}
	
	public boolean unseenState( State schild) { //TO BE FINISHED
		int match_ind = -1;
		for (int i = 0; i < visitedStates.size(); i++) {
			if (schild.robot_point.equals(visitedStates.get(i).robot_point) && schild.robot_o.equals(visitedStates.get(i).robot_o) && schild.getDirtPoints().equals(visitedStates.get(i).getDirtPoints())) { 
				match_ind = i;
			}
		}
		if (match_ind != -1) {
			//System.out.println("	THIS STATE (" + schild.robot_point + " looking "  + schild.robot_o + ") HAS ALREADY BEEN VISITED. Index: " + match_ind);
			return false;
		} else {
			return true;
		}
	}
	private ArrayList<String> goalReached( Node inNode) { //Method that returns the list of moves to get to the goal BACKWARDS
		Node goalNode = inNode;
		Node itNode;
		ArrayList<String> move_list_to_goal = new ArrayList<String>();
		move_list_to_goal.add(goalNode.getLastMove());
		itNode = goalNode.getParent();
		move_list_to_goal.add(itNode.getLastMove());
		while (itNode.getParent() != null) {
			itNode = itNode.getParent();
			move_list_to_goal.add(itNode.getLastMove());
		}
		return move_list_to_goal;
		//Stop the cycle and return the correct path!!
	}

	public int expansionCount() {
		return expansion_count;
	}
	public int maxFrontierSize() {
		return max_frontier_size;
	}
	public Node goalNode() {
		return goalNode;
	}
}