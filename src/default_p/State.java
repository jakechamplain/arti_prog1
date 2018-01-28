package default_p;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class State {

	//Data
	public Point2D robot_point; //Position of the robot
	public String robot_o; 	//Orientation of the robot

	private ArrayList<Point2D> dirt_points;
	public Point2D dc_toadd;

	public ArrayList<String> moves; //List of legal moves

	private Environment env;
	int sizeX; //Size of the Environment
	int sizeY;
	ArrayList<Point2D> o_points; //Location of obstacles

	//Constructor
	public State(Point2D ir_point, String r_o, ArrayList<Point2D> idirt, Environment ienv) { 
		robot_point = ir_point;
		robot_o = r_o;
		dirt_points = idirt;
		env = ienv;
		sizeX = env.getSizeX();
		sizeY = env.getSizeY();
		o_points = env.getObstacles();	
	}
	
	//Methods
	public ArrayList<Point2D> getDirtPoints() { //Returns coordinates of the dirts that the state has received as input
		return (ArrayList)dirt_points.clone();
	}
	
	public ArrayList<String> giveLegalOptions() {
		//Testing
		//System.out.println("  Calculating legal options from: " + robot_point + " currently facing: " + robot_o);
		moves = new ArrayList<String>();
		
		if (dirtOnTile() != -1) { //If there is dirt at the position of the robot
			moves.add("SUCK");		
		} else if (isGOLegal()) {
			moves.add("GO");
			//System.out.println(" --GO ADDED TO LEGAL MOVES LIST--");
			moves.add("TURN_RIGHT");
			moves.add("TURN_LEFT");
		}
		else { 
			moves.add("TURN_RIGHT");
			moves.add("TURN_LEFT");		
		}	
		return moves;
			
	}
	
	public int dirtOnTile() { //Returns a number if there is dirt on the tile
		int matches = 0;
		int index = -1;
		for (int i = 0; i < dirt_points.size(); i++) {
			//System.out.println(dirt_points.get(i));
				
			if ( robot_point.equals(dirt_points.get(i))) {
				
				//REMEMBER NOT TO IMPUT THIS POSITION OF DIRT ANYMORE!!!!!
				//WILL REQUIRE EXTRACTING INFOR ABOUT dirt_points.get(i) AND REMOVING
				//THOSE COORDINATES FROM THE LIST WE ARE IMPUTING
				matches++;
				index = i;
				break;
			} 
		}
		if (matches != 0) {
			return index;
		} else {
			return -1;
		}
		
	}
	
	private boolean isGOLegal() { //Returns 'true' if GO is allowed in this State
		int match;
		switch(robot_o) {
		case "NORTH":
			match = 0;
			for (int i = 0; i < o_points.size(); i++) {
				if (robot_point.getY() == o_points.get(i).getY()-1 && robot_point.getX() == o_points.get(i).getX()) {
					match++;
				}
			}
			if (robot_point.getY() == sizeY || match != 0) {
				return false;
			} else {
				return true;
			}
		case "EAST":
			match = 0;
			for (int i = 0; i < o_points.size(); i++) {
				if (robot_point.getY() == o_points.get(i).getY() && robot_point.getX() == o_points.get(i).getX()-1) {
					match++;
				}
			}
			if (robot_point.getX() == sizeX || match != 0) {
				return false;
			} else {
				return true;
			}
		case "SOUTH":
			match = 0;
			for (int i = 0; i < o_points.size(); i++) {
				if (robot_point.getY() == o_points.get(i).getY()+1 && robot_point.getX() == o_points.get(i).getX()) {
					match++;
				}
			}
			if (robot_point.getY() == 1 || match != 0) {
				return false;
			} else {
				return true;
			}
		case "WEST":
			match = 0;
			for (int i = 0; i < o_points.size(); i++) {
				if (robot_point.getY() == o_points.get(i).getY() && robot_point.getX() == o_points.get(i).getX()+1) {
					match++;
				}
			}
			if (robot_point.getX() == 1 || match != 0) {
				return false;
			} else {
				
				return true;
			}
		default:
			return true;
	
		}
		
	}

	public State predictState( String chosen_move) {
		int dirt_index;
		Point2D predict_point = (Point2D) robot_point.clone();
		String predict_o = robot_o;
		ArrayList<Point2D> predict_dirt_points = (ArrayList<Point2D>) dirt_points.clone();
		
		if(chosen_move == "TURN_RIGHT") {
			switch(robot_o) {
			case "NORTH":
				predict_o = "EAST";
				break;
			case "EAST":
				predict_o = "SOUTH";
				break;
			case "SOUTH":
				predict_o = "WEST";
				break;
			case "WEST":
				predict_o = "NORTH";
				break;
			}
		}
		else if(chosen_move == "TURN_LEFT") {
			switch(robot_o) {
			case "NORTH":
				predict_o = "WEST";
				break;
			case "WEST":
				predict_o = "SOUTH";
				break;
			case "SOUTH":
				predict_o = "EAST";
				break;
			case "EAST":
				predict_o = "NORTH";
				break;
			}
		}
		else if(chosen_move == "GO") {
			double rx = robot_point.getX();
			double ry = robot_point.getY();
			switch(robot_o) {
			case "NORTH":
				predict_point.setLocation(rx, ry + 1);
				break;
			case "EAST":
				predict_point.setLocation(rx + 1, ry);
				break;
			case "SOUTH":
				predict_point.setLocation(rx, ry - 1);
				break;
			case "WEST":
				predict_point.setLocation(rx - 1, ry);
				break;
			}
		}
		else if(chosen_move == "SUCK") {
			dirt_index = dirtOnTile();
			predict_dirt_points.remove(dirt_index);
			System.out.println(" -- D I R T  S U C K E D --");
		}
		
		State predictState = new State(predict_point, predict_o, predict_dirt_points, env);
		return predictState;
	}
}
