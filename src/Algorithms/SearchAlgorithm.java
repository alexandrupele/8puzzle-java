package Algorithms;

import Domain.MutablePuzzleState;

/**
 * Created by Alexandru Pele on 3/28/2015.
 */
public abstract class SearchAlgorithm {
    public abstract MutablePuzzleState getFinalState (MutablePuzzleState initialState) throws SearchFailedException;
}
