package PacMan;

import java.awt.Color;

import acm.graphics.GArc;
import acm.graphics.GCompound;
import acm.graphics.GOval;
import acm.graphics.GRect;
import xolmes.Launcher;

public class Hero extends tools.Hero {

	GCompound obj;
	GCompound path;
	private int curr_x;
	private int curr_y;
	private int dir_x = 0;
	private int dir_y = 0;
	private final int RADIUS = World.BLOCK_SIZE / 2;
	private boolean mouth_opened = true;
	private boolean animated = false;
	private GOval oval;

	public Hero(int x, int y) {

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
		obj.move(curr_x * World.BLOCK_SIZE + World.BLOCK_SIZE / 4 + RADIUS / 6,
				curr_y * World.BLOCK_SIZE + World.BLOCK_SIZE / 3);
	}

	public GCompound getGHero() {
		return obj;
	}

	public GCompound getPath() {
		return path;
	}

	private void animate(double x1, double y1, double x2, double y2, int steps) {

		// double delta_x = (double)(x2 - x1)/steps;
		// double delta_y = (double)(y2 - y1)/steps;
		if (animated) {
			obj.removeAll();
			if (mouth_opened) {
				obj.add(oval);
				mouth_opened = false;
			} else {
				int angle1 = 0;
				// LEFT
				if (x1 > x2) {
					angle1 = 225;
				}
				// RIGHT
				if (x1 < x2) {
					angle1 = 45;
				}
				// TOP
				if (y1 > y2) {
					angle1 = 135;
				}
				// BOTT
				if (y1 < y2) {
					angle1 = 315;
				}

				GArc arc = new GArc(RADIUS, RADIUS, angle1, 270);
				arc.setColor(Color.YELLOW);
				arc.setFilled(true);
				arc.setFillColor(Color.YELLOW);
				obj.add(arc);
				mouth_opened = true;
			}
			animated = false;
		}
		else {
			animated = true;
		}

		/*
		 * for(int i = 0; i < steps; i++){ obj.move(delta_x*World.BLOCK_SIZE,
		 * delta_y*World.BLOCK_SIZE); long time = System.currentTimeMillis();
		 * while (System.currentTimeMillis()-time<=30){}
		 * 
		 * obj.sendForward(); }
		 */
	}

	public int getX() {
		return curr_x;
	}

	public int getY() {
		return curr_y;
	}

	public void move(int x, int y) {
		curr_x = x;
		curr_y = y;
	}

	public void moveForward(double steps, int dir_x, int dir_y) {
		obj.move((dir_x) / (double) steps * World.BLOCK_SIZE, (dir_y) / (double) steps * World.BLOCK_SIZE);
		animate(curr_x, curr_y, curr_x+dir_x, curr_y+dir_y, 10);
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
