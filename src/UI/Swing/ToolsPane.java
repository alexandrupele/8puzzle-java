package UI.Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandru Pele on 3/31/2015.
 */
public class ToolsPane extends JToolBar {

    private JButton solveButton;
    private JButton scrabbleButton;
    private List<SolveListener> solveListeners;
    private List<ScrabbleListener> scrabbleListeners;

    private void configurePane() {
        setFloatable(false);

        solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (SolveListener listner : solveListeners)
                    listner.solutionRequested();
            }
        });

        scrabbleButton = new JButton("Scrabble");
        scrabbleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (ScrabbleListener listener : scrabbleListeners) {
                    listener.scrabbleRequested();
                }
            }
        });

        add(solveButton);

        add(scrabbleButton);
    }

    public void addSolveListener(SolveListener listener) {
        solveListeners.add(listener);
    }

    public void addScrabbleListener(ScrabbleListener listener) {
        scrabbleListeners.add(listener);
    }

    public ToolsPane() {
        super();
        solveListeners = new ArrayList<SolveListener>();
        scrabbleListeners = new ArrayList<ScrabbleListener>();
        configurePane();
    }
}