package com.isnakebuzz.meetup.e;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.p.MySQL;
import com.isnakebuzz.meetup.p.PlayerDataInterface;
import com.isnakebuzz.meetup.p.VMongoDB;
import com.isnakebuzz.meetup.p.VMySQL;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;

public class Connection {

    private Main plugin;
    private MongoClient mongoClient;

    public Connection(Main plugin) {
        this.plugin = plugin;
    }

    public void loadMongo() {
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Extra/Database");
        String user = config.getString("MongoDB.user");
        String pass = config.getString("MongoDB.pass");
        String servers = config.getString("MongoDB.ips");
        String uri = "mongodb+srv://" + user + ":" + pass + "@" + servers + "/";

        MongoClientURI clientURI = new MongoClientURI(uri);
        mongoClient = new MongoClient(clientURI);
    }

    public void loadMySQL() {
        FileConfiguration config = plugin.getConfigUtils().getConfig(plugin, "Extra/Database");
        MySQL.host = config.getString("MySQL.hostname");
        MySQL.port = config.getInt("MySQL.port");
        MySQL.database = config.getString("MySQL.database");
        MySQL.username = config.getString("MySQL.username");
        MySQL.password = config.getString("MySQL.password");

        MySQL.connect();
        MySQL.update("CREATE TABLE IF NOT EXISTS UHCM_solo_stats (UUID VARCHAR(100), Wins Integer, Kills Integer, Deaths Integer)");
        MySQL.update("CREATE TABLE IF NOT EXISTS UHCM_team_stats (UUID VARCHAR(100), Wins Integer, Kills Integer, Deaths Integer)");
        MySQL.update("CREATE TABLE IF NOT EXISTS UHCM_inv (UUID VARCHAR(100), Inventory text)");
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public enum Database {
        MONGODB, MYSQL, NONE;
        public static Database database;
    }
}
