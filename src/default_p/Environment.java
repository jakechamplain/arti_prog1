package default_p;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Environment {

	//Data
	int sizeX;
	int sizeY;
	Point2D home_point;
	int[][] o_coor;
	private ArrayList<Point2D> obstacle_points;
	public Point2D oc_toadd;
	
	//Constructor
	public Environment(int sx, int sy, Point2D hxy, int[][] obstacles) {
		sizeX = sx;
		sizeY = sy;
		home_point = hxy;
		o_coor = obstacles;
		
		obstacle_points = new ArrayList<Point2D>();
		for (int i = 0; i <= o_coor.length-1; i++) {
		oc_toadd = new Point2D.Double(o_coor[i][0],o_coor[i][1]);
		obstacle_points.add(oc_toadd);
		}
				
	}
	
	//Methods
	int getSizeX() {
		return sizeX;
	}
	int getSizeY() {
		return sizeY;
	}
	ArrayList<Point2D> getObstacles() {	
		return obstacle_points;
	}
}
