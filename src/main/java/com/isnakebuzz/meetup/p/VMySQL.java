package com.isnakebuzz.meetup.p;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.d.GamePlayer;
import com.isnakebuzz.meetup.d.PlayerInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VMySQL implements PlayerDataInterface {

    private Main plugin;

    public VMySQL(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void loadPlayer(Player p) throws SQLException, IOException {
        loadPlayerInventory(p);
        if (Main.getMode.state.equals(Main.getMode.SOLO)) {
            loadPlayerSoloStats(p);
        } else if (Main.getMode.state.equals(Main.getMode.TEAM)) {
            loadPlayerTeamStats(p);
        }
    }

    @Override
    public void savePlayer(Player p) {
        savePlayerInventory(p);
        if (Main.getMode.state.equals(Main.getMode.SOLO)) {
            savePlayerSoloStats(p);
        } else if (Main.getMode.state.equals(Main.getMode.TEAM)) {
            savePlayerTeamStats(p);
        }
    }

    private void savePlayerSoloStats(Player p) {
        if (!isPlayerInSoloStats(p)) {
            MySQL.update("INSERT INTO UHCM_solo_stats (UUID, Wins, Kills, Deaths) VALUES ('" + p.getUniqueId().toString() + "', '0', '0', '0');");
        } else {
            GamePlayer gamePlayer = plugin.getPlayerManager().getUuidGamePlayerMap().get(p.getUniqueId());
            MySQL.update("UPDATE UHCM_solo_stats SET Wins='" + gamePlayer.getWins() + "', Kills='" + gamePlayer.getKills() + "', Deaths='" + gamePlayer.getDeaths() + "' WHERE UUID='" + p.getUniqueId() + "'");
        }
    }

    private void savePlayerTeamStats(Player p) {
        if (!isPlayerInTeamStats(p)) {
            MySQL.update("INSERT INTO UHCM_team_stats (UUID, Wins, Kills, Deaths) VALUES ('" + p.getUniqueId().toString() + "', '0', '0', '0');");
        } else {
            GamePlayer gamePlayer = plugin.getPlayerManager().getUuidGamePlayerMap().get(p.getUniqueId());
            MySQL.update("UPDATE UHCM_team_stats SET Wins='" + gamePlayer.getWins() + "', Kills='" + gamePlayer.getKills() + "', Deaths='" + gamePlayer.getDeaths() + "' WHERE UUID='" + p.getUniqueId() + "'");
        }
    }

    private void savePlayerInventory(Player p) {
        if (!isPlayerInTeamStats(p)) {
            ItemStack[] inventory = plugin.getAutoKits().getInventory();
            MySQL.update("INSERT INTO UHCM_inv (UUID, Inventory) VALUES ('" + p.getUniqueId().toString() + "', '" + plugin.getInvManager().itemStackArrayToBase64(inventory) + "');");
        } else {
            PlayerInventory playerInventory = plugin.getPlayerManager().getUuidPlayerInventoryMap().get(p.getUniqueId());
            MySQL.update("UPDATE UHCM_team_stats SET Wins='" + plugin.getInvManager().itemStackArrayToBase64(playerInventory.getInventory()) + "' WHERE UUID='" + p.getUniqueId() + "'");
        }
    }

    private void loadPlayerSoloStats(Player p) throws SQLException {
        if (!isPlayerInSoloStats(p)) {
            MySQL.update("INSERT INTO UHCM_solo_stats (UUID, Wins, Kills, Deaths) VALUES ('" + p.getUniqueId().toString() + "', '0', '0', '0');");
            plugin.getPlayerManager().getUuidGamePlayerMap().put(p.getUniqueId(), new GamePlayer(plugin, p, p.getUniqueId(), false, 0, 0, 0));
        } else {
            GamePlayer gamePlayer = new GamePlayer(plugin, p, p.getUniqueId(), false, 0, 0, 0);

            ResultSet player = MySQL.query("SELECT * FROM UHCM_solo_stats WHERE UUID='" + p.getUniqueId().toString() + "'");
            if (player.next()) gamePlayer.setWins(player.getInt("Wins"));
            if (player.next()) gamePlayer.setKills(player.getInt("Kills"));
            if (player.next()) gamePlayer.setDeaths(player.getInt("Deaths"));

            plugin.getPlayerManager().getUuidGamePlayerMap().put(p.getUniqueId(), gamePlayer);
        }
    }

    private void loadPlayerTeamStats(Player p) throws SQLException {
        if (!isPlayerInTeamStats(p)) {
            MySQL.update("INSERT INTO UHCM_team_stats (UUID, Wins, Kills, Deaths) VALUES ('" + p.getUniqueId().toString() + "', '0', '0', '0');");
            plugin.getPlayerManager().getUuidGamePlayerMap().put(p.getUniqueId(), new GamePlayer(plugin, p, p.getUniqueId(), false, 0, 0, 0));
        } else {
            GamePlayer gamePlayer = new GamePlayer(plugin, p, p.getUniqueId(), false, 0, 0, 0);

            ResultSet player = MySQL.query("SELECT * FROM UHCM_team_stats WHERE UUID='" + p.getUniqueId().toString() + "'");
            if (player.next()) gamePlayer.setWins(player.getInt("Wins"));
            if (player.next()) gamePlayer.setKills(player.getInt("Kills"));
            if (player.next()) gamePlayer.setDeaths(player.getInt("Deaths"));

            plugin.getPlayerManager().getUuidGamePlayerMap().put(p.getUniqueId(), gamePlayer);
        }
    }

    private void loadPlayerInventory(Player p) throws SQLException, IOException {
        if (!isPlayerInInventory(p)) {
            ItemStack[] inventory = plugin.getAutoKits().getInventory();
            MySQL.update("INSERT INTO UHCM_inv (UUID, Inventory) VALUES ('" + p.getUniqueId().toString() + "', '" + plugin.getInvManager().itemStackArrayToBase64(inventory) + "');");
            plugin.getPlayerManager().getUuidPlayerInventoryMap().put(p.getUniqueId(), new PlayerInventory(p.getUniqueId(), p, inventory));
        } else {

            ResultSet player = MySQL.query("SELECT * FROM UHCM_inv WHERE UUID='" + p.getUniqueId().toString() + "'");
            ItemStack[] inventory = null;
            if (player.next())
                inventory = plugin.getInvManager().itemStackArrayFromBase64(player.getString("Inventory"));

            PlayerInventory playerInventory = new PlayerInventory(p.getUniqueId(), p, inventory);
            plugin.getPlayerManager().getUuidPlayerInventoryMap().put(p.getUniqueId(), playerInventory);
        }
    }

    private boolean isPlayerInInventory(Player p) {
        try {
            final ResultSet rs = MySQL.query("SELECT * FROM UHCM_inv WHERE UUID='" + p.getUniqueId().toString() + "'");
            return rs.next() && rs.getString("UUID") != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isPlayerInTeamStats(Player p) {
        try {
            final ResultSet rs = MySQL.query("SELECT * FROM UHCM_team_stats WHERE UUID='" + p.getUniqueId().toString() + "'");
            return rs.next() && rs.getString("UUID") != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isPlayerInSoloStats(Player p) {
        try {
            final ResultSet rs = MySQL.query("SELECT * FROM UHCM_solo_stats WHERE UUID='" + p.getUniqueId().toString() + "'");
            return rs.next() && rs.getString("UUID") != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
