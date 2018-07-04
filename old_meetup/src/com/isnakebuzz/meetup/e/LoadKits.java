package com.isnakebuzz.meetup.e;

import com.isnakebuzz.meetup.b.Kits;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class LoadKits {
    private static LoadKits instance;
    private File pluginDir;
    private File configFile;
    private FileConfiguration config;

    public static LoadKits get() {
        if (instance == null) {
            instance = new LoadKits();
        }
        return instance;
    }

    public void a(Plugin p) {
        this.pluginDir = p.getDataFolder();
        Kits.CheckIfFolderExist();
        this.configFile = new File(this.pluginDir + "//Kits//", "0.yml");
        if (!this.configFile.exists()) {
            p.saveResource("0.yml", true);
            File a = new File(this.pluginDir + "//0.yml");
            a.renameTo(new File(this.pluginDir + "//Kits//" + a.getName()));
            a.delete();
        }
        this.config = YamlConfiguration.loadConfiguration(this.configFile);
    }

    public File getConfigFile() {
        return this.configFile;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public void saveConfig() {
        try {
            this.config.save(this.configFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}