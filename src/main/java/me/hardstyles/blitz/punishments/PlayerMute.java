package me.hardstyles.blitz.punishments;

import lombok.Getter;
import lombok.Setter;
import me.hardstyles.blitz.util.ChatUtil;

@Getter
@Setter
public class PlayerMute {

    private long endTime;
    private String reason;
    private String sender;

    public PlayerMute(long endTime, String reason, String sender) {
        this.endTime = endTime;
        this.reason = reason;
        this.sender = sender;

    }

    public boolean isMuted() {
        System.out.println("System.currentTimeMillis() < endTime: " + System.currentTimeMillis() + " < " + endTime);
        return System.currentTimeMillis() < endTime;
    }

    @Override
    public String toString() {
        return "Muted for " + reason + " for " + ChatUtil.formatDate(endTime) + " (" + endTime + ")";
    }
}
