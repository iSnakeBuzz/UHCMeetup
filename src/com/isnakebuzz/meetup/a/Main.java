package com.isnakebuzz.meetup.a;

import com.comphenix.protocol.ProtocolLibrary;
import com.isnakebuzz.meetup.b.Kits;
import com.isnakebuzz.meetup.b.States;
import com.isnakebuzz.meetup.c.GameListeners;
import com.isnakebuzz.meetup.c.PlayerListeners;
import com.isnakebuzz.meetup.d.Border;
import com.isnakebuzz.meetup.d.WorldGenerator;
import com.isnakebuzz.meetup.e.API;
import com.isnakebuzz.meetup.e.LoadKits;
import com.isnakebuzz.meetup.g.ScoreboardAPI;
import com.isnakebuzz.meetup.h.CheckBorder;
import com.isnakebuzz.meetup.h.Cmds;
import com.isnakebuzz.meetup.i.NMS_1_7_R3;
import com.isnakebuzz.meetup.i.NMS_1_8_R3;
import java.util.HashMap;
import java.util.logging.Level;
import net.minecraft.server.v1_8_R3.WorldBorder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
    
    public Main (){
        this.gl = new GameListeners(this);
        this.pl = new PlayerListeners(this);
        this.scoreboard = new HashMap<>();
        this.cmds = new Cmds(this);
    }
    
    public static String world = "uhc";
    public static double ServerVersion;
    
    public VersionHandler versionHandler;
    private GameListeners gl;
    private PlayerListeners pl;
    private Cmds cmds;
    public static Main plugin;
    public HashMap<Player, ScoreboardAPI> scoreboard;
    
    @Override
    public void onEnable() {
        super.onEnable();
        States.state = States.LOBBY;
        Border.walls = 125;
        Main.ServerVersion = getConfig().getDouble("ServerVersion");
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        saveDefaultConfig();
        Main.plugin = this;
        gl.init();
        pl.init();
        cmds.init();
        getServerVersion();
        versionHandler.SwapBiomes();
        Kits.kits = Main.plugin.getConfig().getInt("Kits");
        if (getConfig().getBoolean("NewWorldGenerator") == false){
            API.DeleteWorld(world);
            World w = Bukkit.getWorld(world);
            w.setAutoSave(false);
            w.setSpawnLocation(0, w.getHighestBlockYAt(0, 0) + 10, 0);
            getServer().getScheduler().runTaskLater(this, () -> {
                Border.buildWalls(Border.walls, Material.BEDROCK, 6, w);
            }, 30);
        }else{
            new WorldGenerator().runTaskTimer(plugin, 20, 20);
        }
        new CheckBorder(Main.plugin).runTaskTimer(Main.plugin, 30L, 15L);
        LoadKits.get().a(plugin);
    }
    
    private void getServerVersion(){
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
        boolean isVaild = true;
        switch (version){
            case "v1_7_R3":
                versionHandler = new NMS_1_7_R3();
                getLogger().log(Level.INFO, "Cargando version: {0}", version);
                break;
            case "v1_8_R3":
                versionHandler = new NMS_1_8_R3();
                getLogger().log(Level.INFO, "Cargando version: {0}", version);
                break;
            default:
                isVaild = false;
                break;
        }
        if (!isVaild){
            getLogger().log(Level.SEVERE, "Need Spigot 1.7.10 or Spigot 1.8.8 :)");
            Bukkit.shutdown();
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }
    
    @Override
    public void onDisable() {
        Bukkit.getServer().unloadWorld(world, true);
        for (Player all : Bukkit.getOnlinePlayers()){
            all.kickPlayer("Restarting World");
        }
    }
}
