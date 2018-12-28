package com.isnakebuzz.meetup.Utils;

import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.Database.Core.MySQL;
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
        plugin.getMySQL_v2().connect();
    }

    public void disconnect() {
        if (Database.database == Database.MYSQL) {
            plugin.getMySQL_v2().disconnect();
        }
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public enum Database {
        MONGODB, MYSQL, NONE;
        public static Database database;
    }
}
