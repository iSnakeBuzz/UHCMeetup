package com.isnakebuzz.meetup.a;

import org.bukkit.*;
import org.bukkit.entity.Player;

public interface VersionHandler {
    public void SwapBiomes();
    
    public void setWorldBoder18(Player p);
    
    public void setWorldBoder182(Player p, int size);
    
    public void SitPlayer(Player p);
	
	public void UnSitPlayer(Player p);
	
	public void sendLighting(Player p, Player k);	
}
