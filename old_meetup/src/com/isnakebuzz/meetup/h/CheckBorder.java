package com.isnakebuzz.meetup.h;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.d.Border;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CheckBorder extends BukkitRunnable {

    @SuppressWarnings("unused")
	private final Main plugin;

    public CheckBorder(Main plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()){
            Border.checkBorder(p);
        }
    }

}
