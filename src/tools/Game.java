package tools;

import java.awt.Color;

public abstract class Game {

	public void play() {
	}

	public World getWorld(){
		return null;
	}
	
	public int getX(){
		return 0;
		
	}
	
	public int getY(){
		return 0;
	}
	
	public Color getBGColor(){
		return null;
	}

}
