package im.fuad.rit.copads.p4;

import java.io.IOException;

import im.fuad.rit.copads.p4.C4ModelListener;
import im.fuad.rit.copads.p4.C4ViewListener;
import im.fuad.rit.copads.p4.C4Board;

/**
 * Server side model for the Connect Four game.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
class C4Model implements C4ViewListener {
    private C4Board board;
    private C4ModelListener player1;
    private C4ModelListener player2;

    private String player1Name;
    private String player2Name;

    private Integer turn;
    private Boolean terminated;

    /**
     * Intializes this model with an empty board.
     */
    public C4Model() {
        this.terminated = false;
        this.board = new C4Board();
    }

    /**
     * Joins this game model.
     *
     * @param listener the object to listen for model events for this new player.
     * @param playerName this new player's name.
     */
    public void join(C4ModelListener listener, String playerName) {
        try {
            if (this.player1 == null) {
                this.player1 = listener;
                this.player1Name = playerName;

                this.player1.number(1);
                this.player1.name(1, this.player1Name);
            } else if (this.player2 == null) {
                this.player2 = listener;
                this.player2Name = playerName;

                this.turn = 1;

                this.player1.name(2, this.player2Name);
                this.player1.turn(this.turn);

                this.player2.number(2);
                this.player2.name(1, this.player1Name);
                this.player2.name(2, this.player2Name);
                this.player2.turn(this.turn);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Informs the model that a marker should be added for the current player in the given column.
     *
     * @param playerNumber the player number for which the marker is to be added.;
     * @param column the column in which the marker is to be added.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void addMarker(Integer playerNumber, Integer column) throws IOException {
        if (this.turn == playerNumber) {
            try {
                int[] coords = this.board.play(playerNumber, column);

                if (coords[0] != -1 && coords[1] != -1) {
                    if (this.board.hasWon() != null)
                        this.turn = 0;
                    else if (this.turn == 1)
                        this.turn = 2;
                    else
                        this.turn = 1;

                    this.player1.markerAdded(playerNumber, coords[0], coords[1]);
                    this.player2.markerAdded(playerNumber, coords[0], coords[1]);

                    this.player1.turn(this.turn);
                    this.player2.turn(this.turn);
                }
            } catch(IllegalArgumentException e) {
            }
        }
    }

    /**
     * Informs the model to clear the game board.
     *
     * @exception IOException thrown if an I/O error occurred.
     */
    public void clear() throws IOException {
        try {
            this.board.clear();

            this.player1.cleared();
            this.player2.cleared();

            this.turn = 1;

            this.player1.turn(this.turn);
            this.player2.turn(this.turn);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Leave the game model.
     */
    public void leave() {
        try {
            if (this.player1 != null) this.player1.left();
            if (this.player2 != null) this.player2.left();

            this.terminated = true;
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Indicates whether this game session has terminated already.
     *
     * @return whether this game session is terminated.
     */
    public Boolean isTerminated() { return this.terminated; }
}
