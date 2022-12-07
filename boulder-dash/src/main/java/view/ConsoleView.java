package view;

import controller.BoulderDash;
import model.Direction;

import java.util.Scanner;

public class ConsoleView {
    private final BoulderDash game;

    public ConsoleView() {
        this.game = new BoulderDash();
    }

    public void play() {
        Scanner sc = new Scanner(System.in);
        ConsoleDisplay gameView = new ConsoleDisplay(game.getFacade());
        game.start();
        String input;
        do {
            System.out.print("> ");
            input = sc.nextLine().toLowerCase().strip();
            try {
                processInput(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Cannot move in this direction!");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("You won !!!");
                System.exit(0);
            }
        } while (game.isPlayOn());
    }

    private void processInput(String input) {
        switch (input) {
            case "right" -> game.move(Direction.RIGHT);
            case "left" -> game.move(Direction.LEFT);
            case "up" -> game.move(Direction.UP);
            case "down" -> game.move(Direction.DOWN);
            case "abandon" -> game.abandon();
            default -> System.out.println("No such direction.");
        }
    }

    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        view.play();
    }

}
