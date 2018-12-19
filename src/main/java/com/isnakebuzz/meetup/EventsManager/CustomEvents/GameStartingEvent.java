package com.isnakebuzz.meetup.EventsManager.CustomEvents;

import com.isnakebuzz.meetup.Utils.Enums.StartingType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Collection;

public class GameStartingEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Collection<Player> players;
    private StartingType startingType;

    public GameStartingEvent(Collection<Player> players, StartingType startingType) {
        this.players = players;
        this.startingType = startingType;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Collection<Player> getPlayers() {
        return players;
    }

    public StartingType getStartingType() {
        return startingType;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
