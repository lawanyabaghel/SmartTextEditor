package textgen;

import java.util.AbstractList;

public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;
	public MyLinkedList() {
		// TODO: Implement this method
		size = 0;
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		head.next = tail;
		tail.prev = head;
	}

	public boolean add(E element ) 
	{
		if (element == null) {
			throw new NullPointerException("Invalid element input!!");
		}
		// TODO: Implement this method
		LLNode<E> newNode = new LLNode<E>(element);
		newNode.next = tail;
		newNode.prev = tail.prev;
		tail.prev.next=newNode;
		tail.prev = newNode;
		size++;
		return true;
	}

	public E get(int index) 
	{
		// TODO: Implement this method.
		if(index>size-1 || index < 0) {
			throw new IndexOutOfBoundsException("Invalid index input!!");
		}
		
		LLNode<E> current = head;
		for(int i = 0; i<=index; i++) {
			current =current.next;
		}
	        return current.data;
	}

	public void add(int index, E element ) 
	{
		if (element == null) {
			throw new NullPointerException("Invalid element input!!");
		}
		if ((index < 0 || index > size - 1) && (index != 0 || size != 0)) {
			throw new IndexOutOfBoundsException("Invalid index input!!");
		}
		LLNode<E> nNode = new LLNode<E>(element);
		LLNode<E> current = head;
		for(int i = 0; i<= index; i++) {
			current = current.next;
		}
		nNode.next = current;
		nNode.prev = current.prev;
		current.prev.next = nNode;
		current.prev = nNode;
		size++;
			
	}
	public int size() {
	    return size;
	}

	public E remove(int index) 
	{
		// TODO: Implement this method
		if(index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException("Invalid index input!!");
		}
		LLNode<E> current = head;
		for(int i = 0; i<= index; i++) {
			current = current.next;
			if(i == index) {
				current.prev.next= current.next;
				current.next.prev = current.prev;
				current.next = null;
				current.prev = null;
				size--;
			}
		}
		return current.data;
		
	}

	public E set(int index, E element) 
	{
		if(index<0 || index>size-1) {
			throw new IndexOutOfBoundsException("Invalid index input!!!");
		}
		if (element == null) {
			throw new NullPointerException("Invalid element input!!");
		}
		LLNode<E> node = new LLNode<E>(element);
		LLNode<E> current = head;
		for(int i = 0; i<= index; i++) {
			current = current.next;
		}
		node.next = current.next;
		node.prev = current.prev;
		current.prev.next = node;
		current.next.prev = node;
		current.next = null;
		current.prev = null;
		
		return current.data;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	public LLNode()
	{
		this.data = null;
		this.prev = null;
		this.next = null;
	}

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
