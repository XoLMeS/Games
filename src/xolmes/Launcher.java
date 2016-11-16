package xolmes;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JTextField;

import PacMan.ThePacGame;
import VampusTheBeast.VampusGame;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import tools.Game;
import tools.Graph;

public class Launcher extends GraphicsProgram {

	private int HEIGHT = 0;
	private int WIDTH = 0;
	private HashMap<String, Graph> maps;
	private HashMap<String, tools.Game> games;
	private Game game;

	public void run() {
		initMaps();
		
		initGames();
	
	
		play("Vampus");

	}

	private void play(String game_name) {
		game = games.get(game_name);
		add(game.getWorld().getGWorld());
		this.getGCanvas().addKeyListener(game.getWorld().keybinding());
		WIDTH = game.getWorld().BLOCK_SIZE * game.getX();
		HEIGHT = game.getWorld().BLOCK_SIZE * game.getY();
		setBackground(game.getBGColor());
		resize(WIDTH, HEIGHT + 50);
		ArrayList<Object> buttons = game.getButtons();
		if (buttons != null) {
			for (Object o : buttons) {
				
				if (o.getClass().getName().equals(JButton.class.getName())) {
					JButton b = (JButton) o;
					add(b, EAST);
				}
				if (o.getClass().getName().equals(JTextField.class.getName())) {
					JTextField b = (JTextField) o;
					add(b, EAST);
				}
			}
		}
		game.play();
	}

	private void initGames() {
		games = new HashMap<String, tools.Game>();
		games.put("PacMan", new ThePacGame(maps.get("PacMan_map_01")));
		games.put("Vampus", new VampusGame());
	}

	private void initMaps() {
		maps = new HashMap<String, Graph>();
		maps.put("PacMan_map_01", new Graph("labirinth_test.txt"));
	}

	public void addG(GObject obj) {
		add(obj);
	}
}
