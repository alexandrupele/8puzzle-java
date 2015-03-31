package UI.Swing;

import Controller.Controller;
import Domain.MutablePuzzleState;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alexandru Pele on 3/31/2015.
 */
public class MainFrame extends JFrame {

    private ToolsPane bar;
    private PuzzlePane puzzle;
    private Controller ctrl;

    public void configureFrame() {
        setTitle("Puzzle Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Configure JMenuBar
        JMenuBar menuBar = new JMenuBar();
        JMenu files = new JMenu("Files");
        menuBar.add(files);

        setJMenuBar(menuBar);

        puzzle = new PuzzlePane(ctrl.getScrabbledState());

        bar = new ToolsPane();
        bar.addSolveListener(new SolveListener() {
            @Override
            public void solutionRequested() {
                // ask ctrl for solution
                try {
                    puzzle.addSteps(ctrl.getSolution(puzzle.getState()).getSteps());
                } catch (Exception ex) {
                }
                puzzle.animateSolution();
            }
        });

        bar.addScrabbleListener(new ScrabbleListener() {
            @Override
            public void scrabbleRequested() {
                // ask ctrl for a scrabbled puzzle
                MutablePuzzleState newState = ctrl.getScrabbledState();
                puzzle.reloadPuzzle(newState);
            }
        });

        add(puzzle, BorderLayout.WEST);
        add(bar, BorderLayout.EAST);
        pack();
    }

    public MainFrame(Controller ctrl) {
        super();
        this.ctrl = ctrl;
        configureFrame();
    }
}