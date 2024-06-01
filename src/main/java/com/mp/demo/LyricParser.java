package com.mp.demo;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;




public class LyricParser {
    public static ArrayList<LyricLine> parseLyricsFile(String lyricsPath) throws IOException {
        ArrayList<LyricLine> lyrics = new ArrayList<>();
        File file = new File("lyrics/"+lyricsPath);
        try (BufferedReader reader = new BufferedReader(new FileReader(file.getAbsoluteFile().getPath()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[") && line.contains("]")) {
                    String timeString = line.substring(1, line.indexOf("]"));
                    long startTime = parseTime(timeString);
                    String lyricText = line.substring(line.indexOf("]") + 1).trim();
                    lyrics.add(new LyricLine(startTime, lyricText));
                }
            }
        } catch (IOException e) {
            return null;
        }
        return lyrics;
    }

    private static long parseTime(String timeString) {
        String[] parts = timeString.split(":");
        int minutes = Integer.parseInt(parts[0]);
        float seconds = Float.parseFloat(parts[1]);
        return (long) ((minutes * 60L + seconds) * 1000);
    }
}
