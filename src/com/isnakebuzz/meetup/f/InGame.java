package com.isnakebuzz.meetup.f;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.b.Kits;
import com.isnakebuzz.meetup.b.States;
import com.isnakebuzz.meetup.d.Border;
import com.isnakebuzz.meetup.e.API;
import static com.isnakebuzz.meetup.e.API.c;
import com.isnakebuzz.meetup.g.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class InGame extends BukkitRunnable {

    private final Main plugin;

    public InGame(Main plugin) {
        this.plugin = plugin;
    }
    
    public static int ingtime = 0;
    
    World w = Bukkit.getWorld(Main.world);
    
    @Override
    public void run() {
        
        if (ingtime < 180 && ingtime >= 170){
            Bukkit.broadcastMessage(c(Main.plugin.getConfig().getString("Border")
                    .replaceAll("%time%", ""+API.nextborder)
                    .replaceAll("%coords%", "40")
            ));
        }else if (ingtime == 180){
            Border.walls = 40;
            API.nextborder = 1;
            Border.buildWalls(Border.walls, Material.BEDROCK, 5, w);
            Bukkit.broadcastMessage(c(Main.plugin.getConfig().getString("BorderNow")
                    .replaceAll("%coords%", "40")
            ));
            this.cancel();
        }
        
        if (ingtime < 120 && ingtime >= 110){
            Bukkit.broadcastMessage(c(Main.plugin.getConfig().getString("Border")
                    .replaceAll("%time%", ""+API.nextborder)
                    .replaceAll("%coords%", "60")
            ));
        }else if (ingtime == 120){
            Border.walls = 60;
            API.nextborder = 60;
            Border.buildWalls(Border.walls, Material.BEDROCK, 5, w);
            Bukkit.broadcastMessage(c(Main.plugin.getConfig().getString("BorderNow")
                    .replaceAll("%coords%", "60")
            ));
        }
        
        if (ingtime < 60 && ingtime >= 50){
            Bukkit.broadcastMessage(c(Main.plugin.getConfig().getString("Border")
                    .replaceAll("%time%", ""+API.nextborder)
                    .replaceAll("%coords%", "100")
            ));
        }else if (ingtime == 60){
            Border.walls = 100;
            API.nextborder = 60;
            Border.buildWalls(Border.walls, Material.BEDROCK, 5, w);
            Bukkit.broadcastMessage(c(Main.plugin.getConfig().getString("BorderNow")
                    .replaceAll("%coords%", "100")
            ));
        }
        
        API.nextborder--;
        ingtime++;
    }

}
