package view.javafx;

import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import model.Facade;
import util.Observer;

public class InfoBox extends TilePane implements Observer {
    private final Text levelTxt = new Text("Level");
    private final Text levelNb = new Text("0");
    private final Text diamondTxt = new Text("Diamonds");
    private final Text diamondNb = new Text("0");
    private final Text minDiamondTxt = new Text("Min.\nDiamonds");
    private final Text minDiamondNb = new Text("0");
    private final Text livesTxt = new Text("Lives");
    private final Text livesNb = new Text("0");

    private final Facade game;

    public InfoBox(Facade game) {
        this.game = game;
        game.registerObserver(this);
        this.getChildren().addAll(
                levelTxt, levelNb,
                diamondTxt, diamondNb,
                minDiamondTxt, minDiamondNb,
                livesTxt, livesNb);
    }

    @Override
    public void update() {
        levelNb.setText(String.valueOf(game.getLvlNumber()));
        diamondNb.setText(String.valueOf(game.getDiamondCount()));
        minDiamondNb.setText(String.valueOf(game.getMinimumDiamonds()));
        livesNb.setText(String.valueOf(game.getNbOfLives()));
    }
}
