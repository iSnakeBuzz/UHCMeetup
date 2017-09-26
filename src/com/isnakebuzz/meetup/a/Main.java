package com.isnakebuzz.meetup.a;

import com.isnakebuzz.meetup.b.States;
import com.isnakebuzz.meetup.c.GameListeners;
import com.isnakebuzz.meetup.c.PlayerListeners;
import com.isnakebuzz.meetup.d.Border;
import com.isnakebuzz.meetup.e.API;
import com.isnakebuzz.meetup.g.ScoreboardAPI;
import java.util.HashMap;
import java.util.logging.Level;
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
    }
    
    public static String world = "uhc";
    
    private GameListeners gl;
    private PlayerListeners pl;
    public static Main plugin;
    public HashMap<Player, ScoreboardAPI> scoreboard;
    
    @Override
    public void onEnable() {
        super.onEnable();
        Main.plugin = this;
        gl.init();
        pl.init();
        API.Generating();
        API.DeleteWorld(world);
        World w = Bukkit.getWorld(world);
        w.setAutoSave(false);
        w.setSpawnLocation(0, w.getHighestBlockYAt(0, 0) + 10, 0);
        getServer().getScheduler().runTaskLater(this, () -> {
            Border.buildWalls(Border.walls, Material.BEDROCK, 5, w);
        }, 30);
        States.state = States.LOBBY;
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().unloadWorld(world, true);
        for (Player all : Bukkit.getOnlinePlayers()){
            all.kickPlayer("Restarting World");
        }
    }
}
