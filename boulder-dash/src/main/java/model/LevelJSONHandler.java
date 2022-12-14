package model;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * A Singleton handler of the JSON file where levels are stored.
 * <p>
 * This class, upon creation, will read and store all levels in memory, ready to be accessed.
 */
public class LevelJSONHandler {
    private static final String LEVELS_PATH = "/levels.json";
    private static LevelJSONHandler instance = null;
    private LevelJSON[] levels;

    private LevelJSONHandler() {
        if (instance == null) {
            Gson gson = new Gson();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            Objects.requireNonNull(
                                    getClass().getResourceAsStream(LEVELS_PATH)
                            )
                    ))) {
                levels = gson.fromJson(br, LevelJSON[].class);
            } catch (IOException e) {
                throw new RuntimeException("Cannot find " + LEVELS_PATH);
            }
        }

        instance = this;
    }

    /**
     * Returns the instance of this class, or creates it if none were made yet.
     *
     * @return this class' instance
     */
    public static LevelJSONHandler getInstance() {
        return instance == null ? new LevelJSONHandler() : instance;
    }

    /**
     * Returns the map of a given level.
     *
     * @param lvlNumber the number of the level to get the map from
     * @return the textual representation of the level's map
     */
    public String getMap(int lvlNumber) {
        return levels[lvlNumber].map;
    }

    /**
     * Returns the number of diamonds required to beat a given level.
     *
     * @param lvlNumber the level to get the minimum number of diamonds from
     * @return the minimum number of diamonds required to beat that level
     */
    public int getMinimumDiamonds(int lvlNumber) {
        return levels[lvlNumber].minimumDiamonds;
    }

    /**
     * Returns the length of a given level.
     * <p>
     * The length is the number of tiles the map takes horizontally.
     *
     * @param lvlNumber the level to get the length from
     * @return the level's horizontal length
     */
    public int getLength(int lvlNumber) {
        return levels[lvlNumber].length;
    }

    /**
     * Returns the height of a given level.
     * <p>
     * The height is the number of tiles the map takes vertically.
     *
     * @param lvlNumber the level to get the height from
     * @return the level's vertical height
     */
    public int getHeight(int lvlNumber) {
        return levels[lvlNumber].height;
    }

    public int getNbOfLevels() {
        return levels.length;
    }

    private static class LevelJSON {
        public String map;
        public int minimumDiamonds;
        public int length;
        public int height;

    }
}
