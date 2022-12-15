package view.console;

import controller.BoulderDash;
import model.Direction;
import model.Facade;
import model.LevelState;
import util.Observer;

import java.util.EmptyStackException;
import java.util.Scanner;

/**
 * A ConsoleView is a text-based view of the Boulder Dash game.
 * <p>
 * Upon creation, the game is immediately started.
 * <p>
 * First, it asks the player for the level to play, and then starts it.
 * Upon player death, a retry prompt is given. Another prompt is given to the player
 * when the game is over, asking them if they want to select another level.
 */
public class ConsoleView implements Observer {
    private final BoulderDash controller;
    private final Facade game;

    /**
     * Creates a new ConsoleView with a given controller and a model to observe.
     *
     * @param controller the controller to interact with
     * @param model      the model to observe
     */
    public ConsoleView(BoulderDash controller, Facade model) {
        this.controller = controller;
        this.game = model;

        game.registerObserver(this);
        this.play();
    }

    private void play() {
        // Showing help
        Scanner sc = new Scanner(System.in);
        System.out.println("Show help ? (y/n)");
        if (sc.nextLine().toLowerCase().charAt(0) == 'y') {
            showHelp();
        }

        do {
            // Level Selection
            int lvl = askForLevel("Choose your level ! [0-" + (controller.getNbOfLevels() - 1) + ']');
            controller.start(lvl);

            String input;
            do {
                // Actually playing

                System.out.print("> ");
                input = sc.nextLine().toLowerCase().strip();
                try {
                    processInput(input);
                } catch (EmptyStackException e) {
                    System.out.println("No more moves in history.");
                }
                if (game.getLevelState() == LevelState.CRUSHED) {
                    System.out.println("Wanna retry ? (y/n)");
                    if (sc.nextLine().toLowerCase().charAt(0) == 'y') {
                        controller.start(lvl);
                    }
                }
            } while (!game.isGameOver());

            System.out.println("Wanna go back to the level selection ? (y/n)");
        } while (sc.nextLine().toLowerCase().charAt(0) == 'y');
    }

    private int askForLevel(String message) {
        Scanner sc = new Scanner(System.in);
        int lvl = -1;
        while (lvl < 0 || lvl > controller.getNbOfLevels()) {
            System.out.println(message);
            while (!sc.hasNextInt()) {
                System.out.println("Not a valid number");
                System.out.println(message);
                sc.next();
            }

            lvl = Integer.parseInt(sc.next());
        }

        return lvl;
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
                System.out.println("Abandoned...");
            }
            case "help" -> showHelp();
            default -> System.out.println("No such command.");
        }
    }

    @Override
    public void update() {
        if (game.getLevelState() == LevelState.INVALID_MOVE) {
            System.out.println("Invalid Move!\n");
            return;
        }

        // Displaying the board
        System.out.println("Diam. : " + game.getDiamondCount()
                + " | Min. Diam. : " + game.getMinimumDiamonds()
                + " | Lvl : " + game.getLvlNumber()
                + " | Lives : " + game.getNbOfLives());
        System.out.println("-----------------------------------");
        System.out.println(game);
        System.out.println("-----------------------------------");

        // The messages
        switch (game.getLevelState()) {
            case CRUSHED -> System.out.println("You were crushed....");
            case WON -> System.out.println("You found the exit !!!");
            case REVEAL -> System.out.println("The exit has opened...");
        }

        // Game Over can be both WIN or complete loss, so we need to know if the player's crushed
        // You can't win when crushed
        if (game.isGameOver() && game.getLevelState() == LevelState.CRUSHED) {
            System.out.println("Game Over...");
        }

        System.out.println();
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
