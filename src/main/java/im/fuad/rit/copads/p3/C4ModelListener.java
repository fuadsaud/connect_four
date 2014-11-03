package im.fuad.rit.copads.p3;

/**
 * Defines the operations necessary for objects that want to listen to model events.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
public interface C4ModelListener {
    public void markerAdded(Player player, Integer row, Integer col);
    public void cleared();
    public void gameStarted();
    public void gameOver();
    public void turn(Player player);
}

