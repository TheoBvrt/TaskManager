package ch.theo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class Options {
    public static void AddTask() {
        final String taskName = TaskManager.GetUserInput("Name");
        final String taskDescription = TaskManager.GetUserInput("Description");
        final String taskExpirationDate = TaskManager.GetUserInput("Expiration date (mm.dd.yyyy)");

        final ObjectMapper objectMapper = new ObjectMapper();
        final TaskManager taskManager = new TaskManager();

        try {
            final ObjectNode rootNode = (ObjectNode) objectMapper.readTree(new File(Setting.jsonPath)); //récupère le Json
            final ObjectNode taskToAdd = objectMapper.createObjectNode(); //Créer une futur node vide
            final JsonNode jsonNode = rootNode.get("Task"); //Récupère la node "Task"

            if (jsonNode.has(taskName)) { //Vérifie si la node existe pas
                System.out.println("This task already exists !");
                BackToHome(taskManager);
                return;
            }
            taskToAdd.put("name", taskName); //ajoute les donnée à futur node
            taskToAdd.put("expirationData", taskExpirationDate);
            taskToAdd.put("description", taskDescription);
            rootNode.with("Task").set(taskName, taskToAdd); //ajoute la nouvelle node
            objectMapper.writeValue(new File(Setting.jsonPath), rootNode); //écrire les nouvelle node
        }
        catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("The task has been added !");
        BackToHome(taskManager);
    }

    public static void RemoveTask() {
        final String taskToDelete = TaskManager.GetUserInput("Task name");

        final ObjectMapper objectMapper = new ObjectMapper();
        final TaskManager taskManager = new TaskManager();

        try {
            final JsonNode jsonNode = objectMapper.readTree(new File(Setting.jsonPath));
            final JsonNode taskNode = jsonNode.get("Task");
            if (!taskNode.has(taskToDelete)) {
                System.out.println("This task does not exist !");
                BackToHome(taskManager);
            }
            ((ObjectNode) taskNode).remove(taskToDelete); //retire la node
            objectMapper.writeValue(new File(Setting.jsonPath), jsonNode); //met à jours le Json
        }
        catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("The task has been deleted !");
        BackToHome(taskManager);
    }

    public static void ShowTaskList() {
        final ObjectMapper objectMapper = new ObjectMapper();
        final TaskManager taskManager = new TaskManager();
        try {
            final JsonNode jsonFileRoot = objectMapper.readTree(new File(Setting.jsonPath));
            final JsonNode taskNode = jsonFileRoot.get("Task");
            taskNode.fieldNames().forEachRemaining(System.out::println); //print tous les element retourner de forEachRemaining
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
