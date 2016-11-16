package VampusTheBeast.Solving;

import java.util.ArrayList;
import java.util.HashMap;

import tools.BreadthFirstPaths;
import tools.Graph;
import VampusTheBeast.Solving.Actions.Action;

public class Solver {
	public static Graph map;

	ArrayList<Action> actions;

	// Objects on map
	ArrayList<Location> beasts;
	ArrayList<Location> pits;

	// Zones near objects
	ArrayList<Sense> senses;

	// Needed to remember when and what hero decided
	ArrayList<Decision> decisions;

	// What hero knows about this world
	HashMap<Integer, ArrayList<Predicats>> knowledge;

	// Current X and Y
	Location hero;

	// Graph to know where hero can reach
	Graph discovered;

	// To find path in graph 'discovered'
	BreadthFirstPaths bfs;

	// Needed for not using bfs every step to know what neighbor rooms are
	// already discovered to connect them with just opened one
	ArrayList<Integer> connected_rooms;

	// Worth of predicats
	int mB = -4;
	int mP = -5;
	int nB = 4;
	int nP = 5;
	int clear = 10;
	int b = -10;
	int p = -15;

	// Some optimizetion. Bot can build his path once then he just pops from
	// array next step. It prevents using bfs in every call of nextStep()
	ArrayList<Integer> path;

	public Solver(Location hero, ArrayList<Location> beasts,
			ArrayList<Location> pits, Graph map) {
		knowledge = new HashMap<Integer, ArrayList<Predicats>>();
		decisions = new ArrayList<Decision>();
		path = new ArrayList<Integer>();
		connected_rooms = new ArrayList<Integer>();
		this.hero = hero;
		Solver.map = map;
		this.beasts = beasts;
		this.pits = pits;
		initSenses();
		int hero_pos = hero.getX() + hero.getY() * map.X();
		for (int i = 0; i < map.V(); i++) {
			knowledge.put(i, new ArrayList<Predicats>());
			if (i != hero_pos) {
				knowledge.get(i).add(Predicats.UNK);
			}
		}

		knowledge.get(hero.getX() + hero.getY() * map.X()).add(Predicats.Clear);

		discovered = new Graph(map.X(), map.Y());
	
		connected_rooms.add(hero_pos);
		if (hero.getX() > 1) {
			discovered.addEdge(hero_pos, hero_pos - 1);
			connected_rooms.add(hero_pos - 1);
		}
		if (hero.getX() < map.X() - 1) {
			discovered.addEdge(hero_pos, hero_pos + 1);
			connected_rooms.add(hero_pos + 1);
		}
		if (hero.getY() < map.Y() - 1) {
			discovered.addEdge(hero_pos, hero_pos + map.X());
			connected_rooms.add(hero_pos + map.X());
		}
		if (hero.getY() > 1) {
			discovered.addEdge(hero_pos, hero_pos - map.X());
			connected_rooms.add(hero_pos - map.X());
		}

		sense();
	}

	public Location nextStep() {
		Location toReturn;
		int best_worth = -999;
		int next_room = -1;
		for(Integer i:connected_rooms){
			System.out.println(i);
		}
		for (int i = 0; i < connected_rooms.size(); i++) {
		
			if (knowledge.get(connected_rooms.get(i)).contains(Predicats.UNK)) {
				int worth = 0;
				//System.out.println(connected_rooms.get(i));
				for (Predicats pred : knowledge.get(connected_rooms.get(i))) {
					switch (pred) {
					case mB:
						worth += mB;
						break;
					case nB:
						worth += nB;
						break;
					case B:
						worth += b;
						break;
					case mP:
						worth += mP;
						break;
					case nP:
						worth += nP;
						break;
					case P:
						worth += p;
						break;
					case Clear:
						worth += clear;
						break;
					case UNK:
						break;
					default:
						break;
					}
				}
				if (worth >= best_worth) {
					best_worth = worth;
					next_room = connected_rooms.get(i);
				}
				System.out.println("room " + connected_rooms.get(i) + " worth " + worth );
			}
			
		}
			if (path.size() == 0) {
			bfs = new BreadthFirstPaths(discovered, hero.getX() + hero.getY()
					* map.X());
			//System.out.println(next_room);
			tools.LinkedQueue<Integer> arr = (tools.LinkedQueue<Integer>) bfs
					.pathTo(next_room);
			for (Integer i : arr) {
				path.add(i);
			}
			
		} 
		System.out.println("Choosed " + next_room + " worth " + best_worth + " " + path.get(path.size()-1));
		if(path.get(path.size()-1)==hero.getX() + hero.getY() * map.X()){
			path.remove(path.size()-1);
		}
		toReturn = new Location(path.get(path.size()-1)%map.X(),path.get(path.size()-1)/map.X());
		hero.setX(toReturn.getX());
		hero.setY(toReturn.getY());
		connectNeighborRooms();
		//System.out.println("Moved to " + path.get(path.size()-1) + " " + toReturn);
		knowledge.get(path.get(path.size()-1)).remove(Predicats.UNK);
		path.remove(path.size()-1);
		sense();
		System.out.println("********************************");

		return toReturn;
	}
	
	private void connectNeighborRooms(){
		int hero_pos = hero.getX() + hero.getY() * map.X();
		if(!connected_rooms.contains(hero_pos)){
			connected_rooms.add(hero_pos);
		}
		if (hero.getX() > 1) {
			discovered.addEdge(hero_pos, hero_pos - 1);
			if(!connected_rooms.contains(hero_pos - 1)){
				connected_rooms.add(hero_pos - 1);
			}
		}
		if (hero.getX() < map.X() - 1) {
			//System.out.println("Connected" + hero_pos + " herox " +hero.getX() );
			discovered.addEdge(hero_pos, hero_pos + 1);
			if(!connected_rooms.contains(hero_pos + 1)){
				connected_rooms.add(hero_pos + 1);
			}
		}
		if (hero.getY() < map.Y() - 1 ) {
			discovered.addEdge(hero_pos, hero_pos + map.X());
			if(!connected_rooms.contains(hero_pos + map.X())){
				connected_rooms.add(hero_pos + map.X());
			}
		}
		if (hero.getY() > 1) {
			discovered.addEdge(hero_pos, hero_pos - map.X());
			if(!connected_rooms.contains(hero_pos - map.X())){
				connected_rooms.add(hero_pos - map.X());
			}
		}
	}
	private void sense() {
		Decision d = new Decision(hero.getX() + hero.getY() * map.X());
		for (Source s : senses.get(hero.getX() + hero.getY() * map.X())
				.getSources()) {
			switch (s) {
			case Beast:
				if (placePred_mB(hero.getX() + hero.getY() * map.X() + 1)) {
					d.add(hero.getX() + hero.getY() * map.X() + 1, Predicats.mB);
				}
				if (placePred_mB(hero.getX() + hero.getY() * map.X() - 1)) {
					d.add(hero.getX() + hero.getY() * map.X() - 1, Predicats.mB);
				}
				if (placePred_mB(hero.getX() + hero.getY() * map.X() + map.X())) {
					d.add(hero.getX() + hero.getY() * map.X() + map.X(),
							Predicats.mB);
				}
				if (placePred_mB(hero.getX() + hero.getY() * map.X() - map.X())) {
					d.add(hero.getX() + hero.getY() * map.X() - map.X(),
							Predicats.mB);
				}
				break;
			case Pit:
				if (placePred_mP(hero.getX() + hero.getY() * map.X() + 1)) {
					d.add(hero.getX() + hero.getY() * map.X() + 1, Predicats.mP);
				}
				if (placePred_mP(hero.getX() + hero.getY() * map.X() - 1)) {
					d.add(hero.getX() + hero.getY() * map.X() - 1, Predicats.mP);
				}
				if (placePred_mP(hero.getX() + hero.getY() * map.X() + map.X())) {
					d.add(hero.getX() + hero.getY() * map.X() + map.X(),
							Predicats.mP);
				}
				if (placePred_mP(hero.getX() + hero.getY() * map.X() - map.X())) {
					d.add(hero.getX() + hero.getY() * map.X() - map.X(),
							Predicats.mP);
				}
				break;
			default:
				break;
			}
		}
		if(senses.get(hero.getX() + hero.getY() * map.X()).getSources().size()==0){
			if (placePred_Clear(hero.getX() + hero.getY() * map.X() + 1)) {
				d.add(hero.getX() + hero.getY() * map.X() + 1,
						Predicats.Clear);
			}
			if (placePred_Clear(hero.getX() + hero.getY() * map.X() - 1)) {
				d.add(hero.getX() + hero.getY() * map.X() - 1,
						Predicats.Clear);
			}
			if (placePred_Clear(hero.getX() + hero.getY() * map.X()
					+ map.X())) {
				d.add(hero.getX() + hero.getY() * map.X() + map.X(),
						Predicats.Clear);
			}
			if (placePred_Clear(hero.getX() + hero.getY() * map.X()
					- map.X())) {
				d.add(hero.getX() + hero.getY() * map.X() - map.X(),
						Predicats.Clear);
			}
		}
		decisions.add(d);
	}

	private boolean placePred_mB(int pos) {
		if (pos > 0 && pos < map.V()) {
			if (!knowledge.get(pos).contains(Predicats.Clear)
					&& !knowledge.get(pos).contains(Predicats.B)
					&& !knowledge.get(pos).contains(Predicats.mB)
					&& !knowledge.get(pos).contains(Predicats.nB)) {
				knowledge.get(pos).add(Predicats.mB);
				return true;
			}
		}
		return false;
	}

	private boolean placePred_mP(int pos) {
		if (pos > 0 && pos < map.V()) {
			if (!knowledge.get(pos).contains(Predicats.Clear)
					&& !knowledge.get(pos).contains(Predicats.P)
					&& !knowledge.get(pos).contains(Predicats.mP)
					&& !knowledge.get(pos).contains(Predicats.nP)) {
				knowledge.get(pos).add(Predicats.mP);
				return true;
			}
		}
		return false;
	}

	private boolean placePred_Clear(int pos) {
		if (pos > 0 && pos < map.V()) {
			if (!knowledge.get(pos).contains(Predicats.Clear)) {
				if (knowledge.get(pos).contains(Predicats.P)) {
					knowledge.get(pos).remove(Predicats.P);
				}
				if (knowledge.get(pos).contains(Predicats.B)) {
					knowledge.get(pos).remove(Predicats.B);
				}
				if (knowledge.get(pos).contains(Predicats.mP)) {
					knowledge.get(pos).remove(Predicats.mP);
				}
				if (knowledge.get(pos).contains(Predicats.mB)) {
					knowledge.get(pos).remove(Predicats.mB);
				}
				knowledge.get(pos).add(Predicats.Clear);
				return true;
			}
		}
		return false;
	}

	private void initSenses() {
		senses = new ArrayList<Sense>();
		for (int i = 0; i < map.V(); i++) {
			senses.add(new Sense());
		}
		for (int i = 0; i < beasts.size(); i++) {
			int pos = beasts.get(i).getX() + beasts.get(i).getY() * map.X() + 1;
			if (pos > 0 && pos < map.V()) {
				senses.get(pos).add(Source.Beast);
			}

			pos = beasts.get(i).getX() + beasts.get(i).getY() * map.X() - 1;
			if (pos > 0 && pos < map.V()) {
				senses.get(pos).add(Source.Beast);
			}

			pos = beasts.get(i).getX() + beasts.get(i).getY() * map.X()
					+ map.X();
			if (pos > 0 && pos < map.V()) {
				senses.get(pos).add(Source.Beast);
			}

			pos = beasts.get(i).getX() + beasts.get(i).getY() * map.X()
					- map.X();
			if (pos > 0 && pos < map.V()) {
				senses.get(pos).add(Source.Beast);
			}
		}

		for (int i = 0; i < pits.size(); i++) {
			int pos = pits.get(i).getX() + pits.get(i).getY() * map.X() + 1;
			if (pos > 0 && pos < map.V()) {
				senses.get(pos).add(Source.Pit);
			}
			pos = pits.get(i).getX() + pits.get(i).getY() * map.X() - 1;
			if (pos > 0 && pos < map.V()) {
				senses.get(pos).add(Source.Pit);
			}
			pos = pits.get(i).getX() + pits.get(i).getY() * map.X() + map.X();
			if (pos > 0 && pos < map.V()) {
				senses.get(pos).add(Source.Pit);
			}
			pos = pits.get(i).getX() + pits.get(i).getY() * map.X() - map.X();
			if (pos > 0 && pos < map.V()) {
				senses.get(pos).add(Source.Pit);
			}
		}
	}
}
