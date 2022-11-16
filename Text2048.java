package project3;

import java.util.Scanner;

public class Text2048 {
    private GameController game = new GameController();
    private Scanner scanner = new Scanner(System.in);

    public void run(){
        while(game.getStatus() == GameStatus.IN_PROGRESS){
            game.getB().printBoard();
            System.out.println("Move in which direction? (WASD): ");
            String direction = scanner.nextLine();
            System.out.println("\"" + direction + "\"");
            switch (direction){
                case "w":
                    game.moveVertical(-1);
                    break;
                case "s":
                    game.moveVertical(1);
                    break;
                case "a":
                    game.moveHorizontal(-1);
                    break;
                case "d":
                    game.moveHorizontal(1);
                    break;
                case "q":
                    System.out.println("Thank you for playing!\n\n");
                    System.exit(0);
                    break;
                default:
                    System.out.println("That is not a valid command.");
                    continue;
            }
            if(game.getB().hasEmpty()){
                game.newTile();
            }
        }

        if(game.getStatus() == GameStatus.WON){
            System.out.println("CONGRATULATIONS!\n\n");
        } else {
            System.out.println("Better luck next time.\n\n");
        }
    }

    public static void main(String[] args){
        Text2048 game = new Text2048();
        game.run();
    }
}
