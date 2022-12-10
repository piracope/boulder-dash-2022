package view.console;

import controller.BoulderDash;
import model.Direction;
import model.Facade;
import view.View;

import java.util.EmptyStackException;
import java.util.Scanner;

public class ConsoleView implements View {
    private final BoulderDash game;

    public ConsoleView(BoulderDash controller, Facade model) {
        this.game = controller;
        ConsoleDisplay gameView = new ConsoleDisplay(model);
    }

    public void play() {
        Scanner sc = new Scanner(System.in);
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
                System.exit(0); // FIXME : this is weird
            } catch (EmptyStackException e) {
                System.out.println("No more moves in history.");
            }
        } while (game.isPlayOn());
    }

    private void processInput(String input) {
        switch (input) {
            case "right" -> game.move(Direction.RIGHT);
            case "left" -> game.move(Direction.LEFT);
            case "up" -> game.move(Direction.UP);
            case "down" -> game.move(Direction.DOWN);
            case "undo" -> game.undo();
            case "redo" -> game.redo();
            case "abandon" -> game.abandon();
            case "help" -> showHelp();
            default -> System.out.println("No such command.");
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

    public static void main(String[] args) {
        BoulderDash bd = new BoulderDash();
    }

}
