package VampusTheBeast.Solving;

import java.util.ArrayList;

public class Sense {
	
	private ArrayList<Source> sources;
	public Sense(){
		sources = new ArrayList<Source>();
	}
	
	public void add(Source s){
		sources.add(s);
	}
	public ArrayList<Source> getSources(){
		return sources;
	}
}
