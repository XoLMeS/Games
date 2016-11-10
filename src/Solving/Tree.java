package Solving;

import java.util.ArrayList;
import java.util.List;

public class Tree<T> {
	private Node<T> root;

	public Tree(T rootData) {
		root = new Node<T>(rootData);
		root.data = rootData;
		root.children = new ArrayList<Node<T>>();
	}

	public static class Node<T> {

		public Node(T rootData) {
			data = rootData;
			children = new ArrayList<Node<T>>();

		}

		public boolean canEatAgent = false;
		public boolean canEatGoal = false;
		private T data;
		private Node<T> parent;
		private List<Node<T>> children;

		public T getData() {
			return data;
		}

		public Node<T> getParent() {
			return parent;
		}

		public void addChild(Node c) {
			Node toAdd = c;
			toAdd.parent = this;
			children.add(toAdd);
		}

		public List<Node<T>> getChildren() {
			return children;
		}

	}

	public void add(Node n) {
		root.children.add(n);
	}

	public List<Node<T>> getChildren() {
		return root.children;
	}
}
