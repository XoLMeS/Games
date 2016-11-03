package VampusTheBeast;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import tools.Action;
import tools.Graph;
import acm.graphics.GCompound;
import acm.graphics.GLine;

public class World extends tools.World {

	private GCompound world;
	private Graph map;
	private Hero hero;
	private Color line_color = Color.BLUE;
	private Color border_color = Color.RED;
	private ArrayList<tools.Object> objects;
	private int x; 
	private int y;

	public World(int x, int y) {
		this.x = x;
		this.y = y;
		BLOCK_SIZE = (int) (Toolkit.getDefaultToolkit().getScreenSize()
				.getHeight() / y) - 20;
		create();
	}

	private void create() {
		GenerateMap(x, y,1,x*y-1,1,0,y-1);

		
		drawWorld();
		addMenu();
	}

	private void addMenu() {
		// Add buttons and etc...

	}

	private void GenerateMap(int x, int y, int vampus_num, int max_pit_num, int gold_num, int hero_x, int hero_y) {
		this.x = x;
		this.y = y;
		map = new Graph(x, y);
		world = new GCompound();
		hero = new Hero(hero_x,hero_y);
		objects = new ArrayList<tools.Object>();
		
		for (int i = 0; i < map.Y(); i++) {
			for (int j = 0; j < map.X(); j++) {
				int v = i * map.X() + j;

				// Left
				if (v % map.X() > 0) {
					map.addEdge(v, v - 1);
				}
				// Right
				if (v % map.X() < map.X() - 1) {
					map.addEdge(v, v + 1);
				}
				// Top
				if (v / map.X() > 0) {
					map.addEdge(v, v - map.X());
				}
				// Bottom
				if (v / map.X() < map.Y() - 1) {
					map.addEdge(v, v + map.X());
				}
			}
		}
		Random r = new Random();
		for(int i = 0; i < vampus_num; i++){
			int vam_x;
			int vam_y;
			while(true){
				vam_x = r.nextInt(map.X());
				vam_y = r.nextInt(map.Y());
				boolean found = false;
				for(tools.Object o: objects){
					if(vam_x == o.getX() && vam_y == o.getY()){
						found = true;
					}
				}
				if(vam_x == hero_x && vam_y == hero_y){
					found = true;
				}
				if(!found){
					objects.add(new Beast(vam_x, vam_y, new Action() {
						
						@Override
						public void perfom() {
							// TODO Auto-generated method stub
							
						}
					}));
					break;
				}
			}
		}
		for(tools.Object o: objects){
			System.out.println(o.getGObject());
			world.add(o.getGObject());
		}
	}

	private void loadWorld(File file) {

	}

	public GCompound getGWorld() {
		return world;
	}

	public Hero getHero() {
		return hero;
	}

	public KeyListener keybinding() {
		return null;
	}

	public Graph getMap() {
		return map;
	}

	public void checkColls() {

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
				world.add(createBlock3(i % map.X(), i / map.X(), top, right,
						bottom, left, false));
				System.out.println("s");
			}
		}
	}

	public GCompound createBlock3(int x, int y, boolean top, boolean right,
			boolean bottom, boolean left, boolean border) {
		GCompound block = new GCompound();
		double k = 0.4;
		double k2 = 0.2;
		// BORDER
		if (border) {
			GLine border_01 = new GLine(x * World.BLOCK_SIZE, y
					* World.BLOCK_SIZE,
					x * World.BLOCK_SIZE + World.BLOCK_SIZE, y
							* World.BLOCK_SIZE);
			border_01.setColor(border_color);
			block.add(border_01);
			GLine border_02 = new GLine(
					x * World.BLOCK_SIZE + World.BLOCK_SIZE, y
							* World.BLOCK_SIZE, x * World.BLOCK_SIZE
							+ World.BLOCK_SIZE, y * World.BLOCK_SIZE
							+ World.BLOCK_SIZE);
			border_02.setColor(border_color);
			block.add(border_02);
			GLine border_03 = new GLine(
					x * World.BLOCK_SIZE + World.BLOCK_SIZE, y
							* World.BLOCK_SIZE + World.BLOCK_SIZE, x
							* World.BLOCK_SIZE, y * World.BLOCK_SIZE
							+ World.BLOCK_SIZE);
			border_03.setColor(border_color);
			block.add(border_03);
			GLine border_04 = new GLine(x * World.BLOCK_SIZE, y
					* World.BLOCK_SIZE + World.BLOCK_SIZE,
					x * World.BLOCK_SIZE, y * World.BLOCK_SIZE);
			border_04.setColor(border_color);
			block.add(border_04);
		}

		// TOP
		if (!top) {
			GLine line_09 = new GLine(x * World.BLOCK_SIZE, y
					* World.BLOCK_SIZE,
					x * World.BLOCK_SIZE + World.BLOCK_SIZE, y
							* World.BLOCK_SIZE);
			line_09.setColor(line_color);
			block.add(line_09);
		}

		GLine line_03 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE * k2,
				y * World.BLOCK_SIZE, x * World.BLOCK_SIZE + World.BLOCK_SIZE
						* k2, y * World.BLOCK_SIZE + World.BLOCK_SIZE * k2);
		line_03.setColor(line_color);
		block.add(line_03);
		GLine line_04 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE
				- World.BLOCK_SIZE * k2, y * World.BLOCK_SIZE, x
				* World.BLOCK_SIZE + World.BLOCK_SIZE - World.BLOCK_SIZE * k2,
				y * World.BLOCK_SIZE + World.BLOCK_SIZE * k2);
		line_04.setColor(line_color);
		block.add(line_04);

		// RIGHT
		if (!right) {
			GLine line_12 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE,
					y * World.BLOCK_SIZE, x * World.BLOCK_SIZE
							+ World.BLOCK_SIZE, y * World.BLOCK_SIZE
							+ World.BLOCK_SIZE);
			line_12.setColor(line_color);
			block.add(line_12);
		}
		GLine line_05 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE
				- World.BLOCK_SIZE * k2, y * World.BLOCK_SIZE
				+ World.BLOCK_SIZE * k2, x * World.BLOCK_SIZE
				+ World.BLOCK_SIZE, y * World.BLOCK_SIZE + World.BLOCK_SIZE
				* k2);
		line_05.setColor(line_color);
		block.add(line_05);
		GLine line_06 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE
				- World.BLOCK_SIZE * k2, y * World.BLOCK_SIZE
				+ World.BLOCK_SIZE - World.BLOCK_SIZE * k2, x
				* World.BLOCK_SIZE + World.BLOCK_SIZE, y * World.BLOCK_SIZE
				+ World.BLOCK_SIZE - World.BLOCK_SIZE * k2);
		line_06.setColor(line_color);
		block.add(line_06);

		//BOTTOM
		if (!bottom) {
			GLine line_10 = new GLine(x * World.BLOCK_SIZE, y
					* World.BLOCK_SIZE + World.BLOCK_SIZE, x * World.BLOCK_SIZE
					+ World.BLOCK_SIZE, y * World.BLOCK_SIZE + World.BLOCK_SIZE);
			line_10.setColor(line_color);
			block.add(line_10);
		}
			GLine line_07 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE
					* k2, y * World.BLOCK_SIZE + World.BLOCK_SIZE
					- World.BLOCK_SIZE * k2, x * World.BLOCK_SIZE
					+ World.BLOCK_SIZE * k2, y * World.BLOCK_SIZE
					+ World.BLOCK_SIZE);
			line_07.setColor(line_color);
			block.add(line_07);
			GLine line_08 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE
					- World.BLOCK_SIZE * k2, y * World.BLOCK_SIZE
					+ World.BLOCK_SIZE - World.BLOCK_SIZE * k2, x
					* World.BLOCK_SIZE + World.BLOCK_SIZE - World.BLOCK_SIZE
					* k2, y * World.BLOCK_SIZE + World.BLOCK_SIZE);
			line_08.setColor(line_color);
			block.add(line_08);

			//LEFT
		if (!left) {
			GLine line_11 = new GLine(x * World.BLOCK_SIZE, y
					* World.BLOCK_SIZE, x * World.BLOCK_SIZE, y
					* World.BLOCK_SIZE + World.BLOCK_SIZE);
			line_11.setColor(line_color);
			block.add(line_11);
		}
			GLine line_01 = new GLine(x * World.BLOCK_SIZE, y
					* World.BLOCK_SIZE + World.BLOCK_SIZE * k2, x
					* World.BLOCK_SIZE + World.BLOCK_SIZE * k2, y
					* World.BLOCK_SIZE + World.BLOCK_SIZE * k2);
			line_01.setColor(line_color);
			block.add(line_01);
			GLine line_02 = new GLine(x * World.BLOCK_SIZE, y
					* World.BLOCK_SIZE + World.BLOCK_SIZE - World.BLOCK_SIZE
					* k2, x * World.BLOCK_SIZE + World.BLOCK_SIZE * k2, y
					* World.BLOCK_SIZE + World.BLOCK_SIZE - World.BLOCK_SIZE
					* k2);
			line_02.setColor(line_color);
			block.add(line_02);
		return block;
	}
}
