package tools;

import acm.graphics.GCompound;

public abstract class Hero {

	public void move(int x, int y) {

	}

	public void moveForward(double steps,int dir_x,int dir_y) {

	}

	public int getDirX() {
		return 0;
	}

	public int getDirY() {
		return 0;
	}

	public void setDirX(int x) {

	}

	public void setDirY(int y) {

	}

	public GCompound getGHero() {
		return null;
	}
}
