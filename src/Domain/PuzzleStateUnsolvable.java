package Domain;

/**
 * Created by Alexandru Pele on 3/17/2015.
 */
public class PuzzleStateUnsolvable extends Exception {
    public PuzzleStateUnsolvable() {
        super();
    }

    public PuzzleStateUnsolvable(String message) {
        super(message);
    }
}
