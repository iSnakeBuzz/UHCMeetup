package com.isnakebuzz.meetup.f;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.e.API;
import com.isnakebuzz.meetup.e.MessagesManager;

import static com.isnakebuzz.meetup.e.API.c;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Finish extends BukkitRunnable {

    private final Main plugin;

    public Finish(Main plugin) {
        this.plugin = plugin;
        end = 30;
    }
    
    public static int end = 30;
    
    World w = Bukkit.getWorld(Main.world);
    
    @Override
    public void run() {
        if (API.MLGe == true){
            this.cancel();
        }
        
        if (end == 30){
            Bukkit.broadcastMessage(c(MessagesManager.RestartingMessage
                    .replaceAll("%time%", ""+end)
            ));
        } else if (end == 15){
            Bukkit.broadcastMessage(c(MessagesManager.RestartingMessage
                    .replaceAll("%time%", ""+end)
            ));
        } else if (end == 10){
            Bukkit.broadcastMessage(c(MessagesManager.RestartingMessage
                    .replaceAll("%time%", ""+end)
            ));
        } else if (end >= 1 && end <= 5){
            Bukkit.broadcastMessage(c(MessagesManager.RestartingMessage
                    .replaceAll("%time%", ""+end)
            ));
            if (end == 1) {
            	plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                    for (Player p : Bukkit.getOnlinePlayers()){
                        API.sendLobby(p);
                    }
                }, 5);
            }
        } else if (end == 0){
        	for (Player all : Bukkit.getOnlinePlayers()) {
        		all.kickPlayer("§aRestarting all features on UHCMeetup");
        	}
        	if (plugin.getConfig().getBoolean("GameOptions.NoRestart.enabled")) {
        		Main.plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), plugin.getConfig().getString("GameOptions.NoRestart.command"));
        	}else {
        		plugin.getServer().shutdown();
        	}
        	this.cancel();
        }
        
        end--;
    }

}
