package view.console;

import controller.BoulderDash;
import model.Direction;
import model.Facade;
import model.LevelState;
import view.View;

import java.util.EmptyStackException;
import java.util.Scanner;

public class ConsoleView implements View {
    private final BoulderDash controller;
    private final Facade game;

    public ConsoleView(BoulderDash controller, Facade model) {
        this.controller = controller;
        this.game = model;

        game.registerObserver(this);
        this.play();
    }

    public void play() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose your level ! [0-" + controller.getNbOfLevels() + ']');
        int lvl = Integer.parseInt(sc.nextLine());
        controller.start(lvl);
        System.out.println("Starting...");

        String input;
        while (!game.isGameOver()) {
            do {
                System.out.print("> ");
                input = sc.nextLine().toLowerCase().strip();
                try {
                    processInput(input);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("This level does not exist!");
                } catch (EmptyStackException e) {
                    System.out.println("No more moves in history.");
                }
            } while (!game.isGameOver() && game.getLevelState() != LevelState.CRUSHED);

            System.out.println("Wanna retry ? (y/n)");
            if (sc.nextLine().toLowerCase().charAt(0) == 'y') {
                controller.start(lvl);
            }
        }


        System.exit(0); // when started with JavaFX
    }

    private void processInput(String input) {
        switch (input) {
            case "right" -> controller.move(Direction.RIGHT);
            case "left" -> controller.move(Direction.LEFT);
            case "up" -> controller.move(Direction.UP);
            case "down" -> controller.move(Direction.DOWN);
            case "undo" -> controller.undo();
            case "redo" -> controller.redo();
            case "abandon" -> {
                controller.abandon();
                System.out.println("Bye Bye !");
            }
            case "help" -> showHelp();
            default -> System.out.println("No such command.");
        }
    }

    @Override
    public void update() {
        if (game.getLevelState() == LevelState.INVALID_MOVE) {
            System.out.println("Invalid Move!");
            return;
        }

        System.out.println("D: " + game.getDiamondCount()
                + " | MD : " + game.getMinimumDiamonds()
                + " | Level : " + game.getLvlNumber()
                + " | Lives : " + game.getNbOfLives());
        System.out.println("-----------------------------------");
        System.out.println(game);
        switch (game.getLevelState()) {
            case CRUSHED -> System.out.println("You were crushed....");
            case WON -> System.out.println("You found the exit !!!");
        }
        if (game.isGameOver() && game.getLevelState() == LevelState.CRUSHED) {
            System.out.println("Game Over...");
        }
    }

    private void showHelp() {
        System.out.println("""
                ------------------------
                        Commands
                ------------------------
                left/right/up/down  : make a move in a given direction
                undo : cancel the last move
                redo : do the last move cancelled
                abandon : quit the game
                help : show this help
                ------------------------
                       How to play
                ------------------------
                Littered in the map are diamonds (d). Collect as many as you can!
                When you get enough diamonds, the exit (P) will be opened.
                Your number of diamonds (D: ??) must be equals to the minimum diamonds
                needed for this level (MD: ??) for you to be able to exit to the next level.
                                
                But beware! There are rocks (r) in the cave, and if by misfortune you find
                yourself under one of them, they'll fall and crush you, and you'll lose a life!
                                
                The game ends either when you lost all your lives or if there are no levels to play anymore (you won).
                ------------------------
                    Tile description
                ------------------------
                ' ' -> an empty space.
                 .  -> Soil. The player can dig it by moving into it.
                 r  -> A Rock. It will fall when there's empty space underneath it. It can also slide on other
                       rocks and diamonds and fall at their diagonal. If the player is under a falling rock, he dies.
                 d  -> A Diamond. If the player walks into one, he will collect it. When enough diamonds are collected,
                       the exit will become available. Beware that Diamonds can crush you like Rocks!!
                 w  -> A Wall. An impenetrable tile.
                 P  -> The exit. The exit acts like a wall when not enough diamonds were collected. If the player enters
                       the exit, he won the level and the next one is started.
                """);
    }
}
