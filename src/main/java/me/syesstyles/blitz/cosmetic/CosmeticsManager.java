package me.syesstyles.blitz.cosmetic;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.cosmetic.cosmetics.aura.*;
import me.syesstyles.blitz.cosmetic.cosmetics.taunt.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Set;

public class CosmeticsManager {

    public static final Class<? extends Aura>[] AURAS = new Class[]{RainbowTrail.class, RedTrail.class, BlueTrail.class, GreenTrail.class, MyceliumTrail.class, NetherrackTrail.class, FlowerTrail.class, WheatTrail.class, RedParticle.class, GreenParticle.class, BlueParticle.class, RainbowParticle.class, SlimeParticle.class, PortalParticle.class, EnchantingParticle.class, WaterParticle.class, LavaParticle.class, RainbowDustParticle.class, SnowParticle.class};
    private final ArrayList<Aura> auras = new ArrayList<>();

    public static final Class<? extends Taunt>[] TAUNTS = new Class[]{DefaultTaunt.class, RichJamesTaunt.class, CookieTaunt.class, VillagerDanceTaunt.class, FireworkTaunt.class, IRefuseTaunt.class, PigDanceTaunt.class, SheepDanceTaunt.class, BatDudeTaunt.class, WolfPackTaunt.class};
    private final ArrayList<Taunt> taunts = new ArrayList<>();


    public Aura getAura(Player p) {
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        if (bsgPlayer.getAura() == null)
            return null;
        return bsgPlayer.getAura();
    }

    public Taunt getTaunt(Player p) {
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        if (bsgPlayer.getTaunt() == null)
            return null;
        return bsgPlayer.getTaunt();
    }

    public CosmeticsManager() {
        for (Class<? extends Aura> rankClass : AURAS) {
            try {
                this.auras.add(rankClass.getConstructor().newInstance());

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        for (Class<? extends Taunt> rankClass : TAUNTS) {
            try {
                this.taunts.add(rankClass.getConstructor().newInstance());

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public Aura getAuraByName(String name) {
        for (Aura r : this.auras) {
            if (!r.getName().equalsIgnoreCase(name) && !r.getId().equalsIgnoreCase(name)) continue;
            return r;
        }
        return null;
    }

    public Taunt getTauntByName(String name) {
        for (Taunt r : this.taunts) {
            if (!r.getName().equalsIgnoreCase(name) && !r.getId().equalsIgnoreCase(name)) continue;
            return r;
        }
        return null;
    }


    Set<Player> players = new HashSet<>();

    public Set<Player> getPlayers() {
        return players;
    }

    public void add(Player p) {
        players.add(p);
    }

    public void remove(Player p) {
        players.remove(p);
    }

    public Set<Aura> getAuras() {
        HashSet<Aura> allModules = new HashSet<Aura>();
        allModules.addAll(this.auras);
        return allModules;
    }

    public Set<Taunt> getTaunts() {
        HashSet<Taunt> allModules = new HashSet<Taunt>();
        allModules.addAll(this.taunts);
        return allModules;
    }

    public void initializeAuras() {
        new BukkitRunnable() {

            @Override
            public void run() {
                if (players.size() == 0)
                    return;
                for (Player player : players) {
                    if (player == null){
                        try { players.removeIf(b -> b == null); } catch (ConcurrentModificationException ignored){}
                        continue;

                    }

                    BlitzSGPlayer p = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(player.getUniqueId());
                    if (p == null || p.getAura() == null || !player.isOnline() || p.getGame() == null || !p.getGame().getAlivePlayers().contains(player)) {
                        players.remove(player);
                        return;
                    }
                    p.getAura().uh(player);
                }
            }
        }.runTaskTimerAsynchronously(BlitzSG.getInstance(), 4, 4);
    }
}
