package Controller;

/**
 * Created by Alexandru Pele on 3/17/2015.
 */
public class ControllerUnsolvableState extends Exception {
    public ControllerUnsolvableState() {
        super();
    }

    public ControllerUnsolvableState(String message) {
        super(message);
    }
}
