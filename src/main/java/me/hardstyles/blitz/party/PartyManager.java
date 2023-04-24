package me.hardstyles.blitz.party;

import lombok.Getter;
import me.hardstyles.blitz.player.IPlayerManager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class PartyManager {
    private final IPlayerManager playerManager;
    private final Set<Party> parties = new HashSet<>();

    public PartyManager(IPlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public Party getPartyByOwner(UUID owner) {
        for (Party party : parties) {
            if (party.getOwner().equals(owner)) {
                return party;
            }
        }
        return null;
    }



}
