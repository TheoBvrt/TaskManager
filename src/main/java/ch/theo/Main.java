package ch.theo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        final String jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        final File jarFile = new File(jarPath);
        final  File parentDirectory = jarFile.getParentFile();
        final String jsonDefaultContent = "{\"Task\":{}}";

        Options.ClearTerminal();
        if (parentDirectory == null) {
            return ;
        }
        File jsonFile = new File(parentDirectory.getPath() + "/data.json");
        if (!jsonFile.exists()) {
            try {
                if (jsonFile.createNewFile()){
                    System.out.println("Json file was been created !");
                    FileWriter fileWriter = new FileWriter(jsonFile.getName(), true);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(jsonDefaultContent);
                    bufferedWriter.close();
                } else {
                    System.out.println("Error");
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        TaskManager taskManager = new TaskManager(parentDirectory.getPath() + "/data.json");
        taskManager.TaskManagerStart();
    }
}