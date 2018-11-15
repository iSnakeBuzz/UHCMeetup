package com.isnakebuzz.meetup;

import com.isnakebuzz.meetup.EventsManager.EventManager;
import com.isnakebuzz.meetup.EventsManager.VoteEventManager;
import com.isnakebuzz.meetup.Utils.Managers.*;
import com.isnakebuzz.meetup.Utils.Connection;
import com.isnakebuzz.meetup.Utils.ScoreBoard.ScoreBoardAPI;
import com.isnakebuzz.meetup.Utils.WorldUtils.*;
import com.isnakebuzz.meetup.Configurations.ConfigCreator;
import com.isnakebuzz.meetup.Configurations.ConfigUtils;
import com.isnakebuzz.meetup.Commands.CommandsKits;
import com.isnakebuzz.meetup.Commands.CommandsLobby;
import com.isnakebuzz.meetup.Inventory.AutoKits;
import com.isnakebuzz.meetup.Inventory.CustomKits;
import com.isnakebuzz.meetup.Utils.PluginUtils.Metrics;
import com.isnakebuzz.meetup.Utils.PluginUtils.SpigotUpdater;
import com.isnakebuzz.meetup.Compatibility.Versions.V1_7_10;
import com.isnakebuzz.meetup.Compatibility.Versions.V1_8_8;
import com.isnakebuzz.meetup.Compatibility.PluginVersion;
import com.isnakebuzz.meetup.Database.PlayerDataInterface;
import com.isnakebuzz.meetup.Database.Types.VMongoDB;
import com.isnakebuzz.meetup.Database.Types.VMySQL;
import com.isnakebuzz.meetup.Utils.WorldBorder.BorderData;
import com.isnakebuzz.meetup.Utils.WorldBorder.Config;
import com.isnakebuzz.meetup.Utils.WorldBorder.WBCommand;
import com.isnakebuzz.meetup.Utils.WorldBorder.WBListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
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
    private AutoKits autoKits;
    private Connection connection;
    private VoteManager voteManager;
    private VoteEventManager voteEventManager;
    private PluginVersion versionHandler;
    private PlayerDataInterface playerDataInterface;

    public Main() {
        this.voteEventManager = new VoteEventManager(this);
        this.voteManager = new VoteManager(this);
        this.connection = new Connection(this);
        this.autoKits = new AutoKits(this);
        this.customKits = new CustomKits(this);
        this.eventManager = new EventManager(this);
        this.teleportManager = new TeleportManager(this);
        this.scoreBoardAPI = new ScoreBoardAPI(this);
        this.arenaManager = new ArenaManager(this);
        this.invManager = new InvManager(this);
        this.lobbyManager = new LobbyManager(this);
        this.playerManager = new PlayerManager(this);
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
        ConfigCreator.get().setup(this, "Extra/Votes");
        ConfigCreator.get().setup(this, "Extra/Database");
        ConfigCreator.get().setup(this, "Kits/autokit");

        //Load other classes
        this.timerManager = new TimerManager(this);

        //Check version
        this.checkSpigotVersion();
        this.checkNewVersion();

        //Register CommandsLobby
        this.getCommand("lb").setExecutor(new CommandsLobby(this));
        this.getCommand("kits").setExecutor(new CommandsKits(this));

        //Load BungeeCord
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        //Load Timers
        this.getTimerManager().setVoteEnds(this.getConfigUtils().getConfig(this, "Settings").getInt("GameOptions.VoteTime"));

        //World Creator
        this.versionHandler.biomeSwapper();
        this.loadBorder();
        new WorldGen(this, "uhc").runTaskTimer(this, 0, 20);
        this.getTimerManager().setStartingTime(this.getConfigUtils().getConfig(this, "Settings").getInt("GameOptions.StartingTime"));

        //Load EventsManager
        this.loadListeners();

        //Load metrics
        Metrics metrics = new Metrics(this);

        //Load Database
        loadDatabase();

        //Set game loading..
        getStates.state = getStates.LOADING;
        getMode.state = getMode.SOLO;
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

    public VoteManager getVoteManager() {
        return voteManager;
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

    public AutoKits getAutoKits() {
        return autoKits;
    }

    public VoteEventManager getVoteEventManager() {
        return voteEventManager;
    }

    public Connection getConnection() {
        return connection;
    }

    public PlayerDataInterface getPlayerDataInterface() {
        return playerDataInterface;
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
        registerListener(getEventManager().getEventHealth());
        registerListener(getEventManager().getEventCommand());
        if (getConfigUtils().getConfig(this, "Settings").getBoolean("GameOptions.EPearlCD.enabled")){
            registerListener(getEventManager().getEventEnderCD());
        }
    }

    public void registerListener(Listener listener) {
        log("Listener", "&5-&e Loaded listener &e" + listener.getClass().getSimpleName());
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    public void unregisterListener(Listener listener) {
        log("Listener", "&5-&e Unloading listener &e" + listener.getClass().getSimpleName());
        HandlerList.unregisterAll(listener);
    }

    public void checkVersionPlayer(Player p) {
        SpigotUpdater spigotUpdater = new SpigotUpdater(this, 47646);
        try {
            if (spigotUpdater.checkForUpdates()) {
                p.sendMessage(c("An update was found! New version: &6" + spigotUpdater.getLatestVersion()));
                p.sendMessage(c("Download link: &6" + spigotUpdater.getResourceURL()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PluginVersion getVersionHandler() {
        return versionHandler;
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }


    private void checkSpigotVersion() {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        boolean isVaild = true;
        if ("1.7.10".equals(getVersion())) {
            versionHandler = new V1_7_10();
            log("Compatibility", "Loading version: " + version);
        } else if ("1.8.8".equals(getVersion())) {
            versionHandler = new V1_8_8();
            log("Compatibility", "Loading version: " + version);
        } else {
            isVaild = false;
        }
        if (!isVaild) {
            log("Compatibility", "&4You need Spigot 1.7.10 or Spigot 1.8.8");
            Bukkit.shutdown();
        }
    }

    private String getVersion() {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        if (null != version) switch (version) {
            case "v1_7_R4":
                return "1.7.10";
            case "v1_8_R3":
                return "1.8.8";
        }
        return "none";
    }

    private void checkNewVersion() {
        SpigotUpdater spigotUpdater = new SpigotUpdater(this, 47646);
        try {
            if (spigotUpdater.checkForUpdates()) {
                this.log("Updater", "An update was found! New version: " + spigotUpdater.getLatestVersion());
                this.log("Updater", "Download link: " + spigotUpdater.getResourceURL());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBorder() {
        //WorldBorder
        // Load (or create new) config file
        Config.load(this, false);

        // our one real command, though it does also have aliases "wb" and "WorldBorder"
        getCommand("wborder").setExecutor(new WBCommand(this));

        // keep an eye on teleports, to redirect them to a spot inside the border if necessary
        getServer().getPluginManager().registerEvents(new WBListener(), this);

        // Well I for one find this info useful, so...
        Location spawn = getServer().getWorlds().get(0).getSpawnLocation();
        Config.log("For reference, the main world's spawn location is at X: " + Config.coord.format(spawn.getX()) + " Y: " + Config.coord.format(spawn.getY()) + " Z: " + Config.coord.format(spawn.getZ()));
    }

    public void loadDatabase() {
        Configuration config = getConfigUtils().getConfig(this, "Extra/Database");
        String dbType = config.getString("DatabaseType");
        if (Connection.Database.MONGODB.toString().equalsIgnoreCase(dbType)) {
            this.getConnection().loadMongo();
            log("Database", "Loading MongoDB");
            Connection.Database.database = Connection.Database.MONGODB;
            playerDataInterface = new VMongoDB(this);
        } else if (Connection.Database.MYSQL.toString().equalsIgnoreCase(dbType)) {
            this.getConnection().loadMySQL();
            log("Database", "Loading MySQL");
            playerDataInterface = new VMySQL(this);
            Connection.Database.database = Connection.Database.MYSQL;
        } else if (Connection.Database.NONE.toString().equalsIgnoreCase(dbType)) {
            log("Database", "Has disabled");
            Connection.Database.database = Connection.Database.NONE;
        } else {
            log("Database", "Has disabled");
            Connection.Database.database = Connection.Database.NONE;
        }
    }

    public enum getStates {
        LOADING, LOBBY, STARTING, INGAME, FINISHED;
        public static getStates state;
    }

    public enum getMode {
        SOLO, TEAM;
        public static getMode state;
    }

}
