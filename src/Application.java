import Algorithms.BreadthFirstSearch;
import Algorithms.GreedyBestFirstSearch;

import Controller.Controller;
import UI.Console.Console;
import UI.Swing.MainFrame;

/**
 * Created by Alexandru Pele on 3/16/2015.
 */
public class Application {

    public static void main(String[] args) {
        Controller ctrl = new Controller(new BreadthFirstSearch());
        new MainFrame(ctrl).setVisible(true);
    }
}