package UI;

import java.util.List;
import java.util.Scanner;
import Controller.*;
import Domain.MovablePuzzleState;
import Domain.PuzzleStateNoBlankPosition;

/**
 * Created by Alexandru Pele on 3/16/2015.
 */
public class Console {

    private Controller ctrl;

    public Console(Controller ctrl) {
        this.ctrl = ctrl;
    }

    private MovablePuzzleState readInitialState() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter puzzle order: ");
        int n;
        try {
            String orderString = scan.nextLine();
            n = Integer.parseInt(orderString);
        } catch (NumberFormatException e) {
            System.out.println("Order must be integer!");
            return null;
        }

        int[][] arrayPuzzle;
        try {
            arrayPuzzle = new int[n][n];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Order can be two, three or four!");
            return null;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print("[" + i + "][" + j + "]= ");
                try {
                    String puzzleSlotString = scan.nextLine();
                    arrayPuzzle[i][j] = Integer.parseInt(puzzleSlotString);
                } catch (NumberFormatException e) {
                    System.out.println("Only integers are accepted!");
                }
            }
        }

        scan.close();
        return new MovablePuzzleState(arrayPuzzle);
    }

    public void run() {
        MovablePuzzleState initialState = readInitialState();

        try {
            MovablePuzzleState sol = ctrl.getSolution(initialState);
            System.out.println(sol);
            System.out.println("Found solution: ");
            List<MovablePuzzleState.Step> steps = sol.getSteps();
            System.out.println("Need " + steps.size() + " moves...");
            for (MovablePuzzleState.Step step : steps)
                System.out.print(step.name() + " ");

        } catch (PuzzleStateNoBlankPosition e) {
            System.out.println(e.getMessage());
        } catch (ControllerUnsolvableState e) {
            System.out.println(e.getMessage());
        }

    }
}
