package ch.theo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class Options {
    public static void AddTask() {
        final String taskName = TaskManager.GetUserInput("Name");
        final String taskDescription = TaskManager.GetUserInput("Description");
        final String taskExpirationDate = TaskManager.GetUserInput("Expiration date (mm.dd.yyyy)");
        final String jsonFilePath = "src/main/java/ch/theo/data.json";

        final ObjectMapper objectMapper = new ObjectMapper();
        final TaskManager taskManager = new TaskManager();

        try {
            //Récupère un fichier json que je stock dans un ObjectNode
            final ObjectNode rootNode = (ObjectNode) objectMapper.readTree(new File(jsonFilePath));
            //Je créer une node a ajouter à mon Json
            final ObjectNode taskToAdd = objectMapper.createObjectNode();
            //Je récupère une node Json qui s'appelle Task
            final JsonNode jsonNode = rootNode.get("Task");

            //Je vérifie sur ma node Json à une sous node du même nom que la nouvelle.
            if (jsonNode.has(taskName)) {
                System.out.println("This task already exists !");
                BackToHome(taskManager);
                return;
            }

            //j'ajoute des données à ma futur node avec
            taskToAdd.put("name", taskName);
            taskToAdd.put("expirationData", taskExpirationDate);
            taskToAdd.put("description", taskDescription);

            //j'ajoute à la node Task la nouvelle node avec un nom et la node que j'ai créer au dessus
            rootNode.with("Task").set(taskName, taskToAdd);
            //j'écrit les nouvelles valeurs dans le fichier json
            objectMapper.writeValue(new File(jsonFilePath), rootNode);
        }
        catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("The task has been added !");
        BackToHome(taskManager);
    }

    public static void RemoveTask() {
        final String taskToDelete = TaskManager.GetUserInput("Task name");
        final String jsonFilePath = "src/main/java/ch/theo/data.json";

        final ObjectMapper objectMapper = new ObjectMapper();
        final TaskManager taskManager = new TaskManager();

        try {
            final JsonNode jsonNode = objectMapper.readTree(new File(jsonFilePath));
            final JsonNode taskNode = jsonNode.get("Task");
            if (!taskNode.has(taskToDelete)) {
                System.out.println("This task does not exist !");
                BackToHome(taskManager);
            }
            ((ObjectNode) taskNode).remove(taskToDelete);
            objectMapper.writeValue(new File(jsonFilePath), jsonNode);
        }
        catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("The task has been deleted !");
        BackToHome(taskManager);
    }

    public static void ShowTaskList() {
        final String jsonFilePath = "src/main/java/ch/theo/data.json";

        final ObjectMapper objectMapper = new ObjectMapper();
        final TaskManager taskManager = new TaskManager();
        try {
            final JsonNode jsonFileRoot = objectMapper.readTree(new File(jsonFilePath));
            final JsonNode taskNode = jsonFileRoot.get("Task");
            //permet de print tous les element retourner de forEachRemaining
            taskNode.fieldNames().forEachRemaining(System.out::println);
        }
        catch (IOException e) {
            System.out.println(e);
        }
        BackToHome(taskManager);
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
