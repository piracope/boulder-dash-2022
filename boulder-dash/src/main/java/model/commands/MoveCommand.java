package model.commands;

import model.Direction;
import model.Level;
import model.tiles.FallingTile;
import model.tiles.Move;
import util.Command;

import java.util.Stack;

/**
 * A MoveCommand is a Command that initiates a move of the player in a certain direction.
 *
 * Undoing this command puts every affected tile back to its original position while ensuring model logic.
 */
public class MoveCommand implements Command {
    private final Level level;
    private final Direction dir;

    private int oldDiamondCount;

    private Stack<Move> oldPositions;


    /**
     * Creates a new MoveCommand.
     * @param level the level the move will be played on
     * @param dir the direction of the move
     */
    public MoveCommand(Level level, Direction dir) {
        this.level = level;
        this.dir = dir;
    }

    @Override
    public void execute() {
        oldDiamondCount = level.getDiamondCount();
        oldPositions = level.move(dir);
        level.updateState();

    }

    @Override
    public void undo() {
        // TODO : implement this
        level.setDiamondCount(oldDiamondCount);
        level.changePlayerPos(dir.getOpposite());

        while (!oldPositions.isEmpty()) {
            Move move = oldPositions.pop();
            level.setTile(move.tile(), move.position());
            if (move.tile().canFall()) {
                ((FallingTile) move.tile()).updatePosition(move.position());
            }
        }

        level.updateState();
    }
}
