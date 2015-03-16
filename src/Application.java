import UI.Console;
import Controller.Controller;

/**
 * Created by Alexandru Pele on 3/16/2015.
 */
public class Application {

    public static void main(String[] args) {
        Controller puzzleCtrl = new Controller();
        Console cons = new Console(puzzleCtrl);

        cons.run();
    }
}
