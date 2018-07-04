package com.isnakebuzz.meetup.c;

import com.isnakebuzz.meetup.a.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.scheduler.BukkitRunnable;

public class EndTask extends BukkitRunnable {

    private Main plugin;
    private int time;

    public EndTask(Main plugin, int time) {
        this.plugin = plugin;
        this.time = time;
    }

    @Override
    public void run() {
        time--;
        if (time <= 1) {
            plugin.getPlayerManager().sendToLobby();
        }
        if (time <= 0) {
            Configuration config = plugin.getConfigUtils().getConfig(plugin, "Settings");
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), config.getString("GameOptions.RestartCommand"));
            this.cancel();
        }
    }
}
