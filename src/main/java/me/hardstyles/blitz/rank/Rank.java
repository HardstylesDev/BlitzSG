package me.hardstyles.blitz.rank;

public class Rank {
    private final String rank;
    private final String prefix;
    private final String chatColor;
    private final int multiplier;
    private final int position;


    public Rank(String rank, String prefix, String chatColor, int multiplier, int position) {
        this.multiplier = multiplier;
        this.rank = rank;
        this.prefix = prefix;
        this.chatColor = chatColor;
        this.position = position;
    }

    public int getMultiplier(){
        return multiplier;
    }
    public String getPrefix() {
        return prefix;
    }

    public String getRank() {
        return rank;
    }
    public int getPosition(){return position;}
    public String getChatColor() {
        return chatColor;
    }
    public String getRankFormatted(){
        return prefix.replaceAll("] ","").replaceAll("\\[", "");
    }
}
