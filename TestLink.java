package project3;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestLink {
    private final LinkList<String> list = new LinkList<>();

    @Test
    public void testAddEnd() {

        list.addEnd("A");//Testing one node

        //Data
        assertEquals("A", list.getTop().getData());
        assertEquals("A", list.getTail().getData());
        //Next
        assertNull(list.getTop().getNext());
        assertNull(list.getTail().getNext());
        //Prev
        assertNull(list.getTail().getPrev());
        assertNull(list.getTop().getPrev());


        list.addEnd("B");//Testing two nodes

        //Data
        assertEquals("A", list.getTop().getData());
        assertEquals("B", list.getTail().getData());
        //Next
        assertEquals("B", list.getTop().getNext().getData());
        assertNull(list.getTail().getNext());
        //Prev
        assertEquals("A", list.getTail().getPrev().getData());
        assertNull(list.getTop().getPrev());


        list.addEnd("C");// Testing three nodes

        //Data
        assertEquals("A", list.getTop().getData());
        assertEquals("B", list.getTop().getNext().getData());
        assertEquals("C", list.getTail().getData());
        //Next
        assertEquals("B", list.getTop().getNext().getData());
        assertEquals("C", list.getTop().getNext().getNext().getData());
        assertNull(list.getTail().getNext());
        //Prev
        assertEquals("B", list.getTail().getPrev().getData());
        assertEquals("A", list.getTail().getPrev().getPrev().getData());
        assertNull(list.getTop().getPrev());

    }
    @Test
    public void testAddBeginning() {

        list.addBeginning("C");//Testing one node

        //Data
        assertEquals("C", list.getTop().getData());
        assertEquals("C", list.getTail().getData());
        //Next
        assertNull(list.getTop().getNext());
        assertNull(list.getTail().getNext());
        //Prev
        assertNull(list.getTail().getPrev());
        assertNull(list.getTop().getPrev());


        list.addBeginning("B");//Testing two nodes

        //Data
        assertEquals("B", list.getTop().getData());
        assertEquals("C", list.getTail().getData());
        //Next
        assertEquals("C", list.getTop().getNext().getData());
        assertNull(list.getTail().getNext());
        //Prev
        assertEquals("B", list.getTail().getPrev().getData());
        assertNull(list.getTop().getPrev());


        list.addBeginning("A");// Testing three nodes

        //Data
        assertEquals("A", list.getTop().getData());
        assertEquals("B", list.getTop().getNext().getData());
        assertEquals("C", list.getTail().getData());
        //Next
        assertEquals("B", list.getTop().getNext().getData());
        assertEquals("C", list.getTop().getNext().getNext().getData());
        assertNull(list.getTail().getNext());
        //Prev
        assertEquals("B", list.getTail().getPrev().getData());
        assertEquals("A", list.getTail().getPrev().getPrev().getData());
        assertNull(list.getTop().getPrev());

    }
    @Test
    public void testToString(){
        list.addEnd("A");//Testing one node
        assertEquals("A,A",list.toString());

        list.addEnd("B");//Testing two nodes
        assertEquals("A,B,B,A",list.toString());

        list.addEnd("C");//Testing three nodes
        assertEquals("A,B,C,C,B,A",list.toString());

        list.addBeginning("3");//Testing one node
        assertEquals("3,A,B,C,C,B,A,3",list.toString());

        list.addBeginning("2");//Testing two nodes
        assertEquals("2,3,A,B,C,C,B,A,3,2",list.toString());

        list.addBeginning("1");//Testing three nodes
        assertEquals("1,2,3,A,B,C,C,B,A,3,2,1",list.toString());
    }
    @Test
    public void testLength(){
        assertEquals(0,list.getLen());//Testing empty list

        list.addEnd("1");//Testing one node
        assertEquals(1,list.getLen());

        list.addEnd("2");//Testing two nodes
        assertEquals(2,list.getLen());

        list.addEnd("3");//Testing three nodes
        assertEquals(3,list.getLen());
    }
    @Test
    public void testEmpty(){
        assertTrue(list.isEmpty());
        list.addEnd("A");
        assertFalse(list.isEmpty());
    }
    @Test
    public void testGetNodeAt(){

        try{
            list.getNodeAt(1);
        }
        catch(IndexOutOfBoundsException e){
            assertNotNull(e);
        }

        list.addEnd("A");
        list.addEnd("B");
        list.addEnd("C");

        try{
            list.getNodeAt(0);
        }
        catch(IndexOutOfBoundsException e){
            assertNotNull(e);
        }
        try{
            list.getNodeAt(4);
        }
        catch(IndexOutOfBoundsException e){
            assertNotNull(e);
        }

        assertEquals("A",list.getNodeAt(0).getData());
        assertEquals("B",list.getNodeAt(1).getData());
        assertEquals("C",list.getNodeAt(2).getData());
    }
    @Test
    public void testInsertBefore() {
        try {
            list.insertBefore("1",-1);
        } catch (IndexOutOfBoundsException e) {
            assertNotNull(e);
        }
        try {
            list.insertBefore("1",1);
        } catch (IndexOutOfBoundsException e) {
            assertNotNull(e);
        }
        list.insertBefore("3",0);
        assertEquals("3,3",list.toString());

        list.insertBefore("1",0);
        assertEquals("1,3,3,1",list.toString());

        list.insertBefore("2",1);
        assertEquals("1,2,3,3,2,1",list.toString());

        list.insertBefore("C",2);
        assertEquals("1,2,C,3,3,C,2,1",list.toString());

        list.insertBefore("B",0);
        assertEquals("B,1,2,C,3,3,C,2,1,B",list.toString());

        try {
            list.insertBefore("1",7);
        } catch (IndexOutOfBoundsException e) {
            assertNotNull(e);
        }


    }
    @Test
    public void testInsertAfter() {
        try {
            list.insertAfter("1",-1);
        } catch (IndexOutOfBoundsException e) {
            assertNotNull(e);
        }
        try {
            list.insertAfter("1",1);
        } catch (IndexOutOfBoundsException e) {
            assertNotNull(e);
        }

        list.insertAfter("1",0);
        assertEquals("1,1",list.toString());

        list.insertAfter("2",0);
        assertEquals("1,2,2,1",list.toString());

        list.insertAfter("3",1);
        assertEquals("1,2,3,3,2,1",list.toString());

        list.insertAfter("A",1);
        assertEquals("1,2,A,3,3,A,2,1",list.toString());

        try {
            list.insertAfter("1",7);
        } catch (IndexOutOfBoundsException e) {
            assertNotNull(e);
        }

    }
    @Test
    public void testGetTop(){
        list.addEnd("1");
        list.addEnd("2");
        list.addEnd("3");

        assertEquals("1",list.getTop().getData());
    }
    @Test
    public void testRemoveTop(){
        try {
            list.removeTop();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        list.addEnd("1");
        list.addEnd("2");
        list.addEnd("3");

        assertEquals("1",list.removeTop());
        assertEquals("2,3,3,2",list.toString());

        assertEquals("2",list.removeTop());
        assertEquals("3,3",list.toString());

        assertEquals("3",list.removeTop());
        assertEquals("",list.toString());
    }
    @Test
    public void testDelAt(){
        try {
            list.delAt(1);
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
        list.addEnd("1");
        assertEquals("1",list.delAt(0));

        list.addEnd("1");
        list.addEnd("2");
        list.addEnd("3");
        list.addEnd("4");

        try {
            list.delAt(5);
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        assertEquals("4",list.delAt(3));
        assertEquals("1,2,3,3,2,1", list.toString());
        assertEquals("2",list.delAt(1));
        assertEquals("1,3,3,1", list.toString());

        assertEquals("1", list.getTop().getData());
        assertEquals("3", list.getTail().getData());

    }
    
    
    /******************************************************************
     * Copying and pasting Joe's JUnit's below this line
     *****************************************************************/

    	@Test
    	public void testInsertBefore2() {

    		assertEquals(0, list.getLen());

    		//insert into empty list
    		list.insertBefore("A",0);
    		assertEquals("A", list.getNodeAt(0).getData());
    		//assertEquals("A", list.getDataAt(0)); 
    		assertEquals(1, list.getLen());

    		//insert before first node (tail moves)
    		list.insertBefore("B",0);
    		assertEquals("B", list.getNodeAt(0).getData());
    		assertEquals("A", list.getNodeAt(1).getData()); 
    		assertEquals(2, list.getLen());

    		//insert between two nodes
    		list.insertBefore("A.5",1);
    		assertEquals("B", list.getNodeAt(0).getData());
    		assertEquals("A.5", list.getNodeAt(1).getData());
    		assertEquals("A", list.getNodeAt(2).getData());
    		assertEquals(3, list.getLen());
    		
    		//insert before first node (tail doesn't move)
    		list.insertBefore("0.5",0);
    		assertEquals("B", list.getNodeAt(1).getData());
    		assertEquals("A.5", list.getNodeAt(2).getData());
    		assertEquals("A", list.getNodeAt(3).getData());
    		assertEquals("0.5", list.getNodeAt(0).getData());

    		assertEquals(4, list.getLen());
    		//testing length is always good for add and remove methods, because you could 
    		//have a node pointing to itself. By testing getLength(), we implicitly test 
    		//that we're not creating an infinitely looping list.
    	}
     
    	@Test
    	public void testInsertBeforeException1() {
    		try {
    			list.insertBefore("A",-1);
    		} catch (IndexOutOfBoundsException e){
    			assertTrue(e != null);
    		}
    	}
    	
    	@Test
    	public void testInsertBeforeException2() {
    		list.insertBefore("A",0);
    		
    		try {
    			//should throw exception because 1 = length of list
    			list.insertBefore("B",1);
    		} catch (IndexOutOfBoundsException e){
    			assertTrue(e != null);
    		}
    	}
    	
    	@Test
    	public void testToString2() {
    		list.insertBefore("C", 0);
    		list.insertBefore("B", 0);
    		list.insertBefore("A", 0);
    		
    		assertEquals("A,B,C,C,B,A", list.toString());
    	}
    	
    	@Test
    	public void testToStringEmpty() {	
    		assertEquals("", list.toString());
    	}

    	@Test
    	public void testInsertAfter2() {
    		assertEquals(0, list.getLen());
    		
    		//insert into empty list
    		list.insertAfter("A", 0);
    		assertEquals("A", list.getNodeAt(0).getData());
    		assertEquals(1, list.getLen());
    		
    		//insert after last node 
    		list.insertAfter("B",0);
    		assertEquals("A,B,B,A", list.toString());
    		assertEquals(2, list.getLen());
    		
    		//insert between two nodes
    		list.insertAfter("A.5", 0);
    		assertEquals("A,A.5,B,B,A.5,A", list.toString());
    		assertEquals(3, list.getLen());
    		
    		//insert after last node again
    		list.insertAfter("C", 2);
    		assertEquals("A,A.5,B,C,C,B,A.5,A", list.toString());
    		assertEquals(4, list.getLen());
    	}
    	
    	@Test
    	public void testInsertAfter0() {
    		assertEquals(0, list.getLen());

    		list.insertAfter("A", 0);
    		assertEquals("A,A", list.toString());
    		assertEquals(1, list.getLen());

    		list.insertAfter("B",0);
    		assertEquals("A,B,B,A", list.toString());
    		assertEquals(2, list.getLen());
    	}

    	@Test (expected = IndexOutOfBoundsException.class)
    	public void testInsertAfterException1() {
    		list.insertAfter("A", 1);
    	}
    	
    	@Test
    	public void testRemoveTop2() {
    		list.insertBefore("A", 0);
    		list.insertBefore("B", 0);
    		list.insertBefore("C", 0);
    		
    		assertEquals("C", list.removeTop());
    		assertEquals("B,A,A,B", list.toString());
    		assertEquals(2, list.getLen());
    		
    		assertEquals("B", list.removeTop());
    		assertEquals("A,A", list.toString());
    		assertEquals(1, list.getLen());
    		
    		assertEquals("A", list.removeTop());
    		assertEquals("", list.toString());
    		assertEquals(0, list.getLen());
    	}
    	
    	@Test
    	public void testRemoveTopException() {
    		try {
    			list.removeTop();
    		}
    		catch (IllegalArgumentException e) {
    			assertTrue(e != null);
    		}
    	}
    	
    	@Test
    	public void testDelAt2() {
    		list.insertBefore("A",0);
    		list.insertAfter("B",0);
    		list.insertAfter("C",1);
    		list.insertAfter("D",2);
    		list.insertAfter("E",3);
    		
    		//delete first node
    		assertEquals("A", list.delAt(0));
    		assertEquals("B,C,D,E,E,D,C,B", list.toString());
    		assertEquals(4, list.getLen());
    		
    		//delete node in between 2 nodes
    		assertEquals("C", list.delAt(1));
    		assertEquals("B,D,E,E,D,B", list.toString());
    		assertEquals(3, list.getLen());
    		
    		//delete last node
    		assertEquals("E", list.delAt(2));
    		assertEquals("B,D,D,B", list.toString());
    		assertEquals(2, list.getLen());
    		
    		//delete rest of list
    		assertEquals("B", list.delAt(0));
    		assertEquals("D,D", list.toString());
    		assertEquals("D", list.delAt(0));
    		assertEquals("", list.toString());
    		assertEquals(0, list.getLen());
    	}
    	
    	@Test
    	public void testDelAtException() {
    		list.insertBefore("A",0);
    		
    		try {
    			list.delAt(1);
    		}
    		catch (IllegalArgumentException e) {
    			assertTrue(e != null);
    		}
    	}
    	
    	@Test
    	public void testGetDataAt() {
    		list.insertBefore("A",0);
    		assertEquals("A", list.getDataAt(0));
    	}
    	
    	@Test (expected = IndexOutOfBoundsException.class)
    	public void testGetDataAtException() {
    		assertEquals("A", list.getDataAt(0));
    	}
    	
    	@Test
    	public void testClear() {
    		list.addBeginning("A");
    		list.insertAfter("B",0);
    		list.insertAfter("C",1);
    		
    		list.clear();
    		
    		assertEquals("", list.toString());
    	}
    	
    	@Test
    	public void testAddBeginning2() {
    		list.addBeginning("A");
    		assertEquals("A,A", list.toString());
    		assertEquals(1, list.getLen());
    		
    		list.addBeginning("B");
    		assertEquals("B,A,A,B", list.toString());
    		assertEquals(2, list.getLen());
    	}


}

