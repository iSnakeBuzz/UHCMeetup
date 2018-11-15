package com.isnakebuzz.meetup.EventsManager.Events;

import com.isnakebuzz.meetup.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventEnderCD implements Listener {

    private Main plugin;

    public EventEnderCD(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerUseEP(PlayerInteractEvent event) {
        if ((event.getAction() == Action.LEFT_CLICK_AIR) || (event.getAction() == Action.LEFT_CLICK_BLOCK) || (event.getItem() == null) || (event.getItem().getType() != Material.ENDER_PEARL)) {
            return;
        }

        Player p = event.getPlayer();
        if (plugin.getPlayerManager().isEnderCD(p)) {
            event.setCancelled(true);
            Long rightnow = System.currentTimeMillis();
            Long lastPearl = plugin.getPlayerManager().getEnderCDMap().get(p.getUniqueId());
            Long timeLeft = (plugin.getTimerManager().getEpcooldown() - (rightnow - lastPearl));
            p.sendMessage(ChatColor.RED + "Enderpearl cooldown remaining: " + plugin.getTimerManager().toSecMs(timeLeft) + " seconds.");
            p.updateInventory();
        } else {
            Long rightnaww = System.currentTimeMillis();
            plugin.getPlayerManager().getEnderCDMap().put(p.getUniqueId(), rightnaww);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getPlayerManager().getEnderCDMap().remove(event.getPlayer().getUniqueId());
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
