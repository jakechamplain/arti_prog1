

import java.util.Collection;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewAgent implements Agent
{

public int homeX = 0;
public int homeY = 0;
public int sizeX = 0;
public int sizeY = 0;
String orientation;
String at;

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
							System.out.println("robot is at " + m.group(1) + "," + m.group(2));
							homeX = Integer.parseInt(m.group(1));
							homeY = Integer.parseInt(m.group(2));
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
					if (perceptName.equals("AT")) { //THIS IS NOT FINISHED
						Matcher m = Pattern.compile("\\(\\s*AT\\s+([A-Z]+)\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
						if (m.matches()) {
							at = m.group(1);
						}
					}
					else {
						System.out.println("other percept:" + percept);
					}
				} else {
					System.err.println("strange percept that does not match pattern: " + percept);
				}
			}
	    }

	    public String nextAction(Collection<String> percepts) {
	    		
	     	System.out.print("The size of the enviroment is " + sizeX + " , " + sizeY);
			System.out.print("  Perceiving:");
			for(String percept:percepts) { //THIS IS NOT REALLY NECESARY
				System.out.print("'" + percept + "', "); //BUMP edo DIRT ematen du
			}
			System.out.println("");
			String[] actions = { "TURN_ON", "TURN_OFF", "TURN_RIGHT", "TURN_LEFT", "GO", "SUCK" };
			return actions[0]; //Turn on
		}


}
