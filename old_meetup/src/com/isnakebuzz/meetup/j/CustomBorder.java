package com.isnakebuzz.meetup.j;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.d.Border;
import com.isnakebuzz.meetup.e.API;
import com.isnakebuzz.meetup.e.MessagesManager;
import com.isnakebuzz.meetup.f.InGame;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

import static com.isnakebuzz.meetup.e.API.c;

public class CustomBorder extends BukkitRunnable {

    private int time;
    private int maxbucle;
    private int bucleint;
    private Main plugin;
    private Configuration config;
    private int border;

    public CustomBorder(Main plugin, int firstTime) {
        this.time = firstTime;
        this.plugin = plugin;
        config = plugin.getConfig();
        Set<String> key = config.getConfigurationSection("Border").getKeys(false);
        this.maxbucle = key.size();
        this.bucleint = 1;
        String decrypt = config.getString("Border." + bucleint);
        this.border = Integer.valueOf(decrypt.split(":")[0]);


    }


    @Override
    public void run() {

        if (this.time == 30) {
            Bukkit.broadcastMessage(c(MessagesManager.BorderMessage
                    .replaceAll("%time%", "" + this.time)
                    .replaceAll("%coords%", String.valueOf(this.border))
            ));
        } else if (this.time == 20) {
            Bukkit.broadcastMessage(c(MessagesManager.BorderMessage
                    .replaceAll("%time%", "" + this.time)
                    .replaceAll("%coords%", String.valueOf(this.border))
            ));
        } else if (this.time <= 10 && this.time >= 1) {
            Bukkit.broadcastMessage(c(MessagesManager.BorderMessage
                    .replaceAll("%time%", "" + this.time)
                    .replaceAll("%coords%", String.valueOf(this.border))
            ));
        }

        if (time == 0) {

            String decrypt = config.getString("Border." + bucleint);

            int border = Integer.valueOf(decrypt.split(":")[0]);
            int timeto = Integer.valueOf(decrypt.split(":")[1]);
            this.border = border;
            Border.walls = border;
            for (Player p : API.ALivePs) {
                Border.randomTpOnBorder(p);
            }
            InGame.borderenabled = false;
            Border.newBorderGenerator(5);
            this.bucleint++;
            if (this.bucleint > this.maxbucle) {
                this.cancel();
                return;
            } else if (this.bucleint < this.maxbucle) {
                InGame.borderenabled = true;
                this.time = timeto;
            }
        }
        this.time--;
    }

}
