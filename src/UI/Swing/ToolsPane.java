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
    private JButton scrambleButton;
    private JComboBox diffCombo;
    private JTextArea soltionArea;

    private List<SolveListener> solveListeners;
    private List<ScrambleListener> scrambleListeners;

    public ToolsPane() {
        super();
        solveListeners = new ArrayList<SolveListener>();
        scrambleListeners = new ArrayList<ScrambleListener>();
        configurePane();
    }

    private void addActionListeners() {
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (SolveListener listner : solveListeners)
                    listner.solutionRequested();
            }
        });

        scrambleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (ScrambleListener listener : scrambleListeners) {
                    listener.scrambleRequested();
                }
            }
        });        
    }

    private void configurePane() {
    	setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(210, getHeight()));

    	GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 0.05;
        gc.weighty = 0.05;
        gc.fill = GridBagConstraints.HORIZONTAL;

        // First line
        gc.gridy = 0;
    	gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(5, 10, 5, 10);
        solveButton = new JButton("Solve");
        add(solveButton, gc);

        // Second line
        gc.gridy++;
        gc.weightx = 0.5;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(new JLabel("Difficulty: "), gc);

        // Third lane
        gc.gridy++;
        gc.gridx = 0;
        gc.insets = new Insets(1, 10, 5, 10);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        diffCombo = new JComboBox(new String[] {"Easy", "Medium", "Hard"});
        add(diffCombo, gc);

        // Fourth line
        gc.gridy++;
        gc.gridx = 0;
        gc.weighty = 1;
        gc.insets = new Insets(5, 10, 5, 10);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        scrambleButton = new JButton("Scramble");
        add(scrambleButton, gc);

        addActionListeners();
    }

    public void addSolveListener(SolveListener listener) {
        solveListeners.add(listener);
    }

    public void addScrambleListener(ScrambleListener listener) {
        scrambleListeners.add(listener);
    }

    public int getPrefferedDifficulty() {
        return diffCombo.getSelectedIndex();
    }
}