package project3;
import javax.swing.JOptionPane;

public class Starter {
	public int startBoard() {
		int boardSize = 4;
		String boardInput;
		boolean boardLoopAgain;
		do {
			// set loop condition to false to prevent infinite loop
			boardLoopAgain = false;
			boardInput = JOptionPane.showInputDialog(null, "Enter a board size between 4 and 10:");

			if (boardInput == null)
				System.exit(0);

			try {
				boardSize = Integer.parseInt(boardInput);
			} catch (NumberFormatException e) {
				boardLoopAgain = true;
				JOptionPane.showMessageDialog(null, "Please enter an integer between 4 and 10");
			}

			if (boardSize < 4 || boardSize > 10) {
				boardLoopAgain = true;
				JOptionPane.showMessageDialog(null, "Please enter an integer between 4 and 10");
			}

		} while (boardLoopAgain);
		
		return boardSize;
	}
	public double startNumToWin() {
		double numToWin = 2048;
		String numInput;
		boolean numLoopAgain;
		do {
			numLoopAgain = false;
			numInput = JOptionPane.showInputDialog(null, "Enter a power of 2 (>=8) needed to win:");

			if (numInput == null)
				System.exit(0);

			try {
				numToWin = Double.parseDouble(numInput);
			} catch (NumberFormatException e) {
				numLoopAgain = true;
				JOptionPane.showMessageDialog(null, "Please enter a valid power of 2 needed to win");
			}

			Tile t = new Tile();
			if (!t.power2(numToWin) || numToWin <8) {
				numLoopAgain = true;
				JOptionPane.showMessageDialog(null, "Please enter a valid power of 2 needed to win");
			}

		} while (numLoopAgain);
		
		return numToWin;
	}
}
