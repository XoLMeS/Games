package Solving;

import java.util.ArrayList;

public interface Agent {

	
	public WorldState nextStep();


	ArrayList<Action> getActions(WorldState next);
	
	Tree<WorldState> getPosStates();
}
