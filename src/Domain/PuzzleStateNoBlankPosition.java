package Domain;

/**
 * Created by Alexandru Pele on 3/16/2015.
 */
public class PuzzleStateNoBlankPosition extends Exception {
    public PuzzleStateNoBlankPosition() {
        super();
    }

    public PuzzleStateNoBlankPosition(String message) {
        super(message);
    }
}
