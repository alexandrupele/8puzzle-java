package UI.Swing;

import Algorithms.BreadthFirstSearch;
import Algorithms.GreedyBestFirstSearch;
import Algorithms.SearchAlgorithm;
import Controller.Controller;
import oracle.jrockit.jfr.JFR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alexandru Pele on 4/3/2015.
 */
public class StartFrame extends JFrame {

    private JLabel algoLabel;
    private JLabel typeLabel;
    private JComboBox algoCombo;
    private JComboBox typeCombo;
    private JButton startButton;

    public void configureFrame() {
        setTitle("Puzzle Solver");
        setLayout(new GridBagLayout());
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(320, 230));

        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 0.1;
        gc.weighty = 0.1;

        // First line
        gc.gridy = 0;
        gc.gridx = 0;
        gc.insets = new Insets(20, 0, 10, 94);
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.gridwidth = 2;
        add(new JLabel("Please select your prefferences"), gc);

        // Next line
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(4, 4, 4, 4);
        gc.fill = GridBagConstraints.NONE;
        gc.gridwidth = 1;
        algoLabel = new JLabel("Algorithm: ");
        add(algoLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        algoCombo = new JComboBox(new String[] {"Breadth First Search", "Greedy Best First Search"});
        add(algoCombo, gc);

        // Next line
        gc.gridy++;
        gc.gridx = 0;
        gc.insets = new Insets(4, 4, 4, 4);
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        typeLabel = new JLabel("Type: ");
        add(typeLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        typeCombo = new JComboBox(new String[] {"8-Puzzle", "15-Puzzle"});
        add(typeCombo, gc);

        // Next line
        gc.gridy++;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchAlgorithm algo = new BreadthFirstSearch();
                int order = 3 + typeCombo.getSelectedIndex();

                if (algoCombo.getSelectedIndex() != 0) {
                    algo = new GreedyBestFirstSearch();
                }

                Controller ctrl = new Controller(algo, order);
                setVisible(false);
                new MainFrame(ctrl).setVisible(true);
            }
        });

        add(startButton, gc);
        pack();
    }

    public StartFrame() {
        super();
        configureFrame();
    }
}
