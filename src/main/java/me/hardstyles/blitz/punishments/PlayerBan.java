package me.hardstyles.blitz.punishments;

import lombok.Getter;
import lombok.Setter;
import me.hardstyles.blitz.util.ChatUtil;

@Getter
@Setter
public class PlayerBan {

    private long endTime;
    private String reason;
    private String sender;

    public PlayerBan(long endTime, String reason, String sender) {
        this.endTime = endTime;
        this.reason = reason;
        this.sender = sender;
    }

    public boolean isBanned() {
        return System.currentTimeMillis() < endTime;
    }


    @Override
    public String toString() {
        return "Banned for " + reason + " for " + ChatUtil.formatDate(endTime) + " (" + endTime + ") by " + sender + ".";
    }
}