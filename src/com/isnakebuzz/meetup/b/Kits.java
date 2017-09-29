package com.isnakebuzz.meetup.b;

import com.isnakebuzz.meetup.a.Main;
import static com.isnakebuzz.meetup.e.API.c;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Kits {
    
    public static void RandomKit(Player p){
        Random r = new Random();
        if (Main.plugin.getConfig().getInt("Kits") == 0){
            LoadKit(p, 0);
        }else if (Main.plugin.getConfig().getInt("Kits") > 0){
            int kit = r.nextInt(Main.plugin.getConfig().getInt("Kits"));
            LoadKit(p, kit);
        }else{
            LoadKit(p, 0);
        }
    }
    
    public static void DeleteKit(Player p, int kitnumber){
        Kits.CheckIfFolderExist();
        File file = new File(Main.plugin.getDataFolder() + "//Kits//" +kitnumber+ ".yml");
        if(file.exists()){
            YamlConfiguration inv = YamlConfiguration.loadConfiguration(file);
            ItemStack[] contents = p.getInventory().getContents();
            List<?> list = inv.getList("inventory");
            for(int i = 0; i < list.size(); i++){
                contents[i] = (ItemStack) list.get(i);
            }

            ItemStack[] acontents = p.getInventory().getArmorContents();
            List<?> alist = inv.getList("armor");
            for(int i = 0; i < alist.size(); i++){
                acontents[i] = (ItemStack) alist.get(i);
            }
            file.delete();
        }
    }
    
    public static void SaveKit(Player p, int kitnumber){
        Kits.CheckIfFolderExist();
        ArrayList<ItemStack> list = new ArrayList<>();
        ArrayList<ItemStack> alist = new ArrayList<>();
        File file = new File(Main.plugin.getDataFolder() + "//Kits//" +kitnumber+ ".yml");
        if(!file.exists()){
            try { file.createNewFile(); } catch (IOException e) {}
            YamlConfiguration inv = YamlConfiguration.loadConfiguration(file);
            ItemStack[] contents = p.getInventory().getContents();
            for(int i = 0; i < contents.length; i++){
                ItemStack item = contents[i];
                if(!(item == null)){
                    list.add(item);
                }
            }
            ItemStack[] acontents = p.getInventory().getArmorContents();
            for(int i = 0; i < acontents.length; i++){
                ItemStack item = acontents[i];
                if(!(item == null)){
                    alist.add(item);
                }
            }
            inv.set("armor", alist);
            inv.set("inventory", list);
            try { inv.save(file); } catch (IOException e) { }
        }
    }
    
    public static void LoadKit(Player p, int kitnumber){
        File file = new File(Main.plugin.getDataFolder() + "//Kits//" +kitnumber+ ".yml");
        if(file.exists()){
            YamlConfiguration inv = YamlConfiguration.loadConfiguration(file);
            p.getInventory().clear();
            ItemStack[] contents = p.getInventory().getContents();
            List<?> list = inv.getList("inventory");
            for(int i = 0; i < list.size(); i++){
                contents[i] = (ItemStack) list.get(i);
            }

            ItemStack[] acontents = p.getInventory().getArmorContents();
            List<?> alist = inv.getList("armor");
            for(int i = 0; i < alist.size(); i++){
                acontents[i] = (ItemStack) alist.get(i);
            }
            p.getInventory().setContents(contents);
            p.getInventory().setArmorContents(acontents);
        }
    }
    
    public static void CheckIfFolderExist(){
        File file = new File(Main.plugin.getDataFolder() + "//Kits");
        if(!file.exists()){
            file.mkdir();
        }
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
