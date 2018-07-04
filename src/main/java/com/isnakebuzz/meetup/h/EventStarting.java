package com.isnakebuzz.meetup.h;

import com.isnakebuzz.meetup.a.Main;
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
        if (Main.getStates.state == Main.getStates.STARTING) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void DamageToOther(EntityDamageByEntityEvent e) {
        if (Main.getStates.state == Main.getStates.STARTING) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void EntityDamage(EntityDamageEvent e) {
        if (Main.getStates.state == Main.getStates.STARTING) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void ItemDrop(PlayerDropItemEvent e) {
        if (Main.getStates.state == Main.getStates.STARTING) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockBreak(BlockBreakEvent e) {
        if (Main.getStates.state == Main.getStates.STARTING) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockPlace(BlockPlaceEvent e) {
        if (Main.getStates.state == Main.getStates.STARTING) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void InvDrag(InventoryDragEvent e) {
        if (Main.getStates.state == Main.getStates.STARTING) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void FoodLevelChange(FoodLevelChangeEvent e) {
        if (Main.getStates.state == Main.getStates.STARTING) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void ItemPickUp(PlayerPickupItemEvent e) {
        if (Main.getStates.state == Main.getStates.STARTING) {
            e.setCancelled(true);
        }
    }


    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
