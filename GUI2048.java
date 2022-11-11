package project3;
import javax.swing.*;
import java.awt.*;
public class GUI2048 {

	public static void main(String[] args) {
		int boardSize = 4;
		double numToWin = 2048;
		String boardInput;
		String numInput;

//		boolean boardLoopAgain;
//		do {
//			// set loop condition to false to prevent infinite loop
//			boardLoopAgain = false;
//			boardInput = JOptionPane.showInputDialog(null, "Enter a board size between 4 and 10:");
//
//			if (boardInput == null)
//				System.exit(0);
//
//			try {
//				boardSize = Integer.parseInt(boardInput);
//			} catch (NumberFormatException e) {
//				boardLoopAgain = true;
//				JOptionPane.showMessageDialog(null, "Please enter an integer between 4 and 10");
//			}
//
//			if (boardSize < 4 || boardSize > 10) {
//				boardLoopAgain = true;
//				JOptionPane.showMessageDialog(null, "Please enter an integer between 4 and 10");
//			}
//
//		} while (boardLoopAgain);
//
//		boolean numLoopAgain;
//		do {
//			numLoopAgain = false;
//			numInput = JOptionPane.showInputDialog(null, "Enter a power of 2 needed to win:");
//
//			if (numInput == null)
//				System.exit(0);
//
//			try {
//				numToWin = Double.parseDouble(numInput);
//			} catch (NumberFormatException e) {
//				numLoopAgain = true;
//				JOptionPane.showMessageDialog(null, "Please enter a valid power of 2 needed to win");
//			}
//			
//			Tile t = new Tile();
//			if (!t.power2(numToWin)) {
//				numLoopAgain = true;
//				JOptionPane.showMessageDialog(null, "Please enter a valid power of 2 needed to win");
//			}
//
//		} while (numLoopAgain);


		GameController game = new GameController(boardSize, numToWin);

		JFrame gui = new JFrame("2048");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GamePanel panel = new GamePanel(game);
		gui.getContentPane().add(panel);

		gui.setSize(1100, 700);
		gui.setPreferredSize(new Dimension(1100, 700));
		gui.pack();
		gui.setVisible(true);
	}
}
