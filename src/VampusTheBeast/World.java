package VampusTheBeast;

import java.awt.Color;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JTextField;

import tools.Action;
import tools.Graph;
import VampusTheBeast.Solving.Location;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLine;
import acm.graphics.GRect;

public class World extends tools.World {

	private GCompound world;
	private GCompound fog;
	private ArrayList<GImage> fog_of_war;
	private Graph map;
	private Hero hero;
	private Color line_color = Color.BLUE;
	private Color border_color = Color.RED;
	private ArrayList<tools.Object> objects;
	private int x;
	private int y;
	private int menu_x = 5;
	private GCompound panel;
	private ArrayList<Object> jObjects;
	public boolean pause = false;
	public World(int x, int y) {
		this.x = x;
		this.y = y;
		BLOCK_SIZE = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()- 220) / y ;
		create();
	}

	private void create() {
		world = new GCompound();
		fog = new GCompound();
		fog_of_war = new ArrayList<GImage>();
		GenerateMap(x, y, 1, x * y - 1, 1, 0, y - 1);
		
		drawWorld();
		addMenu();
	}
	
	public ArrayList<tools.Object> getObjescts(){
		return objects;
	}

	private void addMenu() {
		// Add buttons and etc...
		panel = new GCompound();

		world.add(panel, x * World.BLOCK_SIZE + 50, 10);

		// ADD BackGround
		GRect bg = new GRect(menu_x * World.BLOCK_SIZE - 100, y * World.BLOCK_SIZE - 20);
		bg.setFilled(true);
		bg.setFillColor(Color.GRAY);
		bg.setColor(border_color);
		// panel.add(bg);

		jObjects = new ArrayList<Object>();

		final JTextField x_field = new JTextField();
		x_field.setVisible(true);
		x_field.setSize(100, 50);
		x_field.setText("X");
		jObjects.add(x_field);

		final JTextField y_field = new JTextField();
		y_field.setVisible(true);
		y_field.setSize(100, 50);
		y_field.setText("Y");
		jObjects.add(y_field);

		final JTextField beasts_num_field = new JTextField();
		beasts_num_field.setVisible(true);
		beasts_num_field.setSize(100, 50);
		beasts_num_field.setText("beasts_num");
		jObjects.add(beasts_num_field);

		// Add button GENERATE
		JButton generate = new JButton("Generate");
		generate.setVisible(true);
		generate.setSize(100, 50);
		generate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				world.removeAll();
				int new_x = Integer.valueOf(x_field.getText());
				int new_y = Integer.valueOf(y_field.getText());
				int beasts_num = Integer.valueOf(beasts_num_field.getText());
				GenerateMap(new_x, new_y, beasts_num, new_x * new_y - 1, 1, 0, new_y - 1);
				drawWorld();

			}
		});
		jObjects.add(generate);

	}

	public ArrayList<Object> getButtons() {
		System.out.println("#World. Buttons sent.");
		return jObjects;
	}

	private void GenerateMap(int x, int y, int vampus_num, int max_pit_num, int gold_num, int hero_x, int hero_y) {
		this.x = x;
		this.y = y;
		BLOCK_SIZE = (int) ((Toolkit.getDefaultToolkit().getScreenSize().height - 150) / y);

		fog.removeAll();
		fog_of_war.removeAll(fog_of_war);

		for (int i = 0; i < this.x; i++) {
			for (int j = 0; j < this.y; j++) {
				GImage fog_texture = new GImage("fog.jpg", j * BLOCK_SIZE, i * BLOCK_SIZE);
				fog_texture.scale(((double) World.BLOCK_SIZE) / 790, ((double) World.BLOCK_SIZE) / 600);
				fog_of_war.add(fog_texture);
				fog.add(fog_texture);

			}
		}

		world.add(fog);
		map = new Graph(x, y);
		hero = new Hero(hero_x, hero_y);
		world.add(hero.getGHero());
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
		generateObject(Beast.class.getName(), vampus_num, new Action() {

			@Override
			public void perfom() {
				// TODO Auto-generated method stub

			}
		});
		generateObject(Gold.class.getName(), gold_num, new Action() {

			@Override
			public void perfom() {
				// TODO Auto-generated method stub

			}
		});
		Random r = new Random();
		for(int i= 0; i < map.X(); i++){
			for(int j = 0; j < map.Y(); j++){
				if(i!=hero.getDirY() && j!=hero.getX()){
				int rand = r.nextInt(6);
				if(rand == 0){
					objects.add(new Pit(j,i,new Action() {
						
						@Override
						public void perfom() {
							// TODO Auto-generated method stub
							
						}
					}));
				}
			}
			}
		}
		for (tools.Object o : objects) {
			world.add(o.getGObject());
			o.getGObject().sendToBack();
		}
		fog.sendForward();
		fog.remove(fog_of_war.get(map.Y() * map.X() - map.X()));
	}

	private void loadWorld(File file) {

	}

	private void generateObject(String class_name, int num, Action a) {
		Random r = new Random();
		for (int i = 0; i < num; i++) {
			int vam_x;
			int vam_y;
			while (true) {
				vam_x = r.nextInt(map.X());
				vam_y = r.nextInt(map.Y());
				boolean found = false;
				for (tools.Object o : objects) {
					if (o.getClass().getName().equals(class_name)) {
						if (vam_x == o.getX() && vam_y == o.getY()) {
							found = true;
						}
					}
				}
				if (vam_x == hero.getX() && vam_y == hero.getY()) {
					found = true;
				}
				if (!found) {
					if (class_name.equals(Beast.class.getName())) {
						objects.add(new Beast(vam_x, vam_y, a));
					}
					if (class_name.equals(Gold.class.getName())) {
						objects.add(new Gold(vam_x, vam_y, a));
					}
					if (class_name.equals(Pit.class.getName())) {
						objects.add(new Pit(vam_x, vam_y, a));
					}
					break;
				}
			}
		}
	}

	public GCompound getGWorld() {
		return world;
	}

	public Hero getHero() {
		return hero;
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
					}
				}
				if (e.getKeyChar() == 'a') {
					if (hero.getX() > 0) {
						hero.setDirX(-1);
						hero.setDirY(0);
					}
				}
				if (e.getKeyChar() == 's') {
					if (hero.getY() < map.Y() - 1) {
						hero.setDirX(0);
						hero.setDirY(1);
					}
				}
				if (e.getKeyChar() == 'd') {
					if (hero.getX() < map.X() - 1) {
						hero.setDirX(1);
						hero.setDirY(0);
					}
				}
				
				if (e.getKeyChar() == 32) {
					pause = !pause;
				}

				if (hero.getX() + hero.getDirX() >= 0 && hero.getX() + hero.getDirX() < map.X()
						&& hero.getY() + hero.getDirY() >= 0 && hero.getY() + hero.getDirY() < map.Y()) {
					hero.move(0, 0);
					fog.remove(fog_of_war.get(hero.getY() * map.X() + hero.getX()));
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		};
		return kl;
	}

	public void perfomMoveAction(Location l){
		
		hero.setDirX(l.getX()-hero.getX());
		hero.setDirY(l.getY()-hero.getY());
		
			hero.move(0, 0);
			fog.remove(fog_of_war.get(hero.getY() * map.X() + hero.getX()));
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
				world.add(createBlock3(i % map.X(), i / map.X(), top, right, bottom, left, true));
			}
		}
	}

	public GCompound createBlock3(int x, int y, boolean top, boolean right, boolean bottom, boolean left,
			boolean border) {
		GCompound block = new GCompound();
		double k2 = 0.2;
		// BORDER
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

		// TOP
		if (!top) {
			GLine line_09 = new GLine(x * World.BLOCK_SIZE, y * World.BLOCK_SIZE,
					x * World.BLOCK_SIZE + World.BLOCK_SIZE, y * World.BLOCK_SIZE);
			line_09.setColor(line_color);
			block.add(line_09);
		}

		GLine line_03 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE * k2, y * World.BLOCK_SIZE,
				x * World.BLOCK_SIZE + World.BLOCK_SIZE * k2, y * World.BLOCK_SIZE + World.BLOCK_SIZE * k2);
		line_03.setColor(line_color);
		block.add(line_03);
		GLine line_04 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE - World.BLOCK_SIZE * k2, y * World.BLOCK_SIZE,
				x * World.BLOCK_SIZE + World.BLOCK_SIZE - World.BLOCK_SIZE * k2,
				y * World.BLOCK_SIZE + World.BLOCK_SIZE * k2);
		line_04.setColor(line_color);
		block.add(line_04);

		// RIGHT
		if (!right) {
			GLine line_12 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE, y * World.BLOCK_SIZE,
					x * World.BLOCK_SIZE + World.BLOCK_SIZE, y * World.BLOCK_SIZE + World.BLOCK_SIZE);
			line_12.setColor(line_color);
			block.add(line_12);
		}
		GLine line_05 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE - World.BLOCK_SIZE * k2,
				y * World.BLOCK_SIZE + World.BLOCK_SIZE * k2, x * World.BLOCK_SIZE + World.BLOCK_SIZE,
				y * World.BLOCK_SIZE + World.BLOCK_SIZE * k2);
		line_05.setColor(line_color);
		block.add(line_05);
		GLine line_06 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE - World.BLOCK_SIZE * k2,
				y * World.BLOCK_SIZE + World.BLOCK_SIZE - World.BLOCK_SIZE * k2,
				x * World.BLOCK_SIZE + World.BLOCK_SIZE,
				y * World.BLOCK_SIZE + World.BLOCK_SIZE - World.BLOCK_SIZE * k2);
		line_06.setColor(line_color);
		block.add(line_06);

		// BOTTOM
		if (!bottom) {
			GLine line_10 = new GLine(x * World.BLOCK_SIZE, y * World.BLOCK_SIZE + World.BLOCK_SIZE,
					x * World.BLOCK_SIZE + World.BLOCK_SIZE, y * World.BLOCK_SIZE + World.BLOCK_SIZE);
			line_10.setColor(line_color);
			block.add(line_10);
		}
		GLine line_07 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE * k2,
				y * World.BLOCK_SIZE + World.BLOCK_SIZE - World.BLOCK_SIZE * k2,
				x * World.BLOCK_SIZE + World.BLOCK_SIZE * k2, y * World.BLOCK_SIZE + World.BLOCK_SIZE);
		line_07.setColor(line_color);
		block.add(line_07);
		GLine line_08 = new GLine(x * World.BLOCK_SIZE + World.BLOCK_SIZE - World.BLOCK_SIZE * k2,
				y * World.BLOCK_SIZE + World.BLOCK_SIZE - World.BLOCK_SIZE * k2,
				x * World.BLOCK_SIZE + World.BLOCK_SIZE - World.BLOCK_SIZE * k2,
				y * World.BLOCK_SIZE + World.BLOCK_SIZE);
		line_08.setColor(line_color);
		block.add(line_08);

		// LEFT
		if (!left) {
			GLine line_11 = new GLine(x * World.BLOCK_SIZE, y * World.BLOCK_SIZE, x * World.BLOCK_SIZE,
					y * World.BLOCK_SIZE + World.BLOCK_SIZE);
			line_11.setColor(line_color);
			block.add(line_11);
		}
		GLine line_01 = new GLine(x * World.BLOCK_SIZE, y * World.BLOCK_SIZE + World.BLOCK_SIZE * k2,
				x * World.BLOCK_SIZE + World.BLOCK_SIZE * k2, y * World.BLOCK_SIZE + World.BLOCK_SIZE * k2);
		line_01.setColor(line_color);
		block.add(line_01);
		GLine line_02 = new GLine(x * World.BLOCK_SIZE, y * World.BLOCK_SIZE + World.BLOCK_SIZE - World.BLOCK_SIZE * k2,
				x * World.BLOCK_SIZE + World.BLOCK_SIZE * k2,
				y * World.BLOCK_SIZE + World.BLOCK_SIZE - World.BLOCK_SIZE * k2);
		line_02.setColor(line_color);
		block.add(line_02);
		return block;
	}
}
