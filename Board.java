package project3;

public class Board {
	private int boardSize;
    private LinkList<LinkList<Tile>> b;
    
    public Board(){
        boardSize = 4;
        b = new LinkList<>();

        for (int x = 0; x < boardSize; x++){
            LinkList<Tile> list = new LinkList<>();

            for (int y = 0; y < boardSize; y++){
                list.addEnd(null);
            }

            b.addEnd(list);
        }
    }
    
    public Board(int size){
        if (size < 4 || size > 10){
            throw new IllegalArgumentException();
        }
        else {
            boardSize = size;
            b = new LinkList<>();
            for (int x = 0; x < boardSize; x++){
                LinkList<Tile> list = new LinkList<>();

                for (int y = 0; y < boardSize; y++){
                    list.addEnd(null);
                }

                b.addEnd(list);
            }
        }
    }
    
    public int getBoardSize() {
    	return boardSize;
    }
    
//    public int getRow() {
//        return this.row;
//    }
//
//    public int getCol() {
//        return this.col;
//    }
    
    public boolean hasEmpty(){
        boolean isEmpty = false;
        Tile t;
        for (int x = 0; x < boardSize; x++){
            for (int y = 0; y < boardSize; y++){
            	t = getTile(x,y);              
            	
            	if (t == null){
                    isEmpty = true;
                    break;
                }
            }
        }
        return isEmpty;
    }
    
    public Tile getTile(int row, int col) {
        Tile t;
        if (row < 0 || col < 0 || row > boardSize || col > boardSize) {
            throw new IllegalArgumentException();
        }
        else {
            t = b.getNodeAt(row).getData().getNodeAt(col).getData();
        }
        return t;
    }
    
    public void setTile(int row, int col, Tile t){
        if (row < 0 || col < 0 || row > boardSize || col > boardSize) {
            throw new IllegalArgumentException();
        }
        else {
        	b.getNodeAt(row).getData().getNodeAt(col).setData(t);
        }
    }
    
    public int getValue(int row, int col){
        int value;

        if (row < 0 || col < 0 || row > boardSize || col > boardSize) {
            throw new IllegalArgumentException();
        }
        else {
            if (b.getNodeAt(row).getData().getNodeAt(col).getData() == null){
                value = -1;
            }
            else {
                value = b.getNodeAt(row).getData().getNodeAt(col).getData().getValue();
            }
        }
        return value;
    }
    
	public void printBoard() {
		Tile t;
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				t = getTile(i,j);
				if (t == null) {
					System.out.print("[ ]");
				}
				else {
					System.out.print("["+t+"]");
				}
				
			}
			System.out.println("\n");
		}
	}
}
