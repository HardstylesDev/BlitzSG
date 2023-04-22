package me.hardstyles.blitz.cosmetic;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.cosmetic.cosmetics.aura.*;
import me.hardstyles.blitz.cosmetic.cosmetics.taunt.*;
import me.hardstyles.blitz.player.IPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CosmeticsManager {

    public static final Class<? extends Aura>[] AURAS = new Class[]{HeartParticle.class, RainbowTrail.class, RedTrail.class, BlueTrail.class, GreenTrail.class, MyceliumTrail.class, NetherrackTrail.class, FlowerTrail.class, WheatTrail.class, RedParticle.class, GreenParticle.class, BlueParticle.class, RainbowParticle.class, SlimeParticle.class, PortalParticle.class, EnchantingParticle.class, WaterParticle.class, LavaParticle.class, RainbowDustParticle.class, SnowParticle.class};
    private final ArrayList<Aura> auras = new ArrayList<>();

    public static final Class<? extends Taunt>[] TAUNTS = new Class[]{DefaultTaunt.class, RichJamesTaunt.class, CookieTaunt.class, VillagerDanceTaunt.class, FireworkTaunt.class, IRefuseTaunt.class, PigDanceTaunt.class, SheepDanceTaunt.class, BatDudeTaunt.class, WolfPackTaunt.class};
    private final ArrayList<Taunt> taunts = new ArrayList<>();


    public Aura getAura(Player p) {
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        if (bsgPlayer.getAura() == null)
            return null;
        return bsgPlayer.getAura();
    }

    public CosmeticsManager() {
        for (Class<? extends Aura> r : AURAS) {
            try {
                this.auras.add(r.getConstructor().newInstance());

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        for (Class<? extends Taunt> t : TAUNTS) {
            try {
                this.taunts.add(t.getConstructor().newInstance());

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
            if (!r.getName().equalsIgnoreCase(name) && !r.getId().equalsIgnoreCase(name)) {
                System.out.println("not equal to " + r.getName() + " or " + r.getId() + " (name: " + name + " ) ");
                continue;
            };
            return r;
        }
        return null;
    }

    public Set<Taunt> getTaunts() {
        HashSet<Taunt> allModules = new HashSet<>(this.taunts);
        return allModules;
    }

    ArrayList<Player> players = new ArrayList<>();

    public ArrayList<Player> getPlayers() {
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

    public void init() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (players.isEmpty()) {
                    return;
                }

                Iterator<Player> playerIterator = players.iterator();

                while (playerIterator.hasNext()) {
                    Player player = playerIterator.next();
                    IPlayer p = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());

                    if (p == null || p.getAura() == null || !player.isOnline()) {
                        playerIterator.remove();
                    } else {
                        if (p.isSpectating()) {
                            continue;
                        }
                        p.getAura().tick(player);
                    }
                }
            }
        }.runTaskTimerAsynchronously(BlitzSG.getInstance(), 4, 4);
    }
}
