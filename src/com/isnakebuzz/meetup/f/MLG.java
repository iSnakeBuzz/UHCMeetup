package com.isnakebuzz.meetup.f;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.b.Kits;
import com.isnakebuzz.meetup.b.States;
import com.isnakebuzz.meetup.d.Border;
import com.isnakebuzz.meetup.e.API;
import com.isnakebuzz.meetup.g.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MLG extends BukkitRunnable {

    private final Main plugin;

    public MLG(Main plugin) {
        this.plugin = plugin;
    }
    
    public static int ingtime = 0;
    
    World w = Bukkit.getWorld(Main.world);
    
    @Override
    public void run() {
        if (API.MLG.isEmpty()){
            this.cancel();
        }
        
        if (ingtime == 100){
            Player p = (Player) API.MLG;
            this.cancel();
        }else if (ingtime == 70){
            for (Player p : API.MLG){
                API.mlg3(p, Border.walls);
            }
        }else if (ingtime == 30){
            for (Player p : API.MLG){
                API.mlg2(p, Border.walls);
            }
        }else if(ingtime == 0){
            for (Player p : API.MLG){
                API.mlg1(p, Border.walls);
            }
        }
        ingtime++;
    }

}
