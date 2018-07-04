package com.isnakebuzz.meetup.h;

import com.isnakebuzz.meetup.a.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EventHealth implements Listener {

    private Main plugin;

    public EventHealth(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRegainHealth(EntityRegainHealthEvent event) {
        if (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED || event.getRegainReason() == EntityRegainHealthEvent.RegainReason.REGEN) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void Consume(final PlayerItemConsumeEvent e) {
        final Player p = e.getPlayer();
        if (e.getItem() == null){
            return;
        } else if (e.getItem().getItemMeta().getDisplayName() == null){
            return;
        }
        final ItemMeta pm = e.getItem().getItemMeta();
        if (pm.getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Golden Heads")) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 3400, 0));
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
        }
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
