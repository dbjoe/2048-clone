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

	private JLabel numGamesLabel;
	private JLabel numWinsLabel;

	private JMenuItem quit;
	private JMenuItem reset;

	private JPanel gamePanel;
	private JPanel sidePanel;

	/*
	 * RGB values from
	 * https://github.com/daniel-huang-1230/Game-2048/blob/master/Gui2048.java
	 */
	private static final Color COLOR_EMPTY = new Color(204, 192, 179);
	private static final Color COLOR_2 = new Color(238, 228, 218);
	private static final Color COLOR_4 = new Color(237, 224, 200);
	private static final Color COLOR_8 = new Color(242, 177, 121);
	private static final Color COLOR_16 = new Color(245, 149, 99);
	private static final Color COLOR_32 = new Color(246, 124, 95);
	private static final Color COLOR_64 = new Color(246, 94, 59);
	private static final Color COLOR_128 = new Color(237, 207, 114);
	private static final Color COLOR_256 = new Color(237, 204, 97);
	private static final Color COLOR_512 = new Color(237, 200, 80);
	private static final Color COLOR_1024 = new Color(237, 197, 63);
	private static final Color COLOR_2048 = new Color(237, 194, 46);

	private int BOARD_SIZE;

	public GamePanel(GameController aGame, JMenuItem quitItem, JMenuItem resetItem) {
		//this.game = game;
		game = aGame;
		quit = quitItem;
		reset = resetItem;

		MenuListener mListener = new MenuListener();
		quit.addActionListener(mListener);
		reset.addActionListener(mListener);

		setUpBoard(game);
		displayBoard();
	}

	private void setUpBoard(GameController game) {
		board = game.getBoard();
		BOARD_SIZE = board.getBoardSize();

		GridLayout layout = new GridLayout(BOARD_SIZE, BOARD_SIZE);

		gamePanel = new JPanel();
		gamePanel.setLayout(layout);
		add(gamePanel);

		jButtonsBoard = new JButton[BOARD_SIZE][BOARD_SIZE];
		int buttonSize = 600/BOARD_SIZE;

		for (int row = 0; row < BOARD_SIZE; row++)
			for (int col = 0; col < BOARD_SIZE; col++) {
				jButtonsBoard[row][col] = new JButton("");
				jButtonsBoard[row][col].setPreferredSize(new Dimension(buttonSize,buttonSize));

				gamePanel.add(jButtonsBoard[row][col]);
			}

		ButtonListener listener = new ButtonListener();
		GridLayout sideLayout = new GridLayout(5,3);
		sidePanel = new JPanel();
		sidePanel.setLayout(sideLayout);
		add(sidePanel);

		upButton = new JButton("Up");
		upButton.addActionListener(listener);
		upButton.setFocusable(false);
		downButton = new JButton("Down");
		downButton.addActionListener(listener);
		downButton.setFocusable(false);
		leftButton = new JButton("left");
		leftButton.addActionListener(listener);
		leftButton.setFocusable(false);
		rightButton = new JButton("right");
		rightButton.addActionListener(listener);
		rightButton.setFocusable(false);

		sidePanel.add(new JLabel(""));
		sidePanel.add(upButton);
		sidePanel.add(new JLabel(""));

		sidePanel.add(leftButton);
		sidePanel.add(new JLabel(""));
		sidePanel.add(rightButton);

		sidePanel.add(new JLabel(""));
		sidePanel.add(downButton);
		sidePanel.add(new JLabel(""));

		numGamesLabel = new JLabel(String.valueOf(GameController.numGames));
		numWinsLabel = new JLabel(String.valueOf(GameController.numWins));

		sidePanel.add(new JLabel("Games played: "));
		sidePanel.add(numGamesLabel);
		sidePanel.add(new JLabel(""));

		sidePanel.add(new JLabel("Games won: "));
		sidePanel.add(numWinsLabel);

		addKeyListener(new MyKeyListener());
		setFocusable(true); 
	}

	private void reset() {
		GameController.numGames++;
		removeAll();

		Starter s = new Starter();
		int size = s.startBoard();
		double num = s.startNumToWin();

		Board b = new Board(size);
		game.setBoard(b);
		game.setWinningValue(num);
		game.setStatus(GameStatus.IN_PROGRESS);
		setUpBoard(game);
		game.newTile();

		revalidate();
		repaint();
		displayBoard();
	}

	private void displayBoard() {
		Tile t;
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				t = board.getTile(i,j);
				if (t == null) {
					jButtonsBoard[i][j].setText(" "); 
					jButtonsBoard[i][j].setBackground(COLOR_EMPTY);
				}
				else {
					jButtonsBoard[i][j].setText(""+t);

					Color c;
					switch(t.getValue()) {
					case 2: c = COLOR_2;
					break;
					case 4: c = COLOR_4;
					break;
					case 8: c = COLOR_8;
					break;
					case 16: c = COLOR_16;
					break;
					case 32: c = COLOR_32;
					break;
					case 64: c = COLOR_64;
					break;
					case 128: c = COLOR_128;
					break;
					case 256: c = COLOR_256;
					break;
					case 512: c = COLOR_512;
					break;
					case 1024: c = COLOR_1024;
					break;
					case 2048: c = COLOR_2048;
					break;
					default: c = Color.black;
					break;
					}

					jButtonsBoard[i][j].setBackground(c);
					
					if (t.getValue() != 2 && t.getValue() != 4) {
						jButtonsBoard[i][j].setForeground(Color.WHITE);
					}
					else {
						jButtonsBoard[i][j].setForeground(Color.BLACK);
					}
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
				reset();
			}
		}
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Determine which button was selected.
			if (upButton == e.getSource()) {
				game.moveVertical(-1);
			}
			else if (downButton == e.getSource()) {
				game.moveVertical(1);
			}
			else if (leftButton == e.getSource()) {
				game.moveHorizontal(-1);
			}
			else if (rightButton == e.getSource()){
				game.moveHorizontal(1);
			}

			displayBoard();

			if(game.getStatus() == GameStatus.WON) {
				JOptionPane.showMessageDialog(null, "You win! Congratulations!");
				reset();
			}
			else if(game.getStatus() == GameStatus.LOST) {
				JOptionPane.showMessageDialog(null, "Better luck next time");
				reset();
			}
		}
	}

	private class MyKeyListener implements KeyListener
	{
		public void keyPressed(KeyEvent e)
		{
			if (e.getKeyCode() == KeyEvent.VK_UP ) {
				game.moveVertical(-1);
			} 
			else if (e.getKeyCode() == KeyEvent.VK_DOWN ) {
				game.moveVertical(1);
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT ) {
				game.moveHorizontal(-1);
			} 
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT ) {
				game.moveHorizontal(1);
			} 

			displayBoard();

			if(game.getStatus() == GameStatus.WON) {
				JOptionPane.showMessageDialog(null, "You win! Congratulations!");
				reset();
			}
			else if(game.getStatus() == GameStatus.LOST) {
				JOptionPane.showMessageDialog(null, "Better luck next time");
				reset();
			}

		}
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}
	}
}


