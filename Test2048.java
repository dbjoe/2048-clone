package project3;

import org.junit.Test;

import static org.junit.Assert.*;

public class Test2048 {
	@Test
	public void testDefaultConstructor() {
		GameController g = new GameController();
		assertEquals(4, g.getBoard().getBoardSize());
		assertEquals(2048, (int)g.getWinningValue());
	}
	
	@Test
	public void testSetBoard1() {
		GameController g = new GameController(5, 1024);
		assertEquals(5, g.getBoard().getBoardSize());
		Board b = new Board(6);
		g.setBoard(b);
		assertEquals(6, g.getBoard().getBoardSize());
	}
	
	@Test
	(expected = IllegalArgumentException.class)
	public void testSetBoard2() {
		GameController g = new GameController();
		g.setBoard(null);
	}
	
	@Test
	(expected = IllegalArgumentException.class)
	public void testSetStatus1() {
		GameController g = new GameController();
		g.setStatus(null);
	}
	
	@Test
	public void testSetStatus2() {
		GameController g = new GameController();
		g.setStatus(GameStatus.WON);
		assertEquals(GameStatus.WON, g.getStatus());
	} 
	
	@Test
	(expected = IllegalArgumentException.class)
	public void testSetWinningValue1() {
		GameController g = new GameController();
		g.setWinningValue(16.0001);
	}
	@Test
	(expected = IllegalArgumentException.class)
	public void testSetWinningValue2() {
		GameController g = new GameController();
		g.setWinningValue(63.9999);
	}
	
	@Test
	public void testSetWinningValue3() {
		GameController g = new GameController();
		g.setWinningValue(64);
		assertEquals(64, (int)g.getWinningValue());
	}
	
	@Test
	public void testSetBoardSize1() {
		GameController g = new GameController();
		g.setBoardSize(5);
		assertEquals(5, g.getBoardSize());
	}
	
	@Test
	(expected = IllegalArgumentException.class)
	public void testSetBoardSize2() {
		GameController g = new GameController();
		g.setBoardSize(11);
	}
	
	@Test
	(expected = IllegalArgumentException.class)
	public void testSetBoardSize3() {
		GameController g = new GameController();
		g.setBoardSize(3);
	}
	
	@Test
	public void testCheckWin1() {
		GameController g = new GameController();
		Tile winner = new Tile(2048);
		g.getBoard().setTile(0, 0, winner);
		g.moveHorizontal(1); //checkWin() called from here
		assertEquals(GameStatus.WON, g.getStatus());
	}
	
	@Test
	public void testCheckWin2() {
		GameController g = new GameController();
		Tile winner = new Tile(2048);
		g.getBoard().setTile(0, 0, winner);
		g.moveHorizontal(1); //checkWin() called from here
		assertEquals(GameStatus.WON, g.getStatus());
	}
	
	//Fills the board and leaves no possible moves, resulting in a loss
	@Test
	public void testCheckLoss1() {
		GameController g = new GameController();
		Tile t1 = new Tile(8);
		Tile t2 = new Tile(16);
		Tile t3 = new Tile(32);
		Tile t4 = new Tile(64);
		
		g.getBoard().setTile(0, 0, t1);
		g.getBoard().setTile(0, 1, t2);
		g.getBoard().setTile(0, 2, t3);
		g.getBoard().setTile(0, 3, t4);
		g.getBoard().setTile(1, 0, t4);
		g.getBoard().setTile(1, 1, t3);
		g.getBoard().setTile(1, 2, t2);
		g.getBoard().setTile(1, 3, t1);
		g.getBoard().setTile(2, 0, t1);
		g.getBoard().setTile(2, 1, t2);
		g.getBoard().setTile(2, 2, t3);
		g.getBoard().setTile(2, 3, t4);
		g.getBoard().setTile(3, 0, t4);
		g.getBoard().setTile(3, 1, t3);
		g.getBoard().setTile(3, 2, t2);
		g.getBoard().setTile(3, 3, t1);


		g.moveHorizontal(1); //move right
		assertFalse(g.getBoard().hasEmpty());
		assertEquals(GameStatus.LOST, g.getStatus());
	}
	
	//Fills the board and leaves no possible moves, but one tile has the winning value
	@Test
	public void testCheckLoss2() {
		GameController g = new GameController();
		Tile t1 = new Tile(8);
		Tile t2 = new Tile(16);
		Tile t3 = new Tile(32);
		Tile t4 = new Tile(64);
		Tile win = new Tile(2048);
		
		g.getBoard().setTile(0, 0, t1);
		g.getBoard().setTile(0, 1, t2);
		g.getBoard().setTile(0, 2, t3);
		g.getBoard().setTile(0, 3, t4);
		g.getBoard().setTile(1, 0, t4);
		g.getBoard().setTile(1, 1, t3);
		g.getBoard().setTile(1, 2, t2);
		g.getBoard().setTile(1, 3, t1);
		g.getBoard().setTile(2, 0, t1);
		g.getBoard().setTile(2, 1, t2);
		g.getBoard().setTile(2, 2, t3);
		g.getBoard().setTile(2, 3, t4);
		g.getBoard().setTile(3, 0, t1);
		g.getBoard().setTile(3, 1, t2);
		g.getBoard().setTile(3, 2, win);
		
		g.moveHorizontal(1); //move right
		assertFalse(g.getBoard().hasEmpty());
		assertEquals(GameStatus.WON, g.getStatus());
	}
	
	//Fills up the board, but leaves a move available
	@Test
	public void testCheckLoss3() {
		GameController g = new GameController();
		Tile t1 = new Tile(8);
		Tile t2 = new Tile(16);
		Tile t3 = new Tile(32);
		Tile t4 = new Tile(64);
		Tile two = new Tile(2);
		Tile four = new Tile(4);
		
		g.getBoard().setTile(0, 0, t1);
		g.getBoard().setTile(0, 1, t2);
		g.getBoard().setTile(0, 2, t3);
		g.getBoard().setTile(0, 3, t4);
		g.getBoard().setTile(1, 0, t4);
		g.getBoard().setTile(1, 1, t3);
		g.getBoard().setTile(1, 2, t2);
		g.getBoard().setTile(1, 3, t1);
		g.getBoard().setTile(2, 0, two);
		g.getBoard().setTile(2, 1, t2);
		g.getBoard().setTile(2, 2, t3);
		g.getBoard().setTile(2, 3, t4);
		g.getBoard().setTile(3, 0, four);
		g.getBoard().setTile(3, 1, t2);
		g.getBoard().setTile(3, 2, t3);
		
		g.moveHorizontal(1); //move right
		assertFalse(g.getBoard().hasEmpty());
		assertEquals(GameStatus.IN_PROGRESS, g.getStatus());
	}
	@Test
	public void testCheckNeighbor() {
		GameController g = new GameController();
		Tile t1 = new Tile(8);

		g.getBoard().setTile(0, 1, t1);
		g.getBoard().setTile(1, 1, t1);
		g.getBoard().setTile(2, 1, t1);

		g.moveHorizontal(1);

	}
	
	//TODO
	@Test
	public void testNewTile1() {
		
	}
	
	//TODO
	@Test
	public void testNewTile2() {
		
	}
	
	@Test 
	public void testMoveHorizontalLeft() {
		GameController g = new GameController();
		Tile t2 = new Tile(2);
		Tile t4 = new Tile(4);
		Tile t8 = new Tile(8);
		Tile t16 = new Tile(16);
		
		g.getBoard().setTile(0, 0, t2);
		g.getBoard().setTile(0, 1, t2);
		g.getBoard().setTile(0, 2, t4);
		g.getBoard().setTile(0, 3, t8);
		
		g.getBoard().setTile(1, 0, t8);
		g.getBoard().setTile(1, 1, null);
		g.getBoard().setTile(1, 2, t8);
		g.getBoard().setTile(1, 3, t16);
		
		g.getBoard().setTile(2, 0, t16);
		g.getBoard().setTile(2, 1, null);
		g.getBoard().setTile(2, 2, t4);
		g.getBoard().setTile(2, 3, t4);
		
		g.getBoard().setTile(3, 0, null);
		g.getBoard().setTile(3, 1, null);
		g.getBoard().setTile(3, 2, t4);
		g.getBoard().setTile(3, 3, t8);
		
		g.moveHorizontal(-1);
		
		assertEquals(16, g.getBoard().getValue(0, 0));
		assertEquals(32, g.getBoard().getValue(1, 0));
		assertEquals(16, g.getBoard().getValue(2, 0));
		assertEquals(8, g.getBoard().getValue(2, 1));
		assertEquals(4, g.getBoard().getValue(3, 0));
		assertEquals(8, g.getBoard().getValue(3, 1));	
	}

	@Test 
	public void testMoveHorizontalRight() {
		GameController g = new GameController();
		Tile t2 = new Tile(2);
		Tile t4 = new Tile(4);
		Tile t8 = new Tile(8);
		Tile t16 = new Tile(16);
		
		g.getBoard().setTile(0, 0, t2);
		g.getBoard().setTile(0, 1, t2);
		g.getBoard().setTile(0, 2, t4);
		g.getBoard().setTile(0, 3, t8);
		
		g.getBoard().setTile(1, 0, t8);
		g.getBoard().setTile(1, 1, null);
		g.getBoard().setTile(1, 2, t8);
		g.getBoard().setTile(1, 3, t16);
		
		g.getBoard().setTile(2, 0, t16);
		g.getBoard().setTile(2, 1, null);
		g.getBoard().setTile(2, 2, t4);
		g.getBoard().setTile(2, 3, t4);
		
		g.getBoard().setTile(3, 0, null);
		g.getBoard().setTile(3, 1, null);
		g.getBoard().setTile(3, 2, t4);
		g.getBoard().setTile(3, 3, t8);
		
		g.moveHorizontal(1);
		
		assertEquals(16, g.getBoard().getValue(0, 3));
		assertEquals(32, g.getBoard().getValue(1, 3));
		assertEquals(16, g.getBoard().getValue(2, 2));
		assertEquals(8, g.getBoard().getValue(2, 3));
		assertEquals(4, g.getBoard().getValue(3, 2));
		assertEquals(8, g.getBoard().getValue(3, 3));	
	}
	
	@Test 
	public void testMoveVerticalUp() {
		GameController g = new GameController();
		Tile t2 = new Tile(2);
		Tile t4 = new Tile(4);
		Tile t8 = new Tile(8);
		Tile t16 = new Tile(16);
		
		g.getBoard().setTile(0, 0, t2);
		g.getBoard().setTile(1, 0, t2);
		g.getBoard().setTile(2, 0, t4);
		g.getBoard().setTile(3, 0, t8);
		
		g.getBoard().setTile(0, 1, null);
		g.getBoard().setTile(1, 1, t2);
		g.getBoard().setTile(2, 1, null);
		g.getBoard().setTile(3, 1, t2);
		
		g.getBoard().setTile(0, 2, t2);
		g.getBoard().setTile(1, 2, null);
		g.getBoard().setTile(2, 2, t16);
		g.getBoard().setTile(3, 2, t16);
		
		g.getBoard().setTile(0, 3, null);
		g.getBoard().setTile(1, 3, null);
		g.getBoard().setTile(2, 3, null);
		g.getBoard().setTile(3, 3, t8);
		
		g.moveVertical(-1);
		
		assertEquals(16, g.getBoard().getValue(0, 0));
		assertEquals(4, g.getBoard().getValue(0, 1));
		assertEquals(2, g.getBoard().getValue(0, 2));
		assertEquals(32, g.getBoard().getValue(1, 2));
		assertEquals(8, g.getBoard().getValue(0, 3));	
	}
	
	@Test 
	public void testMoveVerticalDown() {
		GameController g = new GameController();
		Tile t2 = new Tile(2);
		Tile t4 = new Tile(4);
		Tile t8 = new Tile(8);
		Tile t16 = new Tile(16);
		
		g.getBoard().setTile(0, 0, t2);
		g.getBoard().setTile(1, 0, t2);
		g.getBoard().setTile(2, 0, t4);
		g.getBoard().setTile(3, 0, t8);
		
		g.getBoard().setTile(0, 1, null);
		g.getBoard().setTile(1, 1, t2);
		g.getBoard().setTile(2, 1, null);
		g.getBoard().setTile(3, 1, t2);
		
		g.getBoard().setTile(0, 2, t2);
		g.getBoard().setTile(1, 2, null);
		g.getBoard().setTile(2, 2, t16);
		g.getBoard().setTile(3, 2, t16);
		
		g.getBoard().setTile(0, 3, null);
		g.getBoard().setTile(1, 3, null);
		g.getBoard().setTile(2, 3, t8);
		g.getBoard().setTile(3, 3, null);
		
		g.moveVertical(1);
		
		assertEquals(16, g.getBoard().getValue(3, 0));
		assertEquals(4, g.getBoard().getValue(3, 1));
		assertEquals(2, g.getBoard().getValue(2, 2));
		assertEquals(32, g.getBoard().getValue(3, 2));
		assertEquals(8, g.getBoard().getValue(3, 3));	
	}
	
	//TODO
	@Test 
	public void testFindSimilarNeighbors1() {
		GameController g = new GameController();
		g.moveHorizontal(1);
		assertEquals(GameStatus.IN_PROGRESS, g.getStatus());
	}

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
        b.setTile(3,3,test);
        assertEquals(2, b.getValue(3,3));
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
//        assertEquals(-1, b.getValue(3,3));
//
//        try{
//            Board test2 = new Board(3);
//        }
//        catch (IllegalArgumentException e){
//            assertNotNull(e);
//        }
//        try{
//            Board test2 = new Board(11);
//        }
//        catch (IllegalArgumentException e){
//            assertNotNull(e);
//        }

        Board test2 = new Board(5);

        for(int x = 0; x < 5; x++){
            for (int y = 0; y < 5; y++){
                test2.setTile(x,y,test);
            }
        }

        assertEquals(2,test2.getValue(1,1));
        assertEquals(2,test2.getValue(4,4));
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

//    @Test
//    public void testDefaultGameController(){
//        GameController test = new GameController();
//        String winValTest = "";
//        winValTest = winValTest + test.getWinningValue();
//        assertEquals("2048.0",winValTest);
//        assertEquals(GameStatus.IN_PROGRESS,test.getStatus());
//        assertEquals(4,test.b.getBoardSize());
//    }
//    @Test
//    public void testOverloadedGameController(){
//        try {
//            GameController test1 = new GameController(3, 2048);
//        }
//        catch (IllegalArgumentException e){
//            assertNotNull(e);
//        }
//        try {
//            GameController test1 = new GameController(3, 2047);
//        }
//        catch (IllegalArgumentException e){
//            assertNotNull(e);
//        }
//        try {
//            GameController test1 = new GameController(5, 0);
//        }
//        catch (IllegalArgumentException e){
//            assertNotNull(e);
//        }
//        try {
//            GameController test1 = new GameController(11, 0);
//        }
//        catch (IllegalArgumentException e){
//            assertNotNull(e);
//        }
//
//        GameController test1 = new GameController(5, 1024);
//        String winValTest = "";
//        winValTest = winValTest + test1.getWinningValue();
//        assertEquals("1024.0",winValTest);
//        assertEquals(GameStatus.IN_PROGRESS,test1.getStatus());
//        assertEquals(5,test1.b.getBoardSize());
//    }
    @Test
    public void testGetBoard(){
        GameController test = new GameController();
        Board b = test.getBoard();
        assertEquals(b,test.getBoard());
    }
	@Test
	public void testReset(){
		GameController test = new GameController();
		Tile t1 = new Tile(4);

		test.getBoard().setTile(0,1, t1);
		test.getBoard().setTile(0,3, t1);
		test.getBoard().setTile(2,0, t1);

		test.reset();

		assertEquals(-1,test.getBoard().getValue(0,1));
		assertEquals(-1,test.getBoard().getValue(0,3));
		assertEquals(-1,test.getBoard().getValue(2,0));


	}




}
