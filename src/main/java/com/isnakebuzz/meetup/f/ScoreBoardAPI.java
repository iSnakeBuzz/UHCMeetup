package com.isnakebuzz.meetup.f;


import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.d.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ScoreBoardAPI {

    private Main plugin;
    private HashMap<Player, Integer> scoretask;

    public ScoreBoardAPI(Main plugin) {
        this.plugin = plugin;
        this.scoretask = new HashMap<>();

    }

    public void setScoreBoard(Player p, ScoreboardType scoreboardType, boolean health, boolean spect) {
        removeScoreBoard(p);
        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        ScoreBoardBuilder scoreboard = new ScoreBoardBuilder(randomString(8), health, spect);
        Configuration config2 = plugin.getConfigUtils().getConfig(plugin, "Settings");
        int id = new BukkitRunnable() {
            @Override
            public void run() {
                Configuration config = plugin.getConfigUtils().getConfig(plugin, "Extra/ScoreBoard");
                scoreboard.setName(chars(p, config2, config.getString(scoreboardType.toString() + ".title")));

                int line = config.getStringList(scoreboardType.toString() + ".lines").size();
                for (final String s : config.getStringList(scoreboardType.toString() + ".lines")) {
                    scoreboard.lines(line, chars(p, config2, s));
                    line--;
                }
                if (health) scoreboard.updatelife();
                if (spect) scoreboard.updateSpectPlayer(p);
            }
        }.runTaskTimer(plugin, 0l, 10l).getTaskId();
        p.setScoreboard(scoreboard.getScoreboard());
        this.scoretask.put(p, id);
    }

    private String toMb(Long l) {
        Long longMB = 1024l * 1024l;
        Long toString = l / longMB;
        return String.valueOf(toString) + " MB";
    }

    public void removeScoreBoard(Player p) {
        p.setScoreboard(Bukkit.getServer().getScoreboardManager().getNewScoreboard());
        if (this.scoretask.containsKey(p)) Bukkit.getScheduler().cancelTask(this.scoretask.get(p));
    }

    private String chars(Player p, Configuration config, String c) {
        GamePlayer gamePlayer = plugin.getPlayerManager().getUuidGamePlayerMap().get(p.getUniqueId());
        return ChatColor.translateAlternateColorCodes('&', c)

                .replaceAll("%date%", getDate())
                .replaceAll("%players%", String.valueOf(Bukkit.getOnlinePlayers().size()))
                .replaceAll("%alive%", String.valueOf(plugin.getPlayerManager().getPlayersAlive().size()))
                .replaceAll("%maxplayers%", config.getString("GameOptions.MaxPlayers"))
                .replaceAll("%player%", p.getName())
                .replaceAll("%border%", String.valueOf(plugin.getBorderManager().getBorder()))

                .replaceAll("%kills%", String.valueOf(gamePlayer.getLKills()))

                //Times
                .replaceAll("%game_time%", plugin.getTimerManager().transformToDate(plugin.getTimerManager().getGameTime()))
                .replaceAll("%border_timer%", String.valueOf(plugin.getTimerManager().getBorderTime()))
                .replaceAll("%vote_ends%", String.valueOf(plugin.getTimerManager().getVoteEnds()))
                .replaceAll("%start_time%", plugin.getTimerManager().transformToDate(plugin.getTimerManager().getStartingTime()))
                .replaceAll("%border_time%", bTime())

                // Scenarios
                .replaceAll("%bowless%", "Soon")
                .replaceAll("%default%", "Soon")
                .replaceAll("%fireless%", "Soon")
                .replaceAll("%noclean%", "Soon")
                .replaceAll("%timebomb%", "Soon")
                ;
    }

    public String getDate() {
        return new SimpleDateFormat("MM/dd/yy").format(new Date());
    }

    private String bTime() {
        if (plugin.getTimerManager().getBorderTime() <= 0) {
            return "";
        } else {
            return c(plugin.getConfigUtils().getConfig(plugin, "Extra/ScoreBoard").getString("bTimeFormat").replaceAll("%time%", String.valueOf(plugin.getTimerManager().getBorderTime())));
        }
    }

    private String randomString(int length) {
        String[] randomChars = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "k", "L", "N", "M", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        int[] randomInteger = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};

        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }

    public enum ScoreboardType {
        LOBBY, STARTING, INGAME, FINISHED, SPECTATOR
    }

}
