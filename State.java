import java.util.*;

public class State {
	
	private class Dirt {
		private int x;
		private int y;
		
		public Dirt(int _x, int _y) {
			x = _x;
			y = _y;		
		}
	}
	
	private int robot_x;
	private int robot_y;
	private String robot_o;
	private ArrayList<Dirt> dirts;
	
	public State(int r_x, int r_y, String r_o) {
		robot_x = r_x;
		robot_y = r_y;
		robot_o = r_o;
		dirts = new ArrayList<Dirt>();
	}
}