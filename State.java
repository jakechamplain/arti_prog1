import java.util.ArrayList;

public class State {

	private int sizeX;
	private int sizeY;
	private boolean robot_on; 	//Robot on or off
	private int robot_x;	//Location of the robot (use point class for position?)
	private int robot_y;
	private String robot_o; 	//Orientation of the robot
	//private ArrayList<Dirt> dirts; 	//Location of the dirts
	public ArrayList<String> moves; //List of legal moves
	
 	
	public State(int sx, int sy, boolean r_on, int r_x, int r_y, String r_o) { 
		robot_on = r_on;
		robot_x = r_x;
		robot_y = r_y;
		robot_o = r_o;
		//dirts = new ArrayList<Dirt>();
		//State.Dirt(robot_x,robot_y);
		
		}

	public void giveLegalOptions() {
		
		moves = new ArrayList<String>();
		
		if (!robot_on) {
			moves.add("TURN_ON");
			robot_on = true;
	
		}
		else if (false) { //If there is dirt at the position of the robot
			moves.add("SUCK");
		
		}
		else { 
			moves.add("GO");
			moves.add("TURN_RIGHT");
			moves.add("TURN_LEFT");
		
		
			//I know this code can be reduced, I'm just lazy
			if ((robot_o.equalsIgnoreCase("NORTH") && robot_y == sizeY) || (robot_o.equalsIgnoreCase("EAST") && robot_x == sizeX)) {
				moves.remove("GO");
			}
			if ((robot_o.equalsIgnoreCase("SOUTH") && robot_y == 1) || (robot_o.equalsIgnoreCase("WEST") && robot_x == 1)) {
				moves.remove("GO");
			}
		}
		
		
	}
	
	/*public void predictState(int r_x, int r_y, String r_o) {
		State futureState = new State(r_x,r_y,r_o);
	}*/
	
	void checkGoal() {
		
		//There is no dirt
		//Robot is at home
	}
	
}
