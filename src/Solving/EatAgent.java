package Solving;

public class EatAgent implements Action{

	@Override
	public WorldState perfom() {
		return Solver.ws;
		// TODO Auto-generated method stub
		
	}

	WorldState curr;
	Location curr_l;
	@Override
	public boolean aplicable() {
		if(curr.getBA().eq(curr.getGA())){
			return true;
		}
		return false;
	}

	@Override
	public Location getNextLoc() {
		// TODO Auto-generated method stub
		return curr_l;
	}
	
	 public EatAgent(WorldState w, Location l) {
	curr = w;
	curr_l = l;
	}

}
