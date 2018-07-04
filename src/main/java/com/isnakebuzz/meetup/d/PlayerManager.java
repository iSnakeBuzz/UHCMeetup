package com.isnakebuzz.meetup.d;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.isnakebuzz.meetup.a.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerManager {

    private Main plugin;
    private Collection<Player> players_alive;
    private Map<UUID, GamePlayer> uuidGamePlayerMap;
    private HashMap<Player, Integer> sitted = new HashMap<>();

    public PlayerManager(Main plugin) {
        this.plugin = plugin;
        this.uuidGamePlayerMap = new HashMap<>();
        this.players_alive = new ArrayList<>();
    }

    public void spectator(GamePlayer gamePlayer, boolean spect) {
        if (!gamePlayer.isSpectator()) {
            gamePlayer.setSpectator(spect);
            plugin.getInvManager().loadInventory(gamePlayer.getPlayer());
        } else {
            gamePlayer.setSpectator(spect);
            gamePlayer.getPlayer().getInventory().clear();
        }
    }

    public Collection<Player> getPlayersAlive() {
        return players_alive;
    }

    public void sendToLobby() {
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Settings");
        for (Player p : Bukkit.getOnlinePlayers()) {
            connect(p, config.getString("GameOptions.Lobby"));
        }
    }

    private void connect(Player p, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        p.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }

    public HashMap<Player, Integer> getSitted() {
        return sitted;
    }

    public Map<UUID, GamePlayer> getUuidGamePlayerMap() {
        return uuidGamePlayerMap;
    }
}
