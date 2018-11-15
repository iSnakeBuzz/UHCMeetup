package com.isnakebuzz.meetup.EventsManager;

import com.isnakebuzz.meetup.EventsManager.VoteEvents.VEventNoClean;
import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.EventsManager.VoteEvents.VEventFireless;

public class VoteEventManager {

    private Main plugin;

    private VEventFireless vEventFireless;
    private VEventNoClean vEventNoClean;

    public VoteEventManager(Main plugin) {
        this.plugin = plugin;
        this.vEventNoClean = new VEventNoClean(plugin);
        this.vEventFireless = new VEventFireless(plugin);
    }

    public VEventFireless getEventFireless() {
        return vEventFireless;
    }

    public VEventNoClean getEventNoClean() {
        return vEventNoClean;
    }
}
