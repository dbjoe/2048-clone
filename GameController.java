package project3;

import java.util.Random;

public class GameController {
	private Board b;
	private GameStatus status;
	private double winningValue;
	private Random r;
	public static int numGames = 0;
	public static int numWins = 0;

	GameController(){
		b = new Board(4);
		winningValue = 2048;
		r = new Random();
		status = GameStatus.IN_PROGRESS;
		numGames++;
		newTile();
	}

	GameController(int boardSize, double winningValue){
		b = new Board(boardSize);
		this.winningValue = winningValue;
		r = new Random();
		status = GameStatus.IN_PROGRESS;
		numGames++;
		newTile();
	}

	public Board getBoard() {
		return this.b;
	}
	public void setBoard(Board board){
		this.b = board;
	}
	public double getWinningValue(){
		return this.winningValue;
	}
	public void setWinningValue(double value){
		this.winningValue = value;
	}

	public GameStatus getStatus() {
		return this.status;
	}
	public void setStatus(GameStatus status){
		this.status = status;
	}
	public int getBoardSize(){
		int b = getBoard().getBoardSize();
		return b;
	}
	public void setBoardSize(int boardSize){
		getBoard().setBoardSize(boardSize);
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
					numWins++;
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
	public void recurseHorizontal(int row, int direction) {
		if(row < b.getBoardSize()) {
			int col = 1;
			if (direction == 1) {
				col = b.getBoardSize()-2;
			}
			recurseRow(row, col, direction);
			recurseHorizontal(row+1, direction);
		}
	}

	private void recurseRow(int row, int col, int direction) {
		int startCol = 1;
		if (direction == 1) {
			startCol = b.getBoardSize()-2;
		}

		boolean again = false;
		if (direction == -1) {
			if (col < b.getBoardSize()) {
				again = true;
			}
		}
		else {
			if (col >= 0) {
				again = true;
			}
		}

		if (again) {
			int current = b.getValue(row, col);
			int prev = b.getValue(row, col+direction);

			//if the current tile is null, move on to the next tile
			if (current == -1) {
				recurseRow(row, col-direction, direction);
			}
			else {
				//if current tile has the same value as the previous tile, add them
				if (current == prev) {
					Tile doubleTile = new Tile(2*current);
					b.setTile(row, col+direction, doubleTile);
					b.setTile(row, col, null);
					recurseRow(row, startCol, direction);
				}

				//If the previous tile is null, move our current tile there
				if (prev == -1) {
					b.setTile(row, col+direction, b.getTile(row, col));
					b.setTile(row, col, null);

					recurseRow(row, startCol, direction);
				}

				//If the current tile has a different value than previous, move on
				if (current != prev) {
					recurseRow(row, col-direction, direction);
				}
			}
		}
	}

	public void recurseVertical(int col, int direction) {
		if(col < b.getBoardSize()) {
			int row = 1;
			if (direction == 1) {
				row = b.getBoardSize()-2;
			}
			recurseCol(row, col, direction);
			recurseVertical(col+1, direction);
		}
	}

	private void recurseCol(int row, int col, int direction) {
		int startRow = 1;
		if (direction == 1) {
			startRow = b.getBoardSize()-2;
		}

		boolean again = false;
		if (direction == -1) {//up
			if (row < b.getBoardSize()) {
				again = true;
			}
		}
		else {
			if (row >= 0) {//down
				again = true;
			}
		}

		if (again) {
			int current = b.getValue(row, col);
			int prev = b.getValue(row+direction, col);

			//if the current tile is null, move on to the next tile
			if (current == -1) {
				recurseCol(row-direction, col, direction);
			}
			else {
				//if current tile has the same value as the previous tile, add them
				if (current == prev) {
					Tile doubleTile = new Tile(2*current);
					b.setTile(row+direction, col, doubleTile);
					b.setTile(row, col, null);
					recurseCol(startRow, col, direction);
				}

				//If the previous tile is null, move our current tile there
				if (prev == -1) {
					b.setTile(row+direction, col, b.getTile(row, col));
					b.setTile(row, col, null);

					recurseCol(startRow, col, direction);
				}

				//If the current tile has a different value than previous, move on
				if (current != prev) {
					recurseCol(row-direction, col, direction);
				}
			}
		}
	}

	public void realRecurseDown(int col) {
		if(col < b.getBoardSize()) {
			recurseDown(b.getBoardSize()-2, col);
			realRecurseDown(col+1);
		}
	}

	private void recurseDown(int row, int col) {
		if (row >= 0) {
			int current = b.getValue(row, col);
			int prev = b.getValue(row+1, col);

			//if the current tile is null, move on to the next tile
			if (current == -1) {
				recurseDown(row-1, col);
			}
			else {
				//if current tile has the same value as the previous tile, add them
				if (current == prev) {
					Tile doubleTile = new Tile(2*current);
					b.setTile(row+1, col, doubleTile);
					b.setTile(row, col, null);
					recurseDown(b.getBoardSize()-2, col);
				}

				//If the tile below is null, move our tile there
				if (prev == -1) {
					b.setTile(row+1, col, b.getTile(row, col));
					b.setTile(row, col, null);

					recurseDown(b.getBoardSize()-2, col);
				}

				//If the current tile has a different value than the tile below, move on
				if (current != prev) {
					recurseDown(row-1, col);
				}
			}
		}
	}

	public void moveVertical(int i) {
		recurseVertical(0,i);

		checkWin();
		checkLoss();

		if(getBoard().hasEmpty()){//TODO: have to make sure it doesn't add for a move that changes nothing
			newTile();
		}
	}

	public void moveHorizontal(int i) {
		recurseHorizontal(0,i);

		checkWin();
		checkLoss();

		if(getBoard().hasEmpty()){//TODO: have to make sure it doesn't add for a move that changes nothing
			newTile();
		}
	}
}
