package com.isnakebuzz.meetup.g;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.d.Border;
import com.isnakebuzz.meetup.e.API;
import com.isnakebuzz.meetup.e.Votes;
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

    public void setScoreBoard(final Player p) {
        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        ScoreBoardBuilder scoreboard = new ScoreBoardBuilder(randomString(8));
        int id = new BukkitRunnable() {
            @Override
            public void run() {
                Configuration config = plugin.getConfigUtils().getConfig(plugin, "Extra/ScoreBoards");
                scoreboard.setName(chars(p, config.getString("NormalScoreBoard.Title")));

                int line = config.getStringList("NormalScoreBoard.lines").size();
                for (final String s : config.getStringList("NormalScoreBoard.lines")) {
                    scoreboard.lines(line, chars(p, s));
                    line--;
                }
            }
        }.runTaskTimer(plugin, 5l, 5l).getTaskId();
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

    private String chars(Player p, String c) {
        return ChatColor.translateAlternateColorCodes('&', c)
                .replaceAll("%version%", plugin.getPdf().getVersion())
                .replaceAll("%fireless%", Votes.getFireLess())
                .replaceAll("%timebomb%", Votes.getTimeBomb())
                .replaceAll("%noclean%", Votes.getNoClean())
                .replaceAll("%rod%", Votes.getRodLess())
                .replaceAll("%nogames%", Votes.getNogames())
                .replaceAll("%bow%", Votes.getBowLess())
                .replaceAll("%online%", "" + Bukkit.getOnlinePlayers().size())
                .replaceAll("%kills%", "" + API.GetKills(p))
                .replaceAll("%border%", "" + Border.walls)
                .replaceAll("%specs%", "" + API.Specs.size())
                .replaceAll("%online%", "" + API.ALivePs.size())
                .replaceAll("%time%", "" + API.nextborder)
                ;
    }

    public String getDate() {
        return new SimpleDateFormat("MM/dd/yy").format(new Date());
    }

    private String randomString(int length) {
        String[] randomChars = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "N", "M", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
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

}
