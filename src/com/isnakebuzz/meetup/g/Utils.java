package com.isnakebuzz.meetup.g;

import com.isnakebuzz.meetup.a.Main;
import org.bukkit.configuration.file.*;
import java.io.*;
import org.bukkit.potion.*;
import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import java.util.*;
import java.text.*;
import org.bukkit.entity.*;

public class Utils
{
  
    public static String translate(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    
    public static Main getInstance() {
        return Main.plugin;
    }
    
}
