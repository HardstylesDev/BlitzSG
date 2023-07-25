package me.hardstyles.blitz.party;

import lombok.Getter;
import lombok.Setter;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;


@Getter
@Setter
public class Party {
    public UUID id;
    private UUID owner;
    private List<UUID> members;

    public Party(UUID owner) {
        this.owner = owner;
        this.members = new ArrayList<>();
        BlitzSG.getInstance().getPartyManager().getParties().add(this);

    }



    public Set<Player> getOnlineMembers() {
        Set<Player> members = new HashSet<>();
        for (UUID uuid : this.members) {
            if (Bukkit.getPlayer(uuid).isOnline()) {
                members.add(Bukkit.getPlayer(uuid));
            }
        }
        if (Bukkit.getPlayer(owner).isOnline()) {
            members.add(Bukkit.getPlayer(owner));
        }
        return members;
    }

    public void addMember(UUID uuid) {
        members.add(uuid);
    }

    public void removeMember(UUID uuid) {
        getOnlineMembers().forEach(player -> player.sendMessage(ChatUtil.color("&9Party > &c" + Bukkit.getPlayer(uuid).getName() + " has left the party!")));
        members.remove(uuid);
    }

    public void joinPrivate(Game game) {
        join(game);
        game.startLobbyCountdown();
    }

    public void join(Game game) {
        for (Player player : getOnlineMembers()) {
            game.addPlayer(player);
        }
    }

    public void message(String message) {
        for (Player player : getOnlineMembers()) {
            player.sendMessage(message);
        }
    }

    public void disband() {
        for (Player onlineMember : getOnlineMembers()) {
            IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(onlineMember.getUniqueId());
            iPlayer.setParty(null);
            onlineMember.sendMessage(ChatUtil.color("&9Party > &cYour party has been disbanded!"));
            this.members = null;
            this.owner = null;

            BlitzSG.getInstance().getPartyManager().getParties().remove(this);
        }
    }

    public void invite(Player player){
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());
        if(iPlayer.getParty() != null){
            player.sendMessage(ChatUtil.color("&9Party > &cThat player is already in a party!"));
            return;
        }

        for (Player onlineMember : getOnlineMembers()) {
            onlineMember.sendMessage(ChatUtil.color("&9Party > &a" +iPlayer.getRank().getPrefix() + player.getName() + " &ehas been invited to the party!"));
        }

        iPlayer.setPartyInvite(this);
        player.sendMessage(ChatUtil.color("&9Party > &aYou have been invited to a party!"));
        player.sendMessage(ChatUtil.color("&9Party > &aType &b/party accept &ato accept the invite!"));
    }

    public void accept(Player player){
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());
        if(iPlayer.getPartyInvite() == null){
            player.sendMessage(ChatUtil.color("&9Party > &cYou do not have a party invite!"));
            return;
        }
        if(iPlayer.getParty() != null){
            player.sendMessage(ChatUtil.color("&9Party > &cYou are already in a party!"));
            return;
        }
        Party party = iPlayer.getPartyInvite();
        party.addMember(player.getUniqueId());
        iPlayer.setParty(party);
        iPlayer.setPartyInvite(null);
        for (Player onlineMember : getOnlineMembers()) {
            onlineMember.sendMessage(ChatUtil.color("&9Party > &a" +iPlayer.getRank().getPrefix() + player.getName() + " &ehas joined the party!"));
        }
    }


    public void leave(Player s) {
        if (s.getUniqueId() == owner) {
            disband();
            return;
        }
        removeMember(s.getUniqueId());
    }
}
