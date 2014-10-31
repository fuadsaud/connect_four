package im.fuad.rit.copads.p3;

import java.util.List;
import java.util.ArrayList;

class C4Board implements C4BoardIntf {
    private static final int PLAYER_1_MARK = 1;
    private static final int PLAYER_2_MARK = 2;

    private int[][] board;

    public C4Board() { clear(); }

    public void play(int player, int col) {
        play(player, firstFreeRow(col), col);
    }

    public void play(int player, int row, int col) {
        if (player != 1 && player != 2)
            throw new IllegalArgumentException("Player must be 1 or 2");

        if (col < 0 || col > COLS)
            throw new IllegalArgumentException("Column must be between 0 and 6");

        if (row < 0 || row > ROWS)
            throw new IllegalArgumentException("Row must be between 0 and 5");

        this.board[row][col] = player;
    }

    public void clear() { this.board = new int[ROWS][COLS]; }

    public boolean hasPlayer1Marker(int r, int c) {
        return this.board[r][c] == PLAYER_1_MARK;
    }

    public boolean hasPlayer2Marker(int r, int c) {
        return this.board[r][c] == PLAYER_2_MARK;
    }

    public int[] hasWon() {
        List<int[]> positions = new ArrayList<int[]>();

        // check horizontally
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS - 1; j++) {
                int thisCell = this.board[i][j];
                int nextCell = this.board[i][j + 1];

                if (thisCell != 0 && thisCell == nextCell)
                    positions.add(new int[]{ i, j });
                else
                    positions.clear();

                if (positions.size() == 3) {
                    return new int[] {
                        positions.get(0)[0], positions.get(0)[1], i, j + 1
                    };
                }
            }
        }

        // check vertically
        for (int j = 0; j < COLS; j++) {
            for (int i = 0; i < ROWS - 1; i++) {
                int thisCell = this.board[i][j];
                int nextCell = this.board[i + 1][j];

                if (thisCell != 0 && thisCell == nextCell)
                    positions.add(new int[]{ i, j });
                else
                    positions.clear();

                if (positions.size() == 3) {
                    return new int[] {
                        positions.get(0)[0], positions.get(0)[1], i + 1, j
                    };
                }
            }
        }

        // check diagonally
        for (int i = ROWS - 1; i > 0; i--) {
             for (int j = 0, x = i; x <= COLS - 1; j++, x++) {
                int thisCell = this.board[x][j];

                try {
                    int nextCell = this.board[x + 1][j + 1];

                    if (thisCell != 0 && thisCell == nextCell)
                        positions.add(new int[]{ x, j });
                    else
                        positions.clear();

                    if (positions.size() == 3) {
                        return new int[] {
                            positions.get(0)[0], positions.get(0)[1], x + 1, j + 1
                        };
                    }
                } catch(IndexOutOfBoundsException e) {
                    positions.clear();
                }
             }
        }

        return null;
    }

    private int firstFreeRow(int column) {
        for (int i = ROWS - 1; i >= 0; i--)
            if (this.board[i][column] == 0)
                return i;

        return -1;
    }
}
