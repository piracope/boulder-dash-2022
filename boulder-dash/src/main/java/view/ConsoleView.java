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
            processInput(input);
        } while (!input.equals("stop"));
    }

    private void processInput(String input) {
        switch (input) {
            case "right" -> game.move(Direction.RIGHT);
            case "left" -> game.move(Direction.LEFT);
            case "up" -> game.move(Direction.UP);
            case "down" -> game.move(Direction.DOWN);
            default -> System.out.println("No such direction.");
        }
    }

    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        view.play();
    }

}
