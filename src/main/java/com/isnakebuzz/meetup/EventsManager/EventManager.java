package com.isnakebuzz.meetup.EventsManager;

import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.EventsManager.Events.*;

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
    private EventMeetupChecks eventGameWin;
    private EventHealth eventHealth;
    private EventCommand eventCommand;
    private EventEnderCD eventEnderCD;

    public EventManager(Main plugin) {
        this.plugin = plugin;
        this.eventEnderCD = new EventEnderCD(plugin);
        this.eventCommand = new EventCommand(plugin);
        this.eventHealth = new EventHealth(plugin);
        this.eventGameWin = new EventMeetupChecks(plugin);
        this.eventWorld = new EventWorld(plugin);
        this.eventDeath = new EventDeath(plugin);
        this.eventInteract = new EventInteract(plugin);
        this.eventJoinAndLeave = new EventJoinAndLeave(plugin);
        this.eventLogin = new EventLogin(plugin);
        this.eventSpectator = new EventSpectator(plugin);
        this.eventStarting = new EventStarting(plugin);
        this.eventWorldGen = new EventWorldGen(plugin);
    }

    public EventEnderCD getEventEnderCD() {
        return eventEnderCD;
    }

    public EventCommand getEventCommand() {
        return eventCommand;
    }

    public EventHealth getEventHealth() {
        return eventHealth;
    }

    public EventMeetupChecks getEventGameWin() {
        return eventGameWin;
    }

    public EventWorld getEventWorld() {
        return eventWorld;
    }

    public EventDeath getEventDeath() {
        return eventDeath;
    }

    public EventInteract getEventInteract() {
        return eventInteract;
    }

    public EventJoinAndLeave getEventJoinAndLeave() {
        return eventJoinAndLeave;
    }

    public EventLogin getEventLogin() {
        return eventLogin;
    }

    public EventSpectator getEventSpectator() {
        return eventSpectator;
    }

    public EventStarting getEventStarting() {
        return eventStarting;
    }

    public EventWorldGen getEventWorldGen() {
        return eventWorldGen;
    }
}
