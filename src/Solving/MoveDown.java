package Solving;

public class MoveDown implements Action{

	private WorldState ws;
	@Override
	public WorldState perfom() {
		return ws;
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean aplicable() {
		return ws.getGraph().isConnected(curr_l.getX()+curr_l.getY()*ws.getGraph().X(), nextLoc.getX()+nextLoc.getY()*ws.getGraph().X());
	}
	
	Location nextLoc;
	Location curr_l;
	public MoveDown(WorldState ws,Location l){
		this.ws = ws;
		curr_l = l;
		nextLoc = new Location(l.getX(),l.getY()+1);
		//System.out.println("n d "+nextLoc);
	}
	
	public Location getNextLoc(){
		return nextLoc;
	}
	
	public String toString(){
		return "MoveDown: "+curr_l + " -> " + nextLoc;
	}
	

}
