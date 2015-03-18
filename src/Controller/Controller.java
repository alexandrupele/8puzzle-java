package Controller;

import Domain.PuzzleState;
import Domain.MovablePuzzleState;
import Domain.PuzzleStateNoBlankPosition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Alexandru Pele on 3/16/2015.
 */
public class Controller {

    Queue<MovablePuzzleState> queue;
    List<PuzzleState> blackList;

    public Controller() {
        queue = new LinkedList<MovablePuzzleState>();
        blackList = new ArrayList<PuzzleState>();
    }

    public MovablePuzzleState getSolution(MovablePuzzleState initialState) throws PuzzleStateNoBlankPosition, ControllerUnsolvableState {
        queue.add(initialState);

        while (true) {
            if (queue.isEmpty()) {
                throw new ControllerUnsolvableState("There are no solutions for this configuration");
            }
            MovablePuzzleState current = queue.remove();
            if (current.isSolution()) {
                return current;
            }
            blackList.add(current);
            if(blackList.size() % 2000 == 0) {
                // Logging
                System.out.println("Visited: " + blackList.size());
                System.out.println("To be visited: " + queue.size());
            }
            List<MovablePuzzleState> expanded = current.expandState();
            for (MovablePuzzleState expandedState : expanded) {
                if (!blackList.contains(expandedState))
                    queue.add(expandedState);
            }
        }
    }
}
