package Controller;

import Domain.PuzzleStateUnsolvable;
import Domain.PuzzleState;
import Domain.MovablePuzzleState;
import Domain.PuzzleStateNoBlankPosition;

import java.util.*;

/**
 * Created by Alexandru Pele on 3/16/2015.
 */
public class Controller {

    Queue<MovablePuzzleState> queue;
    Set<PuzzleState> blackList;

    public Controller() {
        queue = new LinkedList<MovablePuzzleState>();
        blackList = new HashSet<PuzzleState>(1000);
    }

    public MovablePuzzleState getSolution(MovablePuzzleState initialState) throws PuzzleStateNoBlankPosition, PuzzleStateUnsolvable {
        queue.add(initialState);

        while (true) {
            if (queue.isEmpty()) {
                throw new PuzzleStateUnsolvable("There are no solutions for this configuration");
            }
            MovablePuzzleState current = queue.remove();
            if (current.isSolution()) {
                return current;
            }
            blackList.add(current);
            if(blackList.size() % 5000 == 0) {
                // Logging visited and to be visited
                System.out.println(blackList.size() + "/" + queue.size() );
            }
            List<MovablePuzzleState> expanded = current.expandState();
            for (MovablePuzzleState expandedState : expanded) {
                if (!blackList.contains(expandedState)) {
                    queue.add(expandedState);
                }
            }
        }
    }
}
