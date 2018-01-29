package default_p;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection; 
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class NewAgent implements Agent
{

public int step_counter = 0;
public int homeX = 0;
public int homeY = 0;
private Point2D home_point;
public int sizeX = 0;
public int sizeY = 0;
String orientation;
ArrayList<String> atObst = new ArrayList<String>();
ArrayList<String> atDirt = new ArrayList<String>();
int[][] o_coor;
int[][] d_coor;
ArrayList<Point2D> dirt_points;
ArrayList<Point2D> emptyAL = new ArrayList<Point2D>(); //Arraylist used to input dirt on goal state
ArrayList<String> correct_moves;
ArrayList<String> correct_moves_reordered = new ArrayList<String>();



		/*
			init(Collection<String> percepts) is called once before you have to select the first action. Use it to find a plan. Store the plan and just execute it step by step in nextAction.
		*/

	    public void init(Collection<String> percepts) {
			/*
				Possible percepts are:
				- "(SIZE x y)" denoting the size of the environment, where x,y are integers
				- "(HOME x y)" with x,y >= 1 denoting the initial position of the robot
				- "(ORIENTATION o)" with o in {"NORTH", "SOUTH", "EAST", "WEST"} denoting the initial orientation of the robot
				- "(AT o x y)" with o being "DIRT" or "OBSTACLE" denoting the position of a dirt or an obstacle
				Moving north increases the y coordinate and moving east increases the x coordinate of the robots position.
				The robot is turned off initially, so don't forget to turn it on.
			*/
	    	
			Pattern perceptNamePattern = Pattern.compile("\\(\\s*([^\\s]+).*"); //Splits strings based on spaces
			for (String percept:percepts) {	
				Matcher perceptNameMatcher = perceptNamePattern.matcher(percept);
				if (perceptNameMatcher.matches()) {
					String perceptName = perceptNameMatcher.group(1);
					if (perceptName.equals("HOME")) {
						Matcher m = Pattern.compile("\\(\\s*HOME\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
						if (m.matches()) {
							System.out.println("Robot is at " + m.group(1) + "," + m.group(2));
							homeX = Integer.parseInt(m.group(1));
							homeY = Integer.parseInt(m.group(2));
							home_point = new Point2D.Double(homeX,homeY);
						}
					}
					if (perceptName.equals("SIZE")) {
						Matcher m = Pattern.compile("\\(\\s*SIZE\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
						if (m.matches()) {
							sizeX = Integer.parseInt(m.group(1));
							sizeY = Integer.parseInt(m.group(2));		
						}
					}
					if (perceptName.equals("ORIENTATION")) {
						Matcher m = Pattern.compile("\\(\\s*ORIENTATION\\s+([A-Z]+)\\s*\\)").matcher(percept);
						if (m.matches()) {
							orientation = m.group(1);
						}
					}
					if (perceptName.equals("AT")) {
						Matcher m1 = Pattern.compile("\\(\\s*AT\\s+(DIRT+)\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
						if (m1.matches()) {
							atDirt.add(m1.group());
						}
						Matcher m2 = Pattern.compile("\\(\\s*AT\\s+(OBSTACLE+)\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
						if (m2.matches()) {
							atObst.add(m2.group());	
						}
					}
					else {
						System.out.println("other percept:" + percept);
					}
				} else {
					System.err.println("strange percept that does not match pattern: " + percept);
				}
			}
						
			//Take the coordinates of the obstacles and store them in a 2D array
			String[] obstacles = atObst.toArray(new String[atObst.size()]);
			o_coor = new int[obstacles.length][2];
			for (int i = 0; i< obstacles.length; i++) {
				Pattern pattern = Pattern.compile("\\(\\s*AT\\s+(OBSTACLE+)\\s+([0-9]+)\\s+([0-9]+)\\s*\\)");
				Matcher matcher = pattern.matcher(obstacles[i]);
				if (matcher.matches()) {
					o_coor[i][0] = Integer.parseInt(matcher.group(2));
					o_coor[i][1] = Integer.parseInt(matcher.group(3));
				}
				
			}		
			//Same for the positions of the dirt
			String[] dirts = atDirt.toArray(new String[atDirt.size()]);
			d_coor = new int[dirts.length][2];
			for (int i = 0; i< dirts.length; i++) {
				Pattern pattern = Pattern.compile("\\(\\s*AT\\s+(DIRT+)\\s+([0-9]+)\\s+([0-9]+)\\s*\\)");
				Matcher matcher = pattern.matcher(dirts[i]);
				if(matcher.matches()) {	
					d_coor[i][0] = Integer.parseInt(matcher.group(2));
					d_coor[i][1] = Integer.parseInt(matcher.group(3));
				}		
			}
			dirt_points = new ArrayList<Point2D>();
			for (int i = 0; i <= d_coor.length-1; i++) {
				Point2D dc_toadd = new Point2D.Double(d_coor[i][0],d_coor[i][1]);
				dirt_points.add(dc_toadd);
			}

			//PERFORM THE SEARCH		
			//

			System.out.println("*************************************************");
			System.out.println("*************************************************");
			System.out.println("*************************************************");

			long startTime = System.nanoTime();


			Environment initialEnv = new Environment(sizeX, sizeY, home_point, o_coor);
			State initialState = new State(home_point, orientation, dirt_points, initialEnv);
			State goalState = new State(home_point, orientation, emptyAL, initialEnv);
			Search theSearch = new Search(initialState, goalState);

			//theSearch.breadthFirstSearch();
			//theSearch.depthFirstSearch();
			theSearch.aStarSearch();
			correct_moves = theSearch.correct_move_list;
						
			System.out.println("*************************************************");
			System.out.println("*************************************************");
			System.out.println("*************************************************");
			
			System.out.println("OBTAINED SOLUTION: ");
			System.out.println( "Size of 'correct_moves' :" + correct_moves.size());
			for (int i = 0; i < correct_moves.size(); i++) {
				String next_move = correct_moves.get( correct_moves.size() - (1 + i));
				System.out.println(next_move);
				if (i > 0) {	
				correct_moves_reordered.add(next_move);
				}
			}
			
			System.out.println("*************************************************");
			System.out.println("*************************************************");
			
			long endTime = System.nanoTime();

			long duration = (endTime - startTime)/1000000;
			
			System.out.println("Total number of expansions: " + theSearch.expansionCount());
			System.out.println("Maximum size of the Frontier: " + theSearch.maxFrontierSize());
			System.out.println("Cost to the Node: " + theSearch.goalNode().getCost());
			System.out.println("Time to compute the solution (in miliseconds) :" + duration);
			
			System.out.println("*************************************************");
			System.out.println("*************************************************");

	    }

	    public String nextAction(Collection<String> percepts) {
	    		
	    		System.out.print(" -- NEW STEP --");
		    System.out.printf("%n");
	     	
			System.out.print("Perceiving:");
			for(String percept:percepts) { //THIS IS NOT REALLY NECESARY
				System.out.print("'" + percept + "', "); //BUMP or DIRT
			}
			System.out.printf("%n");
			//String[] actions = { "TURN_ON", "TURN_OFF", "TURN_RIGHT", "TURN_LEFT", "GO", "SUCK" };
			
			String turnon = "TURN_ON";
			String turnoff = "TURN_OFF";
			step_counter++;
			
			System.out.println( "Currently Step number: " + step_counter);
			if (step_counter == 1) {			
				return turnon;	
			} else if ((step_counter - 2) == correct_moves_reordered.size()) {
				return turnoff;
			}
			else {
				return correct_moves_reordered.get(step_counter - 2);
			}
		
			
			
	
		}


}
