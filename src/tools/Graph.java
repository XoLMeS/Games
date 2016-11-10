package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Graph {

	private int X;
	private int Y;
	private Bag<Integer>[] adj;

	public Graph(String fileName) {
		Scanner sc = null;
		try {
			sc = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.X = sc.nextInt();
		this.Y = sc.nextInt();

		adj = (Bag<Integer>[]) new Bag[(X * Y)];

		for (int v = 0; v < X * Y; v++) {
			adj[v] = new Bag<Integer>();
		}

		while (sc.hasNextInt()) {
			int v1 = sc.nextInt();
			int v2 = sc.nextInt();
			addEdge(v1, v2);
		}
	}

	public Graph(int x, int y) {
		this.X = x;
		this.Y = y;
		adj = (Bag<Integer>[]) new Bag[(X * Y)];
		for (int v = 0; v < X * Y; v++) {
			adj[v] = new Bag<Integer>();
		}
	}

	public void addEdge(int v, int w) {
		adj[v].add(w);
		adj[w].add(v);
	}

	public void addEdge2(int v, int w) {
		adj[v].add(w);
	}

	public int degree(int v) {
		int degree = 0;
		for (int w : adj(v))
			degree++;
		return degree;
	}
	
	public boolean isConnected(int v, int w){
		for(Integer i: adj[v]){
			if(i==w){
				return true;
			}
		}
		return false;
	}

	public Iterable<Integer> adj(int v) {
		return adj[v];
	}

	public int X() {
		return X;
	}

	public int Y() {
		return Y;
	}

	public int V() {
		return X * Y;
	}

}