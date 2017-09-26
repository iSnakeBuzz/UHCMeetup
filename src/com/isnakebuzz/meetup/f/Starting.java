package com.isnakebuzz.meetup.f;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.b.Kits;
import com.isnakebuzz.meetup.b.States;
import com.isnakebuzz.meetup.d.Border;
import com.isnakebuzz.meetup.e.API;
import static com.isnakebuzz.meetup.e.API.c;
import com.isnakebuzz.meetup.g.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Starting extends BukkitRunnable {

    private final Main plugin;

    public Starting(Main plugin) {
        this.plugin = plugin;
    }
    
    public static int time = 15;
    
    @Override
    public void run() {
        
        if (time == 15){
            States.state = States.STARTING;
            for (Player all : Bukkit.getOnlinePlayers()){
                if (API.Teleported.contains(all)){
                    return;
                }
                API.Teleported.add(all);
                Border.teleport(all, Border.walls - 5);
                plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                    all.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 14, 1, true, false));
                }, 5);
            }
            for (Player all : Bukkit.getOnlinePlayers()){
                Kits.RandomKit(all);
                all.setGameMode(GameMode.SURVIVAL);
                API.ALivePs.add(all);
                if (plugin.scoreboard.containsKey(all) && plugin.scoreboard.get(all).getTask() != null) {
                    plugin.scoreboard.get(all).getTask().cancel();
                }
                ScoreboardManager.getManager().ScoreBoard(all);
            }
        }else if (time == 10){
            Bukkit.broadcastMessage(c(Main.plugin.getConfig().getString("StartMSG")
                    .replaceAll("%time%", ""+Starting.time)
            ));
        }else if (time <= 5 && time >= 1){
            Bukkit.broadcastMessage(c(Main.plugin.getConfig().getString("StartMSG")
                    .replaceAll("%time%", ""+Starting.time)
            ));
        }else if (time == 0){
            Bukkit.broadcastMessage(c(Main.plugin.getConfig().getString("StartedMSG")
            ));
            States.state = States.IN_GAME;
            new InGame(Main.plugin).runTaskTimer(Main.plugin, 02L, 20L);
            this.cancel();
        }
            
        
        time--;
    }

}
