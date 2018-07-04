package com.isnakebuzz.meetup.c;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.l.GameWinEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class GameTask extends BukkitRunnable {

    private int time;
    private int maxbucle;
    private int bucleint;
    private Main plugin;
    private int border;
    private int game_time = 0;

    public GameTask(Main plugin) {
        this.plugin = plugin;
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Extra/Border");
        Set<String> key = config.getConfigurationSection("Border").getKeys(false);
        this.maxbucle = key.size();
        this.bucleint = 1;
        String decrypt = config.getString("Border." + bucleint);
        this.border = config.getInt("Border." + bucleint + ".Size");
        this.time = config.getInt("Border." + bucleint + ".Time");
    }


    @Override
    public void run() {
        if (Main.getStates.state == Main.getStates.FINISHED) {
            this.cancel();
        }

        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Extra/Border");
        plugin.getTimerManager().setBorderTime(time);
        plugin.getTimerManager().setGameTime(game_time);


        if (plugin.getArenaManager().isWin()) {
            GameWinEvent gameWinEvent = new GameWinEvent(plugin.getPlayerManager().getPlayersAlive());
            Bukkit.getPluginManager().callEvent(gameWinEvent);
            this.cancel();
            return;
        }

        if (this.bucleint <= this.maxbucle) {
            Set<String> keys = config.getConfigurationSection("Border." + bucleint + ".Broadcasts").getKeys(false);
            for (String time_config : keys) {
                if (time == Integer.valueOf(time_config)) {
                    plugin.broadcast(config.getString("Border." + bucleint + ".Broadcasts." + time_config)
                            .replaceAll("%seconds%", String.valueOf(time))
                            .replaceAll("%size%", String.valueOf(config.getInt("Border." + bucleint + ".Size")))
                    );
                }
            }
        }

        if (time == 0 && this.bucleint <= this.maxbucle) {
            int border = config.getInt("Border." + bucleint + ".Size");
            int timeto = config.getInt("Border." + bucleint + ".Time");
            this.border = border;
            plugin.getBorderManager().setBorder(border, true, "uhc");
            for (Player p : Bukkit.getOnlinePlayers()) {
                plugin.getBorderManager().teleportBorder(p, "uhc");
            }
            plugin.getBorderManager().newBorderGenerator(7, "uhc");
            plugin.broadcast(config.getString("Border." + bucleint + ".Shrunk")
                    .replaceAll("%seconds%", String.valueOf(plugin.getTimerManager().getBorderTime()))
                    .replaceAll("%size%", String.valueOf(border))
            );
            this.bucleint++;
            if (this.bucleint <= this.maxbucle) {
                this.time = timeto;
            }
        }
        if (this.bucleint <= this.maxbucle) {
            this.time--;
        }
        this.game_time++;
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }

}
