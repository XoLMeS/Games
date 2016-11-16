package VampusTheBeast.Solving;

import java.util.ArrayList;

import tools.Graph;

public class WorldState {

	Location hero;
	ArrayList<Location> beasts;
	ArrayList<Location> pits;
	ArrayList<Sense> senses;
	Graph map;
	
	public WorldState(Location hero, ArrayList<Location> beasts,
			ArrayList<Location> pits, ArrayList<Sense> senses, Graph map){
		this.hero = hero;
		this.beasts = beasts;
		this.pits = pits;
		this.senses = senses;
		this.map = map;
	}
	
	public Graph getMap(){
		return map;
	}
	
	public ArrayList<Location> getBeasts(){
		return beasts;
	}
	
	public ArrayList<Location> getPits(){
		return pits;
	}
	
}
