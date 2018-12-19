package com.isnakebuzz.meetup.Utils.ScoreBoard;


import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.Player.GamePlayer;
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

    public void setScoreBoard(Player p, ScoreboardType scoreboardType, boolean health, boolean nametags, boolean spect) {
        removeScoreBoard(p);
        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        ScoreBoardBuilder scoreboard = new ScoreBoardBuilder(randomString(8), health, nametags, spect);
        Configuration config2 = plugin.getConfigUtils().getConfig(plugin, "Settings");
        int id = new BukkitRunnable() {
            @Override
            public void run() {
                Configuration config = plugin.getConfigUtils().getConfig(plugin, "Extra/ScoreBoard");
                scoreboard.setName(chars(p, config2, config.getString(scoreboardType.toString() + ".title")));

                int line = config.getStringList(scoreboardType.toString() + ".lines").size();
                for (final String s : config.getStringList(scoreboardType.toString() + ".lines")) {
                    if (s.contains("<event_epcd>")) {
                        if (plugin.getPlayerManager().isEnderCD(p)) {
                            scoreboard.lines(line, chars(p, config2, s));
                        } else {
                            scoreboard.dLine(line);
                        }
                    } else if (s.contains("<event_noclean>")) {
                        if (plugin.getPlayerManager().isNoClean(p)) {
                            scoreboard.lines(line, chars(p, config2, s));
                        } else {
                            scoreboard.dLine(line);
                        }
                    } else {
                        scoreboard.lines(line, chars(p, config2, s));
                    }
                    line--;
                }
                if (health) scoreboard.updatelife();
                if (spect) scoreboard.updateSpectPlayer(p);
                if (nametags) scoreboard.updateTeams(p, plugin);
            }
        }.runTaskTimer(plugin, 0l, 0l).getTaskId();
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
                .replaceAll("%bowless%", String.valueOf(plugin.getVoteManager().getBowless().size()))
                .replaceAll("%default%", String.valueOf(plugin.getVoteManager().getDefault().size()))
                .replaceAll("%fireless%", String.valueOf(plugin.getVoteManager().getFireless().size()))
                .replaceAll("%noclean%", String.valueOf(plugin.getVoteManager().getNoClean().size()))
                .replaceAll("%rodless%", "Soon")
                .replaceAll("%timebomb%", "Soon")

                // Remove events placeholders
                .replaceAll("<event_noclean>", "")
                .replaceAll("<event_epcd>", "")

                //Cooldowns
                .replaceAll("%epearlcd%", this.getEPCD(p))
                .replaceAll("%noclean_time%", this.getNCCD(p))
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

    private String getEPCD(Player p) {
        if (plugin.getPlayerManager().isEnderCD(p)) {
            Long rightnow = System.currentTimeMillis();
            Long lastPearl = plugin.getPlayerManager().getEnderCDMap().get(p.getUniqueId());
            Long timeLeft = (plugin.getTimerManager().getEpcooldown() - (rightnow - lastPearl));
            double seconds = timeLeft / 1000.0;
            return String.format("%.1f", seconds).replaceAll(",", ".");
        }
        return "";
    }

    private String getNCCD(Player p) {
        if (plugin.getPlayerManager().isNoClean(p)) {
            Long rightnow = System.currentTimeMillis();
            Long lastPearl = plugin.getPlayerManager().getNoCleanCD().get(p.getUniqueId());
            Long timeLeft = (plugin.getTimerManager().getNcCooldown() - (rightnow - lastPearl));
            double seconds = timeLeft / 1000.0;
            return String.format("%.1f", seconds).replaceAll(",", ".");
        }
        return "";
    }

    private String randomString(int length) {
        String[] randomChars = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "C", "L", "N", "M", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
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
