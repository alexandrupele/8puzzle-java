package Domain;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * Created by Alexandru Pele on 3/16/2015.
 */
public class PuzzleState implements Cloneable {

    private int order;
    private int[][] state;

    public PuzzleState(int[][] state) {
        this.state = state;
        order = state.length;
    }

    private int[] getBlankPosition() throws PuzzleStateNoBlankPosition {
        for (int i = 0; i < order; i++)
            for (int j = 0; j < order; j++)
                if (state[i][j] == 0) {
                    return new int[] {i, j};
                }
        throw new PuzzleStateNoBlankPosition();
    }

    public PuzzleState moveUp() throws PuzzleStateNoBlankPosition, PuzzleStateInvalidMove {
        int[] pos = getBlankPosition();
        if (pos[0] == 0) {
            throw new PuzzleStateInvalidMove();
        }
        return null;
    }

    public List<PuzzleState> expandState() {

        throw new NotImplementedException();
    }

    public boolean isSolution() {
        int maxPos = order - 1;

        if (state[0][0] != 0 && state[maxPos][maxPos] != 0) {
            // if it doesn't begin or end with blank, it's not a solution
            return false;
        }

        if (state[maxPos][maxPos] == 0) {
            state[maxPos][maxPos] = order * order;
        }

        for (int i = 0; i < order; i++) {
            for (int j = 0; j < maxPos; j++) {
                if (state[i][j + 1] - state[i][j] != 1)
                    return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                sb.append(state[i][j]);
                if (j < order - 1) {
                    sb.append(" ");
                }
            }
            sb.append(System.getProperty("line.separator"));
        }

        return sb.toString();
    }
}
