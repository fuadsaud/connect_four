package im.fuad.rit.copads.p4.server;

import java.io.IOException;

import im.fuad.rit.copads.p4.C4ModelListener;
import im.fuad.rit.copads.p4.C4ViewListener;
import im.fuad.rit.copads.p4.C4Board;

class C4Model implements C4ViewListener {
    private C4Board board;
    private C4ModelListener player1;
    private C4ModelListener player2;

    private String player1Name;
    private String player2Name;

    private Integer turn;
    private Boolean terminated;

    public C4Model() {
        this.board = new C4Board();
    }

    public void join(C4ModelListener listener, String playerName) {
        try {
            if (this.player1 == null) {
                this.player1 = listener;
                this.player1Name = playerName;

                this.player1.number(1);
                this.player1.name(1, this.player1Name);

                System.out.println("PLAYER 1 JOINING" + playerName);
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

                System.out.println("PLAYER 2 JOINING" + playerName);
            }
        // } catch(IOException e) {
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @see C4ViewListener.addMarker()
     */
    public void addMarker(Integer column) throws IOException {
    }

    /**
     * @see C4ViewListener.clear()
     */
    public void clear() throws IOException {
        try {
            this.board.clear();

            this.turn = 1;

            this.player1.cleared();
            this.player2.cleared();

            this.player1.turn(this.turn);
            this.player2.turn(this.turn);
        // } catch(IOException e) {
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void terminate() { this.terminated = true; }
}
