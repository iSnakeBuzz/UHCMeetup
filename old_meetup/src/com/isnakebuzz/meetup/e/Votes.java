package com.isnakebuzz.meetup.e;

import static com.isnakebuzz.meetup.a.Main.messagesmenu;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.isnakebuzz.meetup.a.Main;

public class Votes {
    
	public static List<Player> Fireless = new ArrayList<Player>();
	public static List<Player> TimeBomb = new ArrayList<Player>();
	public static List<Player> NoClean = new ArrayList<Player>();
	public static List<Player> RodLess = new ArrayList<Player>();
	public static List<Player> BowLess = new ArrayList<Player>();
	public static List<Player> Nogames = new ArrayList<Player>();
	
	public static String getFireLess() {
		if (Fireless.size() > 0) {			
			double result = Fireless.size() * 100;
			result = result / Bukkit.getOnlinePlayers().size();
			
			DecimalFormat decimal = new DecimalFormat("###.##");
			return decimal.format(result).replaceAll(",", ".");
		}
		return "0.00";
	}
	
	public static String getTimeBomb() {
		if (TimeBomb.size() > 0) {			
			double result = TimeBomb.size() * 100;
			result = result / Bukkit.getOnlinePlayers().size();
			
			DecimalFormat decimal = new DecimalFormat("###.##");
			return decimal.format(result).replaceAll(",", ".");
		}
		return "0.00";
	}
	
	public static String getNoClean() {
		if (NoClean.size() > 0) {			
			double result = NoClean.size() * 100;
			result = result / Bukkit.getOnlinePlayers().size();
			
			DecimalFormat decimal = new DecimalFormat("###.##");
			return decimal.format(result).replaceAll(",", ".");
		}
		return "0.00";
	}
	
	public static String getRodLess() {
		if (RodLess.size() > 0) {			
			double result = RodLess.size() * 100;
			result = result / Bukkit.getOnlinePlayers().size();
			
			DecimalFormat decimal = new DecimalFormat("###.##");
			return decimal.format(result).replaceAll(",", ".");
		}
		return "0.00";
	}
	
	public static String getBowLess() {
		if (BowLess.size() > 0) {			
			double result = BowLess.size() * 100;
			result = result / Bukkit.getOnlinePlayers().size();
			
			DecimalFormat decimal = new DecimalFormat("###.##");
			return decimal.format(result).replaceAll(",", ".");
		}
		return "0.00";
	}
	
	public static String getNogames() {
		if (Nogames.size() > 0) {			
			double result = Nogames.size() * 100;
			result = result / Bukkit.getOnlinePlayers().size();
			
			DecimalFormat decimal = new DecimalFormat("###.##");
			return decimal.format(result).replaceAll(",", ".");
		}
		return "0.00";
	}
    
	public static int getFireLessUsers() {
		if (Fireless.size() == 0) {
			return 0;
		}
		return Fireless.size();
	}
	
	public static int getNoGameModesUsers() {
		if (Nogames.size() == 0) {
			return 0;
		}
		return Nogames.size();
	}
	
	public static int getNoCleanUsers() {
		if (NoClean.size() == 0) {
			return 0;
		}
		return NoClean.size();
	}

	public static int getRodLessUsers() {
		if (RodLess.size() == 0) {
			return 0;
		}
		return RodLess.size();
	}
	
	public static int getBowLessUsers() {
		if (BowLess.size() == 0) {
			return 0;
		}
		return BowLess.size();
	}
	
	public static int getTimeBombUsers() {
		if (TimeBomb.size() == 0) {
			return 0;
		}
		return TimeBomb.size();
	}
	
	public static String getPorcentage() {
		if (Bukkit.getOnlinePlayers().size() > 1) {
			
			double result = messagesmenu.getDouble("Scenarios.MinPorcent");
			
			DecimalFormat decimal = new DecimalFormat("###.##");
			return decimal.format(result).replaceAll(",", ".");
		}
		return "100.00";
	}
}