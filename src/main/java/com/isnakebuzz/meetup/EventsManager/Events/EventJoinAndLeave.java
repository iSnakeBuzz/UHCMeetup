package com.isnakebuzz.meetup.EventsManager.Events;

import com.isnakebuzz.meetup.EventsManager.CustomEvents.GameStartEvent;
import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.Player.GamePlayer;
import com.isnakebuzz.meetup.Utils.Connection;
import com.isnakebuzz.meetup.Utils.ScoreBoard.ScoreBoardAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;
import java.sql.SQLException;

public class EventJoinAndLeave implements Listener {

    private Main plugin;

    public EventJoinAndLeave(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) throws IOException, SQLException {
        e.setJoinMessage(null);
        Player p = e.getPlayer();
        //Set Full
        p.setHealth(p.getMaxHealth());
        p.setFoodLevel(40);

        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Settings");
        if (config.getBoolean("Lobby.enabled")) {
            p.teleport(plugin.getLobbyManager().getLobby());
            p.setGameMode(GameMode.ADVENTURE);
        } else {
            p.setGameMode(GameMode.CREATIVE);
            p.teleport(plugin.getLobbyManager().getWorldLobby());
        }
        if (Connection.Database.database.equals(Connection.Database.NONE)) {
            GamePlayer gamePlayer = new GamePlayer(plugin, p, p.getUniqueId(), false, 0, 0, 0);
            plugin.getPlayerManager().getUuidGamePlayerMap().put(p.getUniqueId(), gamePlayer);
            plugin.getPlayerManager().spectator(gamePlayer, true);
            plugin.getScoreBoardAPI().setScoreBoard(p, ScoreBoardAPI.ScoreboardType.LOBBY, false, false, false);
        } else {
            plugin.getPlayerDataInterface().loadPlayer(p);
            Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, () -> {
                GamePlayer gamePlayer = plugin.getPlayerManager().getUuidGamePlayerMap().get(p.getUniqueId());
                plugin.getPlayerManager().spectator(gamePlayer, true);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    plugin.getScoreBoardAPI().setScoreBoard(p, ScoreBoardAPI.ScoreboardType.LOBBY, false, false, false);

                });
            });
        }
        if (plugin.getArenaManager().checkStart()) {
            Bukkit.getPluginManager().callEvent(new GameStartEvent(plugin.getPlayerManager().getPlayersAlive()));
        }
        Configuration lang = plugin.getConfigUtils().getConfig(plugin, "Lang");
        p.sendMessage(c(lang.getString("VoteMessage")));

        if (p.hasPermission("meetup.admin")) plugin.checkVersionPlayer(p);
    }

    @EventHandler
    public void PlayerLeftEvent(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        plugin.getScoreBoardAPI().removeScoreBoard(e.getPlayer());
        plugin.getPlayerManager().getPlayersAlive().remove(e.getPlayer());
        if (!Connection.Database.database.equals(Connection.Database.NONE)) {
            plugin.getPlayerDataInterface().savePlayer(e.getPlayer());
        }
    }

    @EventHandler
    public void PlayerLeftEvent(PlayerKickEvent e) {
        plugin.getScoreBoardAPI().removeScoreBoard(e.getPlayer());
        plugin.getPlayerManager().getPlayersAlive().remove(e.getPlayer());
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
