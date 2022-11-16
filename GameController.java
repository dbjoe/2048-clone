package project3;

import java.util.Random;

public class GameController {
    Board b;
    GameStatus status;
    double winningValue;
    Random r;

    GameController(){
        b = new Board();
        winningValue = 2048;
        r = new Random();
        status = GameStatus.IN_PROGRESS;
        newTile();
    }
    
    GameController(int size, double winningValue){
        b = new Board(size);       
        this.winningValue = winningValue;
        r = new Random();
        status = GameStatus.IN_PROGRESS;
        newTile();
    }
    
    public Board getB() {
        return this.b;
    }

    public GameStatus getStatus() {
        return this.status;
    }
    
    public void newTile(){
        int ranRow = r.nextInt(b.getBoardSize());
        int ranCol = r.nextInt(b.getBoardSize());
        int ranValue = r.nextInt(2);
        Tile t;

        if (ranValue == 1){
            t = new Tile(2);
        }
        else{
            t = new Tile(4);
        }
        while (b.getValue(ranRow,ranCol) != -1){
            ranRow = r.nextInt(b.getBoardSize());
            ranCol = r.nextInt(b.getBoardSize());
        }
        b.setTile(ranRow, ranCol, t);
    }
    
    public void reset(){
        b = new Board(b.getBoardSize());
        r = new Random();
        status = GameStatus.IN_PROGRESS;
        newTile();
    }
    
    private void checkWin(){
        for (int row = 0; row < b.getBoardSize(); row++){
            for (int col = 0; col < b.getBoardSize(); col++){
                Tile t = b.getTile(row,col);
                if (t != null && t.getValue() == winningValue){
                    status = GameStatus.WON;
                }
            }
        }
    }
    
    private void checkLoss(){
    	if (!b.hasEmpty()) {
            for (int row = 0; row < b.getBoardSize(); row++){
                for (int col = 0; col < b.getBoardSize(); col++){
                    Tile t = b.getTile(row,col);
                    if ((t != null && t.getValue() != winningValue) && (!findSimilarNeighbors(row, col))){
                        status = GameStatus.LOST;
                    }
                }
            }
    	}
    }
    
    private boolean findSimilarNeighbors(int row, int col){
        boolean neighbors = false;
        int givenValue = b.getValue(row, col);
        //if tile not null
        if (givenValue != -1) { 
            //check value of tile above
            if (row != 0) {
            	int upValue = b.getValue(row - 1, col);
            	if (givenValue == upValue){
                    neighbors = true;
                }
            }
            
            //check value of tile below
            if (row != b.getBoardSize() - 1) {
            	int downValue = b.getValue(row + 1, col);
            	if (givenValue == downValue){
                    neighbors = true;
                }
            }
            
            //check value of tile to the left
            if (col != 0) {
            	int leftValue = b.getValue(row, col - 1);
            	if (givenValue == leftValue){
                    neighbors = true;
                }
            }
            
            //check value of tile to the right
            if (col != b.getBoardSize() - 1) {
            	int rightValue = b.getValue(row, col + 1);
            	if (givenValue == rightValue){
                    neighbors = true;
                }
            }
        }
        return neighbors;
    }
    
    /*
     * Let's consider the case of moving all the Tiles to the left.  
     * We know that the furthest left column can't move so we can ignore it.  
     * We start then at the second column and begin checking the cell at the first row.  
     * There are only a few things that can happen.
		-	That location is null.  In that case, there is nothing to do!
		-	The next location we are trying to move to is null.  In that case, we just move our 
			Tile to that location.
		-	The next location we are trying to move to has the same value as us. Combine our values into 
			the Tile at the location to which we wish to move and remove the current one.
		-	The Tile we wish to move to is not the same value as us.  There is nothing to do.

		After we check the current cell, we can move down to the next row and do the same.
     */
    public void recurseLeft(int row) {
    	if (row < b.getBoardSize()) {
    		for (int i = 0; i < b.getBoardSize() - 2; i++) {
    			Tile current = b.getTile(row, i);
    			Tile next = b.getTile(row, i+1);
    			int x = b.getValue(row, i);
    			int y = b.getValue(row, i+1);
    			
    			if (y == -1) {//if there is nothing in the next tile
    				//do nothing
    			}
    			else if(x == -1) {//if there is nothing in the current tile
    				b.setTile(row, i, next);
//    				current.setValue(next.getValue());//set it to the next tile
    				b.setTile(row, i+1, null);//set the next tile to null
    				if (row != 0) {
    					recurseLeft(row - 1);//recurse
    				}
    				else {
    					recurseLeft(row + 1);
    				}
    			}
    			else if(x != y) {//if both values are not the same
    				//do nothing
    			}
    			else {
    				Tile doubleTile = new Tile(next.getValue() + current.getValue());
    				b.setTile(row, i, doubleTile);
//    				current.setValue(current.getValue() + next.getValue());
    				b.setTile(row, i+1, null);
    				if (row != 0) {
    					recurseLeft(row - 1);
    				}
    				else {
    					recurseLeft(row + 1);
    				}
    			}
    		}
    	recurseLeft(row+1);
    	}
    }
    
    public void recurseRight(int row) {
    	if (row < b.getBoardSize()) {
    		for (int i = b.getBoardSize() - 1; i > 0; i--) {
    			Tile current = b.getTile(row, i);
    			Tile next = b.getTile(row, i-1);
    			int x = b.getValue(row, i);
    			int y = b.getValue(row, i-1);

    			if (y == -1) {//if there is nothing in the next tile
    				//do nothing
    			}
    			else if(x == -1) {//if there is nothing in the current tile
    				b.setTile(row, i, next);
    				//current.setValue(next.getValue());//set it to the next tile
    				b.setTile(row, i - 1, null);//set the next tile to null
    				if (row != 0) {
    					recurseRight(row - 1);//recurse
    				}
    				else {
    					recurseRight(row + 1);
    				}
    			}
    			else if(x != y) {//if both values are not the same
    				//do nothing
    			}
    			else {
    				Tile doubleTile = new Tile(next.getValue() + current.getValue());
    				b.setTile(row, i, doubleTile);
    				//				current.setValue(current.getValue() + next.getValue());
    				b.setTile(row, i-1, null);
    				if (row != 0) {
    					recurseRight(row - 1);
    				}
    				else {
    					recurseRight(row + 1);
    				}
    			}
    		}
    	recurseRight(row+1);
    	}
    }
   
    public void recurseUp(int col) {
    	if (col < b.getBoardSize()) {
    		for (int i = 0; i < b.getBoardSize() - 1; i++) {
    			Tile current = b.getTile(i, col);
    			Tile next = b.getTile(i+1, col);
    			int x = b.getValue(i, col);
    			int y = b.getValue(i+1, col);
    			
    			if (y == -1) {//if there is nothing in the next tile
    				//do nothing
    			}
    			else if(x == -1) {//if there is nothing in the current tile
    				b.setTile(i, col, next);
//    				current.setValue(next.getValue());//set it to the next tile
    				b.setTile(i+1, col, null);//set the next tile to null
    				if (col != 0) {
    					recurseUp(col - 1);//recurse
    				}
    				else {
    					recurseUp(col + 1);
    				}
    			}
    			else if(x != y) {//if both values are not the same
    				//do nothing
    			}
    			else {
    				Tile doubleTile = new Tile(next.getValue() + current.getValue());
    				b.setTile(i, col, doubleTile);
//    				current.setValue(current.getValue() + next.getValue());
    				b.setTile(i+1, col, null);
    				if (col != 0) {
    					recurseUp(col - 1);
    				}
    				else {
    					recurseUp(col + 1);
    				}
    			}
    		}
    	recurseUp(col+1);
    	}
    }
    
    public void recurseDown(int col) {
    	if (col < b.getBoardSize()) {
    		for (int i = b.getBoardSize() - 1; i > 0; i--) {
    			Tile current = b.getTile(i, col);
    			Tile next = b.getTile(i - 1, col);
    			int x = b.getValue(i, col);
    			int y = b.getValue(i-1, col);

    			if (y == -1) {//if there is nothing in the next tile
    				//do nothing
    			}
    			else if(x == -1) {//if there is nothing in the current tile
    				b.setTile(i, col, next);//set it to the next tile
//    				current.setValue(next.getValue());
    				b.setTile(i - 1, col, null);//set the next tile to null
    				if (col != 0) {
    					recurseDown(col - 1);//recurse
    				}
    				else {
    					recurseDown(col + 1);
    				}
    			}
    			else if(x != y) {//if both values are not the same
    				//do nothing
    			}
    			else {
    				Tile doubleTile = new Tile(next.getValue() + current.getValue());
    				b.setTile(i, col, doubleTile);
    				//				current.setValue(current.getValue() + next.getValue());
    				b.setTile(i-1, col, null);
    				if (col != 0) {
    					recurseDown(col - 1);
    				}
    				else {
    					recurseDown(col + 1);
    				}
    			}
    		}
    		recurseDown(col + 1);
    	}
    }
    
    public void moveVertical(int i) {
    	if (i == -1) {
    		recurseUp(0);
    	}    	
    	if (i == 1) {
    		recurseDown(0);
    	}
    	
    	checkWin();
    	checkLoss();
    }
    
    public void moveHorizontal(int i) {
    	if (i == -1) {
    		recurseLeft(0);
    	}
    	if (i == 1) {
    		recurseRight(0);
    	}
    	
    	checkWin();
    	checkLoss();
    }
}
