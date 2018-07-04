package com.isnakebuzz.meetup.f;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.j.CustomBorder;
import org.bukkit.configuration.Configuration;
import org.bukkit.scheduler.BukkitRunnable;

public class InGame extends BukkitRunnable {

    public static Boolean borderenabled = false;
    @SuppressWarnings("unused")
    private final Main plugin;

    public InGame(Main plugin) {
        this.plugin = plugin;
        Configuration config = plugin.getConfig();
        int time = Integer.valueOf(config.getString("Border.1").split(":")[1]);
        new CustomBorder(plugin, time).runTaskTimer(plugin, 20, 20);
    }

    @Override
    public void run() {
    }

}
