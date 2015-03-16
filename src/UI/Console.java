package UI;

import java.util.Scanner;
import Controller.Controller;
import Domain.PuzzleState;

/**
 * Created by Alexandru Pele on 3/16/2015.
 */
public class Console {

    private Controller ctrl;

    public Console(Controller ctrl) {
        this.ctrl = ctrl;
    }

    private PuzzleState readInitialState() {
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
        return new PuzzleState(arrayPuzzle);
    }

    public void run() {
        PuzzleState initialState = readInitialState(), solution;

        if (initialState == null) {
            return;
        }

        solution = ctrl.findSolution(initialState);

        System.out.println(solution);
    }
}
