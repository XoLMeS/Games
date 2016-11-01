package Snake;

import java.awt.Color;

import acm.graphics.GObject;
import acm.graphics.GOval;
import tools.Action;

public class Dot extends tools.Object{
	
	private int x;
	private int y;
	private Color color = Color.GREEN;
	private GOval obj;
	private int RADIUS = 5;
	private Action a;
	public Dot(int x, int y, Action a){
		obj = new GOval(x*World.BLOCK_SIZE + World.BLOCK_SIZE/2 -RADIUS/2 ,y*World.BLOCK_SIZE + World.BLOCK_SIZE/2 -RADIUS/2,RADIUS,RADIUS);
		obj.setFilled(true);
		obj.setFillColor(color);
		this.x = x;
		this.y = y;
		this.a = a;
	}
	
	public Dot(){
		
	}
	
	public void action(){
		a.perfom();
	}
	
	public GObject getGObject(){
		return obj;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
}
