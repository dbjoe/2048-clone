package project3;
import javax.swing.*;
import java.awt.*;
public class GUI2048 {
	private JFrame frame;
	
	public JFrame getFrame() {
		return frame;
	}
	
	GUI2048(GameController game) {
		JMenu fileMenu;
		JMenuItem quitItem;
		JMenuItem resetItem;
		JMenuBar menus;

		fileMenu = new JMenu("File");
		quitItem = new JMenuItem ("Quit");
		resetItem = new JMenuItem("Reset");

		fileMenu.add(quitItem);
		fileMenu.add(resetItem);
		menus = new JMenuBar();
		menus.add(fileMenu);

		GamePanel panel = new GamePanel(game, quitItem, resetItem);
		frame = new JFrame("2048");
		
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 700);
		frame.setPreferredSize(new Dimension(1000, 700));
		frame.setJMenuBar(menus);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		Starter s = new Starter();
		int boardSize = s.startBoard();
		double numToWin = s.startNumToWin();
		GameController game = new GameController(boardSize, numToWin);
		GUI2048 gui = new GUI2048(game);
	}
}
