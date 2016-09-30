package Snake;

import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import acm.graphics.*;
import tools.Graph;

public class World extends tools.World {

	private GCompound world;
	private Graph map;
	private Hero hero;
	private Color line_color = Color.BLUE;
	private Color border_color = Color.RED;
	private GCompound panel;
	private GLabel score;
	
	public World() {
		create();
	}

	public World(int x, int y, Graph g) {
		map = g;
		BLOCK_SIZE = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/y)-20;
		create();
		
	}

	public Graph getMap(){
		return map;
	}

	private void create() {
		world = new GCompound();
		drawWorld();
		hero = new Hero(0,0);
		world.add(hero.getGHero());
		world.add(hero.getPath());
		
		panel = new GCompound();
		world.add(panel);
		GRect panel_bg = new GRect(10,map.Y()*BLOCK_SIZE+10,map.X()*BLOCK_SIZE-20,35);
		panel_bg.setFilled(true);
		panel_bg.setFillColor(Color.GRAY);
		panel.add(panel_bg);
		score = new GLabel("Score: 0", 25,map.Y()*BLOCK_SIZE+35);
		score.setColor(line_color);
		score.setFont(new Font("SERIF", 20, 20));
		panel.add(score);
	}
	
	public GCompound getGWorld(){
		return world;
	}
	
	public Hero getHero(){
		return hero;
	}
	
	
	public KeyListener keybinding(){

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
						//hero.move(hero.getX(), hero.getY() - 1);
						//moveHero(hero.getX(), hero.getY(),hero.getX(), hero.getY() - 1,10);
					}
				}
				if (e.getKeyChar() == 'a') {
					if (hero.getX() > 0) {
						hero.setDirX(-1);
						hero.setDirY(0);
						//hero.move((hero.getX() - 1), hero.getY());
						//moveHero(hero.getX(), hero.getY(),hero.getX()-1, hero.getY(),10);
					}
				}
				if (e.getKeyChar() == 's') {
					if (hero.getY() < map.Y() - 1) {
						hero.setDirX(0);
						hero.setDirY(1);
						//hero.move(hero.getX(), hero.getY() + 1);
						//moveHero(hero.getX(), hero.getY(),hero.getX(), hero.getY() + 1,10);
					}
				}
				if (e.getKeyChar() == 'd') {
					if (hero.getX() < map.X() - 1) {
						hero.setDirX(1);
						hero.setDirY(0);
						//hero.move(hero.getX() + 1, hero.getY());
						//moveHero(hero.getX(), hero.getY(),hero.getX()+1, hero.getY(),10);
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
				//world.add(createBlock2(i % map.X(), i / map.X(), top, right, bottom, left, true));
			} else {
				world.add(createBlock2(i % map.X(), i / map.X(), top, right, bottom, left, false));
			}
		}
	}
	
	public GCompound createBlock(int x,int y, boolean top, boolean right, boolean bottom, boolean left, boolean border){
		GCompound block = new GCompound();
		
		//BORDER
		if(border){
			GLine border_01 = new GLine(x*World.BLOCK_SIZE, y*World.BLOCK_SIZE, x*World.BLOCK_SIZE + World.BLOCK_SIZE, y*World.BLOCK_SIZE);
			border_01.setColor(Color.RED);
			block.add(border_01);
			GLine border_02 = new GLine(x*World.BLOCK_SIZE + World.BLOCK_SIZE, y*World.BLOCK_SIZE, x*World.BLOCK_SIZE + World.BLOCK_SIZE, y*World.BLOCK_SIZE + World.BLOCK_SIZE);
			border_02.setColor(Color.RED);
			block.add(border_02);
			GLine border_03 = new GLine(x*World.BLOCK_SIZE + World.BLOCK_SIZE, y*World.BLOCK_SIZE + World.BLOCK_SIZE, x*World.BLOCK_SIZE, y*World.BLOCK_SIZE + World.BLOCK_SIZE);
			border_03.setColor(Color.RED);
			block.add(border_03);
			GLine border_04 = new GLine(x*World.BLOCK_SIZE, y*World.BLOCK_SIZE + World.BLOCK_SIZE, x*World.BLOCK_SIZE, y*World.BLOCK_SIZE);
			border_04.setColor(Color.RED);
			block.add(border_04);
		}
		
		if(top){
			GLine line_03 = new GLine(x*World.BLOCK_SIZE + World.BLOCK_SIZE/3, y*World.BLOCK_SIZE, x*World.BLOCK_SIZE + World.BLOCK_SIZE/3, y*World.BLOCK_SIZE + World.BLOCK_SIZE/3);
			block.add(line_03);
			GLine line_04 = new GLine(x*World.BLOCK_SIZE + World.BLOCK_SIZE/3*2, y*World.BLOCK_SIZE, x*World.BLOCK_SIZE + World.BLOCK_SIZE/3*2, y*World.BLOCK_SIZE + World.BLOCK_SIZE/3);
			block.add(line_04);
		}
		else {
			GLine line_09 = new GLine(x*World.BLOCK_SIZE + World.BLOCK_SIZE/3, y*World.BLOCK_SIZE + World.BLOCK_SIZE/3, x*World.BLOCK_SIZE + World.BLOCK_SIZE/3*2, y*World.BLOCK_SIZE + World.BLOCK_SIZE/3);
			block.add(line_09);
		}
		if(right){
			GLine line_05 = new GLine(x*World.BLOCK_SIZE + World.BLOCK_SIZE/3*2, y*World.BLOCK_SIZE + World.BLOCK_SIZE/3, x*World.BLOCK_SIZE + World.BLOCK_SIZE, y*World.BLOCK_SIZE + World.BLOCK_SIZE/3);
			block.add(line_05);
			GLine line_06 = new GLine(x*World.BLOCK_SIZE + World.BLOCK_SIZE/3*2, y*World.BLOCK_SIZE + World.BLOCK_SIZE/3*2, x*World.BLOCK_SIZE + World.BLOCK_SIZE, y*World.BLOCK_SIZE + World.BLOCK_SIZE/3*2);
			block.add(line_06);
		}
		else {
			GLine line_12 = new GLine(x*World.BLOCK_SIZE + World.BLOCK_SIZE/3*2, y*World.BLOCK_SIZE + World.BLOCK_SIZE/3, x*World.BLOCK_SIZE + World.BLOCK_SIZE/3*2, y*World.BLOCK_SIZE + World.BLOCK_SIZE/3*2);
			block.add(line_12);
		}
		
		if(bottom){
			GLine line_07 = new GLine(x*World.BLOCK_SIZE + World.BLOCK_SIZE/3, y*World.BLOCK_SIZE + World.BLOCK_SIZE/3*2, x*World.BLOCK_SIZE + World.BLOCK_SIZE/3, y*World.BLOCK_SIZE + World.BLOCK_SIZE);
			block.add(line_07);
			GLine line_08 = new GLine(x*World.BLOCK_SIZE + World.BLOCK_SIZE/3*2, y*World.BLOCK_SIZE + World.BLOCK_SIZE/3*2, x*World.BLOCK_SIZE + World.BLOCK_SIZE/3*2, y*World.BLOCK_SIZE + World.BLOCK_SIZE);
			block.add(line_08);
		}
		else {
			GLine line_10 = new GLine(x*World.BLOCK_SIZE + World.BLOCK_SIZE/3, y*World.BLOCK_SIZE + World.BLOCK_SIZE/3*2, x*World.BLOCK_SIZE + World.BLOCK_SIZE/3*2, y*World.BLOCK_SIZE + World.BLOCK_SIZE/3*2);
			block.add(line_10);
		}
		
		if(left){
			GLine line_01 = new GLine(x*World.BLOCK_SIZE, y*World.BLOCK_SIZE + World.BLOCK_SIZE/3, x*World.BLOCK_SIZE + World.BLOCK_SIZE/3, y*World.BLOCK_SIZE + World.BLOCK_SIZE/3);
			block.add(line_01);
			GLine line_02 = new GLine(x*World.BLOCK_SIZE, y*World.BLOCK_SIZE + World.BLOCK_SIZE/3*2, x*World.BLOCK_SIZE + World.BLOCK_SIZE/3, y*World.BLOCK_SIZE + World.BLOCK_SIZE/3*2);
			block.add(line_02);
		}
		else {
			GLine line_11 = new GLine(x*World.BLOCK_SIZE + World.BLOCK_SIZE/3, y*World.BLOCK_SIZE + World.BLOCK_SIZE/3, x*World.BLOCK_SIZE + World.BLOCK_SIZE/3, y*World.BLOCK_SIZE + World.BLOCK_SIZE/3*2);
			block.add(line_11);
		}

		return block;
	}
	public GCompound createBlock2(int x,int y, boolean top, boolean right, boolean bottom, boolean left, boolean border){
		GCompound block = new GCompound();
		if(border){
			GLine border_01 = new GLine(x*World.BLOCK_SIZE, y*World.BLOCK_SIZE, x*World.BLOCK_SIZE + World.BLOCK_SIZE, y*World.BLOCK_SIZE);
			border_01.setColor(border_color);
			block.add(border_01);
			GLine border_02 = new GLine(x*World.BLOCK_SIZE + World.BLOCK_SIZE, y*World.BLOCK_SIZE, x*World.BLOCK_SIZE + World.BLOCK_SIZE, y*World.BLOCK_SIZE + World.BLOCK_SIZE);
			border_02.setColor(border_color);
			block.add(border_02);
			GLine border_03 = new GLine(x*World.BLOCK_SIZE + World.BLOCK_SIZE, y*World.BLOCK_SIZE + World.BLOCK_SIZE, x*World.BLOCK_SIZE, y*World.BLOCK_SIZE + World.BLOCK_SIZE);
			border_03.setColor(border_color);
			block.add(border_03);
			GLine border_04 = new GLine(x*World.BLOCK_SIZE, y*World.BLOCK_SIZE + World.BLOCK_SIZE, x*World.BLOCK_SIZE, y*World.BLOCK_SIZE);
			border_04.setColor(border_color);
			block.add(border_04);
		}
		
		if(top){
		
		}
		else {
			GLine line_09 = new GLine(x*World.BLOCK_SIZE, y*World.BLOCK_SIZE, x*World.BLOCK_SIZE + World.BLOCK_SIZE, y*World.BLOCK_SIZE);
			line_09.setColor(line_color);
			block.add(line_09);
		}
		if(right){
			
		}
		else {
			GLine line_12 = new GLine(x*World.BLOCK_SIZE + World.BLOCK_SIZE, y*World.BLOCK_SIZE, x*World.BLOCK_SIZE + World.BLOCK_SIZE, y*World.BLOCK_SIZE + World.BLOCK_SIZE);
			line_12.setColor(line_color);
			block.add(line_12);
		}
		
		if(bottom){
			
		}
		else {
			GLine line_10 = new GLine(x*World.BLOCK_SIZE, y*World.BLOCK_SIZE + World.BLOCK_SIZE, x*World.BLOCK_SIZE + World.BLOCK_SIZE, y*World.BLOCK_SIZE + World.BLOCK_SIZE);
			line_10.setColor(line_color);
			block.add(line_10);
		}
		
		if(left){
			
		}
		else {
			GLine line_11 = new GLine(x*World.BLOCK_SIZE, y*World.BLOCK_SIZE, x*World.BLOCK_SIZE, y*World.BLOCK_SIZE + World.BLOCK_SIZE);
			line_11.setColor(line_color);
			block.add(line_11);
		}

		return block;
	}
	
}
