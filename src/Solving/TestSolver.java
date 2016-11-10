package Solving;

import tools.Graph;

public class TestSolver {

	
	@SuppressWarnings("static-access")
	public static void main(String[] args){
		Location ga,ba,goal;
		ga = new Location(0,0);
		ba = new Location(7,7);
		goal = new Location(0,18);
		Graph g = new Graph("labirinth_test.txt");
		Solver a = new Solver(goal,ga,ba,g);
		
		Solver.ws =  Solver.ga.nextStep();
		System.out.println(Solver.ws.getGA());
		System.out.println(Solver.ws.getBA());
		
		Solver.ws =  Solver.ga.nextStep();
		Solver.ws =  Solver.ba.nextStep();
		System.out.println(Solver.ws.getGA());
		System.out.println(Solver.ws.getBA());
		
		Solver.ws =  Solver.ba.nextStep();
		System.out.println(Solver.ws.getGA());
		System.out.println(Solver.ws.getBA());
		
		Solver.ws =  Solver.ba.nextStep();
		System.out.println(Solver.ws.getGA());
		System.out.println(Solver.ws.getBA());
		//Solver.ws =  Solver.ga.nextStep();
		//System.out.println(Solver.ws.getGA());
		//System.out.println(Solver.ws.getBA());
	}
}
