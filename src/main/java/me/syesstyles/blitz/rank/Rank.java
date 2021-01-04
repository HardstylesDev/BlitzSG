package me.syesstyles.blitz.rank;

public class Rank {
    private final String rank;
    private final String prefix;
    private final String chatColor;

    public Rank(String rank, String prefix, String chatColor){

        this.rank = rank;
        this.prefix = prefix;
        this.chatColor = chatColor;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getRank() {
        return rank;
    }
    public String getChatColor(){
        return chatColor;
    }
}
