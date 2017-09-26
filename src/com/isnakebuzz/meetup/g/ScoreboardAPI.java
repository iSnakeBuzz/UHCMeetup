package com.isnakebuzz.meetup.g;

import java.util.*;
import org.bukkit.scheduler.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.scoreboard.*;

public class ScoreboardAPI
{
    private Scoreboard scoreboard;
    private int lastPage;
    private final UUID uuid;
    private BukkitTask task;
    
    private Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }
    
    public UUID getUniqueId() {
        return this.uuid;
    }
    
    public int getLastPage() {
        return this.lastPage;
    }
    
    private Objective getPage(final int page) {
        if (page > 15) {
            throw new IllegalArgumentException("Page number must be between 0 and 15");
        }
        Objective obj = this.scoreboard.getObjective("page" + page);
        if (obj == null) {
            obj = this.scoreboard.registerNewObjective("page" + page, "dummy");
            for (int i = 0; i < 15; ++i) {
                this.scoreboard.registerNewTeam(ChatColor.getByChar(Integer.toHexString(page)) + ChatColor.getByChar(Integer.toHexString(i)).toString());
            }
        }
        return obj;
    }
    
    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }
    
    public ScoreboardAPI(final UUID uuid) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.uuid = uuid;
        final Objective obj = this.getPage(0);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
    }
    
    public void setTitle(final int page, String title) {
        if (title == null) {
            title = "";
        }
        if (title.length() > 32) {
            title = title.substring(0, 32);
        }
        this.getPage(page).setDisplayName(title);
    }
    
    public void toggleScoreboard() {
        if (this.getPlayer() != null && !this.getPlayer().getScoreboard().equals(this.scoreboard)) {
            this.getPlayer().setScoreboard(this.scoreboard);
        }
        else if (this.scoreboard.getObjective(DisplaySlot.SIDEBAR) == null) {
            this.getPage(this.lastPage).setDisplaySlot(DisplaySlot.SIDEBAR);
        }
        else {
            this.scoreboard.clearSlot(DisplaySlot.SIDEBAR);
        }
    }
    
    public void changePage(final int page) {
        this.lastPage = page;
        if (this.scoreboard.getObjective(DisplaySlot.SIDEBAR) != null) {
            this.getPage(page).setDisplaySlot(DisplaySlot.SIDEBAR);
        }
    }
    
    public void setLineBlank(final int page, final int index) {
        this.setLine(page, index, "", "", false);
    }
    
    public void setLine(final int page, final int index, final String string) {
        this.setLine(page, index, string, true);
    }
    
    public void setLine(final int page, final int index, final String prefix, final String suffix) {
        this.setLine(page, index, prefix, suffix, true);
    }
    
    public void setLine(final int page, final int index, final String string, final boolean copyPreviousColors) {
        if (string.length() > 16) {
            this.setLine(page, index, string.substring(0, 16), string.substring(16), copyPreviousColors);
        }
        else {
            this.setLine(page, index, string, "", copyPreviousColors);
        }
    }
    
    public void setLine(final int page, final int index, String prefix, String suffix, final boolean copyPreviousColors) {
        if (prefix.length() > 16) {
            prefix = prefix.substring(0, 16);
        }
        if (suffix.length() > 16) {
            suffix = suffix.substring(0, 16);
        }
        if (index < 0 || index > 14) {
            throw new IllegalArgumentException("You can only get a line from 0 - 14");
        }
        final Objective obj = this.getPage(page);
        final String name = ChatColor.getByChar(Integer.toHexString(page)) + ChatColor.getByChar(Integer.toHexString(index)).toString();
        final Score score = obj.getScore(String.valueOf(name) + ChatColor.RESET);
        final Team team = this.scoreboard.getTeam(name);
        if (!score.isScoreSet()) {
            score.setScore(index);
            team.addEntry(score.getEntry());
        }
        team.setPrefix(prefix);
        if (copyPreviousColors) {
            suffix = String.valueOf(ChatColor.getLastColors(prefix)) + suffix;
            if (suffix.length() > 16) {
                suffix = suffix.substring(0, 16);
            }
        }
        team.setSuffix(suffix);
    }
    
    public BukkitTask getTask() {
        return this.task;
    }
    
    public void setTask(final BukkitTask task) {
        this.task = task;
    }
}
