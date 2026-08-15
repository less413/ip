import java.util.ArrayList;
import java.util.Scanner;

public class Dog {
    private static ArrayList<Task> taskList = new ArrayList<Task>();

    private static void displayTaskList() {
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + ". " + taskList.get(i));
        }
    }

    public static void main(String[] args) {
        String line = "____________________________________________________________";
        String banner = "      _____                \n"
                + "      |  __ \\  ____   ___ _ \n"
                + "      | |  | |/ __ \\ / __' |\n"
                + "      | |  | | |  | | |__| |\n"
                + "      | |__| | |__| |\\___  |\n"
                + "      |_____/ \\____/ ____/ |\n"
                + "                     \\____/ \n";
        String greeting = "WOOF WOOF How can I help? WOOF";

        System.out.println(banner);
        System.out.println(greeting);
        System.out.println(line);
        System.out.println("\n");
        
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            System.out.println(line);

            if (input.equalsIgnoreCase("bye")) {
                // Say goodbye and exit
                System.out.println("WOOF Goodbye! WOOF WOOF");
                System.out.println(line);
                break;
            } else if (input.equalsIgnoreCase("list")) {
                // Print list of tasks
                displayTaskList();
                System.out.println(line);
                System.out.println("\n");
            } else {
                // Add task to list
                Task newTask = new Task(input);
                taskList.add(newTask);
                System.out.println("WOOF! I've added a new task: \n" + newTask);
                System.out.println(line);
                System.out.println("\n");
            }
        }
    }
}
