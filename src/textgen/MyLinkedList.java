package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		size = 0;
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
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

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
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
		//return null;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		if (element == null) {
			throw new NullPointerException("Invalid element input!!");
		}
		if ((index < 0 || index > size - 1) && (index != 0 || size != 0)) {
			throw new IndexOutOfBoundsException("Invalid index input!!");
		}
		// TODO: Implement this method
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


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
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

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
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

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor
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
