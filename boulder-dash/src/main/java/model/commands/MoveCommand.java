package model.commands;

import model.*;
import model.tiles.FallingTile;
import model.tiles.Tile;
import util.Command;

import java.util.Map;
import java.util.Stack;

public class MoveCommand implements Command {
    private final Level level;
    private final Direction dir;

    private int oldDiamondCount;

    private Map<Tile, Position> oldPositions;



    public MoveCommand(Level level, Direction dir) {
        this.level = level;
        this.dir = dir;
    }

    @Override
    public void execute() {
        oldDiamondCount = level.getDiamondCount();
        oldPositions = level.move(dir);
        level.updateState();

        for (var thing : oldPositions.entrySet()) {
            System.out.println("" + thing.getKey() + thing.getValue().getY() + " " + thing.getValue().getX());
        }
    }

    @Override
    public void undo() {
        // TODO : implement this
        level.setDiamondCount(oldDiamondCount);
        level.changePlayerPos(dir.getOpposite());
        level.updateState();

        for (var thing : oldPositions.entrySet()) {
            level.setTile(thing.getKey(), thing.getValue());
            if(thing.getKey().canFall()) {
                ((FallingTile) thing.getKey()).updatePosition(thing.getValue());
            }
        }
    }
}
