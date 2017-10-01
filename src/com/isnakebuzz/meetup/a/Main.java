package com.isnakebuzz.meetup.a;

import com.isnakebuzz.meetup.b.Kits;
import com.isnakebuzz.meetup.b.States;
import com.isnakebuzz.meetup.c.GameListeners;
import com.isnakebuzz.meetup.c.PlayerListeners;
import com.isnakebuzz.meetup.d.Border;
import com.isnakebuzz.meetup.d.WorldGenerator;
import com.isnakebuzz.meetup.e.API;
import com.isnakebuzz.meetup.e.LoadKits;
import com.isnakebuzz.meetup.e.Metrics;
import com.isnakebuzz.meetup.g.ScoreboardAPI;
import com.isnakebuzz.meetup.h.CheckBorder;
import com.isnakebuzz.meetup.h.Cmds;
import java.util.HashMap;
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
    
    private GameListeners gl;
    private PlayerListeners pl;
    private Cmds cmds;
    public static Main plugin;
    public HashMap<Player, ScoreboardAPI> scoreboard;
    
    @Override
    public void onEnable() {
        super.onEnable();
        new Metrics(this);
        States.state = States.LOBBY;
        Border.walls = 125;
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        saveDefaultConfig();
        Main.plugin = this;
        gl.init();
        pl.init();
        cmds.init();
        API.Generating();
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

    @Override
    public void onDisable() {
        Bukkit.getServer().unloadWorld(world, true);
        for (Player all : Bukkit.getOnlinePlayers()){
            all.kickPlayer("Restarting World");
        }
    }
}
