package com.isnakebuzz.meetup.EventsManager.Events;

import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.Utils.GamePlayer;
import com.isnakebuzz.meetup.Utils.ScoreBoard.ScoreBoardAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class EventDeath implements Listener {

    private Main plugin;

    public EventDeath(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerDeathEvent(PlayerDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = e.getEntity().getPlayer();
            GamePlayer gamePlayer = plugin.getPlayerManager().getUuidGamePlayerMap().get(p.getUniqueId());
            plugin.getPlayerManager().spectator(gamePlayer, true);
            if (plugin.getPlayerManager().getPlayersAlive().contains(p)) {
                plugin.getPlayerManager().getPlayersAlive().remove(p);
            }
            plugin.getScoreBoardAPI().setScoreBoard(p, ScoreBoardAPI.ScoreboardType.SPECTATOR, true, false, true);
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.hidePlayer(p);
            }
            gamePlayer.addDeaths(1);
            if (e.getEntity().getKiller() != null) {
                Player killer = e.getEntity().getKiller();
                GamePlayer gamePlayerk = plugin.getPlayerManager().getUuidGamePlayerMap().get(killer.getUniqueId());
                gamePlayerk.addLKills(1);
                gamePlayerk.addKills(1);
            }
        }
    }

    @EventHandler
    public void PlayerRespawnEvent(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        p.setGameMode(GameMode.CREATIVE);
        p.setFoodLevel(40);
        p.setHealth(p.getMaxHealth());
        e.setRespawnLocation(plugin.getLobbyManager().getWorldLobby());
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
