package model.commands;

import model.*;
import util.Command;

public class MoveCommand implements Command {
    private final Level level;
    private final Direction dir;

    private int oldDiamoundCount;



    public MoveCommand(Level level, Direction dir) {
        this.level = level;
        this.dir = dir;
    }

    @Override
    public void execute() {
        oldDiamoundCount = level.getDiamondCount();
        level.move(dir);
        level.updateState();
    }

    @Override
    public void undo() {
        // TODO : implement this
        level.setDiamondCount(oldDiamoundCount);
        level.updateState();
    }
}
