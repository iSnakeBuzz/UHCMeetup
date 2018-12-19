package com.isnakebuzz.meetup.EventsManager.Events;

import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.Player.GamePlayer;
import com.isnakebuzz.meetup.Utils.ScoreBoard.ScoreBoardAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.projectiles.ProjectileSource;

public class EventDeath implements Listener {

    private Main plugin;

    public EventDeath(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerDeathEvent(PlayerDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = e.getEntity().getPlayer();
            GamePlayer gamePlayer = plugin.getPlayerManager().getUuidGamePlayerMap().get(p.getUniqueId());
            plugin.getPlayerManager().spectator(gamePlayer, true);
            if (plugin.getPlayerManager().getPlayersAlive().contains(p)) {
                plugin.getPlayerManager().getPlayersAlive().remove(p);
            }
            plugin.getScoreBoardAPI().setScoreBoard(p, ScoreBoardAPI.ScoreboardType.SPECTATOR, true, false, true);
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.hidePlayer(p);
            }
            gamePlayer.addDeaths(1);
            if (e.getEntity().getKiller() != null) {
                Player killer = e.getEntity().getKiller();
                GamePlayer gamePlayerk = plugin.getPlayerManager().getUuidGamePlayerMap().get(killer.getUniqueId());
                gamePlayerk.addLKills(1);
                gamePlayerk.addKills(1);
            }
        }

        this.DeathMessages(e);
    }

    @EventHandler
    public void PlayerRespawnEvent(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        p.setGameMode(GameMode.CREATIVE);
        p.setFoodLevel(40);
        p.setHealth(p.getMaxHealth());
        e.setRespawnLocation(plugin.getLobbyManager().getWorldLobby());
    }

    private void DeathMessages(PlayerDeathEvent e) {
        Player deathPlayer = e.getEntity();
        EntityDamageEvent damageEvent = deathPlayer.getLastDamageCause();

        EntityDamageEvent.DamageCause damageCause = damageEvent.getCause();
        Entity killer = null;

        if (damageEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) damageEvent;
            if (entityDamageByEntityEvent.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) entityDamageByEntityEvent.getDamager();
                ProjectileSource source = arrow.getShooter();
                if (source instanceof Player) {
                    killer = (Entity) source;
                }
            } else {
                killer = entityDamageByEntityEvent.getDamager();
            }
        }
        Configuration lang = plugin.getConfig("Lang");
        e.setDeathMessage(c(lang.getString("DeathMessages." + getFormatMessage(deathPlayer, killer, damageCause))
                .replaceAll("%player%", deathPlayer.getName())
                .replaceAll("%killer%", checkKiller(killer))
                .replaceAll("%blocks%", String.valueOf(checkDistance(deathPlayer, killer)))
                .replaceAll("%p_kill_count%", String.valueOf(checkKills(deathPlayer)))
                .replaceAll("%k_kill_count%", String.valueOf(checkKills((Player) killer)))
        ));
    }

    private String getFormatMessage(Player player, Entity killer, EntityDamageEvent.DamageCause damageCause) {
        if (killer instanceof Player) {
            if (killer.getName().equals(player.getName())) {
                return "Death";
            }
            if (damageCause.equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
                return "Normal";
            } else if (damageCause.equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
                if (((EntityDamageByEntityEvent) player.getLastDamageCause()).getDamager() instanceof Arrow) {
                    return "Bow";
                } else if (((EntityDamageByEntityEvent) player.getLastDamageCause()).getDamager() instanceof EnderPearl) {
                    return "EnderPearl";
                }
            } else if (damageCause.equals(EntityDamageEvent.DamageCause.FIRE)) {
                return "Fire";
            } else if (damageCause.equals(EntityDamageEvent.DamageCause.LAVA)) {
                return "Lava";
            } else if (damageCause.equals(EntityDamageEvent.DamageCause.FALL)) {
                return "Fall";
            }
        }
        return "False";
    }

    private int checkDistance(Player player, Entity entity) {
        if (entity != null) {
            return (int) player.getLocation().distance(entity.getLocation());
        } else {
            return 0;
        }
    }

    private String checkKiller(Entity killer) {
        if (killer instanceof Player) {
            return killer.getName();
        } else if (killer != null) {
            return killer.getName();
        } else {
            return "none";
        }
    }

    private int checkKills(Player p) {
        if (p == null) {
            return 0;
        }
        GamePlayer gamePlayer = plugin.getPlayerManager().getUuidGamePlayerMap().get(p.getUniqueId());
        return gamePlayer.getLKills();
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
