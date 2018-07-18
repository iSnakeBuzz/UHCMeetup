package com.isnakebuzz.meetup.p;

import org.bukkit.entity.Player;

import java.io.IOException;
import java.sql.SQLException;

public interface PlayerDataInterface {

    public void loadPlayer(Player p) throws IOException, SQLException;

    public void savePlayer(Player p);

}
