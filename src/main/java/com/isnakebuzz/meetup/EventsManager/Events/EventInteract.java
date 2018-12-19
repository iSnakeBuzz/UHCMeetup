package com.isnakebuzz.meetup.EventsManager.Events;

import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.Player.GamePlayer;
import com.isnakebuzz.meetup.Player.PlayerInventory;
import com.isnakebuzz.meetup.Utils.Connection;
import com.isnakebuzz.meetup.Inventory.Utils.ItemBuilder;
import com.isnakebuzz.meetup.Inventory.MenuManager.MenuCreator;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public class EventInteract implements Listener {

    private Main plugin;

    public EventInteract(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void InventoryInteract(InventoryClickEvent e) {
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Utils/Inventory");
        Set<String> key;
        try {
            key = config.getConfigurationSection("inventory").getKeys(false);
        } catch (Exception ex) {
            key = null;
        }
        if (key == null | key.size() < 1) return;
        Player p = (Player) e.getWhoClicked();
        for (String item : key) {
            String path = "inventory." + item + ".";
            String _item = config.getString(path + "item");
            String _name = config.getString(path + "name");
            List<String> _lore = config.getStringList(path + "lore");
            String _action = config.getString(path + "action");
            ItemStack itemStack = ItemBuilder.crearItem1(plugin, Integer.valueOf(_item.split(":")[0]), 1, Integer.valueOf(_item.split(":")[1]), _name, _lore);

            if (e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null) return;

            if (e.getCurrentItem().equals(itemStack)) {
                e.setCancelled(true);
                if (_action.split(":")[0].equalsIgnoreCase("menu")) {
                    e.setCancelled(true);
                    new MenuCreator(p, plugin, _action.split(":")[1]).o(p);
                } else if (_action.split(":")[0].equalsIgnoreCase("cmd")) {
                    e.setCancelled(true);
                    String cmd = "/" + _action.split(":")[1];
                    p.chat(cmd);
                } else if (_action.split(":")[0].equalsIgnoreCase("server")) {
                    e.setCancelled(true);
                    GamePlayer gamePlayer = plugin.getPlayerManager().getUuidGamePlayerMap().get(p.getUniqueId());
                    gamePlayer.connect(_action.split(":")[1]);
                } else if (_action.split(":")[0].equalsIgnoreCase("lobby")) {
                    e.setCancelled(true);
                    GamePlayer gamePlayer = plugin.getPlayerManager().getUuidGamePlayerMap().get(p.getUniqueId());
                    gamePlayer.sendToLobby();
                } else if (_action.split(":")[0].equalsIgnoreCase("kit")) {
                    if (Connection.Database.database.equals(Connection.Database.NONE)) {
                        p.sendMessage(c("&cDatabase is disabled."));
                        return;
                    }
                    PlayerInventory playerInventory = plugin.getPlayerManager().getUuidPlayerInventoryMap().get(p.getUniqueId());
                    p.getInventory().clear();
                    p.getInventory().setContents(playerInventory.getInventory());
                    p.updateInventory();
                    new MenuCreator(p, plugin, _action.split(":")[1]).o(p);
                }
            }
        }
    }

    @EventHandler
    public void InventoryInteract(PlayerInteractEvent e) {
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Utils/Inventory");
        Set<String> key;
        try {
            key = config.getConfigurationSection("inventory").getKeys(false);
        } catch (Exception ex) {
            key = null;
        }
        if (key == null | key.size() < 1) return;
        for (String item : key) {
            String path = "inventory." + item + ".";
            String _item = config.getString(path + "item");
            String _name = config.getString(path + "name");
            List<String> _lore = config.getStringList(path + "lore");
            String _action = config.getString(path + "action");
            ItemStack itemStack = ItemBuilder.crearItem1(plugin, Integer.valueOf(_item.split(":")[0]), 1, Integer.valueOf(_item.split(":")[1]), _name, _lore);

            if (e.getItem() == null || e.getItem().getItemMeta().equals(null)) return;

            if (e.getItem().equals(itemStack)) {
                Player p = e.getPlayer();
                e.setCancelled(true);
                if (_action.split(":")[0].equalsIgnoreCase("menu")) {
                    e.setCancelled(true);
                    new MenuCreator(e.getPlayer(), plugin, _action.split(":")[1]).o(e.getPlayer());
                } else if (_action.split(":")[0].equalsIgnoreCase("cmd")) {
                    e.setCancelled(true);
                    String cmd = "/" + _action.split(":")[1];
                    e.getPlayer().chat(cmd);
                } else if (_action.split(":")[0].equalsIgnoreCase("server")) {
                    e.setCancelled(true);
                    GamePlayer gamePlayer = plugin.getPlayerManager().getUuidGamePlayerMap().get(e.getPlayer().getUniqueId());
                    gamePlayer.connect(_action.split(":")[1]);
                } else if (_action.split(":")[0].equalsIgnoreCase("lobby")) {
                    e.setCancelled(true);
                    GamePlayer gamePlayer = plugin.getPlayerManager().getUuidGamePlayerMap().get(e.getPlayer().getUniqueId());
                    gamePlayer.sendToLobby();
                } else if (_action.split(":")[0].equalsIgnoreCase("kit")) {
                    if (Connection.Database.database.equals(Connection.Database.NONE)) {
                        p.sendMessage(c("&cDatabase is disabled."));
                        return;
                    }
                    PlayerInventory playerInventory = plugin.getPlayerManager().getUuidPlayerInventoryMap().get(p.getUniqueId());
                    p.getInventory().clear();
                    p.getInventory().setContents(playerInventory.getInventory());
                    p.updateInventory();
                    new MenuCreator(p, plugin, _action.split(":")[1]).o(p);
                }
            }
        }
    }

    @EventHandler
    public void InventoryDrag(InventoryDragEvent e) {
        e.setCancelled(true);
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
