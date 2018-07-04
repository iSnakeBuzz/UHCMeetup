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

    public GamePlayer(Main plugin, Player p, UUID uuid, Boolean spectator) {
        this.plugin = plugin;
        this.p = p;
        this.uuid = uuid;
        this.spectator = spectator;
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

        pig.setLocation(l.getX(), l.getY(), l.getZ(), 0, 0);
        pig.setInvisible(true);
        pig.setHealth(6);

        horse.setLocation(l.getX(), l.getY(), l.getZ(), 0, 0);
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
