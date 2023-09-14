package ch.theo;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class TaskManager {
    String jsonPath;

    public TaskManager(String newJsonPath) {
        jsonPath = newJsonPath;
    }
    public void TaskManagerStart() {
        ProgramSelector(MenuManager(MenuName.Default));
    }

    public int MenuManager(MenuName menuName)
    {
        String choice;

        Scanner scanner = new Scanner(System.in);
        while(true) {
            DisplayMenu(menuName);
            choice = scanner.next();
            if (isInputValid(choice) == 0) {
                return(Integer.parseInt(choice));
            }
        }
    }

    private void DisplayMenu(MenuName menuName)
    {
        if (menuName == MenuName.Default) {
            System.out.println("Choose option for continue :");
            System.out.println("- Add task : 1");
            System.out.println("- Remove task : 2");
            System.out.println("- Show task list : 3");
            System.out.println("- Parameter of a task : 4");
            System.out.println("- Exit : 5");
        }

        if (menuName == MenuName.TaskParameterMenu) {
            System.out.println("Choose option for continue :");
            System.out.println("- Rename : 1");
            System.out.println("- Set notification: 2");
            System.out.println("- Exit : 5");
        }
    }

    public void ProgramSelector(int choice) {
        switch (choice) {
            case 1 -> Options.AddTask(jsonPath);
            case 2 -> Options.RemoveTask(jsonPath);
            case 3 -> Options.ShowTaskList(jsonPath);
            case 4 -> Options.TaskParameter(jsonPath);
            case 5 -> Options.Exit();
        }
    }

    public static int Confirmation(String description) {
        String choice;
        Scanner scanner = new Scanner(System.in);

        System.out.println(description + " -> " + "Confirm ? y/n");
        choice = scanner.next();
        while (!Objects.equals(choice, "y") && !Objects.equals(choice, "n")) {
            System.out.println("Confirm ? y/n");
            choice = scanner.next();
        }
        if (Objects.equals(choice, "n")) {
            return (1);
        }
        return (0);
    }

    public static String GetUserInput(String message)
    {
        String value;

        Scanner scanner = new Scanner(System.in);
        System.out.println(message + " :");
        value = scanner.next();
        while (TaskManager.Confirmation(value) == 1) {
            System.out.println(message + " :");
            value = scanner.next();
        }
        return (value);
    }

    private int isInputValid(String input)
    {
        String[] choiceTab = {"1", "2", "3", "4", "5"};
        for (String s : choiceTab) {
            if (Objects.equals(input, s)) {
                return (0);
            }
        }
        return (1);
    }
}
