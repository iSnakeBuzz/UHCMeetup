package com.isnakebuzz.meetup.n;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.d.Border;
import com.isnakebuzz.meetup.e.API;

public class PillarChecker extends BukkitRunnable {
	
	private Main plugin;
	
	public PillarChecker(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (API.ALivePs.contains(p)) {
				Border.updateBlocks(p);
			}
		}
		
	}
	
}
