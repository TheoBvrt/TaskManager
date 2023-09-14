package ch.theo;

import java.io.IOException;

public class Notification {
    public void SetNotification (String title, String description, String startDate, String endDate){
        try {
            String appleScript = String.format(
                    "try\n" +
                            "    tell application \"Calendar\"\n" +
                            "        tell calendar 1\n" +
                            "            make new event at end with properties {summary:\"%s\", description:\"%s\", start date:date \"%s\", end date:date \"%s\"}\n" +
                            "        end tell\n" +
                            "    end tell\n" +
                            "on error errMsg\n" +
                            "    display dialog \"Erreur lors de la création de l'événement : \" & errMsg\n" +
                            "end try", title, description, startDate, endDate);
            ProcessBuilder processBuilder = new ProcessBuilder("osascript", "-e", appleScript);
            processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
