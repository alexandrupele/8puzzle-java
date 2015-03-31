package Domain;

/**
 * Created by Alexandru Pele on 3/16/2015.
 */
public class MutablePuzzleStateInvalidMove extends Exception {
    public MutablePuzzleStateInvalidMove() {
        super();
    }

    public MutablePuzzleStateInvalidMove(String message) {
        super(message);
    }
}
