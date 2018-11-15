package com.isnakebuzz.meetup.Utils.Managers;

import com.isnakebuzz.meetup.Main;

public class TimerManager {

    private Main plugin;
    private int gameTime = 0;
    private int borderTime = 0;
    private int voteEnds = 0;
    private int startingTime = 0;
    private int epcooldown = 0;
    private int nccooldown = 0;

    public TimerManager(Main plugin) {
        this.epcooldown = plugin.getConfigUtils().getConfig(plugin, "Settings").getInt("GameOptions.EPearlCD.time") * 1000;
        this.nccooldown = plugin.getConfigUtils().getConfig(plugin, "Extra/Votes").getInt("NoClean.time") * 1000;
        this.plugin = plugin;
    }

    public String transformToDate(int seconds) {
        return getDurationString(seconds);
    }

    public int getGameTime() {
        return gameTime;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    public int getBorderTime() {
        return borderTime;
    }

    public void setBorderTime(int borderTime) {
        this.borderTime = borderTime;
    }

    public int getVoteEnds() {
        return voteEnds;
    }

    public void setVoteEnds(int voteEnds) {
        this.voteEnds = voteEnds;
    }

    public int getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(int startingTime) {
        this.startingTime = startingTime;
    }

    public int getEpcooldown() {
        return epcooldown;
    }

    public int getNcCooldown() {
        return nccooldown;
    }

    private String getDurationString(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;
        return twoDigitString(minutes) + ":" + twoDigitString(seconds);
    }

    public String toSecMs(Long l) {
        double seconds = l / 1000.0;
        return String.format("%.1f", seconds).replaceAll(",", ".");
    }

    private String twoDigitString(int number) {
        if (number == 0) {
            return "00";
        }
        if (number / 10 == 0) {
            return "0" + number;
        }
        return String.valueOf(number);
    }

}
