package PacMan;

import java.awt.Color;
import java.util.ArrayList;

import Solving.Location;
import Solving.Solver;
import tools.Action;
import tools.BreadthFirstPaths;
import tools.Graph;
import tools.LinkedQueue;

public class ThePacGame extends tools.Game {

	private World world;
	private int x;
	private int y;
	private Color BGColor = Color.BLACK;
	private tools.BreadthFirstPaths bfs;
	private boolean botIsActive = false;
	private boolean agentsIsActive = true;
	private Solver solver;
	
	public ThePacGame(Graph g) {
		this.x = g.X();
		this.y = g.Y();
		world = new World(x, y, g);
		if(agentsIsActive){
			Location ga,ba,goal;
			ga = new Location(world.getHero().getX(),world.getHero().getY());
			ba = new Location(7,7);
			goal = new Location(16,0);
			solver = new Solver(goal,ga,ba,g);
		}
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
			Location l2 = null;
			if(agentsIsActive){
				solver.GANextStep();
				Location l = solver.getGALoc();
				world.getHero().setDirX(l.getX() - world.getHero().getX());
				world.getHero().setDirY(l.getY() - world.getHero().getY());
				
				
				solver.BANextStep();
				l2 = solver.getBALoc();
				//System.out.println(l + "   sdasd    " + l2);
				
			}
			if(agentsIsActive){
				world.moveAll(l2.getX()+l2.getY()*world.getMap().X(), agentsIsActive);

			}
			if(solver.getGALoc().eq(solver.getGoalLoc())){
				agentsIsActive = false;
			}
		}
	}
	
	public void changeBotState(boolean state){
		botIsActive = state;
	}

	
	
	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
	public Color getBGColor(){
		return BGColor;
	}

}
