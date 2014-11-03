package im.fuad.rit.copads.p3;

/**
 * Model class for a player in the game. It's basically a value object to group values that need to
 * be passed along together.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
public class Player {
    private Integer number;
    private String name;
    private Boolean isMe;

    public Player(Integer number, String name, Boolean isMe) {
        this.number = number;
        this.name   = name;
        this.isMe   = isMe;
    }

    public Integer getNumber() { return this.number; }
    public String getName() { return this.name; }
    public Boolean isMe() { return this.isMe; }
}
