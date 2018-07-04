package com.isnakebuzz.meetup.c;

import static com.isnakebuzz.meetup.a.Main.messages;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.e.API;
import com.isnakebuzz.meetup.e.MessagesManager;
import com.isnakebuzz.meetup.e.Votes;

public class ScenListeners implements Listener{
	
	private Main plugin;
	
	public ScenListeners (Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void FireLess(EntityDamageEvent e) {
		if (e.getCause() == DamageCause.FIRE_TICK || e.getCause() == DamageCause.FIRE || e.getCause() == DamageCause.LAVA) {
			Double fire = Double.valueOf(Votes.getFireLess());
			Double min = Double.valueOf(Votes.getPorcentage());
			Double nogames = Double.valueOf(Votes.getNogames());
			if (fire >= min && fire < nogames) {
				e.setCancelled(true);
			}else {
				e.setCancelled(false);
			}
		}
	}
	
	public List<ItemStack> timebombDrops = new ArrayList<ItemStack>();
	
	@EventHandler
    public void TimeBombEvent(final PlayerDeathEvent event) {
		Double timebomb = Double.valueOf(Votes.getTimeBomb());
		Double min = Double.valueOf(Votes.getPorcentage());
		Double nogames = Double.valueOf(Votes.getNogames());
		if (timebomb >= min && nogames < timebomb) {
			if (event.getEntity() instanceof Player) {
	            final Player player = event.getEntity();
	            final Location loc = player.getLocation();
	            event.getDrops().clear();
	            final Block chest1 = loc.getBlock();
	            chest1.setType(Material.CHEST);
	            final Block chest2 = chest1.getRelative(BlockFace.NORTH);
	            chest2.setType(Material.CHEST);
	            chest1.getRelative(BlockFace.UP).setType(Material.AIR);
	            chest2.getRelative(BlockFace.UP).setType(Material.AIR);
	            final Chest chest3 = (Chest)chest1.getState();
	            final ItemStack[] armor = player.getInventory().getArmorContents();
	            final ItemStack[] inv = player.getInventory().getContents();
	            final ItemStack goldenHead = API.getHeadName();
	            for (final ItemStack stack : armor) {
	                if (stack != null && stack.getType() != Material.AIR) {
	                    chest3.getInventory().addItem(new ItemStack[] { stack });
	                }
	            }
	            chest3.getInventory().addItem(new ItemStack[] { goldenHead });
	            for (final ItemStack stack : inv) {
	                if (stack != null && stack.getType() != Material.AIR) {
	                    chest3.getInventory().addItem(new ItemStack[] { stack });
	                }
	            }
	            for (final ItemStack stack2 : timebombDrops) {
	                if (stack2 != null && stack2.getType() != Material.AIR) {
	                    chest3.getInventory().addItem(new ItemStack[] { stack2 });
	                }
	            }
	            new BukkitRunnable() {
	                public void run() {
	                    Bukkit.broadcastMessage(c(MessagesManager.ScenBombExplode.replaceAll("%player%", ""+event.getEntity().getPlayer().getName())));
	                    chest3.getInventory().clear();
	                    loc.getWorld().createExplosion(loc, 3.5f);
	                }
	            }.runTaskLater(plugin, plugin.getConfig().getInt("ScenariosTime.TimeBombTime") * 20L);
	        }
		}
    
	}
	
	ArrayList<Player> cooldownprroxdddsuhnigga = new ArrayList<>();
	
	@EventHandler
	public void NoClean(PlayerDeathEvent e) {
		//Puto el que lo lea //es de noche
		Double noclean = Double.valueOf(Votes.getNoClean());
		Double min = Double.valueOf(Votes.getPorcentage());
		Double nogames = Double.valueOf(Votes.getNogames());
		if (noclean >= min && nogames < noclean) {
			if (e.getEntity().getKiller() == null) {
				return;
			}
			if (!(e.getEntity().getKiller() instanceof Player)) {
				return;
			}
			final Player p = e.getEntity().getKiller();
				
			if (!cooldownprroxdddsuhnigga.contains(p)) {
				cooldownprroxdddsuhnigga.add(p);
			}
			
			new BukkitRunnable() {
				@Override
				public void run() { //se logico man xd
					if (!cooldownprroxdddsuhnigga.contains(p)) {
						this.cancel();
					} else {
						cooldownprroxdddsuhnigga.remove(p);
						p.sendMessage(c(MessagesManager.removedanticlean));
					}
				}
			}.runTaskLaterAsynchronously(Main.plugin, (long)plugin.getConfig().getInt("ScenariosTime.NoCleanTime") * 20);
		}
	}
	
	@EventHandler //se xD
	public void NoClean2(EntityDamageByEntityEvent e) {
		Double noclean = Double.valueOf(Votes.getNoClean());
		Double min = Double.valueOf(Votes.getPorcentage());
		Double nogames = Double.valueOf(Votes.getNogames());
		if (noclean >= min && nogames < noclean) {
			if (e.getEntity() instanceof Player) { //ahora si xD este si es :v
				if (!(e.getDamager() instanceof Player)) {
					return;
				}
				Player p = (Player) e.getDamager();
				if (cooldownprroxdddsuhnigga.contains(e.getDamager())) {
					cooldownprroxdddsuhnigga.remove(e.getDamager());
					p.sendMessage(c(MessagesManager.removedanticlean));
				}
				if (cooldownprroxdddsuhnigga.contains(e.getEntity())) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void NoClean3(EntityShootBowEvent e) {
		Double noclean = Double.valueOf(Votes.getNoClean());
		Double min = Double.valueOf(Votes.getPorcentage());
		Double nogames = Double.valueOf(Votes.getNogames());
		if (noclean >= min && nogames < noclean) {
			if (!(e.getEntity() instanceof Player)) {
				return;
			}
			Player p = (Player) e.getEntity();
			if (cooldownprroxdddsuhnigga.contains(e.getEntity())) {
				cooldownprroxdddsuhnigga.remove(e.getEntity());
				p.sendMessage(c(MessagesManager.removedanticlean));
			}
		}
	}
	
	@EventHandler
	public void NoClean4(PlayerBucketEmptyEvent e) {
		Double noclean = Double.valueOf(Votes.getNoClean());
		Double min = Double.valueOf(Votes.getPorcentage());
		Double nogames = Double.valueOf(Votes.getNogames());
		if (noclean >= min && nogames < noclean) {
			if (cooldownprroxdddsuhnigga.contains(e.getPlayer())) {
				if (e.getBucket().getId() == 327) {
					cooldownprroxdddsuhnigga.remove(e.getPlayer());
					e.getPlayer().sendMessage(c(MessagesManager.removedanticlean));
				}
			}
		}
	}
	
	@EventHandler
	public void NoClean5(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Double noclean = Double.valueOf(Votes.getNoClean());
		Double min = Double.valueOf(Votes.getPorcentage());
		Double nogames = Double.valueOf(Votes.getNogames());
		if (noclean >= min && nogames < noclean) {
			Player p = (Player) e.getEntity();
			if (e.getCause() == DamageCause.FIRE_TICK || e.getCause() == DamageCause.FIRE || e.getCause() == DamageCause.LAVA || e.getCause() == DamageCause.CONTACT) {
				if (cooldownprroxdddsuhnigga.contains(p)) {
					e.setCancelled(true);
				}
			}
		}
		
	}
	
	protected String c(String string) {
		// TODO Auto-generated method stub
		return ChatColor.translateAlternateColorCodes('&', string);
	}


	public void init() {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
}
