package com.isnakebuzz.meetup.Inventory;

import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.Utils.PlayerInventory;
import com.isnakebuzz.meetup.Utils.Connection;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.List;
import java.util.Random;

public class AutoKits {

    private Main plugin;

    public AutoKits(Main plugin) {
        this.plugin = plugin;
    }

    private ItemStack[] getRandomKit(Random random) {
        //Random Material
        Material[] boots_random = {Material.DIAMOND_BOOTS, Material.IRON_BOOTS};
        Material[] leggings_random = {Material.DIAMOND_LEGGINGS, Material.IRON_LEGGINGS};
        Material[] chest_random = {Material.DIAMOND_CHESTPLATE, Material.IRON_CHESTPLATE};
        Material[] helmet_random = {Material.DIAMOND_HELMET, Material.IRON_HELMET};

        //Items
        ItemStack boots = new ItemStack(boots_random[random.nextInt(boots_random.length)], 1);
        ItemStack leggings = new ItemStack(leggings_random[random.nextInt(leggings_random.length)], 1);
        ItemStack chestplate = new ItemStack(chest_random[random.nextInt(chest_random.length)], 1);
        ItemStack helmet = new ItemStack(helmet_random[random.nextInt(helmet_random.length)], 1);

        ItemMeta bootsItemMeta = boots.getItemMeta();
        ItemMeta leggingsItemMeta = leggings.getItemMeta();
        ItemMeta chestItemMeta = chestplate.getItemMeta();
        ItemMeta helmetItemMeta = helmet.getItemMeta();

        //Random Enchants
        Enchantment[] enchantments_d = {Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.DURABILITY, Enchantment.DURABILITY};
        Enchantment[] enchantments_i = {Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.DURABILITY};
        Integer[] level1 = {1, 2, 3};
        Integer[] level2 = {1, 2};
        Integer[] level3 = {1, 2, 3};

        int porcentage_iron = 1;
        int porcentage_diamond = 3;

        if (boots.getType().equals(Material.DIAMOND_BOOTS)) {
            switch (random.nextInt(porcentage_diamond)) {
                case 0:
                    Enchantment enchantment = enchantments_d[random.nextInt(enchantments_d.length)];
                    if (enchantment.equals(enchantments_d[0])) {
                        bootsItemMeta.addEnchant(enchantment, level2[random.nextInt(level2.length)], true);
                    } else {
                        bootsItemMeta.addEnchant(enchantment, level3[random.nextInt(level3.length)], true);
                    }
                    break;
                case 1:
                    break;
            }
        } else {
            switch (random.nextInt(porcentage_iron)) {
                case 0:
                    Enchantment enchantment = enchantments_i[random.nextInt(enchantments_i.length)];
                    if (enchantment.equals(enchantments_i[0])) {
                        bootsItemMeta.addEnchant(enchantment, level1[random.nextInt(level1.length)], true);
                    } else {
                        bootsItemMeta.addEnchant(enchantment, level3[random.nextInt(level3.length)], true);
                    }
                    break;
            }
        }

        if (boots.getType().equals(Material.DIAMOND_LEGGINGS)) {
            switch (random.nextInt(porcentage_diamond)) {
                case 0:
                    Enchantment enchantment = enchantments_d[random.nextInt(enchantments_d.length)];
                    if (enchantment.equals(enchantments_d[0])) {
                        leggingsItemMeta.addEnchant(enchantment, level2[random.nextInt(level2.length)], true);
                    } else {
                        leggingsItemMeta.addEnchant(enchantment, level3[random.nextInt(level3.length)], true);
                    }
                    break;
                case 1:
                    break;
            }
        } else {
            switch (random.nextInt(porcentage_iron)) {
                case 0:
                    Enchantment enchantment = enchantments_i[random.nextInt(enchantments_i.length)];
                    if (enchantment.equals(enchantments_i[0])) {
                        leggingsItemMeta.addEnchant(enchantment, level1[random.nextInt(level1.length)], true);
                    } else {
                        leggingsItemMeta.addEnchant(enchantment, level3[random.nextInt(level3.length)], true);
                    }
                    break;
            }
        }

        if (boots.getType().equals(Material.DIAMOND_CHESTPLATE)) {
            switch (random.nextInt(porcentage_diamond)) {
                case 0:
                    Enchantment enchantment = enchantments_d[random.nextInt(enchantments_d.length)];
                    if (enchantment.equals(enchantments_d[0])) {
                        chestItemMeta.addEnchant(enchantment, level2[random.nextInt(level2.length)], true);
                    } else {
                        chestItemMeta.addEnchant(enchantment, level3[random.nextInt(level3.length)], true);
                    }
                    break;
                case 1:
                    break;
            }
        } else {
            switch (random.nextInt(porcentage_iron)) {
                case 0:
                    Enchantment enchantment = enchantments_i[random.nextInt(enchantments_i.length)];
                    if (enchantment.equals(enchantments_i[0])) {
                        chestItemMeta.addEnchant(enchantment, level1[random.nextInt(level1.length)], true);
                    } else {
                        chestItemMeta.addEnchant(enchantment, level3[random.nextInt(level3.length)], true);
                    }
                    break;
            }
        }

        if (boots.getType().equals(Material.DIAMOND_HELMET)) {
            switch (random.nextInt(porcentage_diamond)) {
                case 0:
                    Enchantment enchantment = enchantments_d[random.nextInt(enchantments_d.length)];
                    if (enchantment.equals(enchantments_d[0])) {
                        helmetItemMeta.addEnchant(enchantment, level2[random.nextInt(level2.length)], true);
                    } else {
                        helmetItemMeta.addEnchant(enchantment, level3[random.nextInt(level3.length)], true);
                    }
                    break;
                case 1:
                    break;
            }
        } else {
            switch (random.nextInt(porcentage_iron)) {
                case 0:
                    Enchantment enchantment = enchantments_i[random.nextInt(enchantments_i.length)];
                    if (enchantment.equals(enchantments_i[0])) {
                        helmetItemMeta.addEnchant(enchantment, level1[random.nextInt(level1.length)], true);
                    } else {
                        helmetItemMeta.addEnchant(enchantment, level3[random.nextInt(level3.length)], true);
                    }
                    break;
            }
        }

        //Adding meta
        boots.setItemMeta(bootsItemMeta);
        leggings.setItemMeta(leggingsItemMeta);
        chestplate.setItemMeta(chestItemMeta);
        helmet.setItemMeta(helmetItemMeta);

        //Collection
        ItemStack[] itemStacks = {boots, leggings, chestplate, helmet};
        if (!isValidKit(itemStacks)) {
            return this.getRandomKit(new Random());
        }
        return itemStacks;
    }

    private boolean isValidKit(ItemStack[] invs) {
        int diam = 0;
        int iron = 0;
        for (ItemStack i : invs) {
            if (i.getType().equals(Material.DIAMOND_HELMET) || i.getType().equals(Material.DIAMOND_CHESTPLATE)
                    || i.getType().equals(Material.DIAMOND_LEGGINGS) || i.getType().equals(Material.DIAMOND_BOOTS)) {
                diam++;
            }
            if (i.getType().equals(Material.IRON_HELMET) || i.getType().equals(Material.IRON_CHESTPLATE)
                    || i.getType().equals(Material.IRON_LEGGINGS) || i.getType().equals(Material.IRON_BOOTS)) {
                iron++;
            }
        }
        if (diam == iron) {
            return true;
        } else {
            return false;
        }
    }

    public void setUpKit(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(getRandomKit(new Random()));
        File file = new File(plugin.getDataFolder() + File.separator + "Kits" + File.separator + "autokit.yml");
        if (file.exists()) {
            YamlConfiguration inv = YamlConfiguration.loadConfiguration(file);
            ItemStack[] inv_contents;
            if (Connection.Database.database.equals(Connection.Database.NONE)) {
                inv_contents = (ItemStack[]) ((List) inv.get("inventory")).toArray(new ItemStack[0]);
            } else {
                PlayerInventory playerInventory = plugin.getPlayerManager().getUuidPlayerInventoryMap().get(p.getUniqueId());
                inv_contents = playerInventory.getInventory();
            }
            p.getInventory().setContents(inv_contents);
        }

        p.updateInventory();
    }

    public ItemStack[] getInventory() {
        File file = new File(plugin.getDataFolder() + File.separator + "Kits" + File.separator + "autokit.yml");
        if (file.exists()) {
            YamlConfiguration inv = YamlConfiguration.loadConfiguration(file);
            ItemStack[] inv_contents = (ItemStack[]) ((List) inv.get("inventory")).toArray(new ItemStack[0]);
            return inv_contents;
        }
        return null;
    }

    public ItemStack[] getPlayerInventory(Player p) {
        return p.getInventory().getContents();
    }

    private void CheckIfFolderExist() {
        File file = new File(plugin.getDataFolder() + "/Kits");
        if (!file.exists()) {
            file.mkdir();
        }
    }

}
