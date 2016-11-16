package VampusTheBeast;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;

import VampusTheBeast.Solving.Location;
import VampusTheBeast.Solving.Solver;

public class VampusGame extends tools.Game {

	private World world;
	private int def_x = 10;
	private int def_y = 10;
	private int menu_x = 2;
	private int delay = 300;

	public VampusGame() {
		world = new World(def_x, def_y);
	}

	public void play() {
		Solver solver;
		{
			Location hero = new Location(world.getHero().getX(), world
					.getHero().getY());
			ArrayList<Location> beasts = new ArrayList<Location>();
			ArrayList<Location> pits = new ArrayList<Location>();
			for (tools.Object o : world.getObjescts()) {
				if (o.getClass().getName().equals("Beast")) {
					beasts.add(new Location(o.getX(), o.getY()));
				} else {
					pits.add(new Location(o.getX(), o.getY()));
				}
			}
			solver = new Solver(hero, beasts, pits, world.getMap());
		}
		while(true){
			if (!world.pause) {
				long time = System.currentTimeMillis();
				while (System.currentTimeMillis() - time <= delay) {
				}
				Location dir_hero = solver.nextStep();
				
				world.perfomMoveAction(dir_hero);
				
			}
		}

	}

	public World getWorld() {
		return world;
	}

	public int getX() {
		return def_x + menu_x;
	}

	public int getY() {
		return def_y;
	}

	public Color getBGColor() {
		return Color.BLACK;
	}

	public ArrayList<Object> getButtons() {
		return world.getButtons();
	}
}
