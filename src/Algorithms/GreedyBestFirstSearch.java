package Algorithms;

import Domain.MutablePuzzleState;
import Domain.PuzzleState;

import java.util.*;

/**
 * Created by Alexandru Pele on 3/28/2015.
 */
public class GreedyBestFirstSearch extends SearchAlgorithm {

    Stack<MutablePuzzleState> stack;
    Set<PuzzleState> visited;

    public GreedyBestFirstSearch() {
        stack = new Stack<MutablePuzzleState>();
        visited = new HashSet<PuzzleState>(1000);
    }

    int countMisplacedTiles(MutablePuzzleState ps) {
        int misplaced = 0, rightTile = 1, i, j;
        int[][] state = ps.getState();

        for(i = 0; i < state.length - 1; i++)
            for(j = 0; j < state.length; j++)
                if (state[i][j] != rightTile++)
                    misplaced++;

        for (j = 0; j < state.length - 1; j++)
            if (state[i][j] != rightTile++)
                misplaced++;

        return misplaced - 1;
    }

    public MutablePuzzleState getFinalState(MutablePuzzleState initialState) throws SearchFailedException {
        stack.push(initialState);

        while (!stack.isEmpty()) {
            MutablePuzzleState current = stack.peek();

            if (current.isSolution()) {
                return current;
            }

            visited.add(current);

            List<MutablePuzzleState> children = new LinkedList<MutablePuzzleState>();
            for(MutablePuzzleState child : current.expandState()) {
                if (!visited.contains(child)) {
                    children.add(child);
                }
            }

            if (children.isEmpty()) {
                stack.pop();
                continue;
            }

            MutablePuzzleState best = children.get(0);
            for (MutablePuzzleState child : children) {
                if (countMisplacedTiles(child) < countMisplacedTiles(best))
                    best = child;
            }

            stack.push(best);
        }
        throw new SearchFailedException("Could not find solution");
    }
}