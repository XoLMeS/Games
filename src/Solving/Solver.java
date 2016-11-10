package Solving;

import tools.Graph;

public class Solver {
	public static WorldState ws;
	public static GoodAgent ga;
	public static BadAgent ba;

	public Solver(){
		ws = new WorldState();
		ga = new GoodAgent(null);
		ba = new BadAgent(null);
	}
	
	public Solver(Location goal, Location ga,Location ba, Graph g){
		ws = new WorldState(goal,ga,ba,g);
		Solver.ga = new GoodAgent(ga);
		Solver.ba = new BadAgent(ba);
	}
	
	public void GANextStep(){
		Solver.ws =  Solver.ga.nextStep();
	}
	
	public void BANextStep(){
		Solver.ws =  Solver.ba.nextStep();
	}
	
	public Location getGALoc(){
		return Solver.ws.getGA();
	}
	
	public Location getBALoc(){
		return Solver.ws.getBA();
	}
	
	public Location getGoalLoc(){
		return Solver.ws.getGaol();
	}
}
