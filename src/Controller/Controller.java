package Controller;

import Algorithms.SearchAlgorithm;
import Algorithms.SearchFailedException;
import Domain.*;

import java.util.*;

/**
 * Created by Alexandru Pele on 3/16/2015.
 */
public class Controller {

    private SearchAlgorithm algo;

    private int difficulty;
    private int puzzleOrder;

    public Controller(SearchAlgorithm algo, int puzzleOrder) {
        this.algo = algo;
        this.puzzleOrder = puzzleOrder;
    }

    public MutablePuzzleState getSolution(MutablePuzzleState initialState) throws PuzzleStateNoBlankPosition, PuzzleStateUnsolvable {
        try {
            return algo.getFinalState(initialState);
        } catch (SearchFailedException ex) {
            throw new PuzzleStateUnsolvable("There are no solutions for this configuration");
        }
    }

    public MutablePuzzleState getScrambledState() {
        MutablePuzzleState state;
        try {
            // Create default state
            int current = 1;
            int[][] stateArray = new int[puzzleOrder][puzzleOrder];
            for (int i = 0; i < puzzleOrder - 1; i++)
                for (int j = 0; j < puzzleOrder; j++)
                    stateArray[i][j] = current++;
            for (int j = 0; j < puzzleOrder - 1; j++)
                stateArray[puzzleOrder - 1][j] = current++;
            stateArray[puzzleOrder - 1][puzzleOrder - 1] = 0;

            state = new MutablePuzzleState(stateArray);
        } catch (PuzzleStateNoBlankPosition ex) {
            return null;
        }

        List<MutablePuzzleState.Step> steps = new ArrayList<MutablePuzzleState.Step>();

        steps.add(MutablePuzzleState.Step.DOWN);
        steps.add(MutablePuzzleState.Step.UP);
        steps.add(MutablePuzzleState.Step.LEFT);
        steps.add(MutablePuzzleState.Step.RIGHT);

        int i = 0;
        Random r = new Random(System.currentTimeMillis());

        while (i < difficulty * 10) {
            MutablePuzzleState.Step step = steps.get(r.nextInt(4));
            try {
                switch (step) {
                    case DOWN:
                        state = state.moveDown();
                        break;
                    case UP:
                        state = state.moveUp();
                        break;
                    case LEFT:
                        state = state.moveLeft();
                        break;
                    case RIGHT:
                        state = state.moveRight();
                        break;
                }
                i++;
            } catch (MutablePuzzleStateInvalidMove ex) { }
        }

        state.clearSteps();
        return state;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}