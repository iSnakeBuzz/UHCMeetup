package com.isnakebuzz.meetup.a;

import com.isnakebuzz.meetup.b.Kits;
import com.isnakebuzz.meetup.b.States;
import com.isnakebuzz.meetup.c.GameListeners;
import com.isnakebuzz.meetup.c.PlayerListeners;
import com.isnakebuzz.meetup.c.ScenListeners;
import com.isnakebuzz.meetup.d.WorldGenerator;
import com.isnakebuzz.meetup.e.API;
import com.isnakebuzz.meetup.e.LoadKits;
import com.isnakebuzz.meetup.e.MessagesManager;
import com.isnakebuzz.meetup.g.Utils;
import com.isnakebuzz.meetup.h.CheckBorder;
import com.isnakebuzz.meetup.h.Cmds;
import com.isnakebuzz.meetup.i.NMS_1_7_R3;
import com.isnakebuzz.meetup.i.NMS_1_8_R3;
import com.isnakebuzz.meetup.n.MenuUtils;
import com.isnakebuzz.meetup.x.BorderData;
import com.isnakebuzz.meetup.x.Config;
import com.isnakebuzz.meetup.x.WBCommand;
import com.isnakebuzz.meetup.x.WBListener;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    public static String world = "uhc";
    public static String version;
    public static Main plugin;
    public String spawn;
    public VersionHandler versionHandler;
    private PluginDescriptionFile pdf;
    private GameListeners gl;
    private PlayerListeners pl;
    private Cmds cmds;
    private ScenListeners scl;

    public Main(PluginDescriptionFile pdf) {
        this.pdf = this.getDescription();
        this.gl = new GameListeners(this);
        this.pl = new PlayerListeners(this);
        this.cmds = new Cmds(this);
        this.scl = new ScenListeners(this);
        plugin = this;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        PluginDescriptionFile pluginyml = this.getDescription();
        Main.version = c("&aServer running: &eUHCMeetup v" + pluginyml.getVersion());
        States.state = States.LOADING;
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        saveDefaultConfig();
        Main.plugin = this;

        //Load Messages.yml


        //Load MenuAndItems.yml


        MessagesManager.setupMessages();

        try {
            getServerVersion();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        versionHandler.SwapBiomes();
        gl.init();
        cmds.init();
        scl.init();
        pl.init();
        Kits.kits = Main.plugin.getConfig().getInt("Kits");

        // Creating folder
        File dir = new File("SelectedWorlds");
        dir.mkdir();

        spawn = getConfig().getString("WaitingLobby.name");
        if (getConfig().getBoolean("WaitingLobby.enabled") == true) {
            World w = Bukkit.getWorld(spawn);
            if (w == null) {
                w = Bukkit.createWorld(new WorldCreator(spawn));
                w.setPVP(false);
                w.setGameRuleValue("doDaylightCycle", "false");
                w.setStorm(false);
                w.setThundering(false);
                w.setDifficulty(Difficulty.PEACEFUL);
                w.setTime(6000L);
                w.setAutoSave(false);
            }
        }

        // Worlds loading..
        if (getConfig().getBoolean("WorldOptions.RandomWorld") == true) {
            new WorldGenerator().runTaskTimer(plugin, 20, 20);
        } else if (getConfig().getBoolean("WorldOptions.RandomWorld") == false) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Random rs = new Random();

                    int worldint = 0;

                    if (getConfig().getStringList("Tags").size() >= 1) {
                        worldint = rs.nextInt(getConfig().getStringList("WorldOptions.SelectedWorlds").size());
                    }

                    String worlstring = null;

                    if (getConfig().getStringList("Tags").size() < 1) {
                        worlstring = (String) getConfig().getStringList("WorldOptions.SelectedWorlds").get(0);
                    } else if (getConfig().getStringList("Tags").size() >= 1) {
                        worlstring = (String) getConfig().getStringList("WorldOptions.SelectedWorlds").get(worldint);
                    }

                    File world = new File("worlds/" + worlstring);
                    File dest = new File("/");

                    //DELETING THIS
                    File session = new File(worlstring + "/session.lock");
                    File playerdata = new File(worlstring + "/playerdata");
                    File uuid = new File(worlstring + "/uid.dat");

                    if (!world.exists()) {
                        Bukkit.getConsoleSender().sendMessage("§1Loading a new world...");
                    } else {
                        Bukkit.getConsoleSender().sendMessage("§aLoading world: " + worlstring + "...");
                        try {
                            Utils.copyFolder(world, dest);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (session.exists()) {
                            session.delete();
                        }
                        if (playerdata.exists()) {
                            playerdata.delete();
                        }
                        if (uuid.exists()) {
                            uuid.delete();
                        }
                        Main.world = worlstring;
                        World w = Bukkit.getWorld(worlstring);
                        if (w == null) {
                            w = Bukkit.createWorld(new WorldCreator(worlstring));
                            w.setPVP(true);
                            w.setGameRuleValue("doDaylightCycle", "false");
                            w.setStorm(false);
                            w.setThundering(false);
                            w.setDifficulty(Difficulty.HARD);
                            w.setTime(6000L);
                            w.setAutoSave(false);
                            Main.plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "wb shape square");
                            Main.plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "wb " + Main.world + " set " + 125 + " 0 0");
                            Main.plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "wb " + Main.world + " fill 5000");
                            Main.plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "wb fill confirm");
                            w.setSpawnLocation(0, w.getHighestBlockYAt(0, 0) + 15, 0);
                        }
                        States.state = States.LOBBY;
                        Bukkit.getConsoleSender().sendMessage("§aWorld: §e" + worlstring + "§a has been loaded");
                        this.cancel();
                    }
                }
            }.runTaskTimer(this, 20l, 20 * 5l);
        }

        new CheckBorder(Main.plugin).runTaskTimer(Main.plugin, 30L, 15L);
        LoadKits.get().a(plugin);

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

        //DataBase

    }

    private String c(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().unloadWorld(world, true);
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.kickPlayer("Restarting World");
        }
        if (Main.world != null) {
            Bukkit.unloadWorld(Main.world, false);
            File world = new File(Main.world);
            this.deleteDirectory(world);
        }
        this.reloadConfig();
        Config.StopBorderTimer();
        Config.StoreFillTask();
        Config.StopFillTask();
    }

    public boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

    public PluginDescriptionFile getPdf() {
        return pdf;
    }

    public BorderData GetWorldBorder(String worldName) {
        return Config.Border(worldName);
    }

    private void getServerVersion() throws Exception {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        boolean isVaild = true;
        if ("1.7.10".equals(API.getVersion())) {
            versionHandler = new NMS_1_7_R3();
            getLogger().log(Level.INFO, "Cargando version: {0}", version);
        } else if ("1.8.8".equals(API.getVersion())) {
            versionHandler = new NMS_1_8_R3();
            getLogger().log(Level.INFO, "Cargando version: {0}", version);
        } else {
            isVaild = false;
        }
        if (!isVaild) {
            Bukkit.getConsoleSender().sendMessage("§4Need Spigot 1.7.10 or Spigot 1.8.8 :)");
            Bukkit.shutdown();
        }
    }
}
