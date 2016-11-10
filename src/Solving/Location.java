package Solving;

public class Location {
	private int x;
	private int y;
	
	public Location(){
		
	}
	
	public  Location(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public String toString(){
		return "x: " + x + " y: " + y;
	}
	
	public boolean eq(Location l){
		return (this.x == l.getX() && this.y == l.getY());
	}
}
