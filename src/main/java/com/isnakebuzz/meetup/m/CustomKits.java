package com.isnakebuzz.meetup.m;

import com.isnakebuzz.meetup.a.Main;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.regex.Pattern;

public class CustomKits {

    private Main plugin;

    public CustomKits(Main plugin) {
        this.plugin = plugin;
    }

    private String getRandomKit() {
        File dir = new File(plugin.getDataFolder() + "/Kits/");
        File[] files = dir.listFiles();
        SecureRandom rand = new SecureRandom();
        File file = files[rand.nextInt(files.length)];

        String kit = file.getName().split(Pattern.quote("."))[0];

        return kit;
    }

    public void setUpKit(Player p) {
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Settings");
        if (config.getBoolean("GameOptions.CustomKit")) {
            p.getInventory().clear();
            String kit = getRandomKit();
            loadKit(p, kit);
            p.updateInventory();
        } else {
            plugin.getAutoKits().setUpKit(p);
        }
    }

    public boolean deleteKit(Player p, String kitname) {
        CheckIfFolderExist();
        File file = new File(plugin.getDataFolder() + "/Kits/" + kitname + ".yml");
        if (file.exists()) {
            file.delete();
            return true;
        } else {
            return false;
        }
    }

    public boolean saveKit(Player p, String kitname) {
        CheckIfFolderExist();
        File file = new File(plugin.getDataFolder() + "/Kits/" + kitname + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
            }
            YamlConfiguration inv = YamlConfiguration.loadConfiguration(file);
            inv.set("armor", (Object) p.getInventory().getArmorContents());
            inv.set("inventory", (Object) p.getInventory().getContents());
            try {
                inv.save(file);
            } catch (IOException e) {
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean loadKit(Player p, String kitname) {
        File file = new File(plugin.getDataFolder() + "/Kits/" + kitname + ".yml");
        if (file.exists()) {
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);

            YamlConfiguration inv = YamlConfiguration.loadConfiguration(file);

            ItemStack[] armor_contents = (ItemStack[]) ((List) inv.get("armor")).toArray(new ItemStack[0]);
            ItemStack[] inv_contents = (ItemStack[]) ((List) inv.get("inventory")).toArray(new ItemStack[0]);

            p.getInventory().setArmorContents(armor_contents);
            p.getInventory().setContents(inv_contents);
            return true;
        } else {
            return false;
        }
    }

    private void CheckIfFolderExist() {
        File file = new File(plugin.getDataFolder() + "/Kits");
        if (!file.exists()) {
            file.mkdir();
        }
    }

}
