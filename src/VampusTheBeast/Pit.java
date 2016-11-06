package VampusTheBeast;

import PacMan.World;
import acm.graphics.GImage;
import acm.graphics.GObject;
import tools.Action;

public class Pit extends tools.Object{

	private int x;
	private int y;
	private Action a;
	GImage obj;
	
	public Pit(int x, int y, Action a) {
		this.x = x;
		this.y = y;
		obj = new GImage("pit.gif", World.BLOCK_SIZE * x,
				World.BLOCK_SIZE * y);
		obj.scale(((double)World.BLOCK_SIZE)/200,(double)World.BLOCK_SIZE/100);
		this.a = a;
	}

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

	
	public void move(){
		
	}
}
