package Controller;

import Domain.PuzzleState;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Alexandru Pele on 3/16/2015.
 */
public class Controller {

    Queue<PuzzleState> queue;

    public Controller() {
        queue = new LinkedList<PuzzleState>();
    }

    public PuzzleState findSolution(PuzzleState initialState) {

        queue.add(initialState);

        return null;
    }
}
