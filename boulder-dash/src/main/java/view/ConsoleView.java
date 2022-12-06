package view;

import model.Direction;
import model.Game;
import util.Observer;

import java.util.Scanner;

public class ConsoleView implements Observer {
    private final Game game;

    public ConsoleView() {
        this.game = new Game();
        game.registerObserver(this);
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        int currLevel = 1;
        game.start(currLevel);
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

    @Override
    public void update() {
        System.out.println(game.toString());
    }

    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        view.start();
    }

}
