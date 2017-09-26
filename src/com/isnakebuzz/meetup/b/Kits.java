package com.isnakebuzz.meetup.b;

import com.isnakebuzz.meetup.a.Main;
import static com.isnakebuzz.meetup.e.API.c;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Kits {
    
    private static void Kit1(Player p){
        
        ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack leggs = new ItemStack(Material.IRON_LEGGINGS);
        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
        
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemStack fish = new ItemStack(Material.FISHING_ROD);
        ItemStack lava = new ItemStack(Material.LAVA_BUCKET);
        ItemStack bow = new ItemStack(Material.BOW);
        ItemStack gaps = new ItemStack(Material.GOLDEN_APPLE, 8);
        ItemStack ggaps = new ItemStack(Material.GOLDEN_APPLE, 3);
        ItemStack steak = new ItemStack(Material.COOKED_BEEF, 16);
        ItemStack water = new ItemStack(Material.WATER_BUCKET);
        ItemStack blocks = new ItemStack(Material.COBBLESTONE, 64);
        
        ItemStack arrow = new ItemStack(Material.ARROW, 64);
        ItemStack pico = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemStack anvil = new ItemStack(Material.ANVIL);
        ItemStack xp = new ItemStack(Material.EXP_BOTTLE, 24);
        
        ItemMeta hs = helmet.getItemMeta();
        ItemMeta cs = chestplate.getItemMeta();
        ItemMeta ls = leggs.getItemMeta();
        ItemMeta bs = boots.getItemMeta();
        
        ItemMeta sw = sword.getItemMeta();;
        ItemMeta bw = bow.getItemMeta();
        ItemMeta ggps = ggaps.getItemMeta();
        
        hs.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
        cs.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        ls.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
        bs.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 0, true);
        
        sw.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        bw.addEnchant(Enchantment.ARROW_DAMAGE, 3, true);
        
        ggps.setDisplayName("§6Golden Heads");
        
        helmet.setItemMeta(hs);
        chestplate.setItemMeta(cs);
        leggs.setItemMeta(ls);
        boots.setItemMeta(bs);
        
        sword.setItemMeta(sw);
        bow.setItemMeta(bw);
        ggaps.setItemMeta(ggps);
        
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        p.getInventory().setItem(0, sword);
        p.getInventory().setItem(1, fish);
        p.getInventory().setItem(2, lava);
        p.getInventory().setItem(3, bow);
        p.getInventory().setItem(4, gaps);
        p.getInventory().setItem(5, ggaps);
        p.getInventory().setItem(6, steak);
        p.getInventory().setItem(7, water);
        p.getInventory().setItem(8, blocks);
        
        p.getInventory().setItem(9, arrow);
        p.getInventory().setItem(10, pico);
        p.getInventory().setItem(11, anvil);
        p.getInventory().setItem(12, xp);
        
        p.getInventory().setHelmet(helmet);
        p.getInventory().setChestplate(chestplate);
        p.getInventory().setLeggings(leggs);
        p.getInventory().setBoots(boots);
        
    }
    
    private static void Kit2(Player p){
        
    }
    
    private static void Kit3(Player p){
        
    }
    
    private static void Kit4(Player p){
        
    }
    
    private static void Kit5(Player p){
        
    }
    
    private static void Kit6(Player p){
        
    }
    
    public static void RandomKit(Player p){
        Kit1(p);
    }
        
    
    public static void Spectador(Player p){
        
        ItemStack players = new ItemStack(Material.COMPASS);
        ItemStack settigns = new ItemStack(Material.WATCH);
        ItemStack leave = new ItemStack(Material.BED);
        
        ItemMeta pl = players.getItemMeta();
        ItemMeta st = settigns.getItemMeta();
        ItemMeta lv = leave.getItemMeta();
        
        pl.setDisplayName(c(Main.plugin.getConfig().getString("Items.Players")));
        
        st.setDisplayName(c(Main.plugin.getConfig().getString("Items.Options")));
        
        lv.setDisplayName(c(Main.plugin.getConfig().getString("Items.Hub")));
        
        players.setItemMeta(pl);
        settigns.setItemMeta(st);
        leave.setItemMeta(lv);
        
        p.getInventory().setItem(0, players);
        p.getInventory().setItem(4, settigns);
        p.getInventory().setItem(8, leave);
        
        if (Bukkit.getOnlinePlayers().size() >= 2 && States.state == States.LOBBY){
            ItemStack start = new ItemStack(Material.PAPER);
            ItemMeta srt = start.getItemMeta();
            srt.setDisplayName(c(Main.plugin.getConfig().getString("Items.Vote")));
            start.setItemMeta(srt);
            p.getInventory().setItem(1, start);
        }
    }
}
