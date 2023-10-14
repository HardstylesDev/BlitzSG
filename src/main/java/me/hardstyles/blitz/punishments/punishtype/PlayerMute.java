package me.hardstyles.blitz.punishments.punishtype;

import lombok.Getter;
import lombok.Setter;
import me.hardstyles.blitz.util.ChatUtil;

@Getter
@Setter
public class PlayerMute extends PunishType {
    public PlayerMute(long startTime, long endTime, String reason, String sender, boolean active) {
        super(startTime, endTime, reason, sender, active);
    }

    @Override
    public String getName() {
        return "Mute";
    }

    @Override public String getNamePlural() {
        return "Mutes";
    }

    @Override
    public String getPastTense() {
        return "Muted";
    }
}
