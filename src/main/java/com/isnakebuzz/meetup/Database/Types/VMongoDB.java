package com.isnakebuzz.meetup.Database.Types;

import com.isnakebuzz.meetup.Database.PlayerDataInterface;
import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.Player.GamePlayer;
import com.isnakebuzz.meetup.Player.PlayerInventory;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class VMongoDB implements PlayerDataInterface {

    private Main plugin;
    private MongoCollection<Document> collection_player_solo_stats;
    private MongoCollection<Document> collection_player_team_stats;
    private MongoCollection<Document> collection_player_invs;

    public VMongoDB(Main plugin) {
        this.plugin = plugin;
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Extra/Database");
        MongoClient mongoClient = plugin.getConnection().getMongoClient();

        MongoDatabase mongoDatabase = mongoClient.getDatabase("UHCMeetup");

        if (mongoDatabase.getCollection("player_solo_stats") == null)
            mongoDatabase.createCollection("player_solo_stats");
        this.collection_player_solo_stats = mongoDatabase.getCollection("player_solo_stats");

        if (mongoDatabase.getCollection("player_team_stats") == null)
            mongoDatabase.createCollection("player_team_stats");
        this.collection_player_team_stats = mongoDatabase.getCollection("player_team_stats");

        if (mongoDatabase.getCollection("player_invs") == null) mongoDatabase.createCollection("player_invs");
        this.collection_player_invs = mongoDatabase.getCollection("player_invs");
    }

    @Override
    public void loadPlayer(Player p) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, () -> {
            if (!isInPlayerInvs(p)) {
                Document player_data_doc = new Document("UUID", p.getUniqueId());
                Document found = (Document) collection_player_invs.find(player_data_doc).first();
                if (found == null) {
                    ItemStack[] inventory = plugin.getAutoKits().getInventory();
                    player_data_doc.append("Inventory", plugin.getInvManager().itemStackArrayToBase64(inventory));
                    this.collection_player_invs.insertOne(player_data_doc);
                    plugin.getPlayerManager().getUuidPlayerInventoryMap().put(p.getUniqueId(), new PlayerInventory(p.getUniqueId(), p, inventory));
                }
            } else {
                try {
                    loadPlayerInventory(p);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (Main.getMode.state == Main.getMode.SOLO) {
                if (!isInPlayerSoloStats(p)) {
                    Document player_data_doc = new Document("UUID", p.getUniqueId());
                    Document found = (Document) collection_player_solo_stats.find(player_data_doc).first();
                    if (found == null) {
                        int kills = 0;
                        int deaths = 0;
                        int wins = 0;
                        player_data_doc.append("Wins", wins);
                        player_data_doc.append("Kills", kills);
                        player_data_doc.append("Deaths", deaths);
                        this.collection_player_solo_stats.insertOne(player_data_doc);
                        plugin.getPlayerManager().getUuidGamePlayerMap().put(p.getUniqueId(), new GamePlayer(plugin, p, p.getUniqueId(), false, wins, kills, deaths));
                    }
                } else {
                    loadPlayerSoloStats(p);
                }
            } else if (Main.getMode.state == Main.getMode.TEAM) {
                if (!isInPlayerTeamStats(p)) {
                    Document player_data_doc = new Document("UUID", p.getUniqueId());
                    Document found = (Document) collection_player_team_stats.find(player_data_doc).first();
                    if (found == null) {
                        int kills = 0;
                        int deaths = 0;
                        int wins = 0;
                        player_data_doc.append("Wins", wins);
                        player_data_doc.append("Kills", kills);
                        player_data_doc.append("Deaths", deaths);
                        this.collection_player_team_stats.insertOne(player_data_doc);
                        plugin.getPlayerManager().getUuidGamePlayerMap().put(p.getUniqueId(), new GamePlayer(plugin, p, p.getUniqueId(), false, wins, kills, deaths));
                    }
                } else {
                    loadPlayerTeamStats(p);
                }
            }
        });
    }

    @Override
    public void savePlayer(Player p) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, () -> {
            savePlayerInventory(p);

            if (Main.getMode.state == Main.getMode.SOLO) {
                savePlayerSoloStats(p);
            } else if (Main.getMode.state == Main.getMode.TEAM) {
                savePlayerTeamStats(p);
            }
        });
    }

    private void savePlayerSoloStats(Player p) {
        if (!isInPlayerSoloStats(p)) {
            Document player_data_doc = new Document("UUID", p.getUniqueId());
            Document found = (Document) collection_player_solo_stats.find(player_data_doc).first();
            if (found != null) {
                GamePlayer gamePlayer = plugin.getPlayerManager().getUuidGamePlayerMap().get(p.getUniqueId());
                player_data_doc.append("Wins", gamePlayer.getWins());
                player_data_doc.append("Kills", gamePlayer.getKills());
                player_data_doc.append("Deaths", gamePlayer.getDeaths());
                this.collection_player_solo_stats.replaceOne(found, player_data_doc, new UpdateOptions().upsert(true));
            }
        }
    }

    private void savePlayerTeamStats(Player p) {
        if (!isInPlayerTeamStats(p)) {
            Document player_data_doc = new Document("UUID", p.getUniqueId());
            Document found = (Document) collection_player_team_stats.find(player_data_doc).first();
            if (found != null) {
                GamePlayer gamePlayer = plugin.getPlayerManager().getUuidGamePlayerMap().get(p.getUniqueId());
                player_data_doc.append("Wins", gamePlayer.getWins());
                player_data_doc.append("Kills", gamePlayer.getKills());
                player_data_doc.append("Deaths", gamePlayer.getDeaths());
                this.collection_player_team_stats.replaceOne(found, player_data_doc, new UpdateOptions().upsert(true));
            }
        }
    }

    private void savePlayerInventory(Player p) {
        if (!isInPlayerInvs(p)) {
            Document player_data_doc = new Document("UUID", p.getUniqueId());
            Document found = (Document) collection_player_invs.find(player_data_doc).first();
            if (found != null) {
                PlayerInventory playerInventory = plugin.getPlayerManager().getUuidPlayerInventoryMap().get(p.getUniqueId());
                player_data_doc.append("Inventory", (Object) playerInventory.getInventory());
                this.collection_player_invs.replaceOne(found, player_data_doc, new UpdateOptions().upsert(true));
            }
        }
    }

    private void loadPlayerSoloStats(Player p) {
        if (isInPlayerSoloStats(p)) {
            Document player_data_doc = new Document("UUID", p.getUniqueId());
            Document found = (Document) collection_player_solo_stats.find(player_data_doc).first();
            if (found != null) {
                int wins = found.getInteger("Wins");
                int kills = found.getInteger("Kills");
                int deaths = found.getInteger("Deaths");
                plugin.getPlayerManager().getUuidGamePlayerMap().put(p.getUniqueId(), new GamePlayer(plugin, p, p.getUniqueId(), false, wins, kills, deaths));
            }
        }
    }

    private void loadPlayerTeamStats(Player p) {
        if (isInPlayerSoloStats(p)) {
            Document player_data_doc = new Document("UUID", p.getUniqueId());
            Document found = (Document) collection_player_team_stats.find(player_data_doc).first();
            if (found != null) {
                int wins = found.getInteger("Wins");
                int kills = found.getInteger("Kills");
                int deaths = found.getInteger("Deaths");
                plugin.getPlayerManager().getUuidGamePlayerMap().put(p.getUniqueId(), new GamePlayer(plugin, p, p.getUniqueId(), false, wins, kills, deaths));
            }
        }
    }

    private void loadPlayerInventory(Player p) throws IOException {
        if (isInPlayerSoloStats(p)) {
            Document player_data_doc = new Document("UUID", p.getUniqueId());
            Document found = (Document) collection_player_invs.find(player_data_doc).first();
            if (found != null) {
                ItemStack[] inventory = plugin.getInvManager().itemStackArrayFromBase64(found.getString("Inventory"));
                plugin.getPlayerManager().getUuidPlayerInventoryMap().put(p.getUniqueId(), new PlayerInventory(p.getUniqueId(), p, inventory));
            }
        }
    }

    private boolean isInPlayerSoloStats(Player p) {
        Document player_data_doc = new Document("UUID", p.getUniqueId());
        Document found = (Document) collection_player_solo_stats.find(player_data_doc).first();
        if (found == null) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isInPlayerTeamStats(Player p) {
        Document player_data_doc = new Document("UUID", p.getUniqueId());
        Document found = (Document) collection_player_team_stats.find(player_data_doc).first();
        if (found == null) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isInPlayerInvs(Player p) {
        Document player_data_doc = new Document("UUID", p.getUniqueId());
        Document found = (Document) collection_player_invs.find(player_data_doc).first();
        if (found == null) {
            return false;
        } else {
            return true;
        }
    }
}
