package im.fuad.rit.copads.p4;

/**
 * Interface C4BoardIntf specifies the interface for an object that implements
 * the Connect Four game board. The methods in this interface are queried to
 * display the game board.
 *
 * @author  Alan Kaminsky
 * @version 13-Oct-2014
 */
public interface C4BoardIntf {
    /**
     * Number of rows.
     */
    public static final int ROWS = 6;

    /**
     * Number of columns.
     */
    public static final int COLS = 7;

    public static final int[][][] DIAGONALS = new int[][][] {
            new int[][] {
                new int[] { 2, 0 }, new int[] { 3, 1 }, new int[] { 1, 2 }, new int[] { 5, 3 }
            }, new int[][] {
                new int[] { 1, 0 }, new int[] { 2, 1 }, new int[] { 3, 2 }, new int[] { 1, 3 }, new int[] { 5, 4 }
            }, new int[][] {
                new int[] { 0, 0 }, new int[] { 1, 1 }, new int[] { 2, 2 }, new int[] { 3, 3 }, new int[] { 4, 4 }, new int[] { 5, 5 }
            }, new int[][] {
                new int[] { 0, 1 }, new int[] { 1, 2 }, new int[] { 2, 3 }, new int[] { 3, 4 }, new int[] { 4, 5 }, new int[] { 5, 6 }
            }, new int[][] {
                new int[] { 0, 2 }, new int[] { 1, 3 }, new int[] { 2, 4 }, new int[] { 3, 5 }, new int[] { 4, 6 }
            }, new int[][] {
                new int[] { 0, 3 }, new int[] { 1, 4 }, new int[] { 2, 5 }, new int[] { 3, 6 }
            }, new int[][] {
                new int[] { 0, 3 }, new int[] { 1, 2 }, new int[] { 2, 1 }, new int[] { 3, 0 }
            }, new int[][] {
                new int[] { 0, 4 }, new int[] { 1, 3 }, new int[] { 2, 2 }, new int[] { 3, 1 }, new int[] { 4, 0 }
            }, new int[][] {
                new int[] { 0, 5 }, new int[] { 1, 4 }, new int[] { 2, 3 }, new int[] { 3, 2 }, new int[] { 4, 1 }, new int[] { 5, 0 }
            }, new int[][] {
                new int[] { 0, 6 }, new int[] { 1, 5 }, new int[] { 2, 4 }, new int[] { 3, 3 }, new int[] { 4, 2 }, new int[] { 5, 1 }
            }, new int[][] {
                new int[] { 1, 6 }, new int[] { 2, 5 }, new int[] { 3, 4 }, new int[] { 4, 3 }, new int[] { 5, 2 }
            }, new int[][] {
                new int[] { 2, 6 }, new int[] { 3, 5 }, new int[] { 4, 4 }, new int[] { 5, 3 }
            }
        };

    /**
     * Determine if the given row and column contains player 1's marker.
     *
     * @param  r  Row.
     * @param  c  Column.
     *
     * @return  True if (r, c) contains player 1's marker, false otherwise.
     */
    public boolean hasPlayer1Marker(int r, int c);

    /**
     * Determine if the given row and column contains player 2's marker.
     *
     * @param  r  Row.
     * @param  c  Column.
     *
     * @return  True if (r, c) contains player 2's marker, false otherwise.
     */
    public boolean hasPlayer2Marker(int r, int c);

    /**
     * Determine if one player or the other has won; that is, has four markers
     * in a row horizontally, vertically, or diagonally. If so, an array of four
     * integers (r1, c1, r2, c2) is returned, where (r1, c1) is the row/column
     * of the first of the four markers and (r2, c2) is the row/column of the
     * last of the four markers. If neither player has won, null is returned.
     *
     * @return  Array of (r1, c1, r2, c2), or null.
     */
    public int[] hasWon();
}
