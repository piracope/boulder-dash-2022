package view.javafx;

import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import model.Facade;
import util.Observer;

/* TODO : this info box kinda sucks
*   like, some people did some really cool things, like having a cool UI that was
*   identical to the 1984 game so this kinda pales in comparison
*/
public class InfoBox extends TilePane implements Observer {
    private final Text levelTxt = new Text("Level");
    private final Text levelNb = new Text("0");
    private final Text diamondTxt = new Text("D");
    private final Text diamondNb = new Text("0");
    private final Text minDiamondTxt = new Text("MD");
    private final Text minDiamondNb = new Text("0");
    private final Text livesTxt = new Text("❤️");
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
