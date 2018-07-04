package com.isnakebuzz.meetup.c;

import com.isnakebuzz.meetup.a.*;
import com.isnakebuzz.meetup.b.*;
import com.isnakebuzz.meetup.e.*;
import com.isnakebuzz.meetup.k.Speed;

import static com.isnakebuzz.meetup.e.API.*;

import java.util.UUID;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;

public class PlayerListeners implements Listener{
    
	private final Main plugin;
    
    public PlayerListeners (Main plugin){
        this.plugin = plugin;
    }
    
    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        World w = Bukkit.getWorld(Main.world);
        World w2 = Bukkit.getWorld(plugin.spawn);
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
        	if (plugin.getConfig().getBoolean("WaitingLobby.enabled") == true) {
        		p.teleport(w2.getSpawnLocation());
        	}else if (plugin.getConfig().getBoolean("WaitingLobby.enabled") == false) {
        		p.teleport(w.getSpawnLocation());
        	}
            API.JoinClean(p);
            
            p.setAllowFlight(true);
            p.setFlying(true);
            p.getInventory().clear();
            p.setGameMode(GameMode.CREATIVE);
            Kits.newSpectator(p);
            
            if (Main.plugin.scoreboard.containsKey(p) && Main.plugin.scoreboard.get(p).getTask() != null) {
                Main.plugin.scoreboard.get(p).getTask().cancel();
            }
            ScoreboardManager.getManager().TablistLobby(p);
            
        }, 2);
        if (States.state == States.LOBBY){
            e.setJoinMessage(c(MessagesManager.JoinMessage
                    .replaceAll("%player%", e.getPlayer().getName())
                    .replaceAll("%online%", ""+Bukkit.getOnlinePlayers().size())
                    .replaceAll("%max%", ""+Main.plugin.getConfig().getInt("GameOptions.MaxPlayers"))
                    .replaceAll("%min%", ""+Main.plugin.getConfig().getInt("GameOptions.MinPlayers"))
            ));
            need--;
            CheckStart();
        }
        
        if (Bukkit.getOnlinePlayers().size() >= 2){
            for (Player all : Bukkit.getOnlinePlayers()){
                Kits.newSpectator(all);
            }
        }
    }
    
    @EventHandler
    public void PlayerLeave(PlayerQuitEvent e){
        API.ALivePs.remove(e.getPlayer());
        for (Player p : API.ALivePs){
            API.CheckWin(p);
        }
        if (States.state == States.LOBBY){
            need++;
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            if (Bukkit.getOnlinePlayers().size() <= 1){
                    for (Player all : Bukkit.getOnlinePlayers()){
                        Kits.newSpectator(all);
                    }
                }
            }, 10);
            e.setQuitMessage(c(MessagesManager.LeftMessage
                    .replaceAll("%player%", e.getPlayer().getName())
                    .replaceAll("%online%", ""+Bukkit.getOnlinePlayers().size())
                    .replaceAll("%max%", ""+Main.plugin.getConfig().getInt("GameOptions.MaxPlayers"))
                    .replaceAll("%min%", ""+Main.plugin.getConfig().getInt("GameOptions.MinPlayers"))
            ));
        }
    }
    
    @SuppressWarnings("unused")
	@EventHandler (priority = EventPriority.HIGHEST)
    public void PlayerDeath(PlayerDeathEvent e){
        Player p = e.getEntity().getPlayer();
        API.ALivePs.remove(p);
        
        Player k = null;
        UUID ku = null;
        
        if (API.MLG.contains(p)) {
        	API.MLG.remove(p);
        }
        
        if (e.getEntity().getKiller() != null) {
        	k = e.getEntity().getKiller();
            ku = k.getUniqueId();
            API.CheckWin(k);
            plugin.versionHandler.sendLighting(p, k);
        }
        UUID pu = p.getUniqueId();
        
        World w = Bukkit.getWorld(Main.world);
        World w2 = Bukkit.getWorld(plugin.spawn);
        
        if (p.getHealth() < 0.5){
            API.Specs.add(p);
            p.setHealth(p.getMaxHealth());
            p.getInventory().setArmorContents(null);
            p.getInventory().clear();
            p.setGameMode(GameMode.CREATIVE);
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                Kits.newSpectator(p);
            }, 10);
            
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            	if (plugin.getConfig().getBoolean("WaitingLobby.enabled") == true) {
            		p.teleport(w2.getSpawnLocation());
            	}else if (plugin.getConfig().getBoolean("WaitingLobby.enabled") == false) {
            		p.teleport(w.getSpawnLocation());
            	}
            }, 20);
            
            for (Player all : Bukkit.getOnlinePlayers()){
                all.hidePlayer(p);
            }
        }
    }
    
    @EventHandler
    public void PlayerReSpawn(PlayerRespawnEvent e) {
    	World w = Bukkit.getWorld(Main.world);
        World w2 = Bukkit.getWorld(plugin.spawn);
        
        Player p = e.getPlayer();
    	
        
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
        	if (plugin.getConfig().getBoolean("WaitingLobby.enabled") == true) {
        		p.teleport(w2.getSpawnLocation());
        	}else if (plugin.getConfig().getBoolean("WaitingLobby.enabled") == false) {
        		p.teleport(w.getSpawnLocation());
        	}
        }, 10);
    }
    
    @SuppressWarnings("unused")
	@EventHandler
    public void Menus(InventoryClickEvent e){
        if (API.KitMode.contains(e.getWhoClicked())){
            return;
        }
        if (States.state == States.LOBBY || API.Specs.contains((Player) e.getWhoClicked())){
            e.setCancelled(true);
            if (e.getInventory().getName() == null||e.getInventory() == null){
            	e.getWhoClicked().closeInventory();
                return;
            }
            Player p = (Player) e.getWhoClicked();
        }
    }
    
    @EventHandler
    public void Interact(PlayerInteractEvent e){
        if (States.state == States.STARTING || API.Specs.contains(e.getPlayer())){
            e.setCancelled(true);
        }
        if (States.state == States.LOBBY || API.Specs.contains(e.getPlayer())){
            e.setCancelled(true);
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
                if (e.getItem() == null){
                    return;
                }
                if (c(MessagesManager.PlayersItemName).equals(e.getItem().getItemMeta().getDisplayName())){
                    e.getPlayer().openInventory(API.getAlive());
                }
                if (c(MessagesManager.SpeedOptions).equals(e.getItem().getItemMeta().getDisplayName())){
                	new Speed(e.getPlayer()).o(e.getPlayer());
                }
                if (c(MessagesManager.LobbyItemName).equals(e.getItem().getItemMeta().getDisplayName())){
                    API.sendLobby(e.getPlayer());
                }
                if (c(MessagesManager.ArenaLobby).equals(e.getItem().getItemMeta().getDisplayName())){
                    API.sendLobby(e.getPlayer());
                }
                if (c(MessagesManager.VoteNameItem).equals(e.getItem().getItemMeta().getDisplayName()) && !Voted.contains(e.getPlayer()) && started == false){
                    Voted.add(e.getPlayer());
                    votos--;
                    API.CheckStartVote();
                }
            }
        }
    }
    
    @EventHandler
    public void NoDamage(EntityDamageByEntityEvent e){
        if (!(e.getEntity() instanceof Player)){
            return;
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE){
            return;
        }
        if (!API.Specs.contains((Player)e.getDamager()) || !API.ALivePs.contains((Player)e.getEntity())){
            return;
        }
        if (API.Specs.contains((Player)e.getDamager()) && API.ALivePs.contains((Player)e.getEntity())){
            e.setCancelled(true);
        }
        if (States.state == States.LOBBY || States.state == States.STARTING){
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void NoDamage2(EntityDamageEvent e){
         if (!(e.getEntity() instanceof Player)){
            return;
        }
        if (States.state == States.LOBBY || States.state == States.STARTING || API.Specs.contains((Player)e.getEntity())){
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void NoDamage2(EntityDamageByBlockEvent e){
        if (!(e.getEntity() instanceof Player)){
            return;
        }
        if (States.state == States.LOBBY || States.state == States.STARTING || API.Specs.contains((Player)e.getEntity())){
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void PlayerInteract(PlayerInteractEntityEvent e){
        if (States.state == States.LOBBY || States.state == States.STARTING || API.Specs.contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }
    
    public void init(){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
