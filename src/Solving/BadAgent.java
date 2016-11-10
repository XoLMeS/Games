package Solving;

import java.util.ArrayList;

import Solving.Tree.Node;

public class BadAgent implements Agent{

	Tree<WorldState> pos_states02;
	ArrayList<Action> pos_actions;
	ArrayList<Action> pos_enemy_actions;
	private Location curr_loc;
	
	public BadAgent(Location l){
		curr_loc = l;
		pos_actions = new ArrayList<Action>();
		pos_enemy_actions = new ArrayList<Action>();
		pos_states02 = new Tree<WorldState>(Solver.ws);
	}
	
	public WorldState nextStep(){
		
		double priority = 100;
		generateAllPosStates();
		WorldState next = null;
		double best_worth = -999999999;

		
		for(Node<WorldState> n:pos_states02.getChildren()){
			for(Node<WorldState> c:n.getChildren()){
				double worth = priority / n.getData().getPathFromGaToBa()
						- n.getData().getPathFromGaToGoal() * priority / 2;
				if(n.canEatAgent){
					worth = 999999;
				}
				if (worth > best_worth) {
					best_worth = worth;
					next = n.getData();
				}
			}
		}
	
		curr_loc = next.getBA();
		return next;
		
	}
	private void generateAllPosStates(){
		pos_states02 = new Tree<WorldState>(Solver.ws);
		getActions(Solver.ws);
		ArrayList<WorldState> pos_states01 = new ArrayList<WorldState>();
		for (Action a : pos_actions) {
			if (a.aplicable()) {
				pos_states02.add(new Node(a.perfom().updateBALoc(a.getNextLoc())));
			}
		}
		
		for(Node<WorldState> ws:pos_states02.getChildren()){
			Action eat = new EatAgent(ws.getData(), ws.getData().getBA());

			if (eat.aplicable()) {
				ws.canEatAgent = true;
			}
		}
		
		
		for(Node<WorldState> n:pos_states02.getChildren()){
			ArrayList<Action> pos_actions01 = Solver.ga.getActions(((WorldState)n.getData()));
			for (Action a : pos_actions01) {
				if (a.aplicable()){
					n.addChild(new Node(a.perfom().updateGALoc(a.getNextLoc())));
			
				}
			}
		}
	}

	@Override
	public ArrayList<Action> getActions(WorldState next) {
		pos_actions.removeAll(pos_actions);
		pos_actions.add(new MoveDown(next,curr_loc));
		pos_actions.add(new MoveLeft(next,curr_loc));
		pos_actions.add(new MoveRight(next,curr_loc));
		pos_actions.add(new MoveUp(next,curr_loc));
		pos_actions.add(new EatAgent(next,curr_loc));
		return pos_actions;
	}

	@Override
	public Tree<WorldState> getPosStates() {
		generateAllPosStates();
		return pos_states02;
	}
}
