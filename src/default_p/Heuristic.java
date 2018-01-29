package default_p;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Heuristic {

	//Data
	double value;
	private Node node;
	private Point2D home_point;
	private Point2D robot_point;
	private ArrayList<Point2D> unvisited_dirt_points;
	private ArrayList<Point2D> sequence;
	
	//Constructor
	public Heuristic(Node n) {
		node = n;
		
	}
	//Methods
	public void calculateH() {
		double total_dist = 0;
		value = 0;
		
		double min_rd;
		int closestD_index = 0;
		home_point = node.getState().getEnv().home_point; //Home point
		robot_point = node.getState().robot_point; //Robot current point
		unvisited_dirt_points = node.getState().getDirtPoints(); //VERY IMPORTANT: THIS LIST SHOULD CONTAIN THE *REMAINING* DIRTS, NOT ALL OF THEM
		sequence = new ArrayList<Point2D>();
		sequence.add(robot_point);
		Point2D last_added = sequence.get(0); //Put the current position of the robot at the start
		
		while(!unvisited_dirt_points.isEmpty()) { //Complete the sequence
			min_rd = node.getState().sizeX + node.getState().sizeY;
			for (int i = 0; i < unvisited_dirt_points.size(); i++) {
				if (last_added.distance(unvisited_dirt_points.get(i)) < min_rd) {
					min_rd = last_added.distance(unvisited_dirt_points.get(i));
					closestD_index = i;
				}
			}
			//Testing
			//System.out.println(min_rd);
			sequence.add(unvisited_dirt_points.get(closestD_index)); //Dirt closest to the last added dirt gets added.
			last_added = sequence.get(sequence.size()-1);
			unvisited_dirt_points.remove(closestD_index);
			//Testing
			//System.out.println(sequence);
		}
		//Testing
		//System.out.println(sequence);
		
		for (int i = 0; i < sequence.size() - 1; i++) {
			total_dist = total_dist + sequence.get(i).distance(sequence.get(i+1));		
		}
		total_dist = (total_dist + sequence.get(sequence.size()-1).distance(home_point));
		value = total_dist;
	}
	public double getHeuristic() {
		calculateH();
		return value;
	}
}
