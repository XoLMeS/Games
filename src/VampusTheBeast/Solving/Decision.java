package VampusTheBeast.Solving;

import java.util.ArrayList;
import java.util.HashMap;

public class Decision {

	HashMap<Integer, ArrayList<Predicats>> dis;
	int pos;
	public Decision(int pos){
		this.pos = pos;
		dis = new HashMap<Integer, ArrayList<Predicats>>();
		for(int i = 0; i < Solver.map.V(); i++){
			dis.put(i, new ArrayList<Predicats>());
		}
	}
	
	public void remove(int pos, Predicats p){
		dis.get(pos).remove(p);
	}
	
	public ArrayList<Predicats> get(int pos){
		return dis.get(pos);
	}
	
	public void add(int pos, Predicats p){
		dis.get(pos).add(p);
	}
	
	public int getPos(){
		return pos;
	}
}
