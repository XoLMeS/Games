package Solving;

public interface Action {

	
	public WorldState perfom();
	public boolean aplicable();
	public Location getNextLoc();
	
}
