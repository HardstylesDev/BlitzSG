package me.syesstyles.blitz.utils;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.game.Game;
import net.minecraft.server.v1_8_R3.EnumChatFormat;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class FireworkCommand implements CommandExecutor {

    public static HashMap<UUID, Long> cooldown = new HashMap<>();

    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        Player p = (Player) sender;


        if (!(!cooldown.containsKey(p.getUniqueId()) || (cooldown.containsKey(p.getUniqueId()) && System.currentTimeMillis() > cooldown.get(p.getUniqueId()) + 5000))) {
            BlitzSG.send(p, "&cPlease wait before using this again.");
            return true;
        }


        BlitzSGPlayer blitzSGPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        if (blitzSGPlayer.isInGame()) {
            if (blitzSGPlayer.getGame().getGameMode() != Game.GameMode.STARTING) {
                BlitzSG.send(p, BlitzSG.CORE_NAME + "&cYou can't do this after the game has started!");
                return true;
            }

        }
        cooldown.put(p.getUniqueId(), System.currentTimeMillis());
        this.launchFirework(p.getEyeLocation());
        sender.sendMessage(EnumChatFormat.GREEN + "Launched a firework!");
        return true;
    }

    public void launchFirework(Location location){
        Firework fw = (Firework) location.getWorld().spawn(location, Firework.class);
        FireworkMeta meta = fw.getFireworkMeta();
        FireworkEffect.Builder builder = FireworkEffect.builder();
        FireworkEffect fwe = null;
        Random random = new Random();
        builder.withColor(randomColor());
        if (random.nextBoolean())
            builder.withColor(randomColor());
        if (random.nextBoolean())
            builder.withColor(randomColor());
        if (random.nextBoolean())
            builder.withFlicker();

        builder.withFade(randomColor());

        builder.with(randomType());


        builder.trail(true);
        fwe = builder.build();
        meta.addEffect(fwe);
        meta.setPower(1);
        fw.setFireworkMeta(meta);

    }
    public Color[] fireworkEffectColor = new Color[]{Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GRAY, Color.LIME, Color.MAROON, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER, Color.TEAL, Color.WHITE, Color.YELLOW};
    public FireworkEffect.Type[] fireworkEffectType = new FireworkEffect.Type[]{FireworkEffect.Type.BALL, FireworkEffect.Type.BALL_LARGE, FireworkEffect.Type.BURST, FireworkEffect.Type.CREEPER, FireworkEffect.Type.STAR};

    public FireworkEffect.Type randomType() {
        int rnd = new Random().nextInt(fireworkEffectType.length);
        return fireworkEffectType[rnd];
    }

    public Color randomColor() {
        int rnd = new Random().nextInt(fireworkEffectColor.length);
        return fireworkEffectColor[rnd];
    }
}