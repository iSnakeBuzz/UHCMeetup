package com.isnakebuzz.meetup.h;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.c.EndTask;
import com.isnakebuzz.meetup.f.ScoreBoardAPI;
import com.isnakebuzz.meetup.l.GameWinEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EventGameWin implements Listener {

    private Main plugin;

    public EventGameWin(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGameWinEvent(GameWinEvent e) {
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Lang");
        if (e.getPlayers().size() <= 1) {
            for (String msg : config.getStringList("WinMessage")) {
                e.getPlayers().iterator().hasNext();
                plugin.broadcast(c(msg.replaceAll("%winners%", e.getPlayers().iterator().next().getName())));
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            int max = 0;
            while (e.getPlayers().iterator().hasNext()) {
                if (max >= e.getPlayers().size()) {
                    stringBuilder.append(e.getPlayers().iterator().next());
                } else {
                    stringBuilder.append(e.getPlayers().iterator().next() + ", ");
                    max++;
                }
            }
            String winners = stringBuilder.toString();
            for (String msg : config.getStringList("WinMessage")) {
                e.getPlayers().iterator().hasNext();
                plugin.broadcast(c(msg.replaceAll("%winners%", winners)));
            }
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            plugin.getScoreBoardAPI().setScoreBoard(p, ScoreBoardAPI.ScoreboardType.FINISHED, false, false);
        }

        new EndTask(plugin, plugin.getConfigUtils().getConfig(plugin, "Settings").getInt("GameOptions.EndTime")).runTaskTimer(plugin, 0l, 20l);
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
