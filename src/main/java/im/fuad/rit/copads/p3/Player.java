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

    /**
     * Initializes a player.
     */
    public Player(Integer number, String name, Boolean isMe) {
        this.number = number;
        this.name   = name;
        this.isMe   = isMe;
    }

    /**
     * Gets this players' number.
     *
     * @return this player's number.
     */
    public Integer getNumber() { return this.number; }

    /**
     * Gets this players' name.
     *
     * @return this player's name.
     */
    public String getName() { return this.name; }

    /**
     * Gets whether this player object represents the one player who's playing on this client.
     *
     * @return whether this player object represents the one player who's playing on this client.
     */
    public Boolean isMe() { return this.isMe; }
}
