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
public class ToolsPane extends JPanel {

    private JButton solveButton;
    private JButton scrabbleButton;
    private List<SolveListener> solveListeners;
    private List<ScrabbleListener> scrabbleListeners;
    
    private void addActionListeners() {
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (SolveListener listner : solveListeners)
                    listner.solutionRequested();
            }
        });  
        
        scrabbleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (ScrabbleListener listener : scrabbleListeners) {
                    listener.scrabbleRequested();
                }
            }
        });        
    }

    private void configurePane() {
    	setLayout(new GridBagLayout());
    	
    	GridBagConstraints gc = new GridBagConstraints();
    	gc.weightx = 1;
    	gc.weighty = 1;
    	
    	gc.gridx = 0;
    	gc.gridy = 0;
    	
        solveButton = new JButton("Solve");
        add(solveButton, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        
        scrabbleButton = new JButton("Scrabble");
        add(scrabbleButton, gc);
        
        addActionListeners();
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