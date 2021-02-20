package com.kevin.datastructures.queue;

import com.kevin.datastructures.heap.BinaryHeap;
import com.kevin.datastructures.heap.Heap;

import java.util.Comparator;

public class PriorityQueue<E> {
	private Heap<E> heap;

	public PriorityQueue() {
		this(null);
	}

	public PriorityQueue(Comparator<E> comparator) {
		heap = new BinaryHeap<>(comparator);
	}

	public int size() {
		return heap.size();
	}

	public boolean isEmpty() {
		return heap.isEmpty();
	}
	
	public void clear() {
		heap.clear();
	}

	public void enQueue(E element) {
		heap.add(element);
	}

	public E deQueue() {
		return heap.remove();
	}

	public E front() {
		return heap.get();
	}
}
