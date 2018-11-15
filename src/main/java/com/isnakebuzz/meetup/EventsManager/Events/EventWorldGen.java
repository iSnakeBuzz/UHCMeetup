package com.isnakebuzz.meetup.EventsManager.Events;

import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.Utils.WorldBorder.WorldFillerTaskCompleteEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class EventWorldGen implements Listener {

    private Main plugin;

    public EventWorldGen(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWorldFinishGeneration(WorldFillerTaskCompleteEvent e) {
        plugin.getBorderManager().newBorderGenerator(6, e.getWorldName());
        plugin.getTeleportManager().loadLocations(e.getWorldName());
        Main.getStates.state = Main.getStates.LOBBY;
    }

    @EventHandler
    public void onChunkLoadEvent(ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            if (!(entity instanceof Player) && entity instanceof LivingEntity) {
                entity.remove();
            }
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        event.setCancelled(true);
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
