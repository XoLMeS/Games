package Snake;

import java.awt.Color;

import tools.Graph;

public class TheSnake extends tools.Game {

	private World world;
	private int x;
	private int y;
	private Color BGColor = Color.BLACK;

	public TheSnake(Graph g) {
		this.x = g.X();
		this.y = g.Y();
		world = new World(x, y, g);
	}

	@Override
	public void play() {
		while (true) {
			//System.out.println("SNAKE PLAY LOOP");
			if (frontIsClear()) {
				world.getHero().moveForward();
				//System.out.println("MOVED FORWARD");
			}
		}
	}

	private boolean frontIsClear() {
		int arg1 = world.getHero().getDirX();
		int arg2 = world.getHero().getDirY();
		int x = (int) world.getHero().getX();
		int y = (int) world.getHero().getY();
		if (arg1 != 0 && isConnected(x + y * world.getMap().X(), x + y * world.getMap().X() + arg1)) {
			return true;
		}
		if (arg2 != 0
				&& isConnected(x + y * world.getMap().X(), x + y * world.getMap().X() + arg2 * world.getMap().X())) {
			return true;
		}
		return false;
	}

	private boolean isConnected(int arg1, int arg2) {
		for (Integer i : world.getMap().adj(arg1)) {
			if (i == arg2) {
				return true;
			}
		}
		return false;
	}

	public World getWorld() {
		return world;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	public Color getBGColor(){
		return BGColor;
	}

}
