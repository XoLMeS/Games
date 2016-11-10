package Solving;

public class MoveRight implements Action{
	
	private WorldState ws;
	@Override
	public WorldState perfom() {
		return ws;
	}

	@Override
	public boolean aplicable() {
		return ws.getGraph().isConnected(curr_l.getX()+curr_l.getY()*ws.getGraph().X(), nextLoc.getX()+nextLoc.getY()*ws.getGraph().X());
	}
	
	Location nextLoc;
	Location curr_l;
	public MoveRight(WorldState ws,Location l){
		this.ws = ws;
		curr_l = l;
		nextLoc = new Location(l.getX()+1,l.getY());
		//System.out.println("n r "+nextLoc);
	}
	
	public Location getNextLoc(){
		return nextLoc;
	}
	
	public String toString(){
		return "MoveRight: "+curr_l + " -> " + nextLoc;
	}

}
