package Snake;

import java.awt.Color;
import java.util.ArrayList;

import tools.Action;
import tools.BreadthFirstPaths;
import tools.Graph;
import tools.LinkedQueue;

public class TheSnake extends tools.Game {

	private World world;
	private int x;
	private int y;
	private Color BGColor = Color.BLACK;
	private tools.BreadthFirstPaths bfs;
	private boolean botIsActive = true;
	
	public TheSnake(Graph g) {
		this.x = g.X();
		this.y = g.Y();
		world = new World(x, y, g);
		botIsActive = false;
	}

	@Override
	public void play() {
		while (true) {
			//System.out.println("SNAKE PLAY LOOP");
			
			world.checkColls();
			
			
			if(botIsActive){
				bfs = new BreadthFirstPaths(world.getMap(), (int) ((int)world.getHero().getX()+world.getHero().getY()*world.getMap().X()));
				LinkedQueue<Integer> arr = (LinkedQueue<Integer>) bfs.pathTo(world.getNerestDot());
			        ArrayList<Integer> arr2 = new ArrayList<Integer>();
			        for (Integer i : arr) {
			        	if(i!=(int)world.getHero().getX()+(int)world.getHero().getY()*world.getMap().X()){
			        		arr2.add(i);
			        	}
			        }
			      
			        world.getHero().setDirX(((arr2.get(arr2.size()-1)%world.getMap().X()) - (int)world.getHero().getX()));
			        world.getHero().setDirY((arr2.get(arr2.size()-1) - ((int)world.getHero().getY()*world.getMap().X() + (int)world.getHero().getX()))/world.getMap().X());
			        
			}
			world.moveAll();
		}
	}
	
	public void changeBotState(boolean state){
		botIsActive = state;
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
