package UI.Swing;

import Domain.MutablePuzzleState;
import Domain.MutablePuzzleStateInvalidMove;
import Domain.PuzzleStateNoBlankPosition;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandru Pele on 3/30/2015.
 */
public class PuzzlePane extends JLayeredPane {

    private int order;
    private JLabel[][] tiles;
    private MutablePuzzleState state;
    private List<MutablePuzzleState.Step> steps;

    private final int TILE_SIZE = 100;
    private final int TILE_SPEED = 1;
    private final int TILE_SPACING = 2;
    private final int TIME_INTERVAL = 7;

    private JLabel createTile(int number) {
        JLabel tile = new JLabel(Integer.toString(number));

        tile.setBackground(new Color(0x1CAD25));
        tile.setForeground(Color.white);
        tile.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        tile.setHorizontalAlignment(JLabel.CENTER);
        tile.setVerticalAlignment(JLabel.CENTER);
        tile.setOpaque(true);

        return tile;
    }

    private void createPuzzle() {
        int x = 0, y = 0;

        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                if(state.getState()[i][j] == 0) {
                    tiles[i][j] = new JLabel("0");
                    x += TILE_SIZE + TILE_SPACING;
                    continue;
                }

                JLabel tile = createTile(state.getState()[i][j]);
                tile.setBounds(x, y, TILE_SIZE, TILE_SIZE);
                tiles[i][j] = tile;

                x += TILE_SIZE + TILE_SPACING;
                add(tile);
            }
            x = 0;
            y += TILE_SIZE + TILE_SPACING;
        }
    }

    public void reloadPuzzle(MutablePuzzleState newState) {
        state = newState;
        steps.clear();

        removeAll();
        createPuzzle();
        repaint();
    }

    private void moveUp(final JLabel tile) {
        tile.setLocation(tile.getX(), tile.getY() - TILE_SPACING);
        final int destination = tile.getY() - TILE_SIZE;

        while (tile.getY() != destination) {
            try {
                Thread.sleep(TIME_INTERVAL);
            } catch (InterruptedException ex) { }
            tile.setLocation(tile.getX(), tile.getY() - TILE_SPEED);
            paintImmediately(0, 0, getWidth(), getHeight());
        }
    }

    private void moveDown(final JLabel tile) {
        tile.setLocation(tile.getX(), tile.getY() + TILE_SPACING);
        final int destination = tile.getY() + TILE_SIZE;

        while (tile.getY() != destination) {
            try {
                Thread.sleep(TIME_INTERVAL);
            } catch (InterruptedException ex) { }

            tile.setLocation(tile.getX(), tile.getY() + TILE_SPEED);
            paintImmediately(0, 0, getWidth(), getHeight());
        }
    }

    private void moveLeft(final JLabel tile) {
        tile.setLocation(tile.getX() - TILE_SPACING, tile.getY());
        final int destination = tile.getX() - TILE_SIZE;

        while (tile.getX() != destination) {
            try {
                Thread.sleep(TIME_INTERVAL);
            } catch (InterruptedException ex) {}

            tile.setLocation(tile.getX() - TILE_SPEED, tile.getY());
            paintImmediately(0, 0, getWidth(), getHeight());
        }
    }

    private void moveRight(final JLabel tile) {
        tile.setLocation(tile.getX() + TILE_SPACING, tile.getY());
        final int destination = tile.getX() + TILE_SIZE;

        while (tile.getX() != destination) {
            try {
                Thread.sleep(TIME_INTERVAL);
            } catch (InterruptedException ex) { }
            tile.setLocation(tile.getX() + TILE_SPEED, tile.getY());
            paintImmediately(0, 0, getWidth(), getHeight());
        }
    }

    private void swapTiles(int[] pos) {
        JLabel aux = tiles[pos[0]][pos[1]];
        tiles[pos[0]][pos[1]] = tiles[pos[2]][pos[3]];
        tiles[pos[2]][pos[3]] = aux;
    }

    public void animateSolution() {
        while (!steps.isEmpty()) {
            MutablePuzzleState.Step currentStep = steps.get(0);

            try {
                int[] blankPos;
                int x, y;
                try {
                    blankPos = state.getBlankPosition();
                } catch (PuzzleStateNoBlankPosition ex) {
                    return;
                }

                x = blankPos[0];
                y = blankPos[1];

                switch (currentStep) {
                    case DOWN:
                        moveUp(tiles[++x][y]);
                        state = state.moveDown();
                        swapTiles(new int[] {x-1, y, x, y});
                        break;
                    case UP:
                        moveDown(tiles[--x][y]);
                        state = state.moveUp();
                        swapTiles(new int[] {x+1, y, x, y});
                        break;
                    case LEFT:
                        moveRight(tiles[x][--y]);
                        state = state.moveLeft();
                        swapTiles(new int[] {x, y+1, x, y});
                        break;
                    case RIGHT:
                        moveLeft(tiles[x][++y]);
                        state = state.moveRight();
                        swapTiles(new int[] {x, y-1, x, y});
                }
            } catch (MutablePuzzleStateInvalidMove ex) { }
            steps.remove(0);
        }
    }

    public void addSteps(List<MutablePuzzleState.Step> newSteps) {
        steps.clear();
        for(MutablePuzzleState.Step step : newSteps) {
            steps.add(step);
        }
    }

    public MutablePuzzleState getState() {
        return state;
    }

    public PuzzlePane(MutablePuzzleState state) {
        this.state = state;
        order = state.getState().length;
        steps = new ArrayList<MutablePuzzleState.Step>();
        tiles = new JLabel[order][order];

        setPreferredSize(new Dimension(TILE_SIZE * order, TILE_SIZE * order));
        createPuzzle();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D gui = (Graphics2D)g;
        gui.setPaint(Color.DARK_GRAY);
        Rectangle2D rectangle = new Rectangle2D.Double(0, 0, getWidth(), getHeight());
        gui.fill(rectangle);
    }
}