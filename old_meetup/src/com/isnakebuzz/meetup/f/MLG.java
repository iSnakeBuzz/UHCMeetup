package com.isnakebuzz.meetup.f;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.e.API;
import com.isnakebuzz.meetup.e.MessagesManager;
import com.isnakebuzz.meetup.m.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MLG extends BukkitRunnable {
	private Main plugin;
    public MLG(Main plugin) {
    	this.plugin = plugin;
    }
    
    public static int integerprivate = 0;
    public static int downtime = 15;
    
    public static boolean state1 = true;
    public static boolean state2 = false;
    public static boolean state3 = false;
    public static boolean finished = false;
    
    @Override
    public void run() {
    	if (integerprivate == 0) {
        	for (Player p : API.MLG) {
        		Bukkit.broadcastMessage(c(MessagesManager.MLGStarted.replaceAll("%player%", p.getName())));
        	}
        	integerprivate = 15;
        }
    	
    	if (finished == true) {
    		this.cancel();
    		return;
    	}

    	if (state1 == true) {
    		new mlg1(Main.plugin).runTaskTimer(Main.plugin, 02L, 20L);
    	}else if (state2 == true) {
    		new mlg2(Main.plugin).runTaskTimer(Main.plugin, 02L, 20L);
    	}else if (state3 == true) {
    		new mlg3(Main.plugin).runTaskTimer(Main.plugin, 02L, 20L);
    	}
    	
    }

	private String c(String replaceAll) {
		return ChatColor.translateAlternateColorCodes('&', replaceAll);
	}

}
