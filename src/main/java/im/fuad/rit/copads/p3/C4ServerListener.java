package im.fuad.rit.copads.p3;

public interface C4ServerListener {
    public void number(Integer playerNumber);
    public void name(Integer playerNumber, String playerName);
    public void turn(Integer playerNumber);
    public void add(Integer playerNumber, Integer row, Integer col);
    public void clear();
}

