package com.isnakebuzz.meetup.Tasks;

import com.isnakebuzz.meetup.Main;
import org.bukkit.configuration.Configuration;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class ExampleTask extends BukkitRunnable {

    private Main plugin;
    private int time;

    public ExampleTask(Main plugin, int time) {
        this.plugin = plugin;
        this.time = time;
    }

    @Override
    public void run() {
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Lang");
        Set<String> keys = config.getConfigurationSection("VoteEnds").getKeys(false);
        for (String time_config : keys) {
            if (time == Integer.valueOf(time_config)) {
                plugin.broadcast(config.getString("VoteEnds." + time_config).replaceAll("%seconds%", String.valueOf(plugin.getTimerManager().getVoteEnds())));
            }
        }
        time--;
        if (time <= 0) {
            this.cancel();
        }
    }
}
