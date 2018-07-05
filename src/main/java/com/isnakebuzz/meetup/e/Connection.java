package com.isnakebuzz.meetup.e;

import com.isnakebuzz.meetup.a.Main;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.bukkit.configuration.Configuration;

public class Connection {

    private Main plugin;
    private MongoClient mongoClient;

    public Connection(Main plugin) {
        this.plugin = plugin;
    }

    public void loadMongo() {
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Settings");
        if (!config.getBoolean("MongoDB.enabled")) return;
        String user = config.getString("MongoDB.user");
        String pass = config.getString("MongoDB.pass");
        String servers = config.getString("MongoDB.ips");
        String uri = "mongodb+srv://" + user + ":" + pass + "@" + servers + "/";

        MongoClientURI clientURI = new MongoClientURI(uri);
        mongoClient = new MongoClient(clientURI);
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
