package com.isnakebuzz.meetup.k;

import static com.isnakebuzz.meetup.a.Main.messages;
import static com.isnakebuzz.meetup.a.Main.messagesmenu;
import org.bukkit.inventory.*;
import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.e.MessagesManager;
import com.isnakebuzz.meetup.e.Votes;
import com.isnakebuzz.meetup.l.*;

import java.util.List;

import org.bukkit.*;
import org.bukkit.entity.*;

public class Scenarios extends Menu{
	
    @SuppressWarnings({ "unchecked" })
	public Scenarios(final Player player) {
        super(messagesmenu.getString("Scenarios.Menu"), 2);
    	this.updateInv();
    }
    
    @Override
    public void onClick(final Player p, final ItemStack itemStack) {
        final String displayName = itemStack.getItemMeta().getDisplayName();
        if (displayName.contains(messagesmenu.getString("Scenarios.FireLess.name").replaceAll("&", "§"))) {
        	if (!Votes.Fireless.contains(p)) {
        		deleteAll(p);
        		Votes.Fireless.add(p);
        	}
        }else if (displayName.contains(messagesmenu.getString("Scenarios.TimeBomb.name").replaceAll("&", "§"))) {
        	if (!Votes.TimeBomb.contains(p)) {
        		deleteAll(p);
        		Votes.TimeBomb.add(p);
        	}
        }else if (displayName.contains(messagesmenu.getString("Scenarios.BowLess.name").replaceAll("&", "§"))) {
        	if (!Votes.BowLess.contains(p)) {
        		deleteAll(p);
        		Votes.BowLess.add(p);
        	}
        }else if (displayName.contains(messagesmenu.getString("Scenarios.RodLess.name").replaceAll("&", "§"))) {
        	if (!Votes.RodLess.contains(p)) {
        		deleteAll(p);
        		Votes.RodLess.add(p);
        	}
        }else if (displayName.contains(messagesmenu.getString("Scenarios.NoGamemodes.name").replaceAll("&", "§"))) {
        	if (!Votes.Nogames.contains(p)) {
        		deleteAll(p);
        		Votes.Nogames.add(p);
        	}
        }else if (displayName.contains(messagesmenu.getString("Scenarios.NoClean.name").replaceAll("&", "§"))) {
        	if (!Votes.NoClean.contains(p)) {
        		deleteAll(p);
        		Votes.NoClean.add(p);
        	}
        }else if (displayName.contains(messagesmenu.getString("Scenarios.SoonItem").replaceAll("&", "§"))) {
            p.sendMessage(MessagesManager.SoonUpdate);
            p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 1.0f, 0.0f);
        }
        this.updateInv();
    }
    
    private void deleteAll(Player p) {
    	Votes.Fireless.remove(p);
    	Votes.NoClean.remove(p);
    	Votes.Nogames.remove(p);
    	Votes.RodLess.remove(p);
    	Votes.TimeBomb.remove(p);
    	Votes.BowLess.remove(p);
    }
    
    @SuppressWarnings("rawtypes")
	List stringList;
	@SuppressWarnings("rawtypes")
	List stringList2;
	@SuppressWarnings("rawtypes")
	List stringList3;
	@SuppressWarnings("rawtypes")
	List stringList4;
	@SuppressWarnings("rawtypes")
	List stringList5;
	@SuppressWarnings("rawtypes")
	List stringList6;
	
    
    @SuppressWarnings({ "unchecked" })
	private void updateInv() {
    	stringList = messagesmenu.getStringList("Scenarios.FireLess.lore");
    	stringList2 = messagesmenu.getStringList("Scenarios.NoGamemodes.lore");
    	stringList3 = messagesmenu.getStringList("Scenarios.TimeBomb.lore");
    	stringList4 = messagesmenu.getStringList("Scenarios.NoClean.lore");
    	stringList5 = messagesmenu.getStringList("Scenarios.RodLess.lore");
    	stringList6 = messagesmenu.getStringList("Scenarios.BowLess.lore");
    	
        this.s(0, ItemBuilder.crearItem1(346, Votes.getRodLessUsers(), 0, messagesmenu.getString("Scenarios.RodLess.name"), stringList5));
        this.s(1, ItemBuilder.crearItem(160, 1, 14, messagesmenu.getString("Scenarios.SoonItem")));
        this.s(2, ItemBuilder.crearItem1(259, Votes.getFireLessUsers(), 0, messagesmenu.getString("Scenarios.FireLess.name"), stringList));
        this.s(3, ItemBuilder.crearItem1(261, Votes.getBowLessUsers(), 0, messagesmenu.getString("Scenarios.BowLess.name"), stringList6));
        this.s(4, ItemBuilder.crearItem1(46, Votes.getTimeBombUsers(), 0, messagesmenu.getString("Scenarios.TimeBomb.name"), stringList3));
        this.s(5, ItemBuilder.crearItem(160, 1, 14, messagesmenu.getString("Scenarios.SoonItem")));
        this.s(6, ItemBuilder.crearItem(160, 1, 14, messagesmenu.getString("Scenarios.SoonItem")));
        this.s(7, ItemBuilder.crearItem(160, 1, 14, messagesmenu.getString("Scenarios.SoonItem")));
        this.s(9, ItemBuilder.crearItem(160, 1, 14, messagesmenu.getString("Scenarios.SoonItem")));
        this.s(8, ItemBuilder.crearItem1(276, Votes.getNoCleanUsers(), 0, messagesmenu.getString("Scenarios.NoClean.name"), stringList4));
        this.s(17, ItemBuilder.crearItem1(7, Votes.getNoGameModesUsers(), 0, messagesmenu.getString("Scenarios.NoGamemodes.name"), stringList2));
    }
    
    /*
    public String getStatusDK() {
        if (!UHC.get().getGM().isDeathKick()) {
            return "&c&l" + "[CHECK_BAD]".replace("[CHECK_BAD]", "\u2718");
        }
        return "&a&l" + "[CHECK_GOOD]".replace("[CHECK_GOOD]", "\u2714");
    }*/
}
