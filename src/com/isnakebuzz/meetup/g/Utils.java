package com.isnakebuzz.meetup.g;

import com.isnakebuzz.meetup.a.Main;
import org.bukkit.*;

public class Utils
{
  
    public static String translate(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    
    public static Main getInstance() {
        return Main.plugin;
    }
    
}
