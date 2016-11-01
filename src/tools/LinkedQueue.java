package tools;

import java.util.Iterator;

public class LinkedQueue<T> implements Iterable<T> {

	private Node head, last;
	private int count;

	private class Node {
		T item;
		Node next;
	}

	public void enqueue(T item) {
		Node oldLast = last;
		last = new Node();
		last.item = item;
		last.next = null;
		count++;
		if (isEmpty())
			head = last;
		else
			oldLast.next = last;
	}

	public T dequeue() {
		T item = head.item;
		head = head.next;
		count--;
		if (isEmpty())
			last = null;
		return item;
	}

	public boolean isEmpty() {
		return head == null;
	}

	public int size() {
		return count;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new ReverseArrayIterator();
	}

	private class ReverseArrayIterator implements Iterator<T> {

		private Node current = head;
		@Override
		public boolean hasNext() {
			return current!=null;
		}

		@Override
		public T next() {
			T item = current.item;
			current = current.next;
			return item;
		}

		@Override
		public void remove() {
						
		}
	}
}
