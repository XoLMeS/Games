package VampusTheBeast;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextField;

import VampusTheBeast.Solving.Location;
import VampusTheBeast.Solving.Solver;

public class VampusGame extends tools.Game {

	private World world;
	private int def_x = 10;
	private int def_y = 10;
	private int menu_x = 2;
	private int delay = 300;
	Solver solver;
	private boolean loop = false;

	public VampusGame() {
		world = new World(def_x, def_y);
	}

	public void play() {

		{
			Location hero = new Location(world.getHero().getX(), world
					.getHero().getY());
			ArrayList<Location> beasts = new ArrayList<Location>();
			ArrayList<Location> pits = new ArrayList<Location>();
			for (tools.Object o : world.getObjescts()) {
				if (o.getClass().getName().equals("VampusTheBeast.Beast")) {
					beasts.add(new Location(o.getX(), o.getY()));
				}
				if (o.getClass().getName().equals("VampusTheBeast.Pit")) {
					pits.add(new Location(o.getX(), o.getY()));
				}
			}
			solver = new Solver(hero, beasts, pits, world.getMap());

		}

		while (true) {
			System.out.println("");
			if (!world.pause)
				if (loop) {
					System.out.println("");
					long time = System.currentTimeMillis();
					while (System.currentTimeMillis() - time <= delay) {
					}
					nextStep();
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

	private void nextStep() {
		Location dir_hero = solver.nextStep();

		world.perfomMoveAction(dir_hero);
		world.checkColls();
	}

	private void updateSolver() {
		Location hero = new Location(world.getHero().getX(), world.getHero()
				.getY());
		ArrayList<Location> beasts = new ArrayList<Location>();
		ArrayList<Location> pits = new ArrayList<Location>();
		for (tools.Object o : world.getObjescts()) {
			if (o.getClass().getName().equals("VampusTheBeast.Beast")) {
				beasts.add(new Location(o.getX(), o.getY()));
			}
			if (o.getClass().getName().equals("VampusTheBeast.Pit")) {
				pits.add(new Location(o.getX(), o.getY()));
			}
		}
		solver = new Solver(hero, beasts, pits, world.getMap());

	}

	public ArrayList<Object> getButtons() {
		ArrayList<Object> jObjects = world.getButtons();

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
				world.getGWorld().removeAll();
				int new_x = Integer.valueOf(x_field.getText());
				int new_y = Integer.valueOf(y_field.getText());
				int beasts_num = Integer.valueOf(beasts_num_field.getText());
				world.GenerateMap(new_x, new_y, beasts_num, new_x * new_y - 1,
						1, 0, new_y - 1);
				world.drawWorld();
				updateSolver();
			}
		});

		jObjects.add(generate);

		JButton nextStep = new JButton("Next Step");
		nextStep.setVisible(true);
		nextStep.setSize(100, 50);
		nextStep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				nextStep();
			}
		});

		jObjects.add(nextStep);

		JButton setLoop = new JButton("Loop");
		setLoop.setVisible(true);
		setLoop.setSize(100, 50);
		setLoop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loop = !loop;
			}
		});
		jObjects.add(setLoop);

		final JTextField delay_num = new JTextField();
		delay_num.setVisible(true);
		delay_num.setSize(100, 50);
		delay_num.setText("delay");
		jObjects.add(delay_num);

		JButton setDelay = new JButton("Set Delay");
		setDelay.setVisible(true);
		setDelay.setSize(100, 50);
		setDelay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				delay = Integer.valueOf(delay_num.getText());
			}
		});
		jObjects.add(setDelay);

		return world.getButtons();
	}
}
