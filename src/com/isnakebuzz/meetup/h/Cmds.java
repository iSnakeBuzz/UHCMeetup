package com.isnakebuzz.meetup.h;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.b.Kits;
import com.isnakebuzz.meetup.e.API;
import java.io.IOException;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmds implements CommandExecutor{
    private final Main plugin;

    public Cmds(Main plugin) {
        this.plugin = plugin;
    }

    public void init() {
        plugin.getCommand("meetup").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        if (!(sender instanceof Player)){
            return true;
        }
       
        Player p = (Player) sender;
        
        if (!p.hasPermission("meetup.admin")){
            return true;
        }
        
        if (cmd.getName().equalsIgnoreCase("meetup")){
            if (args.length < 1){
                p.sendMessage(c("&e/meetup kitmode | This options is for adds new kits"));
                p.sendMessage(c("&e/meetup addkit | Add new kit"));
                p.sendMessage(c("&e/meetup removekit | Remove latest kit created"));
                p.sendMessage(c("&e/reroll |Reroll player, give new kit. (Recomended for V.I.P.S)"));
                return true;
            } else {
                switch (args[0].toLowerCase()) {
                    case "kitmode":
                        if (API.KitMode.contains(p)){
                            API.KitMode.remove(p);
                            p.sendMessage(c("&eKitMode &cdisabled"));
                        }else{
                            API.KitMode.add(p);
                            p.sendMessage(c("&eKitMode &aenabled"));
                        }
                        break;
                    case "addkit":
                        int kit = Main.plugin.getConfig().getInt("Kits");
                        Kits.SaveKit(p, kit);
                        p.sendMessage(c("&6Kit number &a" + kit + "&6 saved"));
                        kit++;
                        Main.plugin.getConfig().set("Kits", kit);
                        Main.plugin.saveConfig();
                        break;
                    case "removekit":
                        int kit2 = Main.plugin.getConfig().getInt("Kits");
                        kit2--;
                        Kits.DeleteKit(p, kit2);
                        Main.plugin.getConfig().set("Kits", kit2);
                        Main.plugin.saveConfig();
                        p.sendMessage(c("&cKit number &e" + kit2 + "&c deleted"));
                        break;
                }
            }
        }
        
        return false;
    }
    
    private String c(String c){
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
