package dog;

import java.io.IOException;
import java.util.Scanner;

import dog.exceptions.DogException;
import dog.ui.MainWindow;
import dog.ui.Ui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main entry point for the Dog application.
 */
public class Main extends Application {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Dog dog = new Dog();
    private static final Ui ui = new Ui();

    private final Image appIcon = new Image(this.getClass().getResourceAsStream("/images/AvatarDog.jpg"));

    /**
     * Starts the Dog console application.
     * Orchestrates between user input, Dog processing, and UI display.
     *
     * @param args command line arguments (not used).
     */
    public static void main(String[] args) {
        ui.showDogMessage(dog.getWelcomeString());

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            try {
                Dog.CommandResult result = dog.processInput(input);
                ui.showDogMessage(result.getMessage());

                if (result.shouldExit()) {
                    break;
                }
            } catch (DogException e) {
                ui.showErrorMessage(e.getMessage());
            }
        }

        scanner.close();
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(640);
            stage.setMinWidth(480);
            stage.setTitle("im a dog woof woof");
            stage.getIcons().add(appIcon); // set window icon to dog image
            fxmlLoader.<MainWindow>getController().setDog(dog); // inject the Dog instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
