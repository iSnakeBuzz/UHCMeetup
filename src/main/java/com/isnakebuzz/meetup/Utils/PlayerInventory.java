package com.isnakebuzz.meetup.Utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlayerInventory {

    private UUID uuid;
    private Player p;
    private ItemStack[] inventory;

    public PlayerInventory(UUID uuid, Player p, ItemStack[] inventory) {
        this.uuid = uuid;
        this.p = p;
        this.inventory = inventory;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Player getPlayer() {
        return p;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    public void setInventory(ItemStack[] inventory) {
        this.inventory = inventory;
    }
}
