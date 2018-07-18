package com.isnakebuzz.meetup.g;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.i.ItemBuilder;
import net.minecraft.server.v1_7_R4.BiomeBase;
import org.bukkit.configuration.Configuration;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

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
