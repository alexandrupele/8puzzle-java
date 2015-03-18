package Domain;

import java.util.ArrayList;
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
    public enum Step {UP, DOWN, LEFT, RIGHT};
    private List<Step> steps;

    public MovablePuzzleState(int[][] state) {
        super(state);
        order = state.length;
        steps = new ArrayList<Step>();
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
        for(Step step : other.steps)
            steps.add(step);
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
        int[] pos = getBlankPosition();
        if (pos[0] == 0) {
            throw new PuzzleStateInvalidMove("Cannot move up any further");
        }

        MovablePuzzleState cp = new MovablePuzzleState(this);

        cp.state[pos[0]][pos[1]] = cp.state[pos[0] - 1][pos[1]];
        cp.state[pos[0] - 1][pos[1]] = 0;

        steps.add(Step.UP);
        return cp;
    }

    public MovablePuzzleState moveDown() throws PuzzleStateNoBlankPosition, PuzzleStateInvalidMove {
        int[] pos = getBlankPosition();
        if (pos[0] == order - 1) {
            throw new PuzzleStateInvalidMove("Cannot move down any further");
        }

        MovablePuzzleState cp = new MovablePuzzleState(this);

        cp.state[pos[0]][pos[1]] = cp.state[pos[0] + 1][pos[1]];
        cp.state[pos[0] + 1][pos[1]] = 0;

        steps.add(Step.DOWN);
        return cp;
    }

    public MovablePuzzleState moveLeft() throws PuzzleStateNoBlankPosition, PuzzleStateInvalidMove {
        int[] pos = getBlankPosition();
        if (pos[1] == 0) {
            throw new PuzzleStateInvalidMove("Cannot move left any further");
        }

        MovablePuzzleState cp = new MovablePuzzleState(this);

        cp.state[pos[0]][pos[1]] = cp.state[pos[0]][pos[1] - 1];
        cp.state[pos[0]][pos[1] - 1] = 0;

        steps.add(Step.LEFT);
        return cp;
    }

    public MovablePuzzleState moveRight() throws PuzzleStateNoBlankPosition, PuzzleStateInvalidMove {
        int[] pos = getBlankPosition();
        if (pos[1] == order - 1) {
            throw new PuzzleStateInvalidMove("Cannot move down any further");
        }

        MovablePuzzleState cp = new MovablePuzzleState(this);

        cp.state[pos[0]][pos[1]] = cp.state[pos[0]][pos[1] + 1];
        cp.state[pos[0]][pos[1] + 1] = 0;


        steps.add(Step.RIGHT);
        return cp;
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
        return steps;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}