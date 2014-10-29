package im.fuad.rit.copads.p3;

public interface C4ModelListener {
    public void markerAdded(Player player, Integer row, Integer col);
    public void cleared();
    public void gameStarted();
    public void gameOver();
    public void turn(Player player);
}

