package im.fuad.rit.copads.p3;

class C4ModelClone implements C4ModelListener {
    private C4Board board;
    private C4ModelListener modelListener;

    public C4ModelClone(C4Board board) {
        this.board = board;
    }

    public void markerAdded(Player player, Integer row, Integer col) {
        this.board.play(player.getNumber(), row, col);
        this.modelListener.markerAdded(player, row, col);
    }

    public void cleared() {
        this.board.clear();

        this.modelListener.cleared();
    }

    public void gameStarted() { this.modelListener.gameStarted(); }
    public void gameOver() { this.modelListener.gameOver(); }
    public void turn(Player player) { this.modelListener.turn(player); }

    public void setModelListener(C4ModelListener listener) {
        this.modelListener = listener;
    }
}
