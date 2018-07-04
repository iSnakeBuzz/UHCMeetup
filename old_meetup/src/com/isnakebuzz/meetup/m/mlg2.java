package com.isnakebuzz.meetup.m;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.d.Border;
import com.isnakebuzz.meetup.e.API;
import com.isnakebuzz.meetup.e.MessagesManager;
import com.isnakebuzz.meetup.f.Finish;
import com.isnakebuzz.meetup.f.MLG;

public class mlg2 extends BukkitRunnable {
	
	private Main plugin;
	
	public mlg2 (Main plugin) {
		this.plugin = plugin;
	}
	
	int timer2 = 0;
	
	int timer = 15;
	
    @Override
    public void run() {
    	
    	if (timer2 == 21) {
    		Player p = Bukkit.getPlayer(API.winner);
    		if (!API.MLG.contains(p)) {
    			Bukkit.broadcastMessage(c(MessagesManager.MLGFailed.replaceAll("%player%", p.getName())));
    			API.MLGe = false;
    			MLG.finished = true;
    			new Finish(Main.plugin).runTaskTimer(Main.plugin, 02L, 20L);
    			this.cancel();
        	}else {
        		MLG.state3 = true;
        	}
    	}
    	
    	if (timer == 15) {
    		MLG.state2 = false;
    	}
    	
    	if (timer <= 5 && timer >= 1) {
    		Bukkit.broadcastMessage(c(MessagesManager.MLGSecond.replaceAll("%timer%", ""+timer)));
    	}
    	if (timer == 1) {
    		for (Player p : API.MLG) {
    			API.setMLGInventory(p);
    		}
    	}
    	if (timer == 0) {
    		for (Player p : API.MLG) {
    			API.mlg2(p, Border.walls - 5);
    		}
    	}
    	if (timer >= 0) {
    		timer--;
    	}
    	timer2++;
    }
    
	private String c(String c) {
    	return ChatColor.translateAlternateColorCodes('&', c);
    }
    
}
