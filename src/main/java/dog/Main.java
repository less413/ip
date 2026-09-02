package dog;

import java.io.IOException;
import java.util.Scanner;

import dog.exceptions.DogException;
import dog.ui.MainWindow;
import dog.ui.Ui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main entry point for the Dog application.
 */
public class Main extends Application {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Dog DOG = new Dog();
    private static final Ui UI = new Ui();

    /**
     * Orchestrates between user input, Dog processing, and UI display.
     *
     * @param args command line arguments (not used).
     */
    public static void main(String[] args) {
        UI.showDogMessage(DOG.getWelcomeString());

        while (SCANNER.hasNextLine()) {
            String input = SCANNER.nextLine();
            try {
                Dog.CommandResult result = DOG.processInput(input);
                UI.showDogMessage(result.getMessage());

                if (result.shouldExit()) {
                    break;
                }
            } catch (DogException e) {
                UI.showErrorMessage(e.getMessage());
            }
        }

        SCANNER.close();
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
            fxmlLoader.<MainWindow>getController().setDog(DOG);  // inject the Dog instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
