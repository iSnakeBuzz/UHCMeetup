package com.isnakebuzz.meetup.c;

import static com.isnakebuzz.meetup.a.Main.messages;
import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.b.States;
import com.isnakebuzz.meetup.e.API;
import com.isnakebuzz.meetup.e.MessagesManager;

import static com.isnakebuzz.meetup.e.API.c;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GameListeners implements Listener{
    
    private Main plugin;
    
    public GameListeners(Main plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onMotdChange(ServerListPingEvent e){
    	if(States.state == States.LOADING){
    		e.setMotd(MessagesManager.MotdLoading);
    	}else if(States.state == States.LOBBY){
            e.setMotd(MessagesManager.MotdWaiting);
        } else if(States.state == States.STARTING){
            e.setMotd(MessagesManager.MotdStarting);
        }  else if(States.state == States.IN_GAME){
            e.setMotd(MessagesManager.MotdInGame);
        }
    }
    
    @EventHandler
    public void CheckMax(PlayerLoginEvent e){
    	if (States.state == States.LOADING) {
    		e.disallow(Result.KICK_OTHER, MessagesManager.KickMapLoading);
    	}else if (Bukkit.getOnlinePlayers().size() == Main.plugin.getConfig().getInt("GameOptions.MaxPlayers")){
            e.disallow(PlayerLoginEvent.Result.KICK_FULL, MessagesManager.KickFullGame);
        }else if(States.state != States.LOBBY){
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, MessagesManager.KickStarted);
        }
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
    public void LeftAndQuitScore(PlayerQuitEvent e) {
    	Player p = e.getPlayer();
    	if (plugin.scoreboard.containsKey(p) && plugin.scoreboard.get(p).getTask() != null) {
            plugin.scoreboard.get(p).getTask().cancel();
        }
    	
    	API.ALivePs.remove(p);
    	if (States.state == States.IN_GAME) {
    		API.CheckWin(p);
    	}
    }
    
    @EventHandler
    public void LeftAndQuitScore(PlayerKickEvent e) {
    	Player p = e.getPlayer();
    	if (plugin.scoreboard.containsKey(p) && plugin.scoreboard.get(p).getTask() != null) {
            plugin.scoreboard.get(p).getTask().cancel();
        }
    	
    	API.ALivePs.remove(p);
    	if (States.state == States.IN_GAME) {
    		API.CheckWin(p);
    	}
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        event.setCancelled(true);
    }
    
    @EventHandler
    public void DropItems(PlayerDropItemEvent e){
        if (API.KitMode.contains(e.getPlayer())){
            return;
        }
        if (States.state == States.LOBBY || States.state == States.STARTING || API.Specs.contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void PickUpItems(PlayerPickupItemEvent e){
        if (API.KitMode.contains(e.getPlayer())){
            return;
        }
        if (States.state == States.LOBBY || States.state == States.STARTING || API.Specs.contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onPlayerRegainHealth(final EntityRegainHealthEvent event) {
        if (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED || event.getRegainReason() == EntityRegainHealthEvent.RegainReason.REGEN) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void MobSpawn(CreatureSpawnEvent e){
        e.setCancelled(true);
    }
    
    @EventHandler
    public void CancelRain(WeatherChangeEvent e){
        e.setCancelled(true);
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
    
    /*@EventHandler(priority = EventPriority.HIGHEST)
    public void CommandMenuSecretInGame(final PlayerCommandPreprocessEvent e) {
        String cmd;
        final Player p = e.getPlayer();
        cmd = e.getMessage();
        if (cmd.equals("/mlg") && States.state == States.FINISH) {
            Bukkit.broadcastMessage("§aEmpezo el desafio §c§lMLG");
            API.MLGe = true;
            API.MLG.add(p);
            e.setCancelled(true);
        }
    }*/
    
    public void init(){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
