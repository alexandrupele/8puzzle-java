package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alexandru Pele on 3/19/2015.
 */
public class PuzzleFrame extends JFrame {

    public PuzzleFrame() {
        super();
        configureFrame();
    }

    private void configureFrame() {
        setTitle("Sliding Puzzle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setResizable(false);

        // Puzzle panel
        JPanel puzzlePanel = new JPanel();

        final JButton blank = new JButton();
        int number = 1;
        for (int i = 0; i < 9; i++) {
            if (i == 4) {
                //blank.setVisible(false);
                puzzlePanel.add(blank);
            } else {
                puzzlePanel.add(new JButton(Integer.toString(number)));
                number++;
            }
        }
        puzzlePanel.setLayout(new GridLayout(3, 3));

        // Menu panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.X_AXIS));
        JButton moveButton = new JButton("Move");
        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 10; i++) {

                    blank.setLocation(blank.getX() + i, blank.getY());
                }
            }
        });
        menuPanel.add(moveButton);

        // Frame
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.weighty = 0.5;

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 3;
        add(puzzlePanel, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 3;
        add(menuPanel, c);
    }
}
