package com.isnakebuzz.meetup.EventsManager.VoteEvents;

import com.isnakebuzz.meetup.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class VEventNoClean implements Listener {

    private Main plugin;

    public VEventNoClean(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            Player p = e.getEntity().getKiller();
            plugin.log("NoClean", "Debug1");
            Configuration lang = plugin.getConfigUtils().getConfig(plugin, "Lang");
            if (plugin.getPlayerManager().isNoClean(p)) {
                Long rightnow = System.currentTimeMillis();
                Long lastPearl = plugin.getPlayerManager().getNoCleanCD().get(p.getUniqueId());
                Long timeLeft = (plugin.getTimerManager().getNcCooldown() - (rightnow - lastPearl));
                p.sendMessage(c(lang.getString("NoClean.now").replaceAll("%time%", plugin.getTimerManager().toSecMs(timeLeft))));
            } else {
                Long rightnaww = System.currentTimeMillis();
                plugin.getPlayerManager().getNoCleanCD().put(p.getUniqueId(), rightnaww);
            }
        }
    }




    @EventHandler
    public void PlayerLeft(PlayerQuitEvent e) {
        if (plugin.getPlayerManager().getNoCleanCD().get(e.getPlayer().getUniqueId()) != null) {
            plugin.getPlayerManager().getNoCleanCD().remove(e.getPlayer().getUniqueId());
        }
    }


    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
