package model.commands;

import model.Direction;
import model.Level;
import model.LevelState;
import model.Move;
import util.Command;

import java.util.Stack;

/**
 * A MoveCommand is a Command that initiates a move of the player in a certain direction.
 * <p>
 * Undoing this command puts every affected tile back to its original position while ensuring model logic.
 */
public class MoveCommand implements Command {
    private final Level level;
    private final Direction dir;

    private int oldDiamondCount;

    private Stack<Move> oldPositions;

    private LevelState oldState;


    /**
     * Creates a new MoveCommand.
     *
     * @param level the level the move will be played on
     * @param dir   the direction of the move
     */
    public MoveCommand(Level level, Direction dir) {
        this.level = level;
        this.dir = dir;
    }

    @Override
    public void execute() {
        oldDiamondCount = level.getDiamondCount();
        oldState = level.getState();
        oldPositions = level.move(dir);
        if (oldPositions == null) {
            throw new IllegalArgumentException("Invalid move");
        }

    }

    @Override
    public void undo() {
        level.undoMove(oldPositions, oldDiamondCount, dir, oldState);
    }
}
