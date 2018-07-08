package com.isnakebuzz.meetup.h;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.j.MenuCreator;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class EventCommand implements Listener {

    private Main plugin;

    public EventCommand(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void CommandMenuSecretInGame(final PlayerCommandPreprocessEvent e) {
        FileConfiguration config = plugin.getConfigUtils().getConfig(plugin, "Settings");
        Player p = e.getPlayer();
        String cmd = e.getMessage();
        String cmd_config = "/" + config.getString("GameOptions.Vote.cmd");
        if (cmd.equalsIgnoreCase(cmd_config)) {
            new MenuCreator(p, plugin, config.getString("GameOptions.Vote.menu")).o(p);
            e.setCancelled(true);
        }
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
