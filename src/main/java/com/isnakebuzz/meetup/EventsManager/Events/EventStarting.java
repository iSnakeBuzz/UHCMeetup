package com.isnakebuzz.meetup.EventsManager.Events;

import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.Utils.Enums.GameStates;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class EventStarting implements Listener {

    private Main plugin;

    public EventStarting(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void InteractEvent(PlayerInteractEvent e) {
        if (plugin.getArenaManager().getGameStates() == GameStates.STARTING) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void DamageToOther(EntityDamageByEntityEvent e) {
        if (plugin.getArenaManager().getGameStates() == GameStates.STARTING) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void EntityDamage(EntityDamageEvent e) {
        if (plugin.getArenaManager().getGameStates() == GameStates.STARTING) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void ItemDrop(PlayerDropItemEvent e) {
        if (plugin.getArenaManager().getGameStates() == GameStates.STARTING) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockBreak(BlockBreakEvent e) {
        if (plugin.getArenaManager().getGameStates() == GameStates.STARTING) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockPlace(BlockPlaceEvent e) {
        if (plugin.getArenaManager().getGameStates() == GameStates.STARTING) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void InvDrag(InventoryDragEvent e) {
        if (plugin.getArenaManager().getGameStates() == GameStates.STARTING) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void FoodLevelChange(FoodLevelChangeEvent e) {
        if (plugin.getArenaManager().getGameStates() == GameStates.STARTING) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void ItemPickUp(PlayerPickupItemEvent e) {
        if (plugin.getArenaManager().getGameStates() == GameStates.STARTING) {
            e.setCancelled(true);
        }
    }


    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
