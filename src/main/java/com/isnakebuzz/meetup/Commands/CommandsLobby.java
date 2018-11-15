package com.isnakebuzz.meetup.Commands;

import com.isnakebuzz.meetup.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class CommandsLobby implements CommandExecutor {

    private Main plugin;

    public CommandsLobby(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("uhcmeetup.admin") || !(sender instanceof Player)) {
            sender.sendMessage(c("&cYou don't have permissions."));
            return false;
        }

        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("lb")) {
            if (args.length < 1) {
                sendLobbyCommands(p);
                return true;
            }

            if (args[0].equalsIgnoreCase("lobby")) {
                p.sendMessage(c("&EventsManager➠&Managers You has been teleported to spawn"));
                p.teleport(plugin.getLobbyManager().getLobby());
            } else if (args[0].equalsIgnoreCase("addLobby")) {
                try {
                    plugin.getLobbyManager().addSpawnLocation(p);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (args[0].equalsIgnoreCase("removeLobby")) {
                try {
                    plugin.getLobbyManager().removeSpawnLocation(p);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                sendLobbyCommands(p);
                return true;
            }
        }

        return false;
    }

    public void sendLobbyCommands(Player p) {
        p.sendMessage(c("&3&Inventory  [               &6 UHCMeetupV4 &7[&a" + plugin.getDescription().getVersion() + "&7] &3&Inventory               ]  &ScoreBoard"));
        p.sendMessage(c("&a►►&6 /lb lobby &EventsManager➠&Managers Go to Lobby."));
        p.sendMessage(c("&a►►&6 /lb addLobby &EventsManager➠&Managers Add Lobbies locations."));
        p.sendMessage(c("&a►►&6 /lb removeLobby &EventsManager➠&Managers Remove latest Lobby location."));
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
