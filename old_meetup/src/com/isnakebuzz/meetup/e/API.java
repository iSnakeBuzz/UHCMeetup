package com.isnakebuzz.meetup.e;

import static com.isnakebuzz.meetup.a.Main.messagesmenu;
import static com.isnakebuzz.meetup.a.Main.messages;
import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.b.States;
import com.isnakebuzz.meetup.f.Finish;
import com.isnakebuzz.meetup.f.Starting;
import com.isnakebuzz.meetup.l.ItemBuilder;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Beacon;
import org.bukkit.block.BlockState;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.block.Jukebox;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class API {
    
    public static ArrayList<Player> ALivePs = new ArrayList<>();
    public static ArrayList<Player> Specs = new ArrayList<>();
    public static ArrayList<Player> NoBorder = new ArrayList<>();
    
    public static HashMap<Player, Integer> ReRoll = new HashMap<>();
    
    public static ArrayList<Player> KitMode = new ArrayList<>();
    
    public static ArrayList<Player> MLG = new ArrayList<>();
    public static boolean MLGe = false;
    
    public static HashMap<Player, Integer> Kills = new HashMap<>();
    
    public static ArrayList<Player> Voted = new ArrayList<>();
        
    public static Boolean started = false;
    
    public static ArrayList<Player> Teleported = new ArrayList<>();
    
    public static String winner = "none";
    
    public static int need = Main.plugin.getConfig().getInt("GameOptions.MinPlayers");
    public static int votos = Main.plugin.getConfig().getInt("GameOptions.MinVotes");
    public static int nextborder = 30;
    
    
    public static void CheckStart(){
        if (need == 0){
        	started = true;
        	Main.plugin.getServer().getScheduler().runTaskLater(Main.plugin, () -> {
        		new Starting(Main.plugin).runTaskTimer(Main.plugin, 02L, 20L);
        	}, 10);
            Bukkit.broadcastMessage(c(messages.getString("PlayerMessage.StartMSG")
                    .replaceAll("%time%", ""+Starting.time)
            ));
        }
    }
    
    public static ItemStack getHeadName() {
        final ItemStack itemStack = new ItemStack(Material.GOLDEN_APPLE, 1);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Golden Heads"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    
    public static String getVersion(){
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
        if (null != version)switch (version) {
            case "v1_7_R4":
                return "1.7.10";
            case "v1_8_R3":
                return "1.8.8";
        }
        return "none";
    }
    
    public static int GetKills(Player p){
        if (API.Kills.get(p) == null){
            return 0;
        }else{
            return API.Kills.get(p);
        }
    }
    
    public static void CleanPlayer(Player p){
        p.setLevel(0);
        p.setExp(0);
        p.setHealth(p.getMaxHealth());
        p.setFoodLevel(40);
        p.setFlying(false);
        p.setAllowFlight(false);
        p.setFireTicks(0);
        p.getActivePotionEffects().stream().forEach((effect) -> {
            p.removePotionEffect(effect.getType());
        });
    }
    
    public static void JoinClean(Player p){
        p.setLevel(0);
        p.setExp(0);
        p.setHealth(p.getMaxHealth());
        p.setFoodLevel(40);
        p.setFireTicks(0);
        p.getInventory().setArmorContents(null);
        p.getActivePotionEffects().stream().forEach((effect) -> {
            p.removePotionEffect(effect.getType());
        });
    }
    
    public static void sendLobby(Player player) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(Main.plugin.getConfig().getString("Lobby"));

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        player.sendPluginMessage(Main.plugin, "BungeeCord", b.toByteArray());
    }
    
    public static void sendArena(Player player) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(Main.plugin.getConfig().getString("ArenaLobby"));

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        player.sendPluginMessage(Main.plugin, "BungeeCord", b.toByteArray());
    }
    
    @SuppressWarnings("unused")
	public static void CheckWin(Player p){
        if (API.ALivePs.size() == 1){
            Bukkit.broadcastMessage(c(messages.getString("PlayerMessage.WinMSG")
                    .replaceAll("%player%", p.getName())
            ));
            UUID uuid = p.getUniqueId();
            API.winner = p.getName();
            
            for (Player all : Bukkit.getOnlinePlayers()) {
            	if (Main.plugin.scoreboard.containsKey(all) && Main.plugin.scoreboard.get(all).getTask() != null) {
                    Main.plugin.scoreboard.get(all).getTask().cancel();
                }
                ScoreboardManager.getManager().Ended(all);
            }
            
            new Finish(Main.plugin).runTaskTimer(Main.plugin, 02L, 20L);
            
            //p.sendMessage(c(Main.plugin.getConfig().getString("MlgMSG")));
            States.state = States.FINISH;
        }
    }
    
    public static String c(String c){
        return ChatColor.translateAlternateColorCodes('&', c);
    }
    
    public static void DeleteWorld(String world) {
        File toReset = new File(world);
        World w2 = Bukkit.getWorld(world);
        Bukkit.getServer().unloadWorld(w2, true);
        Bukkit.getConsoleSender().sendMessage("�aReseteando mundo...");
        try {
            FileUtils.deleteDirectory(toReset);
            Bukkit.getConsoleSender().sendMessage("�aMundo borrado");
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("�cPlease use \"NewWorldGenerator\": true for 1.7.10 servers :)");
            Bukkit.getPluginManager().disablePlugin(Main.plugin);
            return;
        }
        
        WorldCreator wc = new WorldCreator("world");
        wc.type(WorldType.LARGE_BIOMES);
        wc.createWorld();
        w2.regenerateChunk(0, 0);
        API.cleanMap();
    }
    
    public static void CheckStartVote(){
        if (votos == 0){
            new Starting(Main.plugin).runTaskTimer(Main.plugin, 02L, 20L);
            Bukkit.broadcastMessage(c(messages.getString("PlayerMessage.StartMSG")
                    .replaceAll("%time%", ""+Starting.time)
            ));
            started = true;
        }else if (votos > 0){
            Bukkit.broadcastMessage(c(messages.getString("VoteMessages.NeedVotes")
                    .replaceAll("%votes%", ""+votos)
            ));
        }
    }
    
    public static void cleanMap() {
        World w = Bukkit.getWorld(Main.world);
        Chunk[] chunks = w.getLoadedChunks();
        for (Chunk chunk : chunks) {
            BlockState[] tileEntities = chunk.getTileEntities();
            for (BlockState i : tileEntities) {
                if (i instanceof Beacon) {
                    Beacon blockState = ((Beacon) i);
                    blockState.getInventory().clear();
                } else if (i instanceof BrewingStand) {
                    BrewingStand blockState = ((BrewingStand) i);
                    blockState.getInventory().clear();
                } else if (i instanceof Chest) {
                    Chest blockState = ((Chest) i);
                    blockState.getInventory().clear();
                } else if (i instanceof Dispenser) {
                    Dispenser blockState = ((Dispenser) i);
                    blockState.getInventory().clear();
                } else if (i instanceof Furnace) {
                    Furnace blockState = ((Furnace) i);
                    blockState.getInventory().clear();
                } else if (i instanceof Hopper) {
                    Hopper blockState = ((Hopper) i);
                 blockState.getInventory().clear();
                } else if (i instanceof Jukebox) {
                    Jukebox blockState = ((Jukebox) i);
                    blockState.eject();
                }
            }
        }

        for (Entity entity : w.getEntities()) {
            if (entity.getType() == EntityType.DROPPED_ITEM) {
                entity.remove();
            }
        }

        clearNonPlayerEntities();
    }

    private static void clearNonPlayerEntities() {
        World w = Bukkit.getWorld(Main.world);
        for (Entity entity : w.getEntities()) {
            if (entity instanceof LivingEntity && !(entity instanceof Player) && !entity.hasMetadata("CombatLoggerNPC")) {
                    entity.remove();
            }
        }
    }
    
    private static final Inventory malive = Bukkit.createInventory(null, 9 * 6, c(messagesmenu.getString("PlayersMenu")));
  
    public static Inventory getAlive(){
        API.malive.clear();
        for (Player p : ALivePs){
            Player localPlayer = Bukkit.getServer().getPlayer(p.getName());
            if (localPlayer != null) {
                API.malive.addItem(new ItemStack[] { API.newItem(Material.SKULL_ITEM, p, 3) });
            }
        }
        return API.malive;
    }
    
    
    public static void mlg1(final Player p, int locs) {
        final Random r = new Random();
        final int x = r.nextInt(locs);
        final int z = -r.nextInt(locs);
        final int y = p.getWorld().getHighestBlockYAt(x, z) + 40;
        final World world = Bukkit.getWorld(Main.world);
        final Location randomLoc = new Location(world, (double)x, (double)y, (double)z);
        p.teleport(randomLoc);
    }
    
    public static void mlg2(final Player p, int locs) {
        final Random r = new Random();
        final int x = r.nextInt(locs);
        final int z = -r.nextInt(locs);
        final int y = p.getWorld().getHighestBlockYAt(x, z) + 60;
        final World world = Bukkit.getWorld(Main.world);
        final Location randomLoc = new Location(world, (double)x, (double)y, (double)z);
        p.teleport(randomLoc);
    }
    
    public static void mlg3(final Player p, int locs) {
        final Random r = new Random();
        final int x = r.nextInt(locs);
        final int z = -r.nextInt(locs);
        final int y = p.getWorld().getHighestBlockYAt(x, z) + 90;
        final World world = Bukkit.getWorld(Main.world);
        final Location randomLoc = new Location(world, (double)x, (double)y, (double)z);
        p.teleport(randomLoc);
    }
    
    public static ItemStack newItem(Material paramMaterial, Player paramString, int paramInt){
        ItemStack localItemStack = new ItemStack(paramMaterial, 1, (short)paramInt);
        SkullMeta  meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
        meta.setOwner(paramString.getName());
        meta.setDisplayName("§a"+paramString.getName());
        localItemStack.setItemMeta(meta);

        return localItemStack;
    }
    
    public static void setMLGInventory(Player p) {
    	p.getInventory().clear();
    	p.getInventory().setArmorContents(null);
    	p.setLevel(0);
    	p.setExp(0.0f);
    	Main.plugin.getServer().getScheduler().runTaskLater(Main.plugin, () -> {
    		p.getInventory().setItem(4, ItemBuilder.crearItem(326, 1, 0));
    		p.getInventory().setHeldItemSlot(4);
        }, 10);
    }
    
}
