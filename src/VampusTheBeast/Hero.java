package VampusTheBeast;

import acm.graphics.GCompound;
import acm.graphics.GImage;

public class Hero extends tools.Hero{

	GCompound obj;
	GImage gif;
	private int curr_x;
	private int curr_y;
	private int dir_x = 0;
	private int dir_y = 0;
	
	public Hero(int x, int y) {

		curr_x = x;
		curr_y = y;
		obj = new GCompound();
		gif = new GImage("Hero.gif",x*World.BLOCK_SIZE + World.BLOCK_SIZE*0.2, y*World.BLOCK_SIZE+ World.BLOCK_SIZE*0.2);
		gif.scale((double)World.BLOCK_SIZE/450);
		obj.add(gif);
	}
	
	public GCompound getGHero() {
		return obj;
	}
	
	public int getX() {
		return curr_x;
	}

	public int getY() {
		return curr_y;
	}
	
	public int getDirX() {
		return dir_x;
	}

	public int getDirY() {
		return dir_y;
	}

	public void setDirX(int x) {
		dir_x = x;
	}

	public void setDirY(int y) {
		dir_y = y;
	}
	
	public void move(int x,int y){
		curr_x += dir_x;
		curr_y += dir_y;
		obj.move(dir_x*World.BLOCK_SIZE, dir_y*World.BLOCK_SIZE);
		}

}


