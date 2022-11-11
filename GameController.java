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
        for (int row = 1; row <= b.getBoardSize(); row++){
            for (int col = 1; col <= b.getBoardSize(); col++){
                Tile t = b.getTile(row,col);
                if (t.getValue() == winningValue){
                    status = GameStatus.WON;
                }
            }
        }
    }
    
    private void checkLoss(){
        for (int row = 1; row <= b.getBoardSize(); row++){
            for (int col = 1; col <= b.getBoardSize(); col++){
                Tile t = b.getTile(row,col);
                if ((t.getValue() != winningValue) && (!b.hasEmpty())){
                    status = GameStatus.LOST;
                }
            }
        }
    }
    
    private boolean findSimilarNeighbors(int row, int col){
        boolean neighbors;
        int givenValue = b.getValue(row,col);
        int upValue = b.getValue(row + 1,col);
        int downValue = b.getValue(row - 1,col);
        int leftValue = b.getValue(row,col - 1);
        int rightValue = b.getValue(row, col + 1);

        if (b.getValue(row,col) == -1){
            throw new IllegalArgumentException();
        }
        else if (givenValue == upValue){
            neighbors = true;
        }
        else if (givenValue == downValue){
            neighbors = true;
        }
        else if (givenValue == leftValue){
            neighbors = true;
        }
        else if (givenValue == rightValue){
            neighbors = true;
        }
        else {
            neighbors = false;
        }
        return neighbors;
    }
    
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
