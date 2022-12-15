package view.javafx;

import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import model.Facade;
import util.Observer;

/**
 * An InfoBox is a TilePane that holds all information relative to a level.
 * <p>
 * Information such as the level's number, the current number of diamonds, the minimum number of diamonds required
 * to win and the number of lives left.
 */
public class InfoBox extends TilePane implements Observer {
    private final Text levelTxt = new Text("Lvl");
    private final Text levelNb = new Text("0");
    private final Text diamondTxt = new Text("Diam.");
    private final Text diamondNb = new Text("0");
    private final Text minDiamondTxt = new Text("Min.\nDiam.");
    private final Text minDiamondNb = new Text("0");

    private final Text remainDiamondsTxt = new Text("Remain.\nDiam.");
    private final Text remainDiamondsNb = new Text("0");
    private final Text livesTxt = new Text("Lives");
    private final Text livesNb = new Text("0");

    private final Facade game;

    public InfoBox(Facade game) {
        this.game = game;
        game.registerObserver(this);
        this.getChildren().addAll(
                levelTxt, levelNb,
                minDiamondTxt, minDiamondNb,
                diamondTxt, diamondNb,
                remainDiamondsTxt, remainDiamondsNb,
                livesTxt, livesNb);
    }

    @Override
    public void update() {
        levelNb.setText(String.valueOf(game.getLvlNumber() + 1));
        diamondNb.setText(String.valueOf(game.getDiamondCount()));
        minDiamondNb.setText(String.valueOf(game.getMinimumDiamonds()));
        remainDiamondsNb.setText(String.valueOf(game.getRemainingDiamonds()));
        livesNb.setText(String.valueOf(game.getNbOfLives()));
    }
}
