package com.isnakebuzz.meetup.Tasks;

import com.isnakebuzz.meetup.EventsManager.CustomEvents.GameStartingEvent;
import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.Utils.Enums.StartingType;
import com.isnakebuzz.meetup.Player.GamePlayer;
import com.isnakebuzz.meetup.Utils.ScoreBoard.ScoreBoardAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class LobbyTask extends BukkitRunnable {

    private Main plugin;
    private int time;

    public LobbyTask(Main plugin, int time) {
        this.plugin = plugin;
        this.time = time;
    }

    @Override
    public void run() {
        Bukkit.getPluginManager().callEvent(new GameStartingEvent(plugin.getPlayerManager().getPlayersAlive(), StartingType.LOBBY));

        plugin.getTimerManager().setVoteEnds(time);
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Lang");
        Set<String> keys = config.getConfigurationSection("VoteEnds").getKeys(false);
        for (String time_config : keys) {
            if (time == Integer.valueOf(time_config)) {
                plugin.broadcast(config.getString("VoteEnds." + time_config).replaceAll("%seconds%", String.valueOf(plugin.getTimerManager().getVoteEnds())));
            }
        }
        time--;
        if (time <= 0) {
            Configuration config2 = plugin.getConfigUtils().getConfig(plugin, "Settings");
            new StartingTask(plugin, config2.getInt("GameOptions.StartingTime")).runTaskTimer(plugin, 0l, 20l);
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!plugin.getPlayerManager().getPlayersAlive().contains(p)) {
                    plugin.getPlayerManager().getPlayersAlive().add(p);
                }
                p.setGameMode(GameMode.SURVIVAL);
                plugin.getScoreBoardAPI().setScoreBoard(p, ScoreBoardAPI.ScoreboardType.STARTING, false, false, false);
                GamePlayer gamePlayer = plugin.getPlayerManager().getUuidGamePlayerMap().get(p.getUniqueId());
                plugin.getTeleportManager().teleport();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        gamePlayer.sit();
                    }
                }.runTaskLater(plugin, 5);
                gamePlayer.setSpectator(false);
                p.setAllowFlight(true);
                p.setFlying(true);
                p.getInventory().clear();
                p.getInventory().setArmorContents(null);
                plugin.getCustomKits().setUpKit(p);
            }
            plugin.getVoteManager().checkVoteWin();
            plugin.getEventManager().unregisterListener(plugin.getEventManager().getEventInteract());
            this.cancel();
        }
    }
}
