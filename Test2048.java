package project3;

import org.junit.Test;

import static org.junit.Assert.*;

public class Test2048 {
    Tile test = new Tile();

    @Test
    public void testTile(){

        test.setValue(2048);
        assertEquals(2048,test.getValue());
        try{
            test.setValue(2047);
        }
        catch(IllegalArgumentException e){
            assertNotNull(e);
        }
        try{
            test.setValue(2046);
        }
        catch(IllegalArgumentException e){
            assertNotNull(e);
        }
        assertEquals(2048,test.getValue());
        assertEquals("2048",test.toString());

        new Tile(1024);

        try{
            new Tile (1023);
        }
        catch (IllegalArgumentException e){
            assertNotNull(e);
        }
    }
    
    @Test
    public void idkMan() {
    	Board b = new Board();
    	Tile t = new Tile(2);
        b.setTile(1,1,test);
        assertEquals(2, b.getValue(1,1));
        assertNotEquals(2, b.getValue(0, 1));
    }
    
    @Test
    public void testPower2() {
    	assertFalse(test.power2(16.001));
    	assertFalse(test.power2(5));
    	assertTrue(test.power2(2));
    	assertTrue(test.power2(16));
    	assertTrue(test.power2(2048));
    }
    
    @Test
    public void testBoard(){
        Board b = new Board();
        assertTrue(b.hasEmpty());
        Tile test = new Tile(2);
        b.setTile(1,1,test);
        assertEquals(2, b.getValue(1,1));
        try{
            b.setTile(0,1,test);
        }
        catch (IllegalArgumentException e){
            assertNotNull(e);
        }
        try{
            b.setTile(1,0,test);
        }
        catch (IllegalArgumentException e){
            assertNotNull(e);
        }
        b.setTile(4,4,test);
        assertEquals(2, b.getValue(4,4));
        try{
            b.setTile(4,5,test);
        }
        catch (IllegalArgumentException e){
            assertNotNull(e);
        }
        try{
            b.setTile(5,4,test);
        }
        catch (IllegalArgumentException e){
            assertNotNull(e);
        }
        assertEquals(-1, b.getValue(3,3));

        try{
            Board test2 = new Board(3);
        }
        catch (IllegalArgumentException e){
            assertNotNull(e);
        }
        try{
            Board test2 = new Board(11);
        }
        catch (IllegalArgumentException e){
            assertNotNull(e);
        }

        Board test2 = new Board(5);

        for(int x = 1; x <= 5; x++){
            for (int y = 1; y <= 5; y++){
                test2.setTile(x,y,test);
            }
        }

        assertEquals(2,test2.getValue(1,1));
        assertEquals(2,test2.getValue(5,5));
        try{
            test2.getValue(6,5);
        }
        catch (IllegalArgumentException e){
            assertNotNull(e);
        }
        try{
            test2.getValue(5,6);
        }
        catch (IllegalArgumentException e){
            assertNotNull(e);
        }
        assertFalse(test2.hasEmpty());

    }

}