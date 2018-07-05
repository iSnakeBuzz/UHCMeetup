package com.isnakebuzz.meetup.d;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.isnakebuzz.meetup.a.Main;
import net.minecraft.server.v1_7_R4.*;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GamePlayer {

    private Main plugin;
    private Player p;
    private UUID uuid;
    private boolean spectator;
    private int wins = 0;
    private int kills = 0;
    private int deaths = 0;
    private int local_kills = 0;

    public GamePlayer(Main plugin, Player p, UUID uuid, Boolean spectator, int wins, int kills, int deaths) {
        this.plugin = plugin;
        this.p = p;
        this.uuid = uuid;
        this.spectator = spectator;

        this.wins = wins;
        this.deaths = deaths;
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void addKills(int number) {
        setKills(getKills() + number);
    }

    public void addDeaths(int number) {
        setDeaths(getDeaths() + number);
    }

    public void addWins(int number) {
        setWins(getWins() + number);
    }

    public int getLKills() {
        return local_kills;
    }

    public void setLKills(int local_kills) {
        this.local_kills = local_kills;
    }

    public void addLKills(int number) {
        setLKills(getLKills() + number);
    }

    public boolean isSpectator() {
        return spectator;
    }

    public void setSpectator(boolean spectator) {
        this.spectator = spectator;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Player getPlayer() {
        return p;
    }

    public void sit() {

        Location l = p.getLocation();
        EntityHorse horse = new EntityHorse(((CraftWorld) l.getWorld()).getHandle());

        EntityBat pig = new EntityBat(((CraftWorld) l.getWorld()).getHandle());

        pig.setLocation(l.getX(), l.getY() + 0.5, l.getZ(), 0, 0);
        pig.setInvisible(true);
        pig.setHealth(6);

        horse.setLocation(l.getX(), l.getY() + 0.5, l.getZ(), 0, 0);
        horse.setInvisible(true);

        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(pig);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

        plugin.getPlayerManager().getSitted().put(p, pig.getId());

        PacketPlayOutAttachEntity sit = new PacketPlayOutAttachEntity(0, ((CraftPlayer) p).getHandle(), pig);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(sit);
    }

    public void unsit() {
        if (plugin.getPlayerManager().getSitted().get(p) != null) {
            PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(plugin.getPlayerManager().getSitted().get(p));
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public void sendToLobby() {
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Settings");
        connect(config.getString("GameOptions.Lobby"));
    }

    public void connect(String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        p.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }

}
