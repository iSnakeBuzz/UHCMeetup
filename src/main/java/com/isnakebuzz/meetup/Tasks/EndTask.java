package com.isnakebuzz.meetup.Tasks;

import com.isnakebuzz.meetup.EventsManager.CustomEvents.GameEndEvent;
import com.isnakebuzz.meetup.EventsManager.CustomEvents.GameEndingEvent;
import com.isnakebuzz.meetup.Main;
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
        if (time == 1) {
            plugin.getPlayerManager().sendToLobby();
            Bukkit.getPluginManager().callEvent(new GameEndEvent(plugin.getPlayerManager().getPlayersAlive()));
        }
        if (time <= (-2)) {
            Configuration config = plugin.getConfigUtils().getConfig(plugin, "Settings");
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), config.getString("GameOptions.RestartCommand"));
            this.cancel();
        }
        Bukkit.getPluginManager().callEvent(new GameEndingEvent(plugin.getPlayerManager().getPlayersAlive()));
        time--;
    }
}
