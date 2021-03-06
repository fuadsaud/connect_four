package im.fuad.rit.copads.p4;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Game board model implementation.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
public class C4Board implements C4BoardIntf {
    private static final int PLAYER_1_MARK = 1;
    private static final int PLAYER_2_MARK = 2;

    private int[][] board;

    /**
     * Initializes an empty board.
     */
    public C4Board() { clear(); }

    /**
     * Adds a marker to the board. Figures out the proper row in which to add the marker in the
     * given column.
     *
     * @param player the player number for which the move is being made (1 or 2).
     * @param col the column in which the marker should be added.
     */
    public int[] play(int player, int col) {
        return play(player, rowForColumn(col), col);
    }

    /**
     * Adds a marker to the board.
     *
     * @param player the player number for which the move is being made (1 or 2).
     * @param row the row in which the marker should be added.
     * @param col the column in which the marker should be added.
     */
    public int[] play(int player, int row, int col) {
        if (player != 1 && player != 2)
            throw new IllegalArgumentException("Player must be 1 or 2");

        if (col < 0 || col > COLS)
            throw new IllegalArgumentException("Column must be between 0 and 6");

        if (row < 0 || row > ROWS)
            throw new IllegalArgumentException("Row must be between 0 and 5");

        this.board[row][col] = player;

        return new int[] { row, col };
    }

    /**
     * Clears the game board.
     */
    public void clear() { this.board = new int[ROWS][COLS]; }

    /**
     * Determine if the given row and column contains player 1's marker.
     *
     * @param  r  Row.
     * @param  c  Column.
     *
     * @return  True if (r, c) contains player 1's marker, false otherwise.
     */
    public boolean hasPlayer1Marker(int r, int c) {
        return this.board[r][c] == PLAYER_1_MARK;
    }

    /**
     * Determine if the given row and column contains player 2's marker.
     *
     * @param  r  Row.
     * @param  c  Column.
     *
     * @return  True if (r, c) contains player 2's marker, false otherwise.
     */
    public boolean hasPlayer2Marker(int r, int c) {
        return this.board[r][c] == PLAYER_2_MARK;
    }

    /**
     * Determine if one player or the other has won; that is, has four markers
     * in a row horizontally, vertically, or diagonally. If so, an array of four
     * integers (r1, c1, r2, c2) is returned, where (r1, c1) is the row/column
     * of the first of the four markers and (r2, c2) is the row/column of the
     * last of the four markers. If neither player has won, null is returned.
     *
     * @return  Array of (r1, c1, r2, c2), or null.
     */
    public int[] hasWon() {
        int[] result;

        result = hasWonHorizontally();

        if (result != null) return result;

        result = hasWonVertically();

        if (result != null) return result;

        result = hasWonDiagonally();

        if (result != null) return result;

        return null;
    }

    /**
     * Determines whether either player has won the game with an horizontal sequence of markers.
     * If so, an array of four integers (r1, c1, r2, c2) is returned, where (r1, c1) is the
     * row/column of the first of the four markers and (r2, c2) is the row/column of the last of
     * the four markers. If neither player has won horizontally, null is returned.
     *
     * @return an int array of (r1, c1, r2, c2) or null.
     */
    private int[] hasWonHorizontally() {
        for (int i = 0; i < ROWS; i++) {
            List<int[]> positions = new ArrayList<int[]>();

            for (int j = 0; j < COLS - 1; j++) {
                int thisCell = this.board[i][j];
                int nextCell = this.board[i][j + 1];

                if (thisCell != 0 && thisCell == nextCell)
                    positions.add(new int[]{ i, j });
                else
                    positions.clear();

                if (positions.size() == 3) {
                    int[] result = new int[] {
                        positions.get(0)[0], positions.get(0)[1], i, j + 1
                    };

                    return result;
                }
            }
        }

        return null;
    }

    /**
     * Determines whether either player has won the game with a vertical sequence of markers.
     * If so, an array of four integers (r1, c1, r2, c2) is returned, where (r1, c1) is the
     * row/column of the first of the four markers and (r2, c2) is the row/column of the last of
     * the four markers. If neither player has won vertically, null is returned.
     *
     * @return an int array of (r1, c1, r2, c2) or null.
     */
    private int[] hasWonVertically() {
        for (int j = 0; j < COLS; j++) {
            List<int[]> positions = new ArrayList<int[]>();

            for (int i = 0; i < ROWS - 1; i++) {
                int thisCell = this.board[i][j];
                int nextCell = this.board[i + 1][j];

                if (thisCell != 0 && thisCell == nextCell)
                    positions.add(new int[]{ i, j });
                else
                    positions.clear();

                if (positions.size() == 3) {
                    int[] result =  new int[] {
                        positions.get(0)[0], positions.get(0)[1], i + 1, j
                    };

                    return result;
                }
            }
        }

        return null;
    }

    /**
     * Determines whether either player has won the game with a diagonal sequence of markers.
     * If so, an array of four integers (r1, c1, r2, c2) is returned, where (r1, c1) is the
     * row/column of the first of the four markers and (r2, c2) is the row/column of the last of
     * the four markers. If neither player has won diagonally, null is returned.
     *
     * @return an int array of (r1, c1, r2, c2) or null.
     */
    private int[] hasWonDiagonally() {
        // My algorithm for generating the diagonal indices was problematic and I couldn't find an
        // acceptable solution so the diagonal indices were hardcoded on the DIAGONALS constant. The
        // current solution works for a board of 6 rows and 7 columns only.
        for (int[][] diagonal : DIAGONALS) {
            List<int[]> positions = new ArrayList<int[]>();

            for (int coord = 0; coord < diagonal.length - 1; coord++) {
                int thisI = diagonal[coord][0], thisJ = diagonal[coord][1];
                int nextI = diagonal[coord + 1][0], nextJ = diagonal[coord + 1][1];

                int thisCell = this.board[thisI][thisJ];
                int nextCell = this.board[nextI][nextJ];

                if (thisCell != 0 && thisCell == nextCell)
                    positions.add(new int[] { thisI, thisJ });
                else
                    positions.clear();

                if (positions.size() == 3) {
                    int[] result = new int[] {
                        positions.get(0)[0], positions.get(0)[1], nextI, nextJ
                    };

                    return result;
                }
            }
        }

        return null;
    }

    /**
     * Determines the first free row in a given column.
     *
     * @param col the column in which to search the free row.
     *
     * @return the first free row index or -1 if column is full.
     */
    private int rowForColumn(int col) {
        for (int i = ROWS - 1; i >= 0; i--)
            if (this.board[i][col] == 0)
                return i;

        return -1;
    }
}
