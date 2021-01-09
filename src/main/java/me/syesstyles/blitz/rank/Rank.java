package me.syesstyles.blitz.rank;

public class Rank {
    private final String rank;
    private final String prefix;
    private final String chatColor;
    private final int multiplier;


    public Rank(String rank, String prefix, String chatColor, int multiplier) {
        this.multiplier = multiplier;
        this.rank = rank;
        this.prefix = prefix;
        this.chatColor = chatColor;
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

    public String getChatColor() {
        return chatColor;
    }
}
