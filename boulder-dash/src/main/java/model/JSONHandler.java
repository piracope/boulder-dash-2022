package model;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class JSONHandler {
    private static JSONHandler instance = null;
    private static final String LEVELS_PATH = "/levels.json";

    private LevelJSON[] levels;

    private static class LevelJSON {
        public String map;
        public int minimumDiamonds;
        public int length;
        public int height;

    }

    public JSONHandler() {
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

    public static JSONHandler getInstance() {
        return instance == null ? new JSONHandler() : instance;
    }

    public String getMap(int lvlNumber) {
        return levels[lvlNumber].map;
    }

    public int getMinimumDiamonds(int lvlNumber) {
        return levels[lvlNumber].minimumDiamonds;
    }

    public int getLength(int lvlNumber) {
        return levels[lvlNumber].length;
    }

    public int getHeight(int lvlNumber) {
        return levels[lvlNumber].height;
    }
}
