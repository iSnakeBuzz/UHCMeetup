package com.isnakebuzz.meetup.Utils.WorldUtils;

import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.Inventory.Utils.ItemBuilder;
import org.bukkit.configuration.Configuration;
import org.bukkit.inventory.ItemStack;

public class WorldUitls {

    private Main plugin;

    public WorldUitls(Main plugin) {
        this.plugin = plugin;
    }

    public ItemStack goldenHead(int number) {
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Settings");
        return ItemBuilder.crearItem(322, number, 0, config.getString("GameOptions.GAppleName"));
    }

}
