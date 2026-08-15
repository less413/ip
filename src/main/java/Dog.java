import java.util.ArrayList;
import java.util.Scanner;

public class Dog {
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
                System.out.println("WOOF Goodbye! WOOF WOOF");
                System.out.println(line);
                break;
            } else {
                System.out.println(input);
                System.out.println(line);
                System.out.println("\n");
            }
        }
    }
}
