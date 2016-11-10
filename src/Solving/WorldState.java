package Solving;

import tools.BreadthFirstPaths;
import tools.Graph;
import tools.LinkedQueue;

public class WorldState{

	private Location goalLoc, goodAgentLoc, badAgentLoc;
	private Graph g;
	private BreadthFirstPaths bfs;
	
	public WorldState() {

	}

	public WorldState(Location goal, Location ga, Location ba, Graph g) {
		goalLoc = goal;
		goodAgentLoc = ga;
		badAgentLoc = ba;
		this.g = g;
	}

	public void update(Location goal, Location ga, Location ba) {
		if (goal != null)
			goalLoc = goal;
		if (ga != null)
			goodAgentLoc = ga;
		if (ba != null)
			badAgentLoc = ba;
	}

	public Location getGA() {
		return goodAgentLoc;
	}

	public Location getBA() {
		return badAgentLoc;
	}

	public Location getGaol() {
		return goalLoc;
	}

	public WorldState updateGALoc(Location l) {
		return new WorldState(this.goalLoc,l,badAgentLoc,this.g);
	}

	public WorldState updateBALoc(Location l) {
		return new WorldState(this.goalLoc,goodAgentLoc,l,this.g);
	}
	
	public Graph getGraph(){
		return g;
	}
	public double getPathFromGaToGoal(){
		bfs = new BreadthFirstPaths(g, goodAgentLoc.getX()+goodAgentLoc.getY()*g.X());
		LinkedQueue<Integer> d = (LinkedQueue<Integer>) bfs.pathTo(goalLoc.getX()+goalLoc.getY()*g.X());
		return d.size();
	}
	
	public double getPathFromGaToBa(){
		bfs = new BreadthFirstPaths(g, goodAgentLoc.getX()+goodAgentLoc.getY()*g.X());
		LinkedQueue<Integer> d = (LinkedQueue<Integer>) bfs.pathTo(badAgentLoc.getX()+badAgentLoc.getY()*g.X());
		
		int result = 0;
		if(d!=null){
			result = d.size();
		}
		
		return result;
	}
	
}
