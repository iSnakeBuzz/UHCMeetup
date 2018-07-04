package com.isnakebuzz.meetup.a;

import com.isnakebuzz.meetup.b.EventManager;
import com.isnakebuzz.meetup.d.ArenaManager;
import com.isnakebuzz.meetup.d.PlayerManager;
import com.isnakebuzz.meetup.e.InvManager;
import com.isnakebuzz.meetup.e.TimerManager;
import com.isnakebuzz.meetup.f.ScoreBoardAPI;
import com.isnakebuzz.meetup.g.*;
import com.isnakebuzz.meetup.i.ConfigCreator;
import com.isnakebuzz.meetup.i.ConfigUtils;
import com.isnakebuzz.meetup.k.CommandsKits;
import com.isnakebuzz.meetup.k.CommandsLobby;
import com.isnakebuzz.meetup.m.CustomKits;
import com.isnakebuzz.meetup.worldborder.BorderData;
import com.isnakebuzz.meetup.worldborder.Config;
import com.isnakebuzz.meetup.worldborder.WBCommand;
import com.isnakebuzz.meetup.worldborder.WBListener;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private ConfigUtils configUtils;
    private BorderManager borderManager;
    private WorldUitls worldUitls;
    private PlayerManager playerManager;
    private TimerManager timerManager;
    private InvManager invManager;
    private LobbyManager lobbyManager;
    private ArenaManager arenaManager;
    private ScoreBoardAPI scoreBoardAPI;
    private TeleportManager teleportManager;
    private EventManager eventManager;
    private CustomKits customKits;

    public Main() {
        this.customKits = new CustomKits(this);
        this.eventManager = new EventManager(this);
        this.teleportManager = new TeleportManager(this);
        this.scoreBoardAPI = new ScoreBoardAPI(this);
        this.arenaManager = new ArenaManager(this);
        this.invManager = new InvManager(this);
        this.lobbyManager = new LobbyManager(this);
        this.playerManager = new PlayerManager(this);
        this.timerManager = new TimerManager(this);
        this.worldUitls = new WorldUitls(this);
        this.borderManager = new BorderManager(this);
        this.configUtils = new ConfigUtils();
    }

    @Override
    public void onEnable() {
        //Creating config
        ConfigCreator.get().setup(this, "Settings");
        ConfigCreator.get().setup(this, "Lang");
        ConfigCreator.get().setup(this, "Utils/MenuCreator");
        ConfigCreator.get().setup(this, "Utils/Inventory");
        ConfigCreator.get().setup(this, "Utils/SpawnLocs");
        ConfigCreator.get().setup(this, "Extra/ScoreBoard");
        ConfigCreator.get().setup(this, "Extra/Border");

        //Register CommandsLobby
        this.getCommand("lb").setExecutor(new CommandsLobby(this));
        this.getCommand("kits").setExecutor(new CommandsKits(this));

        //Load BungeeCord
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        //Load Timers
        this.getTimerManager().setVoteEnds(this.getConfigUtils().getConfig(this, "Settings").getInt("GameOptions.VoteTime"));

        //World Creator
        this.loadBorder();
        this.getWorldUitls().swapBiomes();
        new WorldGen(this, "uhc").runTaskTimer(this, 0, 20);
        this.getTimerManager().setStartingTime(this.getConfigUtils().getConfig(this, "Settings").getInt("GameOptions.StartingTime"));

        //Load Events
        this.loadListeners();

        //Set game loading..
        getStates.state = getStates.LOADING;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public void log(String sender, String log) {
        this.getServer().getConsoleSender().sendMessage(c("&e[" + sender + "]&a " + log));
    }

    public void broadcast(String log) {
        this.getServer().broadcastMessage(c(log));
    }

    public BorderData GetWorldBorder(String worldName) {
        return Config.Border(worldName);
    }

    public ConfigUtils getConfigUtils() {
        return configUtils;
    }

    public WorldUitls getWorldUitls() {
        return worldUitls;
    }

    public TimerManager getTimerManager() {
        return timerManager;
    }

    public LobbyManager getLobbyManager() {
        return lobbyManager;
    }

    public BorderManager getBorderManager() {
        return borderManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public InvManager getInvManager() {
        return invManager;
    }

    public TeleportManager getTeleportManager() {
        return teleportManager;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public ScoreBoardAPI getScoreBoardAPI() {
        return scoreBoardAPI;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public CustomKits getCustomKits() {
        return customKits;
    }

    private void loadListeners() {
        log("Listener", "Loading listeners..");
        registerListener(getEventManager().getEventInteract());
        registerListener(getEventManager().getEventJoinAndLeave());
        registerListener(getEventManager().getEventLogin());
        registerListener(getEventManager().getEventSpectator());
        registerListener(getEventManager().getEventStarting());
        registerListener(getEventManager().getEventWorldGen());
        registerListener(getEventManager().getEventDeath());
        registerListener(getEventManager().getEventWorld());
        registerListener(getEventManager().getEventGameWin());
    }

    private void registerListener(Listener listener) {
        log("Listener", "&5-&e Loaded listener &b" + listener.getClass().getSimpleName());
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    public void unregisterListener(Listener listener) {
        log("Listener", "&5-&e Unloading listener &b" + listener.getClass().getSimpleName());
        HandlerList.unregisterAll(listener);
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }

    private void loadBorder() {
        //WorldBorder
        // Load (or create new) config file
        Config.load(this, false);

        // our one real command, though it does also have aliases "wb" and "worldborder"
        getCommand("wborder").setExecutor(new WBCommand(this));

        // keep an eye on teleports, to redirect them to a spot inside the border if necessary
        getServer().getPluginManager().registerEvents(new WBListener(), this);

        // Well I for one find this info useful, so...
        Location spawn = getServer().getWorlds().get(0).getSpawnLocation();
        Config.log("For reference, the main world's spawn location is at X: " + Config.coord.format(spawn.getX()) + " Y: " + Config.coord.format(spawn.getY()) + " Z: " + Config.coord.format(spawn.getZ()));
    }

    public enum getStates {
        LOADING, LOBBY, STARTING, INGAME, FINISHED;
        public static getStates state;
    }

}
