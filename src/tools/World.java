package tools;

import java.awt.Event;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import acm.graphics.GCompound;

public abstract class World {

	public static int BLOCK_SIZE = 50;

	private void create() {
	}

	public GCompound getGWorld() {
		return null;
	}

	public Hero getHero() {
		return null;
	}
	
	public KeyListener keybinding(){
		return null;
	}
	
	public Graph getMap(){
		return null;
	}
	
	
}
