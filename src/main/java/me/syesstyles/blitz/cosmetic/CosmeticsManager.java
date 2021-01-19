package me.syesstyles.blitz.cosmetic;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.cosmetic.cosmetics.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CosmeticsManager {

    public static final Class<? extends Aura>[] AURAS = new Class[]{RainbowTrail.class, RedTrail.class, BlueTrail.class, GreenTrail.class, MyceliumTrail.class, NetherrackTrail.class, FlowerTrail.class, WheatTrail.class, RedParticle.class, GreenParticle.class, BlueParticle.class, RainbowParticle.class, SlimeParticle.class, PortalParticle.class, EnchantingParticle.class, WaterParticle.class, LavaParticle.class, RainbowDustParticle.class, SnowParticle.class};
    private final ArrayList<Aura> auras = new ArrayList<>();


    public Aura getAura(Player p) {
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        if (bsgPlayer.getAura() == null)
            return null;
        return bsgPlayer.getAura();
    }

    public CosmeticsManager() {
        for (Class<? extends Aura> rankClass : AURAS) {
            try {
                this.auras.add(rankClass.getConstructor().newInstance());

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
                if (players.size() == 0)
                    return;
                for (Player player : players) {
                    BlitzSGPlayer p = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(player.getUniqueId());
                    if (p.getAura() == null || !player.isOnline()) {
                        players.remove(player);
                        return;
                    }
                    p.getAura().uh(player);
                }
            }
        }.runTaskTimerAsynchronously(BlitzSG.getInstance(), 4, 4);
    }
}
