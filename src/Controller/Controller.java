package Controller;

import Algorithms.SearchAlgorithm;
import Algorithms.SearchFailedException;
import Domain.*;

import java.util.*;

/**
 * Created by Alexandru Pele on 3/16/2015.
 */
public class Controller {

    SearchAlgorithm algo;

    public Controller(SearchAlgorithm algo) {
        this.algo = algo;
    }

    public MutablePuzzleState getSolution(MutablePuzzleState initialState) throws PuzzleStateNoBlankPosition, PuzzleStateUnsolvable {
        try {
            return algo.getFinalState(initialState);
        } catch (SearchFailedException ex) {
            throw new PuzzleStateUnsolvable("There are no solutions for this configuration");
        }
    }

    public MutablePuzzleState getScrabbledState() {
        MutablePuzzleState state;
        try {
            state = new MutablePuzzleState(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
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

        while (i < 30) {
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
}
