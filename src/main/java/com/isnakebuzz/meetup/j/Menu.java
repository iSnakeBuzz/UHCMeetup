package com.isnakebuzz.meetup.j;

import com.isnakebuzz.meetup.a.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public abstract class Menu implements Listener {

    Inventory _inv;
    Main plugin;
    private String _name;

    public Menu(Main plugin, String menu_name) {
        this._name = menu_name;
        this.plugin = plugin;
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Utils/MenuCreator");
        String path = "MenuCreator." + menu_name + ".";
        if (config.getString(path + "title") == null) return;
        this._inv = Bukkit.createInventory((InventoryHolder) null, 9 * config.getInt(path + "rows"), ChatColor.translateAlternateColorCodes('&', config.getString(path + "title")));
        plugin.getServer().getPluginManager().registerEvents((Listener) this, plugin);
    }

    public void a(final ItemStack itemStack) {
        this._inv.addItem(new ItemStack[]{itemStack});
    }

    public void s(final int n, final ItemStack itemStack) {
        this._inv.setItem(n, itemStack);
    }

    public Inventory i() {
        return this._inv;
    }

    public String n() {
        return this._inv.getName();
    }

    public void o(final Player player) {
        if (this._inv == null) {
            player.sendMessage("§4Menu error.. Wrong name.");
        }
        player.openInventory(this._inv);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent inventoryClickEvent) throws IOException {
        if (inventoryClickEvent.getInventory().equals(this.i()) && inventoryClickEvent.getCurrentItem() != null && this.i().contains(inventoryClickEvent.getCurrentItem()) && inventoryClickEvent.getWhoClicked() instanceof Player) {
            this.onClick((Player) inventoryClickEvent.getWhoClicked(), inventoryClickEvent.getCurrentItem(), _name);
            inventoryClickEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent inventoryCloseEvent) {
        if (inventoryCloseEvent.getInventory().equals(this.i()) && inventoryCloseEvent.getPlayer() instanceof Player) {
            this.onClose((Player) inventoryCloseEvent.getPlayer());
        }
    }

    public void onClose(final Player player) {
    }

    public abstract void onClick(final Player p0, final ItemStack p1, String _name) throws IOException;

}
