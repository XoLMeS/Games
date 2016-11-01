package tools;

import java.util.Stack;

import tools.Graph;


public class BreadthFirstPaths {
	private boolean[] marked;
	private int[] edgeTo;
	private int[] distTo;
	private final int s;

	public BreadthFirstPaths(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        distTo = new int[G.V()];
        marked = new boolean[G.V()];
        bfs(G, s);
    }
	
	private void bfs(Graph G, int s) {
		LinkedQueue<Integer> q = new LinkedQueue<Integer>();
		q.enqueue(s);
		marked[s] = true;
		distTo[s] = 0;
		while (!q.isEmpty()) {
			int v = q.dequeue();
			for (int w : G.adj(v)) {
				if (!marked[w]) {
					q.enqueue(w);
					marked[w] = true;
					edgeTo[w] = v;
					distTo[w] = distTo[v] + 1;
				}
			}
		}
	}
	
	public boolean hasPathTo(int v) {
        return marked[v];
    }
	
	public int getMarkedV(){
		  int count = 0;
		  for(int i = 0; i < marked.length; i++){
		   if(marked[i] = true){
		    count++;
		   }
		  }
		  return count;
	}
    
    /**
     * ������� ���� �� s �� v; null ���� ����� ����
     */
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        LinkedQueue<Integer> path = new LinkedQueue<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.enqueue(x);
        path.enqueue(s);
        return path;
    }
}
