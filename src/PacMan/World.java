package PacMan;

import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import acm.graphics.*;
import tools.Action;
import tools.Graph;

public class World extends tools.World {

	private int GAME_SPEED = 6;
	private GCompound world;
	private Graph map;
	private Hero hero;
	private Color line_color = Color.BLUE;
	private Color border_color = Color.RED;
	private GCompound panel;
	private GLabel score_l;
	private int score = 0;
	private final int num_of_dots = 200;
	private final int num_of_ghosts = 1;
	private ArrayList<Dot> dots = new ArrayList<Dot>();
	private ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
	
	public World() {
		create();
	}

	public World(int x, int y, Graph g) {
		map = g;
		BLOCK_SIZE = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()- 220) / y ;
	
		create();
	}

	public Graph getMap() {
		return map;
	}

	private boolean frontIsClear() {
		int arg1 = hero.getDirX();
		int arg2 = hero.getDirY();
		int x = (int) hero.getX();
		int y = (int) hero.getY();
		if (arg1 != 0 && isConnected(x + y * map.X(), x + y * map.X() + arg1)) {
			return true;
		}
		if (arg2 != 0 && isConnected(x + y * map.X(), x + y * map.X() + arg2 * map.X())) {
			return true;
		}
		return false;
	}

	private boolean isConnected(int arg1, int arg2) {
		for (Integer i : map.adj(arg1)) {
			if (i == arg2) {
				return true;
			}
		}
		return false;
	}

	private void create() {
		//Set World
		world = new GCompound();
		drawWorld();

		//Set hero
		hero = new Hero(0, 0);
		world.add(hero.getGHero());
		
		//Set Panel
		panel = new GCompound();
		world.add(panel);
		
		//Add background to the panel
		GRect panel_bg = new GRect(10, map.Y() * BLOCK_SIZE + 10, map.X() * BLOCK_SIZE - 20, 35);
		panel_bg.setFilled(true);
		panel_bg.setFillColor(Color.GRAY);
		panel.add(panel_bg);
		
		//Add Score label
		score_l = new GLabel("Score: " + score, 25, map.Y() * BLOCK_SIZE + 35);
		score_l.setColor(line_color);
		score_l.setFont(new Font("SERIF", 20, 20));
		panel.add(score_l);
		
		spawnGhosts();
	}

	public GCompound getGWorld() {
		return world;
	}

	public Hero getHero() {
		return hero;
	}

	public void checkColls() {
		ArrayList<Dot> toRem = new ArrayList<Dot>();
		for (Dot d : dots) {
			if ((int) hero.getX() == d.getX() && (int) hero.getY() == d.getY()) {
				toRem.add(d);
				d.action();
			}
		}
		for (Dot d : toRem) {
			dots.remove(d);
			world.remove(d.getGObject());
		}
		if (dots.size() == 0) {
			spawnDots();
		}
	}

	private void spawnDots() {
		int x = 0;
		int y = 0;
		Random r = new Random();

		for (int i = 0; i < num_of_dots; i++) {
			do {
				x = r.nextInt(map.X());
				y = r.nextInt(map.Y());

			} while (!map.adj(y * map.X() + x).iterator().hasNext());

			Dot d = new Dot(x, y, new Action() {

				@Override
				public void perfom() {
					score++;
					score_l.setLabel("Score: " + score);
				}
			});
			world.add(d.getGObject());
			dots.add(d);
		}
	}

	private void spawnGhosts() {
		ghosts.add(new Ghost(7, 7, new Action() {

			@Override
			public void perfom() {
				// TODO Auto-generated method stub

			}
		}));
		/*
		ghosts.add(new Ghost(8, 7, new Action() {

			@Override
			public void perfom() {
				// TODO Auto-generated method stub

			}
		}));
		 */
		for (Ghost g : ghosts) {
			world.add(g.getGObject());
		}
	}

	public void moveAll(int next_step_ba, boolean baIsActive) {
		boolean moved = false;
		int steps = 10;

		int next_block_ghosts[] = new int[num_of_ghosts];
		for (int i = 0; i < ghosts.size(); i++) {
			//next_block_ghosts[i] = ghosts.get(i).nextStep(map, (int) (hero.getX() + map.X() * hero.getY()));
			if(baIsActive){
				next_block_ghosts[i] = next_step_ba;
			}
		}
		if (frontIsClear()) {
			hero.move(hero.getX() +  hero.getDirX(), hero.getY() +   hero.getDirY());
			moved = true;
		}
		int buf_hero_dir_x = hero.getDirX();
		int buf_hero_dir_y = hero.getDirY();
		double ghost_delta_x = 0;
		double ghost_delta_y = 0;
		for (int i = 0; i < steps; i++) {
			long time = System.currentTimeMillis();
			while (System.currentTimeMillis() - time <= 150/GAME_SPEED) {
			}

			if (moved) {
				hero.moveForward(10,buf_hero_dir_x,buf_hero_dir_y);
			}
			
			for (int k = 0; k < ghosts.size(); k++) {
			
				int curr = ghosts.get(k).getX()+ghosts.get(k).getY()*map.X();
				if(curr + 1 ==next_block_ghosts[k]){
					ghost_delta_x = 0.1;
				}
				else if(curr - 1 ==next_block_ghosts[k]){
					ghost_delta_x = -0.1;
				}
				if(curr+map.X() == next_block_ghosts[k]){
					ghost_delta_y = 0.1;
				}
				else if(curr-map.X() == next_block_ghosts[k]){
					ghost_delta_y = -0.1;
				}
				//System.out.println("delta " + ghost_delta_x + " " + ghost_delta_y + " " + curr);
				ghosts.get(k).getGObject().move(ghost_delta_x * World.BLOCK_SIZE, ghost_delta_y * World.BLOCK_SIZE);
				
			}
		}

		for (int i = 0; i < ghosts.size(); i++) {
			ghosts.get(i).moveTo(ghost_delta_x*10,ghost_delta_y*10);
		}
		
		
	}

	public KeyListener keybinding() {

		KeyListener kl = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

				if (e.getKeyChar() == 'w') {

					if (hero.getY() > 0) {
						hero.setDirX(0);
						hero.setDirY(-1);
						// hero.move(hero.getX(), hero.getY() - 1);
						// moveHero(hero.getX(), hero.getY(),hero.getX(),
						// hero.getY() - 1,10);
					}
				}
				if (e.getKeyChar() == 'a') {
					if (hero.getX() > 0) {
						hero.setDirX(-1);
						hero.setDirY(0);
						// hero.move((hero.getX() - 1), hero.getY());
						// moveHero(hero.getX(), hero.getY(),hero.getX()-1,
						// hero.getY(),10);
					}
				}
				if (e.getKeyChar() == 's') {
					if (hero.getY() < map.Y() - 1) {
						hero.setDirX(0);
						hero.setDirY(1);
						// hero.move(hero.getX(), hero.getY() + 1);
						// moveHero(hero.getX(), hero.getY(),hero.getX(),
						// hero.getY() + 1,10);
					}
				}
				if (e.getKeyChar() == 'd') {
					if (hero.getX() < map.X() - 1) {
						hero.setDirX(1);
						hero.setDirY(0);
						// hero.move(hero.getX() + 1, hero.getY());
						// moveHero(hero.getX(), hero.getY(),hero.getX()+1,
						// hero.getY(),10);
					}
				}
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		};
		return kl;
	}

	public void drawWorld() {

		ArrayList<Integer> sides = new ArrayList<Integer>();
		for (int i = 0; i < map.X() * map.Y(); i++) {

			boolean top = false;
			boolean right = false;
			boolean bottom = false;
			boolean left = false;
			sides.add(i + 1);
			sides.add(i - 1);
			sides.add(i + map.X());
			sides.add(i - map.X());
			for (Integer in : map.adj(i)) {

				if (sides.contains(in)) {
					if (in == i + 1) {
						right = true;
					}
					if (in == i - 1) {
						left = true;
					}
					if (in == i + map.X()) {
						bottom = true;
					}
					if (in == i - map.X()) {
						top = true;
					}
				}

			}
			sides.clear();
			if (!top && !bottom && !left && !right) {
				// world.add(createBlock2(i % map.X(), i / map.X(), top, right,
				// bottom, left, true));
			} else {
				world.add(createBlock2(i % map.X(), i / map.X(), top, right, bottom, left, false));
			}
		}
		spawnDots();
	}

	public GCompound createBlock(int x, int y, boolean top, boolean right, boolean bottom, boolean left,
			boolean border) {
		GCompound block = new GCompound();

		// BORDER
		if (border) {
			GLine border_01 = new GLine(x * World.BLOCK_SIZE, y * World.BLOCK_SIZE,
					x * World.BLOCK_SIZE + World.BLOCK_SIZE, y * World.BLOCK_SIZE);
			border_01.setColor(Color.RED);
			block.add(border_01);
			GLine border_02 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE, y * World.BLOCK_SIZE,
					x * World.BLOCK_SIZE + World.BLOCK_SIZE, y * World.BLOCK_SIZE + World.BLOCK_SIZE);
			border_02.setColor(Color.RED);
			block.add(border_02);
			GLine border_03 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE,
					y * World.BLOCK_SIZE + World.BLOCK_SIZE, x * World.BLOCK_SIZE,
					y * World.BLOCK_SIZE + World.BLOCK_SIZE);
			border_03.setColor(Color.RED);
			block.add(border_03);
			GLine border_04 = new GLine(x * World.BLOCK_SIZE, y * World.BLOCK_SIZE + World.BLOCK_SIZE,
					x * World.BLOCK_SIZE, y * World.BLOCK_SIZE);
			border_04.setColor(Color.RED);
			block.add(border_04);
		}

		if (top) {
			GLine line_03 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE / 3, y * World.BLOCK_SIZE,
					x * World.BLOCK_SIZE + World.BLOCK_SIZE / 3, y * World.BLOCK_SIZE + World.BLOCK_SIZE / 3);
			block.add(line_03);
			GLine line_04 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE / 3 * 2, y * World.BLOCK_SIZE,
					x * World.BLOCK_SIZE + World.BLOCK_SIZE / 3 * 2, y * World.BLOCK_SIZE + World.BLOCK_SIZE / 3);
			block.add(line_04);
		} else {
			GLine line_09 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE / 3,
					y * World.BLOCK_SIZE + World.BLOCK_SIZE / 3, x * World.BLOCK_SIZE + World.BLOCK_SIZE / 3 * 2,
					y * World.BLOCK_SIZE + World.BLOCK_SIZE / 3);
			block.add(line_09);
		}
		if (right) {
			GLine line_05 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE / 3 * 2,
					y * World.BLOCK_SIZE + World.BLOCK_SIZE / 3, x * World.BLOCK_SIZE + World.BLOCK_SIZE,
					y * World.BLOCK_SIZE + World.BLOCK_SIZE / 3);
			block.add(line_05);
			GLine line_06 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE / 3 * 2,
					y * World.BLOCK_SIZE + World.BLOCK_SIZE / 3 * 2, x * World.BLOCK_SIZE + World.BLOCK_SIZE,
					y * World.BLOCK_SIZE + World.BLOCK_SIZE / 3 * 2);
			block.add(line_06);
		} else {
			GLine line_12 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE / 3 * 2,
					y * World.BLOCK_SIZE + World.BLOCK_SIZE / 3, x * World.BLOCK_SIZE + World.BLOCK_SIZE / 3 * 2,
					y * World.BLOCK_SIZE + World.BLOCK_SIZE / 3 * 2);
			block.add(line_12);
		}

		if (bottom) {
			GLine line_07 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE / 3,
					y * World.BLOCK_SIZE + World.BLOCK_SIZE / 3 * 2, x * World.BLOCK_SIZE + World.BLOCK_SIZE / 3,
					y * World.BLOCK_SIZE + World.BLOCK_SIZE);
			block.add(line_07);
			GLine line_08 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE / 3 * 2,
					y * World.BLOCK_SIZE + World.BLOCK_SIZE / 3 * 2, x * World.BLOCK_SIZE + World.BLOCK_SIZE / 3 * 2,
					y * World.BLOCK_SIZE + World.BLOCK_SIZE);
			block.add(line_08);
		} else {
			GLine line_10 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE / 3,
					y * World.BLOCK_SIZE + World.BLOCK_SIZE / 3 * 2, x * World.BLOCK_SIZE + World.BLOCK_SIZE / 3 * 2,
					y * World.BLOCK_SIZE + World.BLOCK_SIZE / 3 * 2);
			block.add(line_10);
		}

		if (left) {
			GLine line_01 = new GLine(x * World.BLOCK_SIZE, y * World.BLOCK_SIZE + World.BLOCK_SIZE / 3,
					x * World.BLOCK_SIZE + World.BLOCK_SIZE / 3, y * World.BLOCK_SIZE + World.BLOCK_SIZE / 3);
			block.add(line_01);
			GLine line_02 = new GLine(x * World.BLOCK_SIZE, y * World.BLOCK_SIZE + World.BLOCK_SIZE / 3 * 2,
					x * World.BLOCK_SIZE + World.BLOCK_SIZE / 3, y * World.BLOCK_SIZE + World.BLOCK_SIZE / 3 * 2);
			block.add(line_02);
		} else {
			GLine line_11 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE / 3,
					y * World.BLOCK_SIZE + World.BLOCK_SIZE / 3, x * World.BLOCK_SIZE + World.BLOCK_SIZE / 3,
					y * World.BLOCK_SIZE + World.BLOCK_SIZE / 3 * 2);
			block.add(line_11);
		}

		return block;
	}

	public GCompound createBlock2(int x, int y, boolean top, boolean right, boolean bottom, boolean left,
			boolean border) {
		GCompound block = new GCompound();
		if (border) {
			GLine border_01 = new GLine(x * World.BLOCK_SIZE, y * World.BLOCK_SIZE,
					x * World.BLOCK_SIZE + World.BLOCK_SIZE, y * World.BLOCK_SIZE);
			border_01.setColor(border_color);
			block.add(border_01);
			GLine border_02 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE, y * World.BLOCK_SIZE,
					x * World.BLOCK_SIZE + World.BLOCK_SIZE, y * World.BLOCK_SIZE + World.BLOCK_SIZE);
			border_02.setColor(border_color);
			block.add(border_02);
			GLine border_03 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE,
					y * World.BLOCK_SIZE + World.BLOCK_SIZE, x * World.BLOCK_SIZE,
					y * World.BLOCK_SIZE + World.BLOCK_SIZE);
			border_03.setColor(border_color);
			block.add(border_03);
			GLine border_04 = new GLine(x * World.BLOCK_SIZE, y * World.BLOCK_SIZE + World.BLOCK_SIZE,
					x * World.BLOCK_SIZE, y * World.BLOCK_SIZE);
			border_04.setColor(border_color);
			block.add(border_04);
		}

		if (top) {

		} else {
			GLine line_09 = new GLine(x * World.BLOCK_SIZE, y * World.BLOCK_SIZE,
					x * World.BLOCK_SIZE + World.BLOCK_SIZE, y * World.BLOCK_SIZE);
			line_09.setColor(line_color);
			block.add(line_09);
		}
		if (right) {

		} else {
			GLine line_12 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE, y * World.BLOCK_SIZE,
					x * World.BLOCK_SIZE + World.BLOCK_SIZE, y * World.BLOCK_SIZE + World.BLOCK_SIZE);
			line_12.setColor(line_color);
			block.add(line_12);
		}

		if (bottom) {

		} else {
			GLine line_10 = new GLine(x * World.BLOCK_SIZE, y * World.BLOCK_SIZE + World.BLOCK_SIZE,
					x * World.BLOCK_SIZE + World.BLOCK_SIZE, y * World.BLOCK_SIZE + World.BLOCK_SIZE);
			line_10.setColor(line_color);
			block.add(line_10);
		}

		if (left) {

		} else {
			GLine line_11 = new GLine(x * World.BLOCK_SIZE, y * World.BLOCK_SIZE, x * World.BLOCK_SIZE,
					y * World.BLOCK_SIZE + World.BLOCK_SIZE);
			line_11.setColor(line_color);
			block.add(line_11);
		}

		return block;
	}

	public int getNerestDot() {
		int position = map.V();
		for (Dot d : dots) {
			if (d.getX() + d.getY() * map.X() <= position) {
				position = d.getX() + d.getY() * map.X();
			}
		}
		return position;
	}

}
