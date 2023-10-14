package me.hardstyles.blitz.punishments.punishtype;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerBan extends PunishType {


    public PlayerBan(long startTime, long endTime, String reason, String sender, boolean active) {
        super(startTime, endTime, reason, sender, active);
    }

    @Override
    public String getName() {
        return "Ban";
    }

    @Override public String getNamePlural() {
        return "Bans";
    }

    @Override
    public String getPastTense() {
        return "Banned";
    }
    public boolean isBanned() {
        return this.isActive() && System.currentTimeMillis() < getEndTime();
    }

}