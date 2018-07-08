package com.isnakebuzz.meetup.b;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.n.VEventFireless;

public class VoteEventManager {

    private Main plugin;

    private VEventFireless vEventFireless;

    public VoteEventManager(Main plugin) {
        this.plugin = plugin;
        this.vEventFireless = new VEventFireless(plugin);
    }

    public VEventFireless getEventFireless() {
        return vEventFireless;
    }
}
