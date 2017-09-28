package com.isnakebuzz.meetup.c;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.b.Kits;
import com.isnakebuzz.meetup.b.States;
import com.isnakebuzz.meetup.d.Border;
import com.isnakebuzz.meetup.e.API;
import static com.isnakebuzz.meetup.e.API.Voted;
import static com.isnakebuzz.meetup.e.API.c;
import static com.isnakebuzz.meetup.e.API.need;
import static com.isnakebuzz.meetup.e.API.started;
import static com.isnakebuzz.meetup.e.API.votos;
import com.isnakebuzz.meetup.g.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;

public class PlayerListeners implements Listener{
    private final Main plugin;
    
    public PlayerListeners (Main plugin){
        this.plugin = plugin;
    }
    
    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        World w = Bukkit.getWorld(Main.world);
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            p.teleport(w.getSpawnLocation());
            if (ProtocolSupportAPI.getProtocolVersion(p) == ProtocolVersion.MINECRAFT_1_8 || ProtocolSupportAPI.getProtocolVersion(p) == ProtocolVersion.MINECRAFT_FUTURE){
                Border.setWorldBoder18(p);
            }
            API.JoinClean(p);
        }, 2);
        if (States.state == States.LOBBY){
            e.setJoinMessage(c(Main.plugin.getConfig().getString("JoinMessage")
                    .replaceAll("%player%", e.getPlayer().getName())
                    .replaceAll("%online%", ""+Bukkit.getOnlinePlayers().size())
                    .replaceAll("%max%", ""+Main.plugin.getConfig().getInt("MaxPlayers"))
                    .replaceAll("%min%", ""+Main.plugin.getConfig().getInt("MinPlayers"))
            ));
            need--;
        }
        
        if (Bukkit.getOnlinePlayers().size() >= 2){
            for (Player all : Bukkit.getOnlinePlayers()){
                Kits.Spectador(all);
            }
        }
        
        p.setAllowFlight(true);
        p.setFlying(true);
        p.getInventory().clear();
        p.setGameMode(GameMode.CREATIVE);
        Kits.Spectador(p);
        Border.setWorldBoder18(p);
    }
    
    @EventHandler
    public void PlayerLeave(PlayerQuitEvent e){
        API.ALivePs.remove(e.getPlayer());
        for (Player p : API.ALivePs){
            API.CheckWin(p);
        }
        if (States.state == States.LOBBY){
            e.setQuitMessage(c(Main.plugin.getConfig().getString("QuitMessage")
                    .replaceAll("%player%", e.getPlayer().getName())
                    .replaceAll("%online%", ""+Bukkit.getOnlinePlayers().size())
                    .replaceAll("%max%", ""+Main.plugin.getConfig().getInt("MaxPlayers"))
                    .replaceAll("%min%", ""+Main.plugin.getConfig().getInt("MinPlayers"))
            ));
            need++;
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            if (Bukkit.getOnlinePlayers().size() <= 1){
                    for (Player all : Bukkit.getOnlinePlayers()){
                        Kits.Spectador(all);
                    }
                }
            }, 10);
        }
    }
    
    @EventHandler
    public void DropItems(PlayerDropItemEvent e){
        if (States.state == States.LOBBY || States.state == States.STARTING || API.Specs.contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void PickUpItems(PlayerPickupItemEvent e){
        if (States.state == States.LOBBY || States.state == States.STARTING || API.Specs.contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void PlayerDeath(PlayerDeathEvent e){
        Player p = e.getEntity().getPlayer();
        Player k = e.getEntity().getKiller();
        
        if (API.MLG.contains(p)){
            API.MLG.remove(p);
            Bukkit.broadcastMessage("§e"+p.getName()+"§a Perdio el reto §c§lMLG");
        }
        
        if (p.getHealth() < 0.5){
            API.Specs.add(p);
            p.setHealth(p.getMaxHealth());
            p.getInventory().setArmorContents(null);
            p.getInventory().clear();
            p.setGameMode(GameMode.CREATIVE);
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                Kits.Spectador(p);
            }, 10);
            for (Player all : Bukkit.getOnlinePlayers()){
                all.hidePlayer(p);
            }
        }
        API.ALivePs.remove(p);
        if (API.Kills.get(k) == null){
            API.Kills.put(k, 1);
        } else {
            API.Kills.put(k, API.Kills.get(k) + 1);
        }
        
        API.CheckWin(k);
    }
    
    @EventHandler
    public void Menus(InventoryClickEvent e){
        if (States.state == States.LOBBY || API.Specs.contains((Player) e.getWhoClicked())){
            e.setCancelled(true);
            if (e.getInventory().getName() == null||e.getInventory() == null){
                return;
            }
            Player p = (Player) e.getWhoClicked();
            if (c(Main.plugin.getConfig().getString("OptionsMenu")).equals(e.getInventory().getName())){
                if (p.getItemOnCursor() == null || p.getItemOnCursor().getItemMeta() == null){
                    return;
                }else{
                    if (c(Main.plugin.getConfig().getString("Items.BorderI")).equals(p.getItemOnCursor().getItemMeta().getDisplayName())){
                        
                    }
                }
            }else{
                e.getWhoClicked().closeInventory();
            }
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
                if (c(Main.plugin.getConfig().getString("Items.Players")).equals(e.getItem().getItemMeta().getDisplayName())){
                    e.getPlayer().openInventory(API.getAlive());
                }
                if (c(Main.plugin.getConfig().getString("Items.Options")).equals(e.getItem().getItemMeta().getDisplayName())){
                    //e.getPlayer().sendMessage(c(Main.plugin.getConfig().getString("NextUpdate")));
                    API.SendOptionMenu(e.getPlayer());
                }
                if (c(Main.plugin.getConfig().getString("Items.Hub")).equals(e.getItem().getItemMeta().getDisplayName())){
                    e.getPlayer().sendMessage("§eEnviandote al lobby...");
                    plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                        API.sendLobby(e.getPlayer());
                    }, 10);
                }
                if (c(Main.plugin.getConfig().getString("Items.Vote")).equals(e.getItem().getItemMeta().getDisplayName()) && !Voted.contains(e.getPlayer()) && started == false){
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
    public void PlayerInteract(PlayerInteractAtEntityEvent e){
        if (States.state == States.LOBBY || States.state == States.STARTING || API.Specs.contains(e.getPlayer())){
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
