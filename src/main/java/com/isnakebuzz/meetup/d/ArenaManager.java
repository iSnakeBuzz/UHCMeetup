package com.isnakebuzz.meetup.d;

import com.isnakebuzz.meetup.a.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;

public class ArenaManager {

    private Main plugin;
    private boolean started = false;
    private boolean ended = false;

    public ArenaManager(Main plugin) {
        this.plugin = plugin;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean checkStart() {
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Settings");
        if (!isStarted()) {
            if (Bukkit.getOnlinePlayers().size() >= config.getInt("GameOptions.MinPlayers")) {
                started = true;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean isWin() {
        if (!ended) {
            if (plugin.getPlayerManager().getPlayersAlive().size() <= 1) {
                this.ended = true;
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

}
