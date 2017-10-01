package com.isnakebuzz.meetup.c;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.b.States;
import com.isnakebuzz.meetup.d.Border;
import com.isnakebuzz.meetup.e.API;
import static com.isnakebuzz.meetup.e.API.c;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GameListeners implements Listener{
    
    private final Main plugin;
    
    public GameListeners (Main plugin){
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onMotdChange(ServerListPingEvent e){
        if(States.state == States.LOBBY){
            e.setMotd(plugin.getConfig().getString("Motds.Waiting"));
        } else if(States.state == States.STARTING){
            e.setMotd(plugin.getConfig().getString("Motds.Starting"));
        }  else if(States.state == States.IN_GAME){
            e.setMotd(plugin.getConfig().getString("Motds.InGame"));
        }
    }
    
    @EventHandler
    public void CheckMax(PlayerLoginEvent e){
        if (Bukkit.getOnlinePlayers().size() == Main.plugin.getConfig().getInt("MaxPlayers")){
            e.disallow(PlayerLoginEvent.Result.KICK_FULL, c(Main.plugin.getConfig().getString("GameFull")));
        }else if(States.state != States.LOBBY){
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, c(Main.plugin.getConfig().getString("GameStarted")));
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
    public void ChatEvent(AsyncPlayerChatEvent e){
        String msg = e.getMessage();
        String name = e.getPlayer().getDisplayName();
        Player p = e.getPlayer();
        if (Main.plugin.getConfig().getBoolean("Chats.Alive") == true){
            if (API.ALivePs.contains(p)){
                for (Player alive : API.ALivePs){
                    alive.sendMessage(c(Main.plugin.getConfig().getString("Chats.AliveFormat")
                            .replaceAll("%player%", name)
                    ) + msg);
                }
                e.setCancelled(true);
            }else{
                if (!API.Specs.contains(p)){
                    for (Player all : Bukkit.getOnlinePlayers()){
                        all.sendMessage(c(Main.plugin.getConfig().getString("Chats.AliveFormat")
                                .replaceAll("%player%", name)
                        ) + msg);
                    }
                    e.setCancelled(true);
                }
            }
            e.setCancelled(true);
        }
        
        if (Main.plugin.getConfig().getBoolean("Chats.Spect") == true){
            if (API.Specs.contains(p)){
                for (Player specs : API.Specs){
                    specs.sendMessage(c(Main.plugin.getConfig().getString("Chats.SpectFormat")
                            .replaceAll("%player%", name)
                    ) + msg);
                }
                e.setCancelled(true);
            }
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onPlayerRegainHealth(final EntityRegainHealthEvent event) {
        if (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED || event.getRegainReason() == EntityRegainHealthEvent.RegainReason.REGEN) {
            event.setCancelled(true);
        }
    }
    
    /*@EventHandler
    public void NoLeave(PlayerMoveEvent e){
        Player p = e.getPlayer();
        Border.checkBorder(p);
    }*/
    
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (States.state == States.STARTING){
            if (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getZ() != event.getTo().getZ()) {
                Location loc = event.getFrom();
                event.getPlayer().teleport(loc.setDirection(event.getTo().getDirection()));
            }
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
