package me.hardstyles.blitz.punishments.punishtype;

import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class PunishType {
    private final String executor;
    private final long startTime;
    private final long endTime;
    private final String reason;
    @Setter private boolean active;

    public PunishType(long startTime, long endTime, String reason, String sender, boolean active) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
        this.active = active;
        this.executor = sender;
    }

    public boolean isActive() {
        return active && System.currentTimeMillis() < endTime;
    }

    public abstract String getName();

    public abstract String getNamePlural();

    public abstract String getPastTense();
}
