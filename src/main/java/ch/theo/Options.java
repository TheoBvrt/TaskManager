package ch.theo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class Options {
    public static void AddTask() {
        String taskName;
        String taskDescription;
        String taskExpirationDate;

        taskName =  TaskManager.GetUserInput("Name");
        taskDescription = TaskManager.GetUserInput("Description");
        taskExpirationDate = TaskManager.GetUserInput("Expiration date (mm.dd.yyyy)");

        ObjectMapper objectMapper = new ObjectMapper();
        TaskManager taskManager = new TaskManager();
        String jsonFilePath = "src/main/java/ch/theo/data.json";
        try {
            ObjectNode rootNode = (ObjectNode) objectMapper.readTree(new File(jsonFilePath));
            ObjectNode newCategory = objectMapper.createObjectNode();
            JsonNode jsonNode = rootNode.get("Task");
            if (jsonNode.has(taskName)) {
                System.out.println("This task already exists !");
                BackToHome(taskManager);
                return;
            }
            newCategory.put("name", taskName);
            newCategory.put("expirationData", taskExpirationDate);
            newCategory.put("description", taskDescription);
            rootNode.with("Task").set(taskName, newCategory);
            objectMapper.writeValue(new File(jsonFilePath), rootNode);
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public static void RemoveTask() {

    }

    public static void ShowTaskList() {

    }

    public static void TaskParameter() {
        TaskManager taskManager = new TaskManager();
        switch (taskManager.MenuManager(MenuName.TaskParameterMenu)) {
            case 5:
                BackToHome(taskManager);
                break;
        }
    }

    public static void BackToHome(TaskManager taskManager) {
        taskManager.ProgramSelector(taskManager.MenuManager(MenuName.Default));
    }

    public static void Exit() {
        System.exit(0);
    }
}
