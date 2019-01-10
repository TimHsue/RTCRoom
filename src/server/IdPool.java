package server;

import java.util.Stack;

public class IdPool {
	private int count;
	
	Stack<Integer> delPool;
	
	public IdPool() {
		count = 0;
		delPool = new Stack<Integer>();
	}
	
	public IdPool(int start) {
		count = start;
		delPool = new Stack<Integer>();
	}
	
	public void delId(int id) {
		delPool.push(count);
	}
	
	public int newId() {
		if (delPool.isEmpty()) {
			return ++count;
		} else {
			int tmp = delPool.peek();
			delPool.pop();
			return tmp;
		}
	}
}
