package com.isnakebuzz.meetup.k;

import static com.isnakebuzz.meetup.a.Main.messagesmenu;
import org.bukkit.inventory.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.isnakebuzz.meetup.l.*;

import java.util.List;

import org.bukkit.*;
import org.bukkit.entity.*;

public class Speed extends Menu{
	
	List speed1;
	List speed2;
	List speed3;
	List speed4;
	
	List normal;
	
	public Speed(final Player player) {
        super(messagesmenu.getString("SpeedMenu.name"), messagesmenu.getInt("SpeedMenu.Raws"));
        speed1 = messagesmenu.getStringList("SpeedMenu.Speed1.lore");
        speed2 = messagesmenu.getStringList("SpeedMenu.Speed2.lore");
        speed3 = messagesmenu.getStringList("SpeedMenu.Speed3.lore");
        speed4 = messagesmenu.getStringList("SpeedMenu.Speed4.lore");
        
        normal = messagesmenu.getStringList("SpeedMenu.Normal.lore");
    	
    	if (messagesmenu.getBoolean("SpeedMenu.Speed1.enable")) {
    		this.s(messagesmenu.getInt("SpeedMenu.Speed1.slot"), ItemBuilder.crearItem1(messagesmenu.getInt("SpeedMenu.Speed1.item"), 1, messagesmenu.getInt("SpeedMenu.Speed1.data"), messagesmenu.getString("SpeedMenu.Speed1.name"), speed1));
    	}
    	
    	if (messagesmenu.getBoolean("SpeedMenu.Speed2.enable")) {
    		this.s(messagesmenu.getInt("SpeedMenu.Speed2.slot"), ItemBuilder.crearItem1(messagesmenu.getInt("SpeedMenu.Speed2.item"), 1, messagesmenu.getInt("SpeedMenu.Speed2.data"), messagesmenu.getString("SpeedMenu.Speed2.name"), speed2));
    	}
    	
    	if (messagesmenu.getBoolean("SpeedMenu.Speed3.enable")) {
    		this.s(messagesmenu.getInt("SpeedMenu.Speed3.slot"), ItemBuilder.crearItem1(messagesmenu.getInt("SpeedMenu.Speed3.item"), 1, messagesmenu.getInt("SpeedMenu.Speed3.data"), messagesmenu.getString("SpeedMenu.Speed3.name"), speed3));
    	}
    	
    	if (messagesmenu.getBoolean("SpeedMenu.Speed4.enable")) {
    		this.s(messagesmenu.getInt("SpeedMenu.Speed4.slot"), ItemBuilder.crearItem1(messagesmenu.getInt("SpeedMenu.Speed4.item"), 1, messagesmenu.getInt("SpeedMenu.Speed4.data"), messagesmenu.getString("SpeedMenu.Speed4.name"), speed4));
    	}
    	
    	if (messagesmenu.getBoolean("SpeedMenu.Normal.enable")) {
    		this.s(messagesmenu.getInt("SpeedMenu.Normal.slot"), ItemBuilder.crearItem1(messagesmenu.getInt("SpeedMenu.Normal.item"), 1, messagesmenu.getInt("SpeedMenu.Normal.data"), messagesmenu.getString("SpeedMenu.Normal.name"), normal));
    	}
    	
    }
    
    @Override
    public void onClick(final Player p, final ItemStack itemStack) {
        final String name = itemStack.getItemMeta().getDisplayName();
        
        if (name.equalsIgnoreCase(c(messagesmenu.getString("SpeedMenu.Speed1.name")))) {
        	p.setFlySpeed(0.2f);
        	p.removePotionEffect(PotionEffectType.SPEED);
        	p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 60 * 60, 0));
        }
        
        if (name.equalsIgnoreCase(c(messagesmenu.getString("SpeedMenu.Speed2.name")))) {
        	p.setFlySpeed(0.3f);
        	p.removePotionEffect(PotionEffectType.SPEED);
        	p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 60 * 60, 1));
        }
        
        if (name.equalsIgnoreCase(c(messagesmenu.getString("SpeedMenu.Speed3.name")))) {
        	p.setFlySpeed(0.4f);
        	p.removePotionEffect(PotionEffectType.SPEED);
        	p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 60 * 60, 2));
        }
        
        if (name.equalsIgnoreCase(c(messagesmenu.getString("SpeedMenu.Speed4.name")))) {
        	p.setFlySpeed(0.5f);
        	p.removePotionEffect(PotionEffectType.SPEED);
        	p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 60 * 60, 3));
        }
        
        if (name.equalsIgnoreCase(c(messagesmenu.getString("SpeedMenu.Normal.name")))) {
        	p.setFlySpeed(0.1f);
        	p.removePotionEffect(PotionEffectType.SPEED);
        }
        
        this.update();
    }
    
	private String c(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	private void update() {
		speed1 = messagesmenu.getStringList("SpeedMenu.Speed1.lore");
        speed2 = messagesmenu.getStringList("SpeedMenu.Speed2.lore");
        speed3 = messagesmenu.getStringList("SpeedMenu.Speed3.lore");
        speed4 = messagesmenu.getStringList("SpeedMenu.Speed4.lore");
        
        normal = messagesmenu.getStringList("SpeedMenu.Normal.lore");
    	
    	if (messagesmenu.getBoolean("SpeedMenu.Speed1.enable")) {
    		this.s(messagesmenu.getInt("SpeedMenu.Speed1.slot"), ItemBuilder.crearItem1(messagesmenu.getInt("SpeedMenu.Speed1.item"), 1, messagesmenu.getInt("SpeedMenu.Speed1.data"), messagesmenu.getString("SpeedMenu.Speed1.name"), speed1));
    	}
    	
    	if (messagesmenu.getBoolean("SpeedMenu.Speed2.enable")) {
    		this.s(messagesmenu.getInt("SpeedMenu.Speed2.slot"), ItemBuilder.crearItem1(messagesmenu.getInt("SpeedMenu.Speed2.item"), 1, messagesmenu.getInt("SpeedMenu.Speed2.data"), messagesmenu.getString("SpeedMenu.Speed2.name"), speed2));
    	}
    	
    	if (messagesmenu.getBoolean("SpeedMenu.Speed3.enable")) {
    		this.s(messagesmenu.getInt("SpeedMenu.Speed3.slot"), ItemBuilder.crearItem1(messagesmenu.getInt("SpeedMenu.Speed3.item"), 1, messagesmenu.getInt("SpeedMenu.Speed3.data"), messagesmenu.getString("SpeedMenu.Speed3.name"), speed3));
    	}
    	
    	if (messagesmenu.getBoolean("SpeedMenu.Speed4.enable")) {
    		this.s(messagesmenu.getInt("SpeedMenu.Speed4.slot"), ItemBuilder.crearItem1(messagesmenu.getInt("SpeedMenu.Speed4.item"), 1, messagesmenu.getInt("SpeedMenu.Speed4.data"), messagesmenu.getString("SpeedMenu.Speed4.name"), speed4));
    	}
    	
    	if (messagesmenu.getBoolean("SpeedMenu.Normal.enable")) {
    		this.s(messagesmenu.getInt("SpeedMenu.Normal.slot"), ItemBuilder.crearItem1(messagesmenu.getInt("SpeedMenu.Normal.item"), 1, messagesmenu.getInt("SpeedMenu.Normal.data"), messagesmenu.getString("SpeedMenu.Normal.name"), normal));
    	}
    }
}
