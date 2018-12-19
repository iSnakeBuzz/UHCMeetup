package com.isnakebuzz.meetup.EventsManager.Events;

import com.isnakebuzz.meetup.EventsManager.CustomEvents.*;
import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.Tasks.EndTask;
import com.isnakebuzz.meetup.Tasks.LobbyTask;
import com.isnakebuzz.meetup.Utils.Enums.GameStates;
import com.isnakebuzz.meetup.Utils.GamePlayer;
import com.isnakebuzz.meetup.Utils.ScoreBoard.ScoreBoardAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EventMeetupChecks implements Listener {

    private Main plugin;

    public EventMeetupChecks(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void GameStartEvent(GameStartEvent e) {
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Settings");

        plugin.getArenaManager().setGameStates(GameStates.STARTING);
        new LobbyTask(plugin, config.getInt("GameOptions.VoteTime")).runTaskTimer(plugin, 0l, 20l);
    }

    @EventHandler
    public void GameStartingEvent(GameStartingEvent e) {
        plugin.getArenaManager().setGameStates(GameStates.INGAME);
    }

    @EventHandler
    public void GameWinEvent(GameWinEvent e) {
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Lang");
        if (e.getPlayers().size() <= 1) {
            Player p = e.getPlayers().iterator().next();
            for (String msg : config.getStringList("WinMessage")) {
                e.getPlayers().iterator().hasNext();
                plugin.broadcast(c(msg.replaceAll("%winners%", p.getName())));
            }
            GamePlayer gamePlayer = plugin.getPlayerManager().getUuidGamePlayerMap().get(p.getUniqueId());
            gamePlayer.addWins(1);
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
            for (Player p : e.getPlayers()) {
                GamePlayer gamePlayer = plugin.getPlayerManager().getUuidGamePlayerMap().get(p.getUniqueId());
                gamePlayer.addWins(1);
            }
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            plugin.getScoreBoardAPI().setScoreBoard(p, ScoreBoardAPI.ScoreboardType.FINISHED, true, true, true);
        }
        new EndTask(plugin, plugin.getConfigUtils().getConfig(plugin, "Settings").getInt("GameOptions.EndTime")).runTaskTimer(plugin, 0l, 20l);
    }

    @EventHandler
    public void GameEndingEvent(GameEndingEvent e) {
        plugin.getArenaManager().setGameStates(GameStates.ENDING);
    }

    @EventHandler
    public void GameEndEvent(GameEndEvent e) {
        plugin.getArenaManager().setGameStates(GameStates.ENDED);
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
