package com.isnakebuzz.meetup.j;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.d.Border;
import com.isnakebuzz.meetup.e.API;
import com.isnakebuzz.meetup.e.MessagesManager;
import com.isnakebuzz.meetup.f.InGame;

import static com.isnakebuzz.meetup.e.API.c;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Border50 extends BukkitRunnable {

    public Border50(Main plugin) {
    }
        
    
    @Override
    public void run() {
        if (API.nextborder == 30){
        	Bukkit.broadcastMessage(c(MessagesManager.BorderMessage
                    .replaceAll("%time%", ""+API.nextborder)
                    .replaceAll("%coords%", "50")
            ));
        }else if (API.nextborder == 20){
        	Bukkit.broadcastMessage(c(MessagesManager.BorderMessage
                    .replaceAll("%time%", ""+API.nextborder)
                    .replaceAll("%coords%", "50")
            ));
        }else if (API.nextborder <= 10 && API.nextborder >= 1){
        	Bukkit.broadcastMessage(c(MessagesManager.BorderMessage
                    .replaceAll("%time%", ""+API.nextborder)
                    .replaceAll("%coords%", "50")
            ));
        }
        
        if (API.nextborder == 1) {
        	Border.walls = 50;
            API.nextborder = 31;
            InGame.borderenabled = false;
            Border.newBorderGenerator(5);
            Bukkit.broadcastMessage(c(MessagesManager.BorderNow
                    .replaceAll("%coords%", "50")
            ));
            for (Player p : API.ALivePs) {
            	Border.randomTpOnBorder(p);
            }
        	this.cancel();
        }
        
        API.nextborder--;
    }

}
