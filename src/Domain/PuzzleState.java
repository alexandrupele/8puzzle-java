package Domain;

import java.util.Arrays;

/**
 * Created by Alexandru Pele on 3/17/2015.
 */
public class PuzzleState {
    protected int[][] state;

    public PuzzleState(int[][] state) {
        this.state = state;
    }
    public PuzzleState() { }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }

        PuzzleState other = (PuzzleState)obj;
        for (int i = 0; i < state.length; i++)
            for (int j = 0; j < state.length; j++)
                if (state[i][j] != other.state[i][j])
                    return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                sb.append(state[i][j]);
                if (j < state.length - 1) {
                    sb.append(" ");
                }
            }
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(state);
    }

    public int[][] getState() {
        return state;
    }
}