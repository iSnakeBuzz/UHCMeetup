package com.isnakebuzz.meetup.EventsManager.CustomEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Collection;

public class GameStartEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Collection<Player> players;

    public GameStartEvent(Collection<Player> players) {
        this.players = players;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Collection<Player> getPlayers() {
        return players;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
