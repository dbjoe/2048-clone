package project3;

import java.util.Random;

/**
 * A class that controls the 2048 game logic.
 * @author Gabe Kucinich, Zay Price, Joe Zylla
 * @version 11/21/2022
 */
public class GameController {
	/**
	 * The board - a LinkList of LinkLists containing Tiles
	 */
	private Board b;
	/**
	 * The status of the game - WON, LOST, or IN_PROGRESS
	 */
	private GameStatus status;
	/**
	 * The power of 2 needed to win the game
	 */
	private double winningValue;
	/**
	 * A random variable used to generate new Tiles
	 */
	private Random r;
	/**
	 * The number of games finished
	 */
	public static int numGames = 0;
	/**
	 * The number of games won
	 */
	public static int numWins = 0;
	/**
	 * Indicates whether the board has changed since the last move
	 */
	private boolean boardChanged;

	/******************************************************************
	 * Default constructor. Creates a 4x4 board with a value of
	 * 2048 needed to win.
	 *****************************************************************/
	GameController(){
		b = new Board();
		winningValue = 2048;
		r = new Random();
		status = GameStatus.IN_PROGRESS;
		boardChanged = false;
		newTile();
	}

	/******************************************************************
	 * Parameterized constructor.
	 * @param boardSize the length and width of the square board
	 * @param winningValue the power of 2 needed to win the game
	 *****************************************************************/
	GameController(int boardSize, double winningValue){
		b = new Board(boardSize);
		setWinningValue(winningValue);
		r = new Random();
		status = GameStatus.IN_PROGRESS;
		boardChanged = false;
		newTile();
	}

	/******************************************************************
	 * Gets the Board instance variable
	 * @return b, the Board instance variable
	 *****************************************************************/
	public Board getBoard() {
		return this.b;
	}

	/******************************************************************
	 * Sets the Board instance variable b
	 * @throws IllegalArgumentException if parameter is null
	 *****************************************************************/
	public void setBoard(Board board){
		if (board == null) {
			throw new IllegalArgumentException();
		}
		this.b = board;
	}

	/******************************************************************
	 * Gets the power of 2 needed to win
	 * @return the power of 2 needed to win 
	 *****************************************************************/
	public double getWinningValue(){
		return this.winningValue;
	}

	/******************************************************************
	 * Sets the power of 2 needed to win
	 * @param value the value needed to win the game
	 * @throws IllegalArgumentException if value is invalid
	 *****************************************************************/
	public void setWinningValue(double value){
		Tile t = new Tile();
		if(!t.power2(value)) {
			throw new IllegalArgumentException();
		}
		this.winningValue = value;
	}

	/******************************************************************
	 * Gets the status of the game - WON, LOST, or IN_PROGRESS
	 *****************************************************************/
	public GameStatus getStatus() {
		return this.status;
	}

	/******************************************************************
	 * Sets the status of the game - WON, LOST, or IN_PROGRESS
	 * @param status can be WON, LOST, or IN_PROGRESS
	 * @throws IllegalArgumentException if the parameter is null
	 *****************************************************************/
	public void setStatus(GameStatus status){
		if (status == null) {
			throw new IllegalArgumentException();
		}
		this.status = status;
	}

	/******************************************************************
	 * Gets the size of the board
	 * @return the size of the board
	 *****************************************************************/
	public int getBoardSize(){
		int b = getBoard().getBoardSize();
		return b;
	}

	/******************************************************************
	 * Sets the board size
	 * @param boardSize the size of the board
	 * @throws IllegalArgumentException if boardSize <4 or >10
	 *****************************************************************/
	public void setBoardSize(int boardSize){
		if (boardSize < 4 || boardSize > 10) {
			throw new IllegalArgumentException();
		}
		getBoard().setBoardSize(boardSize);
	}

	/******************************************************************
	 * Creates a new tile with a value of either 2 or 4 and places it
	 * at a random spot on the board
	 * @throws RuntimeException if the board is full
	 *****************************************************************/
	public void newTile(){
		if (!b.hasEmpty()) {
			throw new RuntimeException();
		}
		
		int ranRow;
		int ranCol;
		int ranValue = r.nextInt(2);
		Tile t;

		if (ranValue == 1){
			t = new Tile(2);
		}
		else{
			t = new Tile(4);
		}

		do {
			ranRow = r.nextInt(b.getBoardSize());
			ranCol = r.nextInt(b.getBoardSize());
		} while (b.getValue(ranRow,ranCol) != -1);

		b.setTile(ranRow, ranCol, t);
	}
	
	/******************************************************************
	 * Resets the board to a starting state
	 *****************************************************************/
	public void reset() {
		GameController.numGames++;

		Starter s = new Starter();
		int size = s.startBoard();
		double num = s.startNumToWin();

		Board b = new Board(size);
		setBoard(b);
		setWinningValue(num);
		setStatus(GameStatus.IN_PROGRESS);
		newTile();
	}

	/******************************************************************
	 * Checks for a winning value in each tile of the board
	 *****************************************************************/
	private void checkWin(){
		for (int row = 0; row < b.getBoardSize(); row++){
			for (int col = 0; col < b.getBoardSize(); col++){
				Tile t = b.getTile(row,col);
				if (t != null && t.getValue() >= winningValue){
					status = GameStatus.WON;
					numWins++;
				}
			}
		}
	}

	public void reset() {
		GameController.numGames++;

		Starter s = new Starter();
		int size = s.startBoard();
		double num = s.startNumToWin();

		Board b = new Board(size);
		setBoard(b);
		setWinningValue(num);
		setStatus(GameStatus.IN_PROGRESS);
		newTile();

	}

	/******************************************************************
	 * Checks for a loss by determining if the board is full and there
	 * are no more moves
	 *****************************************************************/
	private void checkLoss(){
		boolean noNeighbor = true;
		if (!b.hasEmpty() && getStatus() != GameStatus.WON) {
			for (int row = 0; row < b.getBoardSize(); row++){
				for (int col = 0; col < b.getBoardSize(); col++){
					if (findSimilarNeighbors(row, col)){
						noNeighbor = false;
					}
				}
			}

			if (noNeighbor) {
				status = GameStatus.LOST;
			}
		}
	}

	/******************************************************************
	 * Checks tiles above, below, to the left, and to the right of a 
	 * tile to see if they can be combined
	 * @param row the row of the tile we're checking around
	 * @param col the column of the tile we're checking around
	 * @return true if the tile at (row,col) can be combined with a
	 * 			neighboring tile, false otherwise
	 *****************************************************************/
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


	/******************************************************************
	 * Calls recurseRow to add/move tiles horizontally for each row of
	 * the board. Passes in the second from the left column as a 
	 * starting point if we are recursing left, and second from the 
	 * right column if we are recursing right.
	 * @param row the row we are currently recursing across
	 * @param direction a value of -1 to recurse left and a value of 1
	 * 		  to recurse right
	 *****************************************************************/
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

	/******************************************************************
	 * Recurses across a single row to add/move tiles to the left or
	 * right.
	 * @param row the current row of the Tile we are checking
	 * @param col the current column of the Tile we are checking
	 * @param direction a value of -1 to indicate recursing left or a
	 * 		  value of 1 to indicated recursing right
	 *****************************************************************/
	private void recurseRow(int row, int col, int direction) {
		int startCol = 1;
		//if we are moving tiles to the right, our starting tile will 
		//be in the second from the right column
		if (direction == 1) {
			startCol = b.getBoardSize()-2;
		}


		boolean again = false;
		//If we're recursing left, we stop recursing when 
		//col is equal to boardSize
		if (direction == -1) {
			if (col < b.getBoardSize()) {
				again = true;
			}
		}
		else {
			//If we're recursing right, we stop recursing when 
			//col less than 0
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
				//if current tile has the same value as the previous
				//tile, add them
				if (current == prev) {
					boardChanged = true;
					Tile doubleTile = new Tile(2*current);
					b.setTile(row, col+direction, doubleTile);
					b.setTile(row, col, null);
					recurseRow(row, startCol, direction);
				}

				//If the previous tile is null, move our current tile
				//to that location
				if (prev == -1) {
					boardChanged = true;
					b.setTile(row, col+direction, b.getTile(row, col));
					b.setTile(row, col, null);

					recurseRow(row, startCol, direction);
				}

				//If the current tile has a different value than the
				//previous tile, move on to the next tile
				if (current != prev) {
					recurseRow(row, col-direction, direction);
				}
			}
		}
	}

	/******************************************************************
	 * Calls recurseCol to add/move tiles vertically for each column of
	 * the board. Passes in the second from the top row as a 
	 * starting point if we are recursing up, and second from the 
	 * bottom row if we are recursing down.
	 * @param col the column we are currently recursing up/down
	 * @param direction a value of -1 to recurse up and a value of 1
	 * 		  to recurse down
	 *****************************************************************/
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

	/******************************************************************
	 * Recurses up/down a single column to add/move tiles up or down.
	 * @param row the current row of the Tile we are checking
	 * @param col the current column of the Tile we are checking
	 * @param direction a value of -1 to indicate recursing up or a
	 * 		  value of 1 to indicated recursing down
	 *****************************************************************/
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
				//if current tile has the same value as the previous
				//tile, add them
				if (current == prev) {
					boardChanged = true;
					Tile doubleTile = new Tile(2*current);
					b.setTile(row+direction, col, doubleTile);
					b.setTile(row, col, null);
					recurseCol(startRow, col, direction);
				}

				//If the previous tile is null, move our current tile
				//to that location
				if (prev == -1) {
					boardChanged = true;
					b.setTile(row+direction, col, b.getTile(row, col));
					b.setTile(row, col, null);

					recurseCol(startRow, col, direction);
				}

				//If the current tile has a different value than 
				//the previous tile, move on
				if (current != prev) {
					recurseCol(row-direction, col, direction);
				}
			}
		}
	}

	/******************************************************************
	 * Calls recurseVertical with the appropriate starting column
	 * and direction. Then calls checkWin, checkLoss, and if a move was
	 * made, calls newTile.
	 * @param i the direction we are moving (-1 for up, 1 for down)
	 *****************************************************************/
	public void moveVertical(int i) {
		recurseVertical(0,i);

		if (boardChanged) {
			checkWin();
			try {
				newTile();
				boardChanged = false;
			} catch(RuntimeException e) {
				
			}
		}
		checkLoss();
	}

	/******************************************************************
	 * Calls recurseHorizontal with the appropriate starting row
	 * and direction. Then calls checkWin, checkLoss, and if a move was
	 * made, calls newTile.
	 * @param i the direction we are moving (-1 for left, 1 for right)
	 *****************************************************************/
	public void moveHorizontal(int i) {
		recurseHorizontal(0,i);

		if (boardChanged) {
			checkWin();
			try {
				newTile();
				boardChanged = false;
			} catch(RuntimeException e) {
				
			}
		}
		checkLoss();
	}
}
