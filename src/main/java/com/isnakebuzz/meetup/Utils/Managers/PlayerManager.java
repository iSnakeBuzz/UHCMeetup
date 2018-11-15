package com.isnakebuzz.meetup.Utils.Managers;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.Utils.GamePlayer;
import com.isnakebuzz.meetup.Utils.PlayerInventory;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerManager {

    // Vote Events
    private static Map<UUID, Long> epearlcd;
    private static Map<UUID, Long> nocleancd;

    //End VoteEvents Utils


    private Main plugin;
    private Collection<Player> players_alive;
    private Map<UUID, GamePlayer> uuidGamePlayerMap;
    private Map<UUID, PlayerInventory> uuidPlayerInventoryMap;

    public PlayerManager(Main plugin) {
        this.plugin = plugin;
        this.epearlcd = new HashMap<>();
        this.nocleancd = new HashMap<>();
        this.uuidPlayerInventoryMap = new HashMap<>();
        this.uuidGamePlayerMap = new HashMap<>();
        this.players_alive = new ArrayList<>();
    }

    public Map<UUID, Long> getEnderCDMap() {
        return epearlcd;
    }

    public Map<UUID, Long> getNoCleanCD() {
        return nocleancd;
    }

    public void spectator(GamePlayer gamePlayer, boolean spect) {
        if (!gamePlayer.isSpectator()) {
            gamePlayer.setSpectator(spect);
            if (Main.getStates.state != Main.getStates.LOBBY) {
                plugin.getInvManager().loadInventorySpect(gamePlayer.getPlayer());
            } else {
                plugin.getInvManager().loadInventory(gamePlayer.getPlayer());
            }
        } else {
            gamePlayer.setSpectator(spect);
            gamePlayer.getPlayer().getInventory().clear();
        }
    }

    public Collection<Player> getPlayersAlive() {
        return players_alive;
    }

    public boolean isEnderCD(Player p) {
        Long rightnow = System.currentTimeMillis();
        Long lastPearl = getEnderCDMap().get(p.getUniqueId());
        if ((lastPearl == null) || (rightnow - lastPearl >= plugin.getTimerManager().getEpcooldown())) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isNoClean(Player p) {
        Long rightnow = System.currentTimeMillis();
        Long lastPearl = getNoCleanCD().get(p.getUniqueId());
        if ((lastPearl == null) || (rightnow - lastPearl >= plugin.getTimerManager().getNcCooldown())) {
            return false;
        } else {
            return true;
        }
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

    public Map<UUID, GamePlayer> getUuidGamePlayerMap() {
        return uuidGamePlayerMap;
    }

    public Map<UUID, PlayerInventory> getUuidPlayerInventoryMap() {
        return uuidPlayerInventoryMap;
    }
}
