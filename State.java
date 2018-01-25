import java.awt.geom.Point2D;
import java.lang.Object;
import java.util.ArrayList;

public class State {

	private boolean robot_on; 	//Robot on or off
	private Point2D robot_point; //Position of the robot
	private String robot_o; 	//Orientation of the robot
	
	private int[][] d_coor;
	private ArrayList<Point2D> dirt_points;
	public Point2D dc_toadd;

	public ArrayList<String> moves; //List of legal moves

	private Environment env;
	int sizeX; //Size of the Environment
	int sizeY;
	ArrayList<Point2D> o_points; //Location of obstacles

	//https://stackoverflow.com/questions/15020850/copy-constructors-and-defensive-copying
	public State(State s) {
		this(s.getRobotOn(), s.getRobotPoint(), s.getRobotO(), s.getDCoor(), s.getEnv());
	}

	public State(boolean r_on, Point2D ir_point, String r_o, int[][] idirt, Environment ienv) { 
		robot_on = r_on;
		robot_point = ir_point;
		robot_o = r_o;
		d_coor = idirt;
		
		dirt_points = new ArrayList<Point2D>();
		for (int i = 0; i <= d_coor.length-1; i++) {
		dc_toadd = new Point2D.Double(d_coor[i][0],d_coor[i][1]);
		dirt_points.add(dc_toadd);
		}
		
		env = ienv;
		sizeX = env.getSizeX();
		sizeY = env.getSizeY();
		o_points = env.getObstacles();
		
	}
	
	public void robotGo() {
		double rx = this.robot_point.getX();
		double ry = this.robot_point.getY();
		switch(this.robot_o) {
		case "NORTH":
			this.robot_point.setLocation(rx, ry + 1);
			break;
		case "EAST":
			this.robot_point.setLocation(rx + 1, ry);
			break;
		case "SOUTH":
			this.robot_point.setLocation(rx, ry - 1);
			break;
		case "WEST":
			this.robot_point.setLocation(rx - 1, ry);
			break;
		}
	}
	
	public boolean getRobotOn() {
		return robot_on;
	}
	
	public Point2D getRobotPoint() {
		return (Point2D)robot_point.clone();
	}
	
	public String getRobotO(){
		return robot_o;
	}
	
	public int[][] getDCoor() {
		return d_coor;
	}
	
	public ArrayList<Point2D> getDirtPoints() {
		return dirt_points;
	}
	
	public Environment getEnv() {
		return env;
	}
	
	public void setRobotO(String o) {
		robot_o = o;
	}

	public void removeDirt(int index) {
		this.dirt_points.remove(index);
	}
	
	public void giveLegalOptions() {
		
		System.out.println("THE CURRENT ORIENTATION OF THE ROBOT IS: " + robot_o);
		moves = new ArrayList<String>();
		
		if (false) {
			moves.add("TURN_ON");
			robot_on = true;
		}
		else if (dirtPresent() != -1) { //If there is dirt at the position of the robot
			moves.add("SUCK");
			System.out.println("-----SUCK ADDED TO LEGAL MOVES LIST-----");
		} else if (isGOLegal()) {
			moves.add("GO");
			System.out.println("-----GO ADDED TO LEGAL MOVES LIST-----");
			moves.add("TURN_RIGHT");
			moves.add("TURN_LEFT");
		}
		else { 
			System.out.println("    --->GO legal: " + isGOLegal());
			moves.add("TURN_RIGHT");
			moves.add("TURN_LEFT");
			
		
		
			//I know this code can be reduced, I'm just lazy

		//	if ((robot_o.equalsIgnoreCase("SOUTH") && robot_y == 1) || (robot_o.equalsIgnoreCase("WEST") && robot_x == 1)) {
		//		moves.remove("GO");
		//	}
			
		}
		
		
	}
	
	/*public void predictState(int r_x, int r_y, String r_o) {
		State futureState = new State(r_x,r_y,r_o);
	}*/
	
	public int dirtPresent() {
		/*
     	System.out.printf("%n");
     	System.out.println("The robot is located at:");
     	System.out.printf("%n");
     	System.out.println(robot_point);
     	System.out.printf("%n");
     	System.out.println("The coordinates for the dirt are: ");
     	*/
		for (int i = 0; i < dirt_points.size(); i++) {
			//System.out.println(dirt_points.get(i));
			if ( robot_point.equals(dirt_points.get(i))) {
				//REMEMBER NOT TO IMPUT THIS POSITION OF DIRT ANYMORE!!!!!
				//WILL REQUIRE EXTRACTIN INFOR ABOUT dirt_points.get(i) AND REMOVING
				//THOSE COORDINATES FROM THE LIST WE ARE IMPUTING
				return i;
			} 
		}
		return -1;
	}
	
	private boolean isGOLegal() {
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
	
	void checkGoal() { //probably not void
		
		//There is no dirt
		//Robot is at home
	}
	
}