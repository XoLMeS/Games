package tools;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;

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
	
	public ArrayList<java.lang.Object> getButtons(){
		return null;
	}

}
