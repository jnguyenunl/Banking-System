package com.tbf;

import java.util.Comparator;

/**
 * My Linked List Class
 * @author James Nguyen and Thi Hoang
 *
 */
public class LinkedList<T> {

	private Node<T> head;
	private int size;
	private final Comparator<T> cmp;

	public LinkedList(Comparator<T> cmp) {
		this.head = null;
		this.size = 0;
		this.cmp = cmp;
	}

	/**
	 * Gets the size
	 * @return
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * Clears the list
	 */
	public void clear() {
		this.head = null;
		this.size = 0;
	}
	
	/**
	 * Inserts the item maintaining order
	 * @param p
	 */
	public void insertItem(T item) {
		Node<T> newNode = new Node<>(item);

		if (this.size == 0) {
			this.head = newNode;
			this.size++;
			return;
		} 
		
		Node<T> curr = this.head;
		Node<T> prev = null;
		
		if (this.cmp.compare(item, curr.getItem()) < 0) {
			newNode.setNext(this.head);
			this.head = newNode;
			this.size++;
		} else {
			      
			while (this.cmp.compare(item, curr.getItem()) >= 0 && curr.hasNext()) {
				prev = curr;
				curr = curr.getNext();
			}

			if (!curr.hasNext() && this.cmp.compare(item, curr.getItem()) >= 0) {
				curr.setNext(newNode);
			} else if (prev == null) {
				newNode.setNext(curr.getNext());
				curr.setNext(newNode);
			} else {
				newNode.setNext(curr);
				prev.setNext(newNode);
			}
			
			this.size++;
		}
	}

	/**
	 * Removes the item at the given index
	 * @param position
	 */
	public T removeAtIndex(int index) {	
		if(index < 0 || index >= this.size) {
			throw new IllegalArgumentException("position out of bounds!");
		}
		
		if (index == 0) {
			T item = this.head.getItem();
			this.head = this.head.getNext();
			this.size--;
			return item;
		}
		
		Node<T> prev = getNodeAtIndex(index - 1);
		Node<T> curr = prev.getNext();
		
		prev.setNext(curr.getNext());
		this.size--;
		
		return curr.getItem();		
	}


	/**
	 * Returns the item at the given index 
	 * @param position
	 * @return
	 */
	public T getItem(int index) {
		return getNodeAtIndex(index).getItem();
	}
	
	/**
	 * Gets node at the given index
	 * @param index
	 * @return
	 */
	private Node<T> getNodeAtIndex(int index){
		if(index < 0 || index >= this.size) {
			throw new IllegalArgumentException("position out of bounds!");
		}
		
		Node<T> curr = this.head;
		for(int i = 0; i < index; i++) {
			curr = curr.getNext();
		}
		
		return curr;
	}
	
	/**
	 * Prints out the list to human readable format
	 */
	public void print() {
		if (this.size == 0) {
			System.out.println("[empty]");
		} else {
			Node<T> curr = this.head;
			while (curr != null) {
				System.out.println(curr.getItem().toString());
				curr = curr.getNext();
			}
		}
	}

}
