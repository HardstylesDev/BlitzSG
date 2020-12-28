package me.syesstyles.blitz.rank;

public class Rank {
    private final String rank;
    private final String prefix;

    public Rank(String rank, String prefix){

        this.rank = rank;
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getRank() {
        return rank;
    }
}
