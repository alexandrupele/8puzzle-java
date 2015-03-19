package Domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alexandru Pele on 3/16/2015.
 */

/**
 * This is a specialized puzzle state
 * The subclass implements a copy constructor for
 * easy expanding and also state altering methods
 */
public class MovablePuzzleState extends PuzzleState {

    private int order;
    private int[] blank;
    public static enum Step {UP, DOWN, LEFT, RIGHT};
    private List<Step> steps;

    /**
     * Constructor will not allow creating an instance
     * if there is no blank slot in the state
     * @param state initial state of the puzzle
     * @throws PuzzleStateNoBlankPosition
     */
    public MovablePuzzleState(int[][] state) throws PuzzleStateNoBlankPosition {
        super(state);
        
        order = state.length;
        steps = new ArrayList<Step>();
        blank = getBlankPosition();
    }

    /**
     * Performs a deep copy of reference type members
     * thus providing isolation between expanded states
     * @param other the state that will be expanded
     */
    public MovablePuzzleState(MovablePuzzleState other) {
        order = other.order;

        // Alloc new memory for non-primitive types
        state = new int[order][order];
        for (int i = 0; i < order; i++)
            for (int j = 0; j < order; j++)
                state[i][j] = other.state[i][j];

        steps = new ArrayList<Step>();
        for(Step step : other.steps) {
            steps.add(step);
        }

        blank = new int[] {other.blank[0], other.blank[1]};
    }

    /**
     * @precondition the state contains an empty slot
     * @return array of integers representing the position of the empty slot
     * @throws PuzzleStateNoBlankPosition
     */
    private int[] getBlankPosition() throws PuzzleStateNoBlankPosition {
        for (int i = 0; i < order; i++)
            for (int j = 0; j < order; j++)
                if (state[i][j] == 0) {
                    return new int[] {i, j};
                }
        throw new PuzzleStateNoBlankPosition("Empty slot not found");
    }

    public MovablePuzzleState moveUp() throws PuzzleStateNoBlankPosition, PuzzleStateInvalidMove {
        if (blank[0] == 0) {
            throw new PuzzleStateInvalidMove("Cannot move up any further");
        }

        MovablePuzzleState clone = new MovablePuzzleState(this);

        clone.state[blank[0]][blank[1]] = clone.state[blank[0] - 1][blank[1]];
        clone.state[blank[0] - 1][blank[1]] = 0;
        clone.blank[0]--;
        clone.steps.add(Step.UP);

        return clone;
    }

    public MovablePuzzleState moveDown() throws PuzzleStateNoBlankPosition, PuzzleStateInvalidMove {
        if (blank[0] == order - 1) {
            throw new PuzzleStateInvalidMove("Cannot move down any further");
        }

        MovablePuzzleState clone = new MovablePuzzleState(this);

        clone.state[blank[0]][blank[1]] = clone.state[blank[0] + 1][blank[1]];
        clone.state[blank[0] + 1][blank[1]] = 0;
        clone.blank[0]++;
        clone.steps.add(Step.DOWN);

        return clone;
    }

    public MovablePuzzleState moveLeft() throws PuzzleStateNoBlankPosition, PuzzleStateInvalidMove {
        if (blank[1] == 0) {
            throw new PuzzleStateInvalidMove("Cannot move left any further");
        }

        MovablePuzzleState clone = new MovablePuzzleState(this);

        clone.state[blank[0]][blank[1]] = clone.state[blank[0]][blank[1] - 1];
        clone.state[blank[0]][blank[1] - 1] = 0;
        clone.blank[1]--;
        clone.steps.add(Step.LEFT);

        return clone;
    }

    public MovablePuzzleState moveRight() throws PuzzleStateNoBlankPosition, PuzzleStateInvalidMove {
        if (blank[1] == order - 1) {
            throw new PuzzleStateInvalidMove("Cannot move down any further");
        }

        MovablePuzzleState clone = new MovablePuzzleState(this);

        clone.state[blank[0]][blank[1]] = clone.state[blank[0]][blank[1] + 1];
        clone.state[blank[0]][blank[1] + 1] = 0;
        clone.blank[1]++;
        clone.steps.add(Step.RIGHT);

        return clone;
    }

    public List<MovablePuzzleState> expandState() throws PuzzleStateNoBlankPosition {
        List<MovablePuzzleState> derivedStates = new LinkedList<MovablePuzzleState>();

        try {
            derivedStates.add(moveUp());
        } catch (PuzzleStateInvalidMove e) { }

        try {
            derivedStates.add(moveDown());
        } catch (PuzzleStateInvalidMove e) { }

        try {
            derivedStates.add(moveLeft());
        } catch (PuzzleStateInvalidMove e) { }

        try {
            derivedStates.add(moveRight());
        } catch (PuzzleStateInvalidMove e) { }

        return derivedStates;
    }

    public boolean isSolution() {
        // TODO: find better way of testing
        int maxPos = order - 1;

        if (state[0][0] != 0 && state[maxPos][maxPos] != 0) {
            // if it doesn't begin or end with blank, it's not a solution
            return false;
        }

        boolean modified = false;
        if (state[maxPos][maxPos] == 0) {
            modified = true;
            state[maxPos][maxPos] = order * order;
        }

        int prev = -1;
        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                if (prev == -1)
                    prev = state[i][j];
                else {
                    if (state[i][j] - prev != 1) {
                        if (modified) {
                            state[maxPos][maxPos] = 0;
                        }
                        return false;
                    }
                    prev = state[i][j];
                }
            }
        }

        if (modified) {
            state[maxPos][maxPos] = 0;
        }
        return true;
    }

    public List<Step> getSteps() {
        // Do not return the actual list. A copy is safer
        return new ArrayList<Step>(steps);
    }
}