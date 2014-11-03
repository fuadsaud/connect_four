package im.fuad.rit.copads.p3;

/**
 * Defines the operations necessary for objects that want to listen to server events based on the
 * application communication protocol.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
public interface C4ServerListener {
    public void number(Integer playerNumber);
    public void name(Integer playerNumber, String playerName);
    public void turn(Integer playerNumber);
    public void add(Integer playerNumber, Integer row, Integer col);
    public void clear();
}

