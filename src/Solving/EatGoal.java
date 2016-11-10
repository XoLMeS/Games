package Solving;

public class EatGoal implements Action{

	@Override
	public WorldState perfom() {
		return Solver.ws;
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean aplicable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Location getNextLoc() {
		// TODO Auto-generated method stub
		return null;
	}

}
