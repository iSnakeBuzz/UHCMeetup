package com.isnakebuzz.meetup.h;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.worldborder.WorldFillerTaskCompleteEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockStoneFormEvent;
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
    public void CancelStone(BlockStoneFormEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        event.setCancelled(true);
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
