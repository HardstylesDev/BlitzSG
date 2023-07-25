package me.hardstyles.blitz.punishments;

import lombok.Getter;
import lombok.Setter;
import me.hardstyles.blitz.util.ChatUtil;

@Getter
@Setter
public class PlayerMute {

    private long endTime;
    private String reason;

    public PlayerMute(long endTime, String reason) {
        this.endTime = endTime;
        this.reason = reason;
    }

    public boolean isMuted() {
        return System.currentTimeMillis() < endTime;
    }

    @Override
    public String toString() {
        return "Muted for " + reason + " for " + ChatUtil.formatDate(endTime) + " (" + endTime + ")";
    }
}
