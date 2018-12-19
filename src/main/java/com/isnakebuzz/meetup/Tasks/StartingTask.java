package com.isnakebuzz.meetup.Tasks;

import com.isnakebuzz.meetup.EventsManager.CustomEvents.GameStartedEvent;
import com.isnakebuzz.meetup.EventsManager.CustomEvents.GameStartingEvent;
import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.Utils.Enums.StartingType;
import com.isnakebuzz.meetup.Utils.GamePlayer;
import com.isnakebuzz.meetup.Utils.ScoreBoard.ScoreBoardAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class StartingTask extends BukkitRunnable {

    private Main plugin;
    private int time;

    public StartingTask(Main plugin, int time) {
        this.plugin = plugin;
        this.time = time;
    }

    @Override
    public void run() {
        Bukkit.getPluginManager().callEvent(new GameStartingEvent(plugin.getPlayerManager().getPlayersAlive(), StartingType.GAME));
        plugin.getTimerManager().setStartingTime(time);
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Lang");
        Set<String> keys = config.getConfigurationSection("Starting").getKeys(false);
        for (String time_config : keys) if (time == Integer.valueOf(time_config))plugin.broadcast(config.getString("Starting." + time_config).replaceAll("%seconds%", String.valueOf(plugin.getTimerManager().getStartingTime())));
        time--;
        if (time <= 0) {
            new GameTask(plugin).runTaskTimer(plugin, 20l, 20l);
            for (Player p : Bukkit.getOnlinePlayers()) {
                GamePlayer gamePlayer = plugin.getPlayerManager().getUuidGamePlayerMap().get(p.getUniqueId());
                gamePlayer.unsit();
                p.setGameMode(GameMode.SURVIVAL);
                p.setAllowFlight(false);
                p.setFlying(false);
                plugin.getScoreBoardAPI().setScoreBoard(p, ScoreBoardAPI.ScoreboardType.INGAME,true, true, false);
            }
            Bukkit.getPluginManager().callEvent(new GameStartedEvent(plugin.getPlayerManager().getPlayersAlive()));
            this.cancel();
            return;
        }
    }
}
