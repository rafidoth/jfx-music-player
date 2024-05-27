package com.mp.demo;


public class LyricLine {
    private long startTime; // Time in milliseconds
    private String lyricText;

    public LyricLine(long startTime, String lyricText) {
        this.startTime = startTime;
        this.lyricText = lyricText;
    }

    public long getStartTime() {
        return startTime;
    }

    public String getLyricText() {
        return lyricText;
    }
}