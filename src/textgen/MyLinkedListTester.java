package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	public void setUp() throws Exception {
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		
	}

	
	@Test
	public void testGet(){
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	@Test
	public void testRemove()
	{
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
		
		try {
			shortList.remove(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.remove(-1);
			fail("Check out of bounds");
		}
		catch(IndexOutOfBoundsException e) {
			
		}
		try {
			longerList.remove(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		for (int i = 0; i < LONG_LIST_LENGTH; i++) {
			int b = longerList.remove(0);
			assertEquals("Remove: check b is correct ", i, b);
			assertEquals("Remove: check size is correct ", LONG_LIST_LENGTH - i - 1, longerList.size());
		}
	}
	@Test
	public void testAddEnd()
	{
		try {
			shortList.add(null);
			fail("Check invalid element");
		}
		catch (NullPointerException e) {
		
		}
		
		boolean state = emptyList.add(0);
		assertEquals("AddEnd: check state is correct ", true, state);
		assertEquals("AddEnd: check value is correct", (Integer)0, emptyList.get(0));
		assertEquals("AddEnd: check size is correct", 1, emptyList.size());
		
		state = emptyList.add(1);
		assertEquals("AddEnd: check state is correct ", true, state);
		assertEquals("AddEnd: check value is correct", (Integer)1, emptyList.get(1));
		assertEquals("AddEnd: check size is correct", 2, emptyList.size());
		
	}
	@Test
	public void testSize(){
		assertEquals("Size: empty list ", 0, emptyList.size());
		assertEquals("Size: short list ", 2, shortList.size());
		assertEquals("Size: longer list ", 10, longerList.size());
		assertEquals("Size: list1 ", 3, list1.size());
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * */
	@Test
	public void testAddAtIndex()
	{
		try {
			shortList.add(0, null);
			fail("Check invalid element");
		}
		catch (NullPointerException e) {
		
		}
		
		try {
			shortList.add(2, "C");
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		try {
			longerList.add(-1, 10);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		longerList.add(2, 10);
		assertEquals("AddAtIndex: check value is correct ", (Integer)10, longerList.get(2));
		assertEquals("AddAtIndex: check size is correct", 11, longerList.size());
		
		shortList.add(1, "C");
		assertEquals("AddAtIndex: check value is correct ", (String)"C", shortList.get(1));
		assertEquals("AddAtIndex: check size is correct", 3, shortList.size());
		
	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
		try {
			shortList.set(0, null);
			fail("Check invalid element");
		}
		catch (NullPointerException e) {
		
		}
		
		try {
			shortList.set(2, "C");
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		try {
			longerList.set(-1, 10);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		longerList.set(2, 10);
		assertEquals("AddAtIndex: check value is correct ", (Integer)10, longerList.get(2));
		assertEquals("AddAtIndex: check size is correct", 10, longerList.size());
		
		shortList.set(1, "C");
		assertEquals("AddAtIndex: check value is correct ", (String)"C", shortList.get(1));
		assertEquals("AddAtIndex: check size is correct", 2, shortList.size());
		
	    
	}
}
