package com.isnakebuzz.meetup.b;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigUtils {
	
	public void saveConfig(Plugin plugin, String configname) {
		File pluginDir = plugin.getDataFolder();
		File configFile = new File(pluginDir, configname+".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
		
		try{
			config.save(configFile);
		}catch (IOException ex){ex.printStackTrace();}
	}
	
	public FileConfiguration getConfig(Plugin plugin, String configname) {
		
		File pluginDir = plugin.getDataFolder();
		File configFile = new File(pluginDir, configname+".yml");
		final FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
				
		return config;
	}
	
	public File getFile(Plugin plugin, String configname) {
		File pluginDir = plugin.getDataFolder();
		File configFile = new File(pluginDir, configname+".yml");
		return configFile;
	}
	
}
