package com.isnakebuzz.meetup.EventsManager;

import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.EventsManager.Events.*;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class EventManager {

    private Main plugin;

    private EventInteract eventInteract;
    private EventJoinAndLeave eventJoinAndLeave;
    private EventLogin eventLogin;
    private EventSpectator eventSpectator;
    private EventStarting eventStarting;
    private EventWorldGen eventWorldGen;
    private EventDeath eventDeath;
    private EventWorld eventWorld;
    private EventMeetupChecks eventMeetupChecks;
    private EventHealth eventHealth;
    private EventCommand eventCommand;
    private EventEnderCD eventEnderCD;

    public EventManager(Main plugin) {
        this.plugin = plugin;
        this.eventEnderCD = new EventEnderCD(plugin);
        this.eventCommand = new EventCommand(plugin);
        this.eventHealth = new EventHealth(plugin);
        this.eventMeetupChecks = new EventMeetupChecks(plugin);
        this.eventWorld = new EventWorld(plugin);
        this.eventDeath = new EventDeath(plugin);
        this.eventInteract = new EventInteract(plugin);
        this.eventJoinAndLeave = new EventJoinAndLeave(plugin);
        this.eventLogin = new EventLogin(plugin);
        this.eventSpectator = new EventSpectator(plugin);
        this.eventStarting = new EventStarting(plugin);
        this.eventWorldGen = new EventWorldGen(plugin);
    }

    public void loadListeners() {
        plugin.log("Listener", "Loading listeners..");
        registerListener(this.eventInteract);
        registerListener(this.eventJoinAndLeave);
        registerListener(this.eventLogin);
        registerListener(this.eventSpectator);
        registerListener(this.eventStarting);
        registerListener(this.eventWorldGen);
        registerListener(this.eventDeath);
        registerListener(this.eventWorld);
        registerListener(this.eventMeetupChecks);
        registerListener(this.eventHealth);
        registerListener(this.eventCommand);
        registerListener(new EventMotd(plugin));
        if (plugin.getConfigUtils().getConfig(plugin, "Settings").getBoolean("GameOptions.EPearlCD.enabled")) {
            registerListener(this.eventEnderCD);
        }
    }

    public void registerListener(Listener listener) {
        plugin.log("Listener", "&5-&e Loaded listener &e" + listener.getClass().getSimpleName());
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    public void unregisterListener(Listener listener) {
        plugin.log("Listener", "&5-&e Unloading listener &e" + listener.getClass().getSimpleName());
        HandlerList.unregisterAll(listener);
    }

    //Getting events
    public EventInteract getEventInteract() {
        return eventInteract;
    }
}
