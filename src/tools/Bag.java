package tools;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class Bag <Item> implements Iterable<Item> {

	protected Item[] s;
	private int n = 0;
	

	public Bag() {
		s = (Item[]) new Object[10];
	}

	public boolean isEmpty() {
		return n == 0;
	}

	public int size() {
		return n;
	}
	
	
	
	public int getMin(){
		int min = 0;
		for(int i = 0; i < n; i++){
			if(min == 0){min =(Integer)s[i];}
			else 
			if(min>(Integer)s[i]){min = (Integer)s[i];}
			
		}
		return min;
	}
	
	public void add(Item item) {
		
		if(item==null){throw new NullPointerException();}
		if (n == s.length)
			resize(2 * s.length);
		s[n++] = item;
	}


	private void resize(int capacity) {
		Item[] copy = (Item[]) new Object[capacity];
		for (int i = 0; i < n; i++) {
			copy[i] = s[i];
		}
	
		s = copy;
	}

	public Iterator<Item> iterator() {
		return new BagIterator();
	}
	

	private class BagIterator implements Iterator<Item>{

		private int i=0;
		@Override
		public boolean hasNext() {
			return i<n;
		}

		@Override
		public Item next() {	
			return s[i++];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}

}

