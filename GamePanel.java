package project3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel{

	private JButton[][] jButtonsBoard;
	private GameController game;
	private Board board;
	private JButton upButton;
	private JButton downButton;
	private JButton leftButton;
	private JButton rightButton;

	private int BOARD_SIZE;

	public GamePanel(GameController aGame) {
		//this.game = game;
		game = aGame;
		board = game.getB();
		BOARD_SIZE = board.getBoardSize();

		String projectDir = "src/project2/";

		GridLayout layout = new GridLayout(BOARD_SIZE, BOARD_SIZE);
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(layout);
		add(gamePanel);

		jButtonsBoard = new JButton[BOARD_SIZE][BOARD_SIZE];
		ButtonListener listener = new ButtonListener();
		for (int row = 0; row < BOARD_SIZE; row++)
			for (int col = 0; col < BOARD_SIZE; col++) {
				jButtonsBoard[row][col] = new JButton("");
				jButtonsBoard[row][col].setPreferredSize(new Dimension(100,100));
				jButtonsBoard[row][col].addActionListener(listener);
				gamePanel.add(jButtonsBoard[row][col]);
			}

		GridLayout buttonLayout = new GridLayout(2,1);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(buttonLayout);
		add(buttonPanel);

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
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Determine which button was selected.
			if (upButton == e.getSource()) {
				
			}
			else if (downButton == e.getSource()) {

			}
			else if (leftButton == e.getSource()) {

			}
			else {
				//right button was selected
			}

			displayBoard();

			// Determine if there is a winner by asking the game object. (see step 6)
			if (game.getStatus().equals(GameStatus.IN_PROGRESS)) {
				displayBoard();
			} 
			else if (game.getStatus().equals(GameStatus.WON)) {
				JOptionPane.showMessageDialog(null, "You win!\nThe game will reset");
				game.reset();
				displayBoard();
			} 
			else { //game was lost
				JOptionPane.showMessageDialog(null, "Game over.\nThe game will reset");
				game.reset();
				displayBoard();
			} 
		}
	}
}


