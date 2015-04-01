package UI.Swing;

import Controller.Controller;
import Domain.MutablePuzzleState;
import Domain.MutablePuzzleState.Step;

import javax.swing.*;
import java.util.List;
import java.awt.*;

/**
 * Created by Alexandru Pele on 3/31/2015.
 */
public class MainFrame extends JFrame {

    private ToolsPane bar;
    private PuzzlePane puzzle;
    private NavigationBar menuBar;
    private Controller ctrl;
    private JTextArea solutionArea;
    private JScrollPane solutionScroll;

    public void configureFrame() {
        setTitle("Puzzle Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);


        bar = new ToolsPane();
        bar.addSolveListener(new SolveListener() {
            @Override
            public void solutionRequested() {
                // ask ctrl for solution
                try {
                    MutablePuzzleState currentState = puzzle.getState();
                    currentState.clearSteps();

                    List<Step> steps = ctrl.getSolution(currentState).getSteps();
                    puzzle.addSteps(steps);

                    // print the soluton as text
                    solutionArea.setText(steps.size() + " moves" + System.getProperty("line.separator"));
                    for (Step s : steps) {
                        solutionArea.append(s.toString() + " ");
                    }
                    solutionArea.setCaretPosition(0);
                    solutionScroll.revalidate();
                } catch (Exception ex) {
                }
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        puzzle.animateSolution();
                    }
                });
            }
        });

        bar.addScrambleListener(new ScrambleListener() {
            @Override
            public void scrambleRequested() {
                // ask ctrl for a scrabbled puzzle
                MutablePuzzleState newState = ctrl.getScrambledState();
                puzzle.reloadPuzzle(newState);
            }
        });

        puzzle = new PuzzlePane(ctrl.getScrambledState());

        // Configure solution JTextArea
        solutionArea = new JTextArea(3, 20);
        solutionArea.setText("PRESS SOLVE TO GET A SOLUTION...");
        solutionArea.setBackground(new Color(0x6BE373));
        solutionArea.setForeground(Color.WHITE);
        solutionArea.setFont(new Font("Verdana", Font.BOLD, 11));
        solutionArea.setEditable(false);
        solutionArea.setMargin(new Insets(11, 7, 7, 7));
        solutionScroll = new JScrollPane(solutionArea);

        // Configure JMenuBar
        menuBar = new NavigationBar();

        // Add components to the panel
        setJMenuBar(menuBar);
        add(puzzle, BorderLayout.WEST);
        add(bar, BorderLayout.EAST);
        add(solutionScroll, BorderLayout.SOUTH);

        pack();
    }

    public MainFrame(Controller ctrl) {
        super();
        this.ctrl = ctrl;
        configureFrame();
    }
}