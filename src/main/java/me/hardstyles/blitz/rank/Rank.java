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

    public boolean isStaff(){
        return this.position >= 6;
    }

    public boolean isMvpPlus(){
        return this.position >= 4;
    }

    public boolean isVip(){
        return this.position >= 1;
    }

    public boolean isManager(){
        return this.position >= 8;
    }

    public boolean canBan(){
        return this.position >= 7;
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
