package VampusTheBeast;

import java.awt.Color;

public class VampusGame extends tools.Game{

	private World world;
	private int def_x = 10;
	private int def_y = 10;
	
	public VampusGame(){
		world = new World(def_x,def_y);
	}
	
	public void play() {
		
	}

	public World getWorld(){
		return world;
	}
	
	public int getX(){
		return def_x;
	}
	
	public int getY(){
		return def_y;
	}
	
	public Color getBGColor(){
		return Color.BLACK;
	}
}
