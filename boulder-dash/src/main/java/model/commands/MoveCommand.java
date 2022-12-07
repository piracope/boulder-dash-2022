package model.commands;

import model.*;
import util.Command;

public class MoveCommand implements Command {
    private final Level level;
    private final Direction dir;

    // to restore
    private String oldState;


    public MoveCommand(Level level, Direction dir) {
        this.level = level;
        this.dir = dir;
    }

    @Override
    public void execute() {
        oldState = level.toString();
        level.move(dir);
        level.updateState();

        // TODO : restoring diamondCount too
    }

    @Override
    public void undo() {
        // TODO : don't do that
        level.processMap(oldState);
        level.updateState();
    }
}
