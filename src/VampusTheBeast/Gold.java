package VampusTheBeast;

import PacMan.World;
import acm.graphics.GImage;
import acm.graphics.GObject;
import tools.Action;

public class Gold extends tools.Object{

	private int x;
	private int y;
	private Action a;
	GImage obj;
	
	public Gold(int x, int y, Action a) {
		this.x = x;
		this.y = y;
		obj = new GImage("gold.gif", World.BLOCK_SIZE * x + World.BLOCK_SIZE*0.2,
				World.BLOCK_SIZE * y + World.BLOCK_SIZE*0.4);
		obj.scale(((double)World.BLOCK_SIZE)/750);
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
