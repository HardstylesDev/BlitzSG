package me.hardstyles.blitz.nickname;

public class Nick {
    private String nickName;
    private String skinValue;
    private String skinSignature;
    private boolean nicked;

    public boolean isNicked() {
        return nicked;
    }

    public String getNickName() {
        return nickName;
    }

    public String getSkinValue() {
        return skinValue;
    }

    public String getSkinSignature() {
        return skinSignature;
    }


    public Nick(final String nickName, final String skinValue, final String skinSignature, final boolean isNicked) {
        this.nickName = nickName;
        this.skinValue = skinValue;
        this.skinSignature = skinSignature;
        this.nicked = isNicked;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setSkinValue(String skinValue) {
        this.skinValue = skinValue;
    }
    public void setNicked(boolean isNicked) {
        this.nicked = isNicked;
    }
    public void setSkinSignature(String skinSignature) {
        this.skinSignature = skinSignature;
    }
}
