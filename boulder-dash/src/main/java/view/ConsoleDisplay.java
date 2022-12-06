package view;

import model.Facade;
import util.Observer;

public class ConsoleDisplay implements Observer {
    private final Facade game;

    public ConsoleDisplay(Facade game) {
        this.game = game;
        game.registerObserver(this);
    }

    @Override
    public void update() {
        switch (game.getLevelState()) {
            case LOST -> System.out.println("You were crushed....");
            case WON -> System.out.println("You found the exit !!!");
            case PLAYING -> {
                System.out.println("D: " + game.getDiamondCount()
                        + " | MD : " + game.getMinimumDiamonds()
                        + " | Level : " + game.getLvlNumber());
                System.out.println("-----------------------------------");
                System.out.println(game);
            }
        }
    }
}