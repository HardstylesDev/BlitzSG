package me.hardstyles.blitz.game;

import lombok.Getter;
import lombok.Setter;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.player.IPlayerManager;
import me.hardstyles.blitz.map.Map;
import me.hardstyles.blitz.utils.ChatUtil;
import me.hardstyles.blitz.utils.ItemBuilder;
import me.hardstyles.blitz.utils.ItemUtils;
import me.hardstyles.blitz.BlitzSG;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Golem;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.logging.Level;

@Getter
@Setter
public class Game {

    private NextEvent nextEvent;
    private boolean starAvailable = false;
    private boolean deathMatchStarted = false;
    private long INTERVAL = 20L;

    public void applyDeathEffects() {
        for (Player alivePlayer : alivePlayers) {
            alivePlayer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 15 * 15, 1));
        }
    }

    public enum GameMode {
        LOADING, INACTIVE, WAITING,
        STARTING, INGAME, RESETTING,
    }

    public enum NextEvent {
        STAR, REFILL, DEATHMATCH, ENDING
    }

    private ArrayList<Player> allPlayers, alivePlayers, deadPlayers;
    private GameMode gameMode;

    private Map map;
    private Player winner;

    private HashMap<UUID, Boolean> votes;
    private ArrayList<Location> openedChests, starChests;
    private HashSet<Location> spawnUsed;
    private boolean isDeathmatchStarting = false;
    private boolean godGame;

    private int countdownTime, gameTime, deathmatchCountDownTime;


    public Game() {
        Map map = BlitzSG.getInstance().getMapManager().getRandomMap();
        if (map == null) {
            Bukkit.broadcastMessage(BlitzSG.CORE_NAME + ChatColor.RED + "Couldn't find an available arena!");
        }
    }

    public Game(Map toLoad) {
        gameMode = GameMode.LOADING;
        this.map = toLoad;

        this.map.copy();
        this.map.load();

        Bukkit.getScheduler().runTaskLater(BlitzSG.getInstance(), () -> {
            BlitzSG.getInstance().getMapManager().populateMap(this, this.map);

            if (map == null) {
                Bukkit.broadcastMessage(BlitzSG.CORE_NAME + ChatColor.RED + "Couldn't find an available arena!");
                return;
            }

            this.openedChests = new ArrayList<>();
            this.starChests = new ArrayList<>();

            if (map.getSpawns().get(0).getWorld() == null) {
                return;
            }
            map.setInUse(true);
            BlitzSG.getInstance().getGameManager().addGame(this);


            allPlayers = new ArrayList<>();
            alivePlayers = new ArrayList<>();
            deadPlayers = new ArrayList<>();
            votes = new HashMap<>();
            spawnUsed = new HashSet<>();
            gameMode = GameMode.WAITING;
        }, 50L);


    }

    public void addPlayer(Player p) {
        if (gameMode != GameMode.WAITING) {
            p.sendMessage(BlitzSG.CORE_NAME + ChatColor.RED + "This game already started!");
            return;
        }
        if (map == null || map.getSpawns().get(0).getWorld() == null) {
            p.sendMessage(BlitzSG.CORE_NAME + ChatColor.YELLOW + "Couldn't find an available arena!");
            return;
        }
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        iPlayer.getGameEntities().clear();
        if (iPlayer.isInGame()) {
            BlitzSG.send(p, BlitzSG.CORE_NAME + "&cYou are already in a game!");
            return;
        }
        if (map.getSpawns().size() < alivePlayers.size() + 1) {
            BlitzSG.send(p, BlitzSG.CORE_NAME + "&cThe game is already full!");
            return;
        }

        iPlayer.setGame(this);
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        IPlayerManager.get().resetPlayerStatus(p);

        iPlayer.setPrefix(ChatUtil.color("&f"));
        p.setPlayerListName(iPlayer.getRank(true).getChatColor() + p.getName());


        if (allPlayers.contains(p)) {
            allPlayers.remove(p);
            alivePlayers.remove(p);
        }
        allPlayers.add(p);
        alivePlayers.add(p);
        p.teleport(map.getLobby().clone().add(0.5, 0, 0.5));
        p.sendMessage(BlitzSG.CORE_NAME + ChatColor.GREEN + "To leave SurivalGames, type /lobby");
        message(iPlayer.getRank(true).getChatColor() + p.getName() + " &ehas joined (&d" + alivePlayers.size() + "&e/&d" + map.getSpawns().size() + "&e)!");
        for (Player allPlayer : allPlayers) {
            allPlayer.showPlayer(p);
            p.showPlayer(allPlayer);
        }
        p.setGameMode(org.bukkit.GameMode.SURVIVAL);
        int requiredPlayers = 0;
        // set required players to 3/4 of the map size
        if (map.getSpawns().size() >= 4) {
            requiredPlayers = (int) Math.ceil(map.getSpawns().size() * 0.75);
        } else {
            requiredPlayers = 2;
        }
        if (alivePlayers.size() >= requiredPlayers && gameMode.equals(GameMode.WAITING)) {
            startLobbyCountdown();
        }
        p.setFlying(false);
        p.setAllowFlight(false);
        try {
            Bukkit.getServer().getOnlinePlayers().stream().filter(player -> player.getWorld().getName().equalsIgnoreCase(map.getMapId())).forEach(player -> {
                p.showPlayer(player);
                player.showPlayer(p);
            });

            Bukkit.getServer().getOnlinePlayers().stream().filter(player -> !player.getWorld().getName().equalsIgnoreCase(map.getMapId())).forEach(player -> {
                player.hidePlayer(p);
                p.hidePlayer(player);
            });
        } catch (ConcurrentModificationException e) {
            Bukkit.getLogger().log(Level.WARNING, "ConcurrentModificationException in Game.addPlayer(Player p)");
        }


    }

    public void teleportSpawn(Player p) {
        Location playerSpawn = null;

        for (Location l : map.getSpawns()) {
            if (!spawnUsed.contains(l)) {
                playerSpawn = l;
                break;
            }
        }
        spawnUsed.add(playerSpawn);
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        iPlayer.setGameSpawn(playerSpawn.clone().add(0.5, 1.0, 0.5));

        p.teleport(playerSpawn.clone().add(0.5, 1.0, 0.5));
    }


    public void castVote(Player p, boolean vote) {
        if (allPlayers.size() < 2) {
            p.sendMessage(ChatUtil.color(BlitzSG.CORE_NAME + "&cThere must be at least 2 players to start the game!"));
            return;
        }
        if (votes.containsKey(p.getUniqueId())) {
            p.sendMessage(ChatUtil.color(BlitzSG.CORE_NAME + "&cYou have already voted!"));
            return;
        }
        votes.put(p.getUniqueId(), vote);
        p.sendMessage(ChatUtil.color(BlitzSG.CORE_NAME + "&eYou have voted to " + (vote ? "&a&lSTART &ethe game!" : "&c&lWAIT &efor more players!")));

        int requiredVotes = (int) Math.ceil(allPlayers.size() * 0.75);
        int yesVotes = 0;
        int noVotes = 0;
        for (boolean b : votes.values()) {
            if (b) {
                yesVotes++;
            } else {
                noVotes++;
            }
        }
        if (yesVotes - noVotes >= requiredVotes) {
            startLobbyCountdown();
        } else {
            String voteMessage;
            if (noVotes > 0) {
                voteMessage = BlitzSG.CORE_NAME + "&6" + yesVotes + " &evotes to start the game, &c" + noVotes + " &evotes to wait!";
            } else {
                voteMessage = BlitzSG.CORE_NAME + "&6" + yesVotes + "&e/&6" + requiredVotes + " &evotes to start the game!";
            }
            p.sendMessage(ChatUtil.color(voteMessage));
        }
    }

    public void exit(Player p) {
        alivePlayers.remove(p);
        allPlayers.remove(p);
        deadPlayers.remove(p);
    }

    public void removePlayer(Player p) {
        if (gameMode == GameMode.INGAME) {
            BlitzSG.send(p, BlitzSG.CORE_NAME + "&cYou can't leave while the game is running!");
            return;
        }
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        iPlayer.setGame(null);
        spawnUsed.remove(map.getSpawns().get(alivePlayers.indexOf(p)));
        alivePlayers.remove(p);
        allPlayers.remove(p);
        message(BlitzSG.CORE_NAME + iPlayer.getRank().getChatColor() + p.getName() + " &ehas left (&b" + alivePlayers.size() + "&e/&b" + map.getSpawns().size() + "&e)!");
        BlitzSG.getInstance().getIPlayerManager().toLobby(p);
        BlitzSG.send(p, BlitzSG.CORE_NAME + "&cYou have left the game!");
    }

    private boolean startedCountdown = false;

    public void startLobbyCountdown() {
        if (startedCountdown) {
            return;
        }
        startedCountdown = true;
        countdownTime = 16;

        new BukkitRunnable() {
            public void run() {
                countdownTime--;
                if (alivePlayers.size() < 2) {
                    this.cancel();
                    gameMode = GameMode.WAITING;
                    startedCountdown = false;
                    message("&cWe don't have enough players! Countdown cancelled.");
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
                    if (countdownTime <= 10) {
                        message(BlitzSG.CORE_NAME + "&e" + countdownTime + " &eseconds until the game starts!");
                    }
                }
            }
        }.runTaskTimer(BlitzSG.getInstance(), 0, INTERVAL);
    }

    public void startCountDown() {
        gameMode = GameMode.STARTING;
        countdownTime = 31;

        new BukkitRunnable() {
            public void run() {
                countdownTime--;
                if (alivePlayers.size() < 2) {
                    this.cancel();
                    gameMode = GameMode.WAITING;
                    message("&cWe don't have enough players! Countdown cancelled.");
                    for (Player alivePlayer : alivePlayers) {
                        resetGame();
                    }
                    return;
                }
                if (countdownTime == 0) {
                    startGame();
                    this.cancel();
                    return;
                }
                if (countdownTime % 10 == 0 || countdownTime <= 5) {
                    if (countdownTime <= 5)
                        message(BlitzSG.CORE_NAME + "&eYou will be able to move in &c" + countdownTime + " &e" + (countdownTime == 1 ? "second!" : "seconds!"));
                    else if (countdownTime <= 15)
                        message(BlitzSG.CORE_NAME + "&eYou will be able to move in &6" + countdownTime + " &eseconds!");
                    else if (countdownTime <= 25)
                        message(BlitzSG.CORE_NAME + "&eYou will be able to move in &e" + countdownTime + " &eseconds!");
                    else if (countdownTime <= 30) {
                        message(BlitzSG.CORE_NAME + "&eYou will be able to move in " + countdownTime + "! Choose a kit by right clicking the bow!");
                    }
                }
            }
        }.runTaskTimer(BlitzSG.getInstance(), 0, INTERVAL);
    }

    public void startGame() {
        nextEvent = NextEvent.STAR;
        gameMode = GameMode.INGAME;
        map.getWorld().setTime(0);
        for (Player p : alivePlayers) {
            p.closeInventory();
            p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 2, 1);
            IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
            if (iPlayer.getSelectedKit() == null) {
                iPlayer.setSelectedKit(BlitzSG.getInstance().getKitManager().getKit("Knight"));
            }
            BlitzSG.getInstance().getIPlayerManager().resetPlayerStatus(p);
            BlitzSG.send(p, BlitzSG.CORE_NAME + "&eYou will get the items for your " + iPlayer.getSelectedKit().getName() + " kit in 60 seconds.");
            BlitzSG.send(p, BlitzSG.CORE_NAME + "&6The Blitz Star will be released in 5 minutes!");
            if (godGame) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60 * 20, 2));
            }
            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60 * 20, 2));
            p.setHealth(19);
            p.setHealth(20);
            p.getInventory().setItem(0, new ItemBuilder(Material.COMPASS).name("&rTracking Device").make());
        }
        for (Location l : map.getSpawns()) {
            if (spawnUsed.contains(l)) {
                gameTime = 0;
                break;
            }
        }
        new BukkitRunnable() {
            public void run() {
                if (alivePlayers.size() < 2) {
                    this.cancel();
                    endGame(false);
                    return;
                }
                if (gameTime > 53 && gameTime < 59) {
                    message(BlitzSG.CORE_NAME + "&eYou will get your items in " + (59 - gameTime) + " seconds!");
                }
                if (gameTime == 59) {
                    message("&eThe grace period has ended, PvP is now enabled!");
                    for (Player p : alivePlayers) {
                        p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
                        if (godGame) {
                            iPlayer.getSelectedKit().giveKit(p, 10);
                        } else {
                            iPlayer.getSelectedKit().giveKit(p, iPlayer.getKitLevel(iPlayer.getSelectedKit()));
                        }
                        BlitzSG.send(p, BlitzSG.CORE_NAME + "&eYou got your " + iPlayer.getSelectedKit().getName() + " kit!");
                    }
                    message(BlitzSG.CORE_NAME + "&6The Blitz Star will be released in 4 minutes!");
                }
                if (gameTime == 119) {
                    message(BlitzSG.CORE_NAME + "&6The Blitz Star will be released in 3 minutes!");
                }
                if (gameTime == 179) {
                    message(BlitzSG.CORE_NAME + "&6The Blitz Star will be released in 2 minutes!");
                }
                if (gameTime == 239) {
                    message(BlitzSG.CORE_NAME + "&6The Blitz Star will be released in 1 minutes!");
                }
                if (gameTime == 299) {
                    message(BlitzSG.CORE_NAME + "&6The Blitz Star has been hidden in a chest! Find it to activate your Blitz!");
                    starAvailable = true;

                    starChests = new ArrayList<>();
                    if (!isDeathmatchStarting) {
                        nextEvent = NextEvent.REFILL;
                    }
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
        }.runTaskTimer(BlitzSG.getInstance(), INTERVAL, INTERVAL);
    }

    private int deathmatchStartTime = 0;

    public void startDeathmatchCounter(int currentTime) {
        deathmatchCountDownTime = currentTime;
        isDeathmatchStarting = true;
        new BukkitRunnable() {
            public void run() {
                if (deathmatchStartTime == 15) {
                    message(BlitzSG.CORE_NAME + "&eDeathmatch begins in 30 seconds!");
                    message(BlitzSG.CORE_NAME + "&eThe Blitz Star can no longer be used!");
                }
                if (deathmatchStartTime > 39 && deathmatchStartTime < 45) {
                    message(BlitzSG.CORE_NAME + "&eDeathmatch begins in " + (45 - deathmatchStartTime) + " second" + (((45 - deathmatchStartTime) == 1) ? "" : "s") + "!");

                }
                if (deathmatchStartTime == 45) {
                    startDeathmatch();
                    message(BlitzSG.CORE_NAME + "&eDeathmatch started! You cannot damage anyone for 15 seconds!");
                }
                if (deathmatchStartTime > 54 && deathmatchStartTime < 60) {
                    message(BlitzSG.CORE_NAME + "&eYou will be able to damage players in " + (60 - deathmatchStartTime) + " second" + (((60 - deathmatchStartTime) == 1) ? "" : "s") + "!");
                }
                if (deathmatchStartTime == 60) {
                    message(BlitzSG.CORE_NAME + "&eKill! Kill! Kill!");
                    deathMatchStarted = true;
                }

                if (deathmatchStartTime == 240) {
                    this.cancel();
                    endGame(true);
                    return;
                }
                deathmatchCountDownTime++;
                deathmatchStartTime++;
            }
        }.runTaskTimer(BlitzSG.getInstance(), INTERVAL, INTERVAL);
    }

    private void startDeathmatch() {
        if (map.getDeathmatch() == null)
            return;
        Location dm = map.getDeathmatch();
        if (map.getDeatchmatchSpead() == 0) {
            alivePlayers.forEach(player -> player.teleport(dm));
            return;
        }
        int dist = (int) map.getDeatchmatchSpead();
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
            if (pl.getAllowFlight()) continue;
            if (pl.getGameMode() == org.bukkit.GameMode.SPECTATOR) continue;
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
        if (alivePlayers.size() <= 3 && !isDeathmatchStarting) {
            isDeathmatchStarting = true;
            startDeathmatchCounter(gameTime);
        }
        message(BlitzSG.CORE_NAME + "&eThere are &c" + alivePlayers.size() + " &eplayers remaning!");

        deadPlayers.add(p);
    }

    public void endGame(boolean draw) {
        if (gameMode == GameMode.RESETTING)
            return;
        gameMode = GameMode.RESETTING;
        message("&6#&7------------------&6#");
        if (draw) {
            message("    &aDRAW! ");
        } else {
            if (alivePlayers.size() > 0) {
                winner = alivePlayers.get(0);
                IPlayer iWinner = BlitzSG.getInstance().getIPlayerManager().getPlayer(winner.getUniqueId());
                iWinner.addWin();
                message("    " + iWinner.getRank(true).getChatColor() + winner.getName() + " &ahas won the Blitz Survival Games!");
            } else {
                message("    &aDRAW! ");
            }
        }
        message("&6#&7------------------&6#");
        if (!draw) {
            BlitzSG.getInstance().getIPlayerManager().handleWinElo(this);
            IPlayer blitzSGWinner = BlitzSG.getInstance().getIPlayerManager().getPlayer(winner.getUniqueId());
            int coins = 75 * blitzSGWinner.getRank().getMultiplier();
            blitzSGWinner.addCoins(coins);
            winner.sendMessage(ChatColor.GOLD + "+" + coins + " Coins (Win)");
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
            IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
            if (iPlayer == null) continue;
            iPlayer.setGame(null);
            BlitzSG.getInstance().getIPlayerManager().toLobby(p);
        }
        allPlayers.clear();
        alivePlayers.clear();
        deadPlayers.clear();
        map.destroy();
        BlitzSG.getInstance().getGameManager().getRunningGames().remove(this);
    }

    private void refillChests() {
        this.openedChests.clear();
        message(BlitzSG.CORE_NAME + "&eAll chests have been refilled!");
    }

    private void setPregameInventory(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        p.getInventory().setItem(0, ItemUtils.buildItem(new ItemStack(Material.BOW), "&aKit Selector &7(Right Click)", Arrays.asList("&7Pick a kit to use in the game!")));
    }


    public void message(String msg) {
        allPlayers.forEach(player -> player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg)));
    }

    public boolean hasDeathMatchStarted() {
        return deathMatchStarted;
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
