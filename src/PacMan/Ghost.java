package PacMan;

import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GObject;
import tools.Action;
import tools.BreadthFirstPaths;
import tools.Graph;
import tools.LinkedQueue;

public class Ghost extends tools.Object {

	private int x;
	private int y;
	private int next_x;
	private int next_y;
	private Action a;
	private tools.BreadthFirstPaths bfs;
	private String state = "active";

	public Ghost(int x, int y, Action a) {
		this.x = x;
		this.y = y;
		obj = new GImage("Ghost1.gif", World.BLOCK_SIZE * x + World.BLOCK_SIZE / 10,
				World.BLOCK_SIZE * y + World.BLOCK_SIZE / 10);
		obj.scale((double)World.BLOCK_SIZE/150);

		this.a = a;
	}

	GImage obj;

	public void action() {
		a.perfom();
	}

	public GObject getGObject() {
		return obj;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	private void move(int x, int y) {
		// animate(this.x, this.y,x,y,10);
		this.x = x;
		this.y = y;
	}

	private void animate(int x1, int y1, int x2, int y2, int steps) {

		double delta_x = (double) (x2 - x1) / steps;
		double delta_y = (double) (y2 - y1) / steps;

		for (int i = 0; i < steps; i++) {

			long time = System.currentTimeMillis();
			while (System.currentTimeMillis() - time <= 40) {

			}
			obj.move(delta_x * World.BLOCK_SIZE, delta_y * World.BLOCK_SIZE);

			// path.add(dot);
			obj.sendForward();
		}
	}

	public void move() {
		move(next_x, next_y);
	}
	
	public void moveTo(double d, double e){
		this.x += d;
		this.y += e;
	}

	public int nextStep(Graph map, int hero_loc) {
		bfs = new BreadthFirstPaths(map, x + map.X() * y);
		LinkedQueue<Integer> arr = null;
		switch (state) {
		case "active":
			arr = (LinkedQueue<Integer>) bfs.pathTo(hero_loc);
			break;
		default:
			break;
		}

		ArrayList<Integer> arr2 = new ArrayList<Integer>();
		if (arr != null) {
			for (Integer i : arr) {
				arr2.add(i);
			}
			int prev_step = x + y * map.X();
			for (int k = arr2.size() - 1; k >= 0; k--) {
				int i = arr2.get(k);
				// DOWN
				if (prev_step - i == map.X()) {
					this.next_y = y - 1;
					this.next_x = x;
					return x + map.X() * (next_y);
				}
				// TOP
				if (prev_step - i == -map.X()) {
					this.next_y = y + 1;
					this.next_x = x;
					return x + map.X() * (next_y);
				}
				// LEFT
				if (prev_step - i == 1) {
					this.next_y = y;
					this.next_x = x - 1;
					return next_x  + map.X() * (y);
				}
				// RIGHT
				if (prev_step - i == -1) {
					this.next_y = y;
					this.next_x = x + 1;
					return next_x  + map.X() * (y);
				}
				prev_step = i;
				// System.out.println("GOTO " + i);
			}
			return prev_step;
		}
		return 0;
	}

}
