package project3;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel{

	private JButton[][] jButtonsBoard;
	private GameController game;
	private Board board;
	
	private JButton upButton;
	private JButton downButton;
	private JButton leftButton;
	private JButton rightButton;
	
	private JMenuItem quit;
	private JMenuItem reset;


	private int BOARD_SIZE;

	public GamePanel(GameController aGame, JMenuItem quitItem, JMenuItem resetItem) {
		//this.game = game;
		game = aGame;
		quit = quitItem;
		reset = resetItem;
		board = game.getB();
		BOARD_SIZE = board.getBoardSize();

		GridLayout layout = new GridLayout(BOARD_SIZE, BOARD_SIZE);
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(layout);
		add(gamePanel);

		jButtonsBoard = new JButton[BOARD_SIZE][BOARD_SIZE];
		ButtonListener listener = new ButtonListener();
		for (int row = 0; row < BOARD_SIZE; row++)
			for (int col = 0; col < BOARD_SIZE; col++) {
				jButtonsBoard[row][col] = new JButton("");
				jButtonsBoard[row][col].setPreferredSize(new Dimension(80,80));
				jButtonsBoard[row][col].addActionListener(listener);
				gamePanel.add(jButtonsBoard[row][col]);
			}

		GridLayout buttonLayout = new GridLayout(2,1);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(buttonLayout);
		add(buttonPanel);
		
		MenuListener mListener = new MenuListener();
		quit.addActionListener(mListener);
		reset.addActionListener(mListener);

		upButton = new JButton("Up");
		upButton.addActionListener(listener);
		downButton = new JButton("Down");
		downButton.addActionListener(listener);
		leftButton = new JButton("left");
		leftButton.addActionListener(listener);
		rightButton = new JButton("right");
		rightButton.addActionListener(listener);
		buttonPanel.add(upButton);
		buttonPanel.add(downButton);
		buttonPanel.add(leftButton);
		buttonPanel.add(rightButton);
		

		
		addKeyListener(new MyKeyListener());
		setFocusable(true);
	}

	private void displayBoard() {

		Tile t;
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				t = board.getTile(i,j);
				if (t == null) {
					jButtonsBoard[i][j].setText(" "); 
				}
				else {
					jButtonsBoard[i][j].setText(""+t);
				}

			}
		}
	}
	
	private class MenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (quit == event.getSource()) {
				System.exit(0);
			}
			if (reset == event.getSource()) {
				game.reset();
			}
		}
	}
	
	//TODO: test for a win somewhere
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Determine which button was selected.
			if (upButton == e.getSource()) {
				game.recurseUp(0);
			}
			else if (downButton == e.getSource()) {
				game.recurseDown(0);
			}
			else if (leftButton == e.getSource()) {
				game.recurseLeft(0);
			}
			else if (rightButton == e.getSource()){
				game.recurseRight(0);
			}

			displayBoard();
		}
	}
	
	private class MyKeyListener implements KeyListener
	{
		public void keyPressed(KeyEvent e)
		{
		    if (e.getKeyCode() == KeyEvent.VK_UP ) {
	            game.recurseUp(0);
		    } 
		    else if (e.getKeyCode() == KeyEvent.VK_DOWN ) {
	            game.recurseDown(0);
		    }
		    else if (e.getKeyCode() == KeyEvent.VK_LEFT ) {
		    	game.recurseLeft(0);
		    } 
		    else if (e.getKeyCode() == KeyEvent.VK_RIGHT ) {
	            game.recurseRight(0);
		    } 
		    
		    displayBoard();
		}
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}
	}
}


