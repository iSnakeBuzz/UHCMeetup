package com.isnakebuzz.meetup.n;

import com.isnakebuzz.meetup.a.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class VEventFireless implements Listener {

    private Main plugin;

    public VEventFireless(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void FireDamange(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getCause().equals(EntityDamageEvent.DamageCause.FIRE) || e.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK) || e.getCause().equals(EntityDamageEvent.DamageCause.LAVA)) {
                e.setCancelled(true);
            }
        }
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
