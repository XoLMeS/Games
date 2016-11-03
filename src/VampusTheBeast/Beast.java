package VampusTheBeast;

import tools.Action;
import PacMan.World;
import acm.graphics.GImage;
import acm.graphics.GObject;

public class Beast extends tools.Object{

	private int x;
	private int y;
	private Action a;
	GImage obj;
	
	public Beast(int x, int y, Action a) {
		this.x = x;
		this.y = y;
		obj = new GImage("beast.gif", World.BLOCK_SIZE * x + World.BLOCK_SIZE*0.2,
				World.BLOCK_SIZE * y + World.BLOCK_SIZE*0.2);
		obj.scale(((double)World.BLOCK_SIZE)/850);
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

	private void move(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void move(){
		
	}
}
