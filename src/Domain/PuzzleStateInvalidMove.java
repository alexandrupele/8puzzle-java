package Domain;

/**
 * Created by Alexandru Pele on 3/16/2015.
 */
public class PuzzleStateInvalidMove extends Exception {
    public PuzzleStateInvalidMove() {
        super();
    }

    public PuzzleStateInvalidMove(String message) {
        super(message);
    }
}
