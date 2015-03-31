package UI.Console;

import java.util.List;
import java.util.Scanner;
import Controller.*;
import Domain.MutablePuzzleState;
import Domain.PuzzleStateNoBlankPosition;

/**
 * Created by Alexandru Pele on 3/16/2015.
 */
public class Console {

    private Controller ctrl;

    public Console(Controller ctrl) {
        this.ctrl = ctrl;
    }

    private MutablePuzzleState readInitialState() throws ConsoleCorruptReading {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter puzzle order: ");
        int n;
        try {
            String orderString = scan.nextLine();
            n = Integer.parseInt(orderString);
        } catch (NumberFormatException e) {
            scan.close();
            throw new ConsoleCorruptReading("Order must be integer");
        }

        int[][] arrayPuzzle;
        try {
            arrayPuzzle = new int[n][n];
        } catch (ArrayIndexOutOfBoundsException e) {
            scan.close();
            throw new ConsoleCorruptReading("Order must pe positive");
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print("[" + i + "][" + j + "]= ");
                try {
                    String puzzleSlotString = scan.nextLine();
                    arrayPuzzle[i][j] = Integer.parseInt(puzzleSlotString);
                } catch (NumberFormatException e) {
                    scan.close();
                    throw new ConsoleCorruptReading("Only integers are accepted!");
                }
            }
        }
        scan.close();

        MutablePuzzleState initialState;
        try {
            initialState = new MutablePuzzleState(arrayPuzzle);
        } catch (PuzzleStateNoBlankPosition e) {
            throw new ConsoleCorruptReading("Error reading the initial state");
        }

        return initialState;
    }

    public void run() {
        try {
            long startTime = System.currentTimeMillis();
            MutablePuzzleState sol = ctrl.getSolution(readInitialState());
            long endTime = System.currentTimeMillis();

            System.out.println("Running time " + (endTime - startTime) + "ms");

            List<MutablePuzzleState.Step> steps = sol.getSteps();
            System.out.println("Need " + steps.size() + " moves...");
            for (MutablePuzzleState.Step step : steps) {
                System.out.print(step.name() + " ");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}