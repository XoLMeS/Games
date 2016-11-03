package VampusTheBeast;

import acm.graphics.GCompound;

public class Hero extends tools.Hero{

	GCompound obj;
	private int curr_x;
	private int curr_y;
	private int dir_x = 0;
	private int dir_y = 0;
	
	public Hero(int x, int y) {

		curr_x = x;
		curr_y = y;
		obj = new GCompound();
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

}


