package com.isnakebuzz.meetup.f;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.d.Border;
import com.isnakebuzz.meetup.e.API;
import static com.isnakebuzz.meetup.e.API.c;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Finish extends BukkitRunnable {

    private final Main plugin;

    public Finish(Main plugin) {
        this.plugin = plugin;
    }
    
    public static int end = 30;
    
    World w = Bukkit.getWorld(Main.world);
    
    @Override
    public void run() {
        if (API.MLGe == true){
            this.cancel();
        }
        
        if (end == 30){
            Bukkit.broadcastMessage(c(Main.plugin.getConfig().getString("Restarting")
                    .replaceAll("%time%", ""+end)
            ));
        } else if (end == 15){
            Bukkit.broadcastMessage(c(Main.plugin.getConfig().getString("Restarting")
                    .replaceAll("%time%", ""+end)
            ));
        } else if (end == 10){
            Bukkit.broadcastMessage(c(Main.plugin.getConfig().getString("Restarting")
                    .replaceAll("%time%", ""+end)
            ));
        } else if (end >= 1 && end <= 5){
            Bukkit.broadcastMessage(c(Main.plugin.getConfig().getString("Restarting")
                    .replaceAll("%time%", ""+end)
            ));
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                for (Player p : Bukkit.getOnlinePlayers()){
                    API.sendLobby(p);
                }
            }, 5);
        } else if (end == 0){
            Main.plugin.getServer().shutdown();
        }
        
        end--;
    }

}
