package im.fuad.rit.copads.p3;

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
