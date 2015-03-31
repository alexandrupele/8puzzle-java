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
public class MutablePuzzleState extends PuzzleState {

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
    public MutablePuzzleState(int[][] state) throws PuzzleStateNoBlankPosition {
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
    public MutablePuzzleState(MutablePuzzleState other) {
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
    public int[] getBlankPosition() throws PuzzleStateNoBlankPosition {
        for (int i = 0; i < order; i++)
            for (int j = 0; j < order; j++)
                if (state[i][j] == 0) {
                    return new int[] {i, j};
                }
        throw new PuzzleStateNoBlankPosition("Empty slot not found");
    }

    public MutablePuzzleState moveUp() throws MutablePuzzleStateInvalidMove {
        if (blank[0] == 0) {
            throw new MutablePuzzleStateInvalidMove("Cannot move up any further");
        }

        MutablePuzzleState clone = new MutablePuzzleState(this);

        clone.state[blank[0]][blank[1]] = clone.state[blank[0] - 1][blank[1]];
        clone.state[blank[0] - 1][blank[1]] = 0;
        clone.blank[0]--;
        clone.steps.add(Step.UP);

        return clone;
    }

    public MutablePuzzleState moveDown() throws MutablePuzzleStateInvalidMove {
        if (blank[0] == order - 1) {
            throw new MutablePuzzleStateInvalidMove("Cannot move down any further");
        }

        MutablePuzzleState clone = new MutablePuzzleState(this);

        clone.state[blank[0]][blank[1]] = clone.state[blank[0] + 1][blank[1]];
        clone.state[blank[0] + 1][blank[1]] = 0;
        clone.blank[0]++;
        clone.steps.add(Step.DOWN);

        return clone;
    }

    public MutablePuzzleState moveLeft() throws MutablePuzzleStateInvalidMove {
        if (blank[1] == 0) {
            throw new MutablePuzzleStateInvalidMove("Cannot move left any further");
        }

        MutablePuzzleState clone = new MutablePuzzleState(this);

        clone.state[blank[0]][blank[1]] = clone.state[blank[0]][blank[1] - 1];
        clone.state[blank[0]][blank[1] - 1] = 0;
        clone.blank[1]--;
        clone.steps.add(Step.LEFT);

        return clone;
    }

    public MutablePuzzleState moveRight() throws MutablePuzzleStateInvalidMove {
        if (blank[1] == order - 1) {
            throw new MutablePuzzleStateInvalidMove("Cannot move down any further");
        }

        MutablePuzzleState clone = new MutablePuzzleState(this);

        clone.state[blank[0]][blank[1]] = clone.state[blank[0]][blank[1] + 1];
        clone.state[blank[0]][blank[1] + 1] = 0;
        clone.blank[1]++;
        clone.steps.add(Step.RIGHT);

        return clone;
    }

    public List<MutablePuzzleState> expandState()  {
        List<MutablePuzzleState> derivedStates = new LinkedList<MutablePuzzleState>();

        try {
            derivedStates.add(moveUp());
        } catch (MutablePuzzleStateInvalidMove e) { }

        try {
            derivedStates.add(moveDown());
        } catch (MutablePuzzleStateInvalidMove e) { }

        try {
            derivedStates.add(moveLeft());
        } catch (MutablePuzzleStateInvalidMove e) { }

        try {
            derivedStates.add(moveRight());
        } catch (MutablePuzzleStateInvalidMove e) { }

        return derivedStates;
    }

    public boolean isSolution() {
        int rightTile = 1, i, j;
        for (i = 0; i < order - 1; i++) {
            for (j = 0; j < order; j++) {
                if (state[i][j] != rightTile++) {
                    return false;
                }
            }
        }

        for (j = 0; j < order - 1; j++) {
            if (state[i][j] != rightTile++) {
                return false;
            }
        }

        return true;
    }

    public List<Step> getSteps() {
        // Do not return the actual list. A copy is safer
        return new ArrayList<Step>(steps);
    }

    public void clearSteps() {
        steps.clear();
    }

    public int getOrder() {
        return order;
    }
}