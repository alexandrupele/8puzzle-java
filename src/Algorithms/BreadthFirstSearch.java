package Algorithms;

import Algorithms.SearchAlgorithm;
import Domain.MutablePuzzleState;
import Domain.PuzzleState;
import Domain.PuzzleStateUnsolvable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

/**
 * Created by Alexandru Pele on 3/28/2015.
 */
public class BreadthFirstSearch extends SearchAlgorithm {

    Queue<MutablePuzzleState> queue;
    Set<PuzzleState> visited;

    public BreadthFirstSearch() {
        queue = new LinkedList<MutablePuzzleState>();
        visited = new HashSet<PuzzleState>(1000);
    }

    public MutablePuzzleState getFinalState (MutablePuzzleState initialState) throws SearchFailedException {
        queue.add(initialState);

        while (!queue.isEmpty()) {
            MutablePuzzleState current = queue.remove();
            if (current.isSolution()) {
                return current;
            }
            visited.add(current);
            if (visited.size() % 5000 == 0) {
                // Logging visited and to be visited
                System.out.println(visited.size() + "/" + queue.size());
            }
            List<MutablePuzzleState> expanded = current.expandState();
            for (MutablePuzzleState expandedState : expanded) {
                if (!visited.contains(expandedState)) {
                    queue.add(expandedState);
                }
            }
        }
        throw new SearchFailedException("Could not find solution");
    }
}
