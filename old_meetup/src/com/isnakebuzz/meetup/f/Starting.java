package com.isnakebuzz.meetup.f;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.b.Kits;
import com.isnakebuzz.meetup.b.States;
import com.isnakebuzz.meetup.d.Border;
import com.isnakebuzz.meetup.e.API;
import com.isnakebuzz.meetup.e.MessagesManager;
import com.isnakebuzz.meetup.e.Votes;

import static com.isnakebuzz.meetup.e.API.c;

import com.isnakebuzz.meetup.k.Scenarios;
import com.isnakebuzz.meetup.n.PillarChecker;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Starting extends BukkitRunnable {

    private final Main plugin;

    public Starting(Main plugin) {
        this.plugin = plugin;
    }
    
    public static int time = 35;
    
    @Override
    public void run() {
        
        if (time == 35){
            
            API.KitMode.clear();
            API.Kills.clear();
            
            States.state = States.STARTING;
            for (Player all : Bukkit.getOnlinePlayers()){
                if (API.Teleported.contains(all)){
                    return;
                }
                API.ReRoll.put(all, 0);
                API.Teleported.add(all);
                API.CleanPlayer(all);
                Border.teleportFIRST(all, Border.walls - 5);
                all.setAllowFlight(true);
                all.setFlying(true);
                plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                	plugin.versionHandler.SitPlayer(all);
                	all.setAllowFlight(true);
                    all.setFlying(true);
                }, 2);
            }
            for (Player all : Bukkit.getOnlinePlayers()){
                API.ALivePs.add(all);
                Kits.RandomKit(all);
                all.setGameMode(GameMode.SURVIVAL);
                new Scenarios(all).o(all);
                if (plugin.scoreboard.containsKey(all) && plugin.scoreboard.get(all).getTask() != null) {
                    plugin.scoreboard.get(all).getTask().cancel();
                }
                ScoreboardManager.getManager().VoteScore(all);
            }
        }else if (time == 25){
            Bukkit.broadcastMessage(c(MessagesManager.StartMSG
                    .replaceAll("%time%", ""+Starting.time)
            ));
        }else if (time == 15){
            Bukkit.broadcastMessage(c(MessagesManager.StartMSG
                    .replaceAll("%time%", ""+Starting.time)
            ));
        }else if (time == 10){
            Bukkit.broadcastMessage(c(MessagesManager.StartMSG
                    .replaceAll("%time%", ""+Starting.time)
            ));
        }else if (time <= 5 && time >= 1){
            Bukkit.broadcastMessage(c(MessagesManager.StartMSG
                    .replaceAll("%time%", ""+Starting.time)
            ));
        }else if (time == 0){
            for (Player all : Bukkit.getOnlinePlayers()){
            	all.setAllowFlight(false);
                all.setFlying(false);
                all.getActivePotionEffects().stream().forEach((effect) -> {
                	all.removePotionEffect(effect.getType());
                });
                plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                	plugin.versionHandler.UnSitPlayer(all);
                }, 3);
                if (plugin.scoreboard.containsKey(all) && plugin.scoreboard.get(all).getTask() != null) {
                    plugin.scoreboard.get(all).getTask().cancel();
                }
                ScoreboardManager.getManager().ScoreBoard(all);
                all.closeInventory();
                CheckRod(all);
                CheckBow(all);
            }
            Bukkit.broadcastMessage(c(MessagesManager.StartedMSG
            ));
            States.state = States.IN_GAME;
            new InGame(Main.plugin).runTaskTimer(Main.plugin, 02L, 20L);
            new PillarChecker(Main.plugin).runTaskTimer(Main.plugin, 02L, 02L);
            this.cancel();
        }
            
        
        time--;
    }
    
    private void CheckRod(Player p) {
    	Double rod = Double.valueOf(Votes.getRodLess());
		Double min = Double.valueOf(Votes.getPorcentage());
		Double nogames = Double.valueOf(Votes.getNogames());
		if (rod >= min && nogames < rod) {
			p.getInventory().remove(Material.FISHING_ROD);
			p.updateInventory();
		}
    }
    
    private void CheckBow(Player p) {
    	Double bow = Double.valueOf(Votes.getBowLess());
		Double min = Double.valueOf(Votes.getPorcentage());
		Double nogames = Double.valueOf(Votes.getNogames());
		if (bow >= min && nogames < bow) {
			p.getInventory().remove(Material.BOW);
			p.getInventory().remove(Material.ARROW);
			p.updateInventory();
		}
    }

}
