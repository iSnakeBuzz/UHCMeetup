package com.isnakebuzz.meetup.Database.Types;

import com.isnakebuzz.meetup.Database.PlayerDataInterface;
import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.Player.GamePlayer;
import com.isnakebuzz.meetup.Player.PlayerInventory;
import com.isnakebuzz.meetup.Utils.Callback;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VMySQL_v2 implements PlayerDataInterface {

    private Main plugin;

    public VMySQL_v2(Main plugin) {
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
        isPlayerInSoloStats(p, isPlayerInSoloStats -> {
            if (!isPlayerInSoloStats) {
                plugin.getMySQL_v2().prepareStatement("INSERT INTO UHCM_solo_stats (UUID, Wins, Kills, Deaths) VALUES ('" + p.getUniqueId().toString() + "', '0', '0', '0');", value -> {
                });
            } else {
                GamePlayer gamePlayer = plugin.getPlayerManager().getUuidGamePlayerMap().get(p.getUniqueId());
                plugin.getMySQL_v2().prepareStatement("UPDATE UHCM_solo_stats SET Wins='" + gamePlayer.getWins() + "', Kills='" + gamePlayer.getKills() + "', Deaths='" + gamePlayer.getDeaths() + "' WHERE UUID='" + p.getUniqueId() + "'", value -> {
                });
            }
        });
    }

    private void savePlayerTeamStats(Player p) {
        isPlayerInTeamStats(p, isPlayerInTeamStats -> {
            if (!isPlayerInTeamStats) {
                plugin.getMySQL_v2().prepareStatement("INSERT INTO UHCM_team_stats (UUID, Wins, Kills, Deaths) VALUES ('" + p.getUniqueId().toString() + "', '0', '0', '0');", value -> {
                });
            } else {
                GamePlayer gamePlayer = plugin.getPlayerManager().getUuidGamePlayerMap().get(p.getUniqueId());
                plugin.getMySQL_v2().prepareStatement("UPDATE UHCM_team_stats SET Wins='" + gamePlayer.getWins() + "', Kills='" + gamePlayer.getKills() + "', Deaths='" + gamePlayer.getDeaths() + "' WHERE UUID='" + p.getUniqueId() + "'", value -> {
                });
            }
        });
    }

    private void savePlayerInventory(Player p) {
        isPlayerInInventory(p, isPlayerInInventory -> {
            if (!isPlayerInInventory) {
                ItemStack[] inventory = plugin.getAutoKits().getInventory();
                plugin.getMySQL_v2().prepareStatement("INSERT INTO UHCM_inv (UUID, Inventory) VALUES ('" + p.getUniqueId().toString() + "', '" + plugin.getInvManager().itemStackArrayToBase64(inventory) + "');", value -> {
                });
            } else {
                PlayerInventory playerInventory = plugin.getPlayerManager().getUuidPlayerInventoryMap().get(p.getUniqueId());
                plugin.getMySQL_v2().prepareStatement("UPDATE UHCM_inv SET Inventory='" + plugin.getInvManager().itemStackArrayToBase64(playerInventory.getInventory()) + "' WHERE UUID='" + p.getUniqueId() + "'", value -> {
                });
            }
        });
    }

    private void loadPlayerSoloStats(Player p) {
        isPlayerInSoloStats(p, isPlayerInSoloStats -> {
            if (!isPlayerInSoloStats) {
                plugin.getMySQL_v2().prepareStatement("INSERT INTO UHCM_solo_stats (UUID, Wins, Kills, Deaths) VALUES ('" + p.getUniqueId().toString() + "', '0', '0', '0');", value -> {
                });
                plugin.getPlayerManager().getUuidGamePlayerMap().put(p.getUniqueId(), new GamePlayer(plugin, p, p.getUniqueId(), false, 0, 0, 0));
            } else {
                GamePlayer gamePlayer = new GamePlayer(plugin, p, p.getUniqueId(), false, 0, 0, 0);

                plugin.getMySQL_v2().prepareStatement("SELECT * FROM UHCM_solo_stats WHERE UUID='" + p.getUniqueId().toString() + "'", rs -> {
                    try {
                        if (rs.next()) gamePlayer.setWins(rs.getInt("Wins"));
                        if (rs.next()) gamePlayer.setKills(rs.getInt("Kills"));
                        if (rs.next()) gamePlayer.setDeaths(rs.getInt("Deaths"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    plugin.getPlayerManager().getUuidGamePlayerMap().put(p.getUniqueId(), gamePlayer);
                });

            }
        });
    }

    private void loadPlayerTeamStats(Player p) {
        isPlayerInTeamStats(p, isPlayerInTeamStats -> {
            if (!isPlayerInTeamStats) {
                plugin.getMySQL_v2().prepareStatement("INSERT INTO UHCM_team_stats (UUID, Wins, Kills, Deaths) VALUES ('" + p.getUniqueId().toString() + "', '0', '0', '0');", value -> {
                });
                plugin.getPlayerManager().getUuidGamePlayerMap().put(p.getUniqueId(), new GamePlayer(plugin, p, p.getUniqueId(), false, 0, 0, 0));
            } else {
                GamePlayer gamePlayer = new GamePlayer(plugin, p, p.getUniqueId(), false, 0, 0, 0);

                plugin.getMySQL_v2().prepareStatement("SELECT * FROM UHCM_team_stats WHERE UUID='" + p.getUniqueId().toString() + "'", rs -> {
                    try {
                        if (rs.next()) gamePlayer.setWins(rs.getInt("Wins"));
                        if (rs.next()) gamePlayer.setKills(rs.getInt("Kills"));
                        if (rs.next()) gamePlayer.setDeaths(rs.getInt("Deaths"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    plugin.getPlayerManager().getUuidGamePlayerMap().put(p.getUniqueId(), gamePlayer);
                });
            }
        });
    }

    private void loadPlayerInventory(Player p) {
        isPlayerInInventory(p, isPlayerInventory -> {
            if (!isPlayerInventory) {
                ItemStack[] inventory = plugin.getAutoKits().getInventory();
                plugin.getMySQL_v2().prepareStatement("INSERT INTO UHCM_inv (UUID, Inventory) VALUES ('" + p.getUniqueId().toString() + "', '" + plugin.getInvManager().itemStackArrayToBase64(inventory) + "');", value -> {
                });
                plugin.getPlayerManager().getUuidPlayerInventoryMap().put(p.getUniqueId(), new PlayerInventory(p.getUniqueId(), p, inventory));
            } else {
                plugin.getMySQL_v2().prepareStatement("SELECT * FROM UHCM_inv WHERE UUID='" + p.getUniqueId().toString() + "'", rs -> {
                    ItemStack[] inventory = null;
                    try {
                        if (rs.next())
                            inventory = plugin.getInvManager().itemStackArrayFromBase64(rs.getString("Inventory"));
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }

                    PlayerInventory playerInventory = new PlayerInventory(p.getUniqueId(), p, inventory);
                    plugin.getPlayerManager().getUuidPlayerInventoryMap().put(p.getUniqueId(), playerInventory);
                });
            }
        });
    }

    private void isPlayerInInventory(Player p, Callback<Boolean> callback) {
        plugin.getMySQL_v2().prepareStatement("SELECT * FROM FROM UHCM_inv WHERE UUID='" + p.getUniqueId().toString() + "'", rs -> {
            try {
                if (rs.next()) {
                    callback.done(rs.getString("UUID") != null);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void isPlayerInTeamStats(Player p, Callback<Boolean> callback) {
        plugin.getMySQL_v2().prepareStatement("SELECT * FROM UHCM_team_stats WHERE UUID='" + p.getUniqueId().toString() + "'", rs -> {
            try {
                if (rs.next()) {
                    callback.done(rs.getString("UUID") != null);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void isPlayerInSoloStats(Player p, Callback<Boolean> callback) {
        plugin.getMySQL_v2().prepareStatement("SELECT * FROM UHCM_solo_stats WHERE UUID='" + p.getUniqueId().toString() + "'", rs -> {
            try {
                if (rs.next()) {
                    callback.done(rs.getString("UUID") != null);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

}
