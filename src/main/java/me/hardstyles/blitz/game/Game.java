package me.hardstyles.blitz.game;

import me.hardstyles.blitz.arena.Arena;
import me.hardstyles.blitz.blitzsgplayer.IPlayer;
import me.hardstyles.blitz.utils.ItemBuilder;
import me.hardstyles.blitz.utils.ItemUtils;
import me.hardstyles.blitz.BlitzSG;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Golem;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Game {

    private boolean speedMode = false;
    private NextEvent nextEvent;
    private boolean canFindStar = false;

    public void applyDeathEffects() {
        for (Player alivePlayer : alivePlayers) {
            alivePlayer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 15 * 15, 1));
        }
    }

    public static enum GameMode {
        LOADING, INACTIVE, WAITING,
        STARTING, INGAME, RESETING
    }

    public static enum NextEvent {
        STAR, REFILL, DEATHMATCH, ENDING
    }

    private ArrayList<Player> allPlayers;
    private ArrayList<Player> alivePlayers;
    private ArrayList<Player> deadPlayers;

    private GameMode gameMode;

    private Arena arena;
    private Player winner;

    private HashMap<Player, Boolean> votes;
    private ArrayList<Location> openedChests;
    private ArrayList<Location> starChests;
    private HashSet<Location> spawnUsed;
    private boolean isDeathmatchStarting = false;

    private int countdownTime, gameTime, deathmatchCountDownTime;

    private double borderSize;
    private double borderShrinkBy;

    public Game() {
        gameMode = GameMode.LOADING;
        arena = BlitzSG.getInstance().getArenaManager().getRandomArena();

        if (arena == null) {
            Bukkit.broadcastMessage(BlitzSG.CORE_NAME + ChatColor.RED + "Couldn't find an available arena!");
            return;
        }

        this.openedChests = new ArrayList<>();
        this.starChests = new ArrayList<>();

        BlitzSG.getInstance().getArenaManager().fixSpawns(arena);
        if (arena == null || arena.getSpawns().get(0).getWorld() == null) {
            return;
        }
        arena.setInUse(true);
        BlitzSG.getInstance().getGameManager().addGame(this);


        allPlayers = new ArrayList<Player>();
        alivePlayers = new ArrayList<Player>();
        deadPlayers = new ArrayList<Player>();
        votes = new HashMap<Player, Boolean>();
        spawnUsed = new HashSet<Location>();
        gameMode = GameMode.WAITING;
    }

    public void addPlayer(Player p) {
        if (gameMode != GameMode.WAITING) {
            p.sendMessage(BlitzSG.CORE_NAME + ChatColor.RED + "This game already started!");
            return;
        }
        if (arena == null || arena.getSpawns().get(0).getWorld() == null) {
            p.sendMessage(BlitzSG.CORE_NAME + ChatColor.YELLOW + "Couldn't find an available arena!");
            return;
        }
        IPlayer uhcPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        uhcPlayer.getGameEntities().clear();
        if (uhcPlayer.isInGame()) {

            BlitzSG.send(p, BlitzSG.CORE_NAME + "&cYou are already in a game!");

            return;
        }
        if (arena.getSpawns().size() < alivePlayers.size() + 1) {
            BlitzSG.send(p, BlitzSG.CORE_NAME + "&cThe game is already full!");
            return;
        }

        uhcPlayer.setGame(this);
        //BlitzSG.getInstance().getNametagManager().update();

        allPlayers.add(p);
        alivePlayers.add(p);
        p.teleport(arena.getLobby().clone().add(0.5, 0, 0.5));
        msgAll(BlitzSG.CORE_NAME + "&7" + uhcPlayer.getRank(true).getChatColor() + p.getName() + " &ehas joined (&b" + alivePlayers.size() + "&e/&b" + arena.getSpawns().size() + "&e)!");
        if (alivePlayers.size() >= 2 && gameMode.equals(GameMode.WAITING)) {
            startLobbyCountdown();
        }
        resetPlayer(p);
        p.setFlying(false);
        p.setAllowFlight(false);

    }

    public void teleportSpawn(Player p) {
        Location playerSpawn = null;
        for (Location l : arena.getSpawns())
            if (!spawnUsed.contains(l)) {
                playerSpawn = l;
                break;
            }
        //createCage(playerSpawn);

        spawnUsed.add(playerSpawn);
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        iPlayer.setGameSpawn(playerSpawn.clone().add(0.5, 1.0, 0.5));

        p.teleport(playerSpawn.clone().add(0.5, 1.0, 0.5));


    }

    public void removePlayer(Player p) {
        if (gameMode == GameMode.INGAME) {
            BlitzSG.send(p, BlitzSG.CORE_NAME + "&cYou can't leave while the game is running!");
            //killPlayer(p);
            return;
        }
        IPlayer uhcPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        uhcPlayer.setGame(null);
        spawnUsed.remove(arena.getSpawns().get(alivePlayers.indexOf(p)));
        alivePlayers.remove(p);
        allPlayers.remove(p);
        msgAll(BlitzSG.CORE_NAME + "&7" + p.getName() + " &ehas left (&b" + alivePlayers.size() + "&e/&b" + arena.getSpawns().size() + "&e)!");
        resetPlayer(p);
        BlitzSG.getInstance().getIPlayerManager().toLobby(p);
        BlitzSG.send(p, BlitzSG.CORE_NAME + "&cYou have left the game!");
    }

    private boolean startedCountdown = false;

    public void startLobbyCountdown() {
        if (startedCountdown)
            return;
        startedCountdown = true;
        countdownTime = speedMode ? 6 : 16; // 21
        //for (Player alivePlayer : alivePlayers) {
        //    teleportSpawn(alivePlayer);
        //}

        new BukkitRunnable() {
            public void run() {
                countdownTime--;
                if (alivePlayers.size() < 2) {
                    this.cancel();
                    gameMode = GameMode.WAITING;
                    startedCountdown = false;
                    msgAll("&cWe don't have enough players! Countdown cancelled.");
                    return;
                }
                if (countdownTime == 0) {

                    for (Player alivePlayer : alivePlayers) {
                        setPregameInventory(alivePlayer);
                        teleportSpawn(alivePlayer);
                    }

                    startCountDown();
                    this.cancel();


                    return;
                }
                if (countdownTime % 10 == 0 || countdownTime <= 10) {
                    if (countdownTime <= 10)
                        msgAll(BlitzSG.CORE_NAME + "&e" + countdownTime + " &eseconds until the game starts!");
                }
            }
        }.runTaskTimer(BlitzSG.getInstance(), 0, 20);
    }

    public void startCountDown() {
        gameMode = GameMode.STARTING;
        countdownTime = speedMode ? 6 : 31;
        ; // 31

        new BukkitRunnable() {
            public void run() {
                countdownTime--;
                if (countdownTime == 30) {
                    //BlitzSG.broadcast("", Bukkit.getWorld("world"));
                    //BlitzSG.broadcast("&eA BSG &egame on the map &a" + arena.getName() + " &eis bound to start in &b" + countdownTime + " &eseconds. Use &6/bsg join &eto enter the game.", Bukkit.getWorld("world"));
                    // BlitzSG.broadcast("", Bukkit.getWorld("world"));
                }
                if (alivePlayers.size() < 2) {
                    this.cancel();
                    gameMode = GameMode.WAITING;
                    msgAll("&cWe don't have enough players! Countdown cancelled.");
                    return;
                }
                if (countdownTime == 0) {
                    startGame();
                    this.cancel();
                    return;
                }
                if (countdownTime % 10 == 0 || countdownTime <= 5) {
                    if (countdownTime <= 5)
                        msgAll(BlitzSG.CORE_NAME + "&eYou will be able to move in &c" + countdownTime + " &e" + (countdownTime == 1 ? "second!" : "seconds!"));
                    else if (countdownTime <= 15)
                        msgAll(BlitzSG.CORE_NAME + "&eYou will be able to move in &6" + countdownTime + " &eseconds!");
                    else if (countdownTime <= 25)
                        msgAll(BlitzSG.CORE_NAME + "&eYou will be able to move in &e" + countdownTime + " &eseconds!");
                    else if (countdownTime <= 30) {
                        msgAll(BlitzSG.CORE_NAME + "&eYou will be able to move in " + countdownTime + "! Choose a kit by right clicking the bow!");
                    }
                }
            }
        }.runTaskTimer(BlitzSG.getInstance(), 0, 20);
    }

    public void startGame() {
        nextEvent = NextEvent.STAR;
        gameMode = GameMode.INGAME;
        arena.getArenaWorld().setTime(0);
        for (Player p : alivePlayers) {
            p.closeInventory();
            resetPlayer(p);

            p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 2, 1);
            // p.sendMessage("&aThe game has started, Good Luck!");
            IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
            if (bsgPlayer.getSelectedKit() == null)
                bsgPlayer.setSelectedKit(BlitzSG.getInstance().getKitManager().getKit("Knight"));
            BlitzSG.send(p, BlitzSG.CORE_NAME + "&eYou will get the items for your " + bsgPlayer.getSelectedKit().getName() + " kit in 60 seconds.");
            BlitzSG.send(p, BlitzSG.CORE_NAME + "&6The Blitz Star will be released in 5 minutes!");
            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60 * 20, 2));
            p.setHealth(19);
            p.setHealth(20);
            p.getInventory().setItem(0, new ItemBuilder(Material.COMPASS).name("&rTracking Device").make());


        }
        for (Location l : arena.getSpawns())
            if (spawnUsed.contains(l))
                //removeCage(l);
                gameTime = 0;
        new BukkitRunnable() {
            public void run() {
                if (alivePlayers.size() < 2) {
                    this.cancel();
                    endGame(false);
                    return;
                }
                if (gameTime > 53 && gameTime < 59)
                    msgAll(BlitzSG.CORE_NAME + "&eYou will get your items in " + (59 - gameTime) + " seconds!");
                if (gameTime == 59) {
                    msgAll("&eThe grace period has ended, PvP is now enabled!");
                    for (Player p : alivePlayers) {
                        p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
                        bsgPlayer.getSelectedKit().giveKit(p, bsgPlayer.getKitLevel(bsgPlayer.getSelectedKit()));
                        BlitzSG.send(p, BlitzSG.CORE_NAME + "&eYou got your " + bsgPlayer.getSelectedKit().getName() + " kit!");
                    }
                    msgAll(BlitzSG.CORE_NAME + "&6The Blitz Star will be released in 4 minutes!");
                    // kit.giveKit(p, BlitzSG.getInstance().getIPlayerManager().getBsgPlayer(p.getUniqueId()).getKitLevel(kit));

                }
                if (gameTime == 119)
                    msgAll(BlitzSG.CORE_NAME + "&6The Blitz Star will be released in 3 minutes!");
                if (gameTime == 179)
                    msgAll(BlitzSG.CORE_NAME + "&6The Blitz Star will be released in 2 minutes!");
                if (gameTime == 239)
                    msgAll(BlitzSG.CORE_NAME + "&6The Blitz Star will be released in 1 minutes!");
                if (gameTime == 299) {
                    msgAll(BlitzSG.CORE_NAME + "&6The Blitz Star has been hidden in a chest! Find it to activate your Blitz!");
                    canFindStar = true;

                    starChests = new ArrayList<>();
                    if (!isDeathmatchStarting)
                        nextEvent = NextEvent.REFILL;
                }

                if (gameTime == 599) {
                    refillChests();
                    nextEvent = NextEvent.DEATHMATCH;
                }
                if (gameTime == 839 || gameTime == 869) {
                    //msgAll(BlitzSG.CORE_NAME + "&eDeathmatch begins in " + (gameTime == 839 ? 60 : 30) + " seconds!");
                    if (!isDeathmatchStarting) {
                        isDeathmatchStarting = true;
                        startDeathmatchCounter(gameTime);
                    }
                }
                if (gameTime == 1199) {
                    this.cancel();
                    endGame(true);
                    return;
                }
                gameTime++;
                if (alivePlayers.size() > 1) {
                    for (Player alivePlayer : alivePlayers) {

                        compassTarget(alivePlayer);
                        mobTeleport(alivePlayer);
                    }
                }
            }
        }.runTaskTimer(BlitzSG.getInstance(), speedMode ? 4 : 20, speedMode ? 4 : 20);
    }

    private int deathmatchStartTime = 0;

    public void startDeathmatchCounter(int currentTime) {
        deathmatchCountDownTime = currentTime;
        isDeathmatchStarting = true;
        String bs = ChatColor.BLUE + "[GAME] ";
        new BukkitRunnable() {
            public void run() {
                if (deathmatchStartTime == 15) {
                    msgAll(BlitzSG.CORE_NAME + "&eDeathmatch begins in 30 seconds!");
                    msgAll(BlitzSG.CORE_NAME + "&eThe Blitz Star can no longer be used!");
                }
                if (deathmatchStartTime > 39 && deathmatchStartTime < 45)
                    msgAll(BlitzSG.CORE_NAME + "&eDeathmatch begins in " + (45 - deathmatchStartTime) + " second" + (((45 - deathmatchStartTime) == 1) ? "" : "s") + "!");
                if (deathmatchStartTime == 45) {
                    startDeathmatch();
                    msgAll(BlitzSG.CORE_NAME + "&eDeathmatch started! You cannot damage anyone for 15 seconds!");
                }
                if (deathmatchStartTime > 54 && deathmatchStartTime < 60)
                    msgAll(BlitzSG.CORE_NAME + "&eYou will be able to damage players in " + (60 - deathmatchStartTime) + " second" + (((60 - deathmatchStartTime) == 1) ? "" : "s") + "!");
                if (deathmatchStartTime == 60)
                    msgAll(BlitzSG.CORE_NAME + "&eKill! Kill! Kill!");
                if (deathmatchStartTime == 240) {
                    this.cancel();
                    endGame(true);
                    return;
                }

                deathmatchCountDownTime++;
                deathmatchStartTime++;
            }
        }.runTaskTimer(BlitzSG.getInstance(), speedMode ? 4 : 20, speedMode ? 4 : 20);
    }

    private void startDeathmatch() {
        if (arena.getDeathmatch() == null)
            return;
        Location dm = arena.getDeathmatch();
        if (arena.getDeathmatchDistance() == 0) {
            alivePlayers.forEach(player -> player.teleport(dm));
            return;
        }
        int dist = arena.getDeathmatchDistance();
        Location[] locations = new Location[]{dm.clone().add(dist, 0, 0), dm.clone().add(-dist, 0, 0), dm.clone().add(0, 0, dist), dm.clone().add(0, 0, -dist)};
        int a = 0;
        for (Player alivePlayer : alivePlayers) {
            if (a == 4) a = 0;
            alivePlayer.teleport(locations[a]);
            a++;
        }
        deadPlayers.forEach(player -> player.teleport(dm));
    }

    public void mobTeleport(Player p) {
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        if (iPlayer.getGameEntities().size() >= 1) {
            List<Entity> removeList = new ArrayList<>();
            iPlayer.getGameEntities().forEach(entity -> {
                        if (entity.isDead())
                            removeList.add(entity);
                        if (p.getLocation().distance(entity.getLocation()) > 15) {
                            entity.teleport(p.getLocation());
                        }
                    }
            );
            iPlayer.getGameEntities().forEach(entity -> {

                for (Entity entityList : entity.getNearbyEntities(15, 15, 15))
                    if (entityList instanceof Player) {
                        Player potentialTarget = (Player) entityList;
                        if (!iPlayer.getGame().isDead(potentialTarget) && potentialTarget != p) {
                            if (entity instanceof Monster)
                                if (((Monster) entity).getTarget() == null)
                                    ((Monster) entity).setTarget(potentialTarget);
                                else if (entity instanceof Golem)
                                    if (((Golem) entity).getTarget() == null)
                                        ((Golem) entity).setTarget(potentialTarget);
                            continue;
                        }
                    }
            });
            removeList.forEach(entity -> iPlayer.getGameEntities().remove(entity));
        }
    }


    public void compassTarget(Player p) {
        Player result = null;
        double lastDistance = 500;

        for (Player pl : p.getWorld().getPlayers()) {
            if (pl == p) continue;
            double distance = pl.getLocation().distance(p.getLocation());
            if (distance < lastDistance) {
                lastDistance = distance;
                result = pl;
            }
        }
        if (result != null)
            p.setCompassTarget(result.getLocation());

    }

    public void killPlayer(Player p) {

        alivePlayers.remove(p);
        if (alivePlayers.size() <= 3) {
            isDeathmatchStarting = true;
            startDeathmatchCounter(gameTime);
        }
        msgAll(BlitzSG.CORE_NAME + "&eThere are &c" + alivePlayers.size() + " &eplayers remaning!");

        deadPlayers.add(p);
        //SpeedUHCPlayer uhcPlayer = SpeedUHC.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(p.getUniqueId());
    }

    public void endGame(boolean draw) {
        if (gameMode == GameMode.RESETING)
            return;
        gameMode = GameMode.RESETING;
        try {
            msgAll("&7&m------------------------------");
            msgAll("                   &f&lBlitz SG     ");
            msgAll("&7");
            if (draw) {
                msgAll("                  &e&lDRAW! ");
            } else {
                winner = alivePlayers.get(0);

                IPlayer winnerSGPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(winner.getUniqueId());
                winnerSGPlayer.addWin();
                msgAll("           &e&lWinner &7- " + winnerSGPlayer.getRank(true).getChatColor() + winner.getName());
            }
            msgAll("&7");

            IPlayer firstKiller = BlitzSG.getInstance().getGameManager().getTopKillers(this).get(1);

            msgAll("      &e&l1st Killer &7- " + firstKiller.getRank(true).getChatColor() + Bukkit.getOfflinePlayer(BlitzSG.getInstance().getGameManager().getTopKillers(this).get(1).getUuid()).getName() + " &7- "
                    + firstKiller.getGameKills());

            if (BlitzSG.getInstance().getGameManager().getTopKillers(this).size() >= 2) {
                IPlayer secondKiller = BlitzSG.getInstance().getGameManager().getTopKillers(this).get(2);

                msgAll("      &6&l2nd Killer &7- " + secondKiller.getRank(true).getChatColor() + Bukkit.getOfflinePlayer(BlitzSG.getInstance().getGameManager().getTopKillers(this).get(2).getUuid()).getName() + " &7- "
                        + secondKiller.getGameKills());
            }
            if (BlitzSG.getInstance().getGameManager().getTopKillers(this).size() >= 3) {
                IPlayer thirdKiller = BlitzSG.getInstance().getGameManager().getTopKillers(this).get(3);

                msgAll("      &c&l3rd Killer &7- " + thirdKiller.getRank(true).getChatColor() + Bukkit.getOfflinePlayer(BlitzSG.getInstance().getGameManager().getTopKillers(this).get(3).getUuid()).getName() + " &7- "
                        + thirdKiller.getGameKills());
            }
            msgAll("&7&m------------------------------");
            if (!draw) {
                BlitzSG.getInstance().getIPlayerManager().handleWinElo(this);
                IPlayer blitzSGWinner = BlitzSG.getInstance().getIPlayerManager().getPlayer(winner.getUniqueId());

                int coins = 75 * blitzSGWinner.getRank().getMultiplier();
                blitzSGWinner.addCoins(coins);
                winner.sendMessage(ChatColor.GOLD + "+" + coins + " Coins (Win)");
            }
        } catch (Exception ignored) {
        }
        new BukkitRunnable() {
            public void run() {
                resetGame();
            }
        }.runTaskLater(BlitzSG.getInstance(), 200);
    }

    public void resetGame() {
        this.startedCountdown = false;
        BlitzSG.getInstance().getGameManager().removeGame(this);
        for (Player p : allPlayers) {
            IPlayer uhcPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
            uhcPlayer.setGame(null);
            resetPlayer(p);
            p.teleport(BlitzSG.lobbySpawn);

            BlitzSG.getInstance().getIPlayerManager().toLobby(p);
            p.setPlayerListName(uhcPlayer.getRank(true).getPrefix() + p.getName() + BlitzSG.getInstance().getEloManager().getEloLevel(uhcPlayer.getElo()).getPrefix()
                    + " [" + uhcPlayer.getElo() + "]");
        }
        allPlayers.clear();
        alivePlayers.clear();
        deadPlayers.clear();
        arena.resetArena();
        BlitzSG.getInstance().getGameManager().getRunningGames().remove(this);
    }

    private void refillChests() {
        this.openedChests.clear();
        msgAll(BlitzSG.CORE_NAME + "&eAll chests have been refilled!");
    }

    private void setPregameInventory(Player p) {
        p.getInventory().setItem(0, ItemUtils.buildItem(new ItemStack(Material.BOW), "&aKit Selector &7(Right Click)", Arrays.asList("&7Right Click to vote for the game settings")));
    }

    private void resetPlayer(Player p) {

    }

    public void msgAll(String msg) {
        for (Player p : allPlayers)
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public ArrayList<Player> getAlivePlayers() {
        return alivePlayers;
    }

    public ArrayList<Player> getDeadPlayers() {
        return deadPlayers;
    }

    public boolean isHeadGame() {
        if (getTrueVotes() > getFalseVotes())
            return true;
        return false;
    }

    public HashMap<Player, Boolean> getVotes() {
        return votes;
    }

    public int getTrueVotes() {
        int v = 0;
        for (boolean b : votes.values())
            if (b)
                v++;
        return v;
    }

    public int getFalseVotes() {
        int v = 0;
        for (boolean b : votes.values())
            if (!b)
                v++;
        return v;
    }

    public int getVotingPercentage() {
        if (votes.size() == 0)
            return 0;
        return (int) ((double) getTrueVotes() * 100 / (double) votes.size());
    }

    public boolean getVote(Player p) {
        return this.votes.get(p);
    }

    public void setVote(Player p, boolean vote) {
        this.votes.put(p, vote);
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public NextEvent getNextEvent() {
        return nextEvent;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public boolean canFindStar() {
        return canFindStar;
    }

    public void setFindStar(boolean b) {
        canFindStar = false;
    }

    public Arena getArena() {
        return arena;
    }

    public boolean isDeathmatchStarting() {
        return isDeathmatchStarting;
    }

    public Player getWinner() {
        return winner;
    }

    public int getGameTime() {
        return gameTime;
    }

    public int getDeathmatchStartTime() {
        return deathmatchStartTime;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    public int getCountdownTime() {
        return countdownTime;
    }

    public void setCountdownTime(int countdownTime) {
        this.countdownTime = countdownTime;
    }

    public boolean isDead(Player p) {
        if (deadPlayers.contains(p))
            return true;
        return false;
    }

    public ArrayList<Location> getOpenedChests() {
        return openedChests;
    }

    public ArrayList<Location> getStarChests() {
        return starChests;
    }


}
