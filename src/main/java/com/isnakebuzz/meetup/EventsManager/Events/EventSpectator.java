package com.isnakebuzz.meetup.EventsManager.Events;

import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.Player.GamePlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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

public class EventSpectator implements Listener {

    private Main plugin;

    public EventSpectator(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void DamageToOther(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player damager = (Player) e.getDamager();
            Player victim = (Player) e.getEntity();

            GamePlayer spectator = plugin.getPlayerManager().getUuidGamePlayerMap().get(damager.getUniqueId());
            if (spectator.isSpectator()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void Interact(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        GamePlayer spectator = plugin.getPlayerManager().getUuidGamePlayerMap().get(player.getUniqueId());
        if (spectator.isSpectator()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void EntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            GamePlayer spectator = plugin.getPlayerManager().getUuidGamePlayerMap().get(player.getUniqueId());
            if (spectator.isSpectator()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void ItemDrop(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        GamePlayer spectator = plugin.getPlayerManager().getUuidGamePlayerMap().get(player.getUniqueId());
        if (spectator.isSpectator()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        GamePlayer spectator = plugin.getPlayerManager().getUuidGamePlayerMap().get(player.getUniqueId());
        if (spectator.isSpectator()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        GamePlayer spectator = plugin.getPlayerManager().getUuidGamePlayerMap().get(player.getUniqueId());
        if (spectator.isSpectator()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void InvDrag(InventoryDragEvent e) {
        Player player = (Player) e.getWhoClicked();
        GamePlayer spectator = plugin.getPlayerManager().getUuidGamePlayerMap().get(player.getUniqueId());
        if (spectator.isSpectator()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void FoodLevelChange(FoodLevelChangeEvent e) {
        Player player = (Player) e.getEntity();
        GamePlayer spectator = plugin.getPlayerManager().getUuidGamePlayerMap().get(player.getUniqueId());
        if (spectator.isSpectator()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void ItemPickUp(PlayerPickupItemEvent e) {
        Player player = e.getPlayer();
        GamePlayer spectator = plugin.getPlayerManager().getUuidGamePlayerMap().get(player.getUniqueId());
        if (spectator.isSpectator()) {
            e.setCancelled(true);
        }
    }


    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
