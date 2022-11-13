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
                if (t.getValue() == winningValue){
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
                    if ((t.getValue() != winningValue) && (!findSimilarNeighbors(row, col))){
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

//    public void recurseLeft(int row){
//        while (row <= b.getBoardSize()){
//            int newRow = row;
//
//            for (int y = 2; y <= b.getBoardSize(); y++){
//                Tile current = b.getTile(row,y);
//                Tile check = b.getTile(row, y - 1);
//
//                if ((current.getValue() != -1) && (check.getValue() != -1) && (current.getValue() == check.getValue())){
//                    check.setValue((check.getValue()) + (current.getValue()));
//                    b.delAt(y);
//                    b.b.insertBefore(null,y);
//                }
//                else if ((current.getValue() != -1) && (check.getValue() != -1) && (current.getValue() != check.getValue())) {
//
//                }
//                else if ((current.getValue() == -1) && (check.getValue() != -1)) {
//
//                }
//                else if ((current.getValue() != -1) && (check.getValue() == -1)) {
//                    b.b.delAt(y - 1);
//                    b.b.addEnd(null);
//                    recurseLeft(row);
//                }
//                else {
//
//                }
//                newRow = row + 1;
//                recurseLeft(newRow);
//            }
//
//        }
//    }
//    
//    public void recurseRight(int row){
//        while (row >= b.getBoardSize()){
//            int newRow = row;
//
//            for (int y = b.getBoardSize() - 1; y >= 1; y--){
//                Tile current = b.getTile(row,y);
//                Tile check = b.getTile(row, y + 1);
//
//                if ((current.getValue() != -1) && (check.getValue() != -1) && (current.getValue() == check.getValue())){
//                    check.setValue((check.getValue()) + (current.getValue()));
//                    b.b.delAt(y);
//                    b.b.insertBefore(null,y);
//                }
//                else if ((current.getValue() != -1) && (check.getValue() != -1) && (current.getValue() != check.getValue())) {
//
//                }
//                else if ((current.getValue() == -1) && (check.getValue() != -1)) {
//
//                }
//                else if ((current.getValue() != -1) && (check.getValue() == -1)) {
//                    b.b.delAt(y + 1);
//                    b.b.addBeginning(null);
//                    recurseRight(row);
//                }
//                else {
//
//                }
//                newRow = row + 1;
//                recurseRight(newRow);
//            }
//
//        }
//    }
//    
//    //TODO: work on recurseDown
//    public void recurseDown(int col){
//        while (col <= b.getBoardSize()){
//            int newCol = col;
//
//            for (int x = 2; x <= b.getBoardSize(); x++){
//                Tile current = b.getTile(x, col);
//                Tile check = b.getTile(x - 1, col);
//
//                if ((current.getValue() != -1) && (check.getValue() != -1) && (current.getValue() == check.getValue())){
//                    check.setValue((check.getValue()) + (current.getValue()));
//                    b.b.delAt(x);
//                    b.b.insertBefore(null, x);
//                }
//                else if ((current.getValue() != -1) && (check.getValue() != -1) && (current.getValue() != check.getValue())) {
//
//                }
//                else if ((current.getValue() == -1) && (check.getValue() != -1)) {
//
//                }
//                else if ((current.getValue() != -1) && (check.getValue() == -1)) {
//                    b.b.delAt(x - 1);
//                    b.b.addEnd(null);
//                    recurseLeft(col);
//                }
//                else {
//
//                }
//                newCol = col + 1;
//                recurseLeft(newCol);
//            }
//
//        }
//    }
}
