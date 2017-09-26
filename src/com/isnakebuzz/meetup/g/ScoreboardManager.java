package com.isnakebuzz.meetup.g;

import com.isnakebuzz.meetup.d.Border;
import com.isnakebuzz.meetup.e.API;
import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.plugin.*;
import org.bukkit.scheduler.*;
import org.bukkit.configuration.file.*;

public class ScoreboardManager
{
    public static ScoreboardManager manager;
    
    static {
        ScoreboardManager.manager = new ScoreboardManager();
    }
    
    public static ScoreboardManager getManager() {
        return ScoreboardManager.manager;
    }
    
    public void ScoreBoard(final Player p) {
        final ScoreboardAPI sc = new ScoreboardAPI(p.getUniqueId());
        sc.setTitle(0, Utils.translate(this.getConfig().getString("ScoreBoard.Title")));
        final BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                final List<String> h = (List<String>)ScoreboardManager.this.getConfig().getStringList("ScoreBoard.Lines");
                int lines = ScoreboardManager.this.getConfig().getStringList("ScoreBoard.Lines").size();
                for (final String s : h) {
                    sc.setLine(0, lines, Utils.translate(s)
                                .replaceAll("%kills%", ""+API.GetKills(p))
                                .replaceAll("%border%", ""+Border.walls)
                                .replaceAll("%specs%", ""+API.Specs.size())
                                .replaceAll("%online%", ""+API.ALivePs.size())
                                .replaceAll("%time%", ""+API.nextborder)
                    );
                    --lines;
                }
            }
        }.runTaskTimer((Plugin)Utils.getInstance(), 0L, 01L);
        sc.setTask(task);
        p.setScoreboard(sc.getScoreboard());
        Utils.getInstance().scoreboard.put(p, sc);
    }
    
    public FileConfiguration getConfig() {
        return Utils.getInstance().getConfig();
    }
}
