package com.isnakebuzz.meetup.k;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.isnakebuzz.meetup.a.Main;

public abstract class Menu implements Listener{

	Inventory _inv;
    
    public Menu(final String s, final int n) {
    	this._inv = Bukkit.createInventory((InventoryHolder)null, 9 * n, ChatColor.translateAlternateColorCodes('&', s));
    	Main.plugin.getServer().getPluginManager().registerEvents((Listener)this, Main.plugin);
    }
    
    public void a(final ItemStack itemStack) {
        this._inv.addItem(new ItemStack[] { itemStack });
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
        player.openInventory(this._inv);
    }
    
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getInventory().equals(this.i()) && inventoryClickEvent.getCurrentItem() != null && this.i().contains(inventoryClickEvent.getCurrentItem()) && inventoryClickEvent.getWhoClicked() instanceof Player) {
            this.onClick((Player)inventoryClickEvent.getWhoClicked(), inventoryClickEvent.getCurrentItem());
            inventoryClickEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent inventoryCloseEvent) {
        if (inventoryCloseEvent.getInventory().equals(this.i()) && inventoryCloseEvent.getPlayer() instanceof Player) {
            this.onClose((Player)inventoryCloseEvent.getPlayer());
        }
    }
    
    public void onClose(final Player player) {
    }
    
    public abstract void onClick(final Player p0, final ItemStack p1);
	
}
