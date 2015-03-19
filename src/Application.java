import Domain.MovablePuzzleState;
import Domain.PuzzleState;
import UI.Console;
import Controller.Controller;
import UI.PuzzleFrame;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexandru Pele on 3/16/2015.
 */
public class Application {

    public static void main(String[] args) {
        Controller ctrl = new Controller();
        //new Console(ctrl).run();

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.AquaLookAndFeel");
        } catch ( Exception e ) {
             System.out.println ("Couldn't load Mac L&F" + e);
        }

        PuzzleFrame main = new PuzzleFrame();

        //main.pack();
        main.setVisible(true);
    }
}
