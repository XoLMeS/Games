package Snake;

import java.awt.Color;

import acm.graphics.GArc;
import acm.graphics.GCompound;
import acm.graphics.GOval;
import acm.graphics.GRect;
import xolmes.Launcher;

public class Hero extends tools.Hero{
	
	GCompound obj;
	GCompound path;
	private double curr_x;
	private double curr_y;
	private int dir_x = 0;
	private int dir_y = 0;
	private final int RADIUS = World.BLOCK_SIZE/2;
	private boolean mouth_opened = true;
	private GOval oval;
	public Hero(int x, int y){
		
		curr_x = x;
		curr_y = y;
		obj = new GCompound();
		path = new GCompound();
		oval = new GOval(RADIUS, RADIUS);
		oval.setFilled(true); 
		oval.setColor(Color.YELLOW); 
		GArc arc = new GArc(RADIUS, RADIUS, 45, 270);
		arc.setColor(Color.YELLOW);
		arc.setFilled(true);
		arc.setFillColor(Color.YELLOW);
		obj.add(arc);
		obj.move(curr_x*World.BLOCK_SIZE + World.BLOCK_SIZE/4 + RADIUS/6, curr_y*World.BLOCK_SIZE + World.BLOCK_SIZE/3);
	}
	
	public GCompound getGHero(){
		return obj;
	}
	
	public GCompound getPath(){
		return path;
	}
	
	public void move(double x, double y){
		animate(curr_x, curr_y,x,y,10);
		curr_x = x;
		curr_y = y;
		//obj.move(x, y);
	}
	
	private void animate(double x1, double y1, double x2, double y2, int steps){
		
		double delta_x = (double)(x2 - x1)/steps;
		double delta_y = (double)(y2 - y1)/steps;
		GOval dot = new GOval(x1*World.BLOCK_SIZE+World.BLOCK_SIZE/2,y1*World.BLOCK_SIZE+World.BLOCK_SIZE/2,World.BLOCK_SIZE/15, World.BLOCK_SIZE/15);

		obj.removeAll();
		if(mouth_opened){
			obj.add(oval);
			mouth_opened = false;
		}
		else {
			int angle1 = 0;
			int angle2 = 0;
			//LEFT
			if(x1 > x2){
				angle1 = 225;
				angle2 = 270;
			}
			//RIGHT
			if(x1 < x2) {
				angle1 = 45;
				angle2 = 270;
			}
			//TOP
			if(y1 > y2){
				angle1 = 135;
				angle2 = 270;
			}
			//BOTT
			if(y1 < y2) {
				angle1 = 315;
				angle2 = 270;
			}
			
			GArc arc = new GArc(RADIUS, RADIUS, angle1, angle2);
			arc.setColor(Color.YELLOW);
			arc.setFilled(true);
			arc.setFillColor(Color.YELLOW);
			obj.add(arc);
			mouth_opened = true;
		}
		
		for(int i = 0; i < steps; i++){
			obj.move(delta_x*World.BLOCK_SIZE, delta_y*World.BLOCK_SIZE);
			long time = System.currentTimeMillis();
			while (System.currentTimeMillis()-time<=30){}
			
			dot = new GOval(x1*World.BLOCK_SIZE+World.BLOCK_SIZE/2 + (double)delta_x*i*World.BLOCK_SIZE-World.BLOCK_SIZE/30,y1*World.BLOCK_SIZE+World.BLOCK_SIZE/2+ (double)delta_y*i*World.BLOCK_SIZE - World.BLOCK_SIZE/30,World.BLOCK_SIZE/15, World.BLOCK_SIZE/15);
			dot.setColor(Color.GREEN);
			dot.setFilled(true);
			dot.setFillColor(Color.GREEN);
			if(i == 0){
				dot.setColor(Color.red);
				dot.setFillColor(Color.red);
			}
			//path.add(dot);
			obj.sendForward();
		}
	}
	
	public double getX(){
		return curr_x;
	}
	
	public double getY(){
		return curr_y;
	}
	
	
	public void moveForward(){
		move(curr_x+dir_x,curr_y+dir_y);
	}
	
	public int getDirX(){
		return dir_x;
	}
	
	public int getDirY(){
		return dir_y;
	}
	
	
	public void setDirX(int x) {
		dir_x = x;
	}

	public void setDirY(int y) {
		dir_y = y;
	}
	
}

