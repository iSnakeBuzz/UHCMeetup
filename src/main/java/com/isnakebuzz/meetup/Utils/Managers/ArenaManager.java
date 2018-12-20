package com.isnakebuzz.meetup.Utils.Managers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.Utils.Enums.GameStates;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class ArenaManager {

    private Main plugin;
    private boolean started = false;
    private boolean ended = false;
    private GameStates gameStates;

    public ArenaManager(Main plugin) {
        this.plugin = plugin;
    }

    public GameStates getGameStates() {
        return gameStates;
    }

    public void setGameStates(GameStates gameStates) {
        this.gameStates = gameStates;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean checkStart() {
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Settings");
        if (!isStarted()) {
            if (Bukkit.getOnlinePlayers().size() >= config.getInt("GameOptions.MinPlayers")) {
                started = true;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean isWin() {
        if (!ended) {
            if (plugin.getPlayerManager().getPlayersAlive().size() <= 1) {
                this.ended = true;
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public void loadProtocol() {
        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.CHAT) {
                    public void onPacketSending(PacketEvent e) {
                        if (e.getPacketType() == PacketType.Play.Server.CHAT) {
                            PacketContainer packet = e.getPacket();
                            List<WrappedChatComponent> components = packet.getChatComponents().getValues();

                            for (WrappedChatComponent component : components) {
                                component.setJson(component.getJson().replace(e.getPlayer().getName(), ChatColor.GREEN + e.getPlayer().getName()));
                                packet.getChatComponents().write(components.indexOf(component), component);
                            }
                        }
                    }
                });
        plugin.log("Compatibility", "Hooked ProtocolLib");
    }

}
