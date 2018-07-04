package com.isnakebuzz.meetup.e;

import com.isnakebuzz.meetup.a.Main;

public class TimerManager {

    private Main plugin;
    private int gameTime = 0;
    private int borderTime = 0;
    private int voteEnds = 0;
    private int startingTime = 0;

    public TimerManager(Main plugin) {
        this.plugin = plugin;
    }

    public String transformToDate(int seconds) {
        return getDurationString(seconds);
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    public int getGameTime() {
        return gameTime;
    }

    public void setBorderTime(int borderTime) {
        this.borderTime = borderTime;
    }

    public int getBorderTime() {
        return borderTime;
    }

    public void setVoteEnds(int voteEnds) {
        this.voteEnds = voteEnds;
    }

    public int getVoteEnds() {
        return voteEnds;
    }

    public int getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(int startingTime) {
        this.startingTime = startingTime;
    }

    private String getDurationString(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;
        return twoDigitString(minutes) + ":" + twoDigitString(seconds);
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
