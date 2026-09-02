package dog;

import java.util.Scanner;

import dog.exceptions.DogException;
import dog.ui.DialogBox;
import dog.ui.Ui;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main entry point for the Dog application.
 */
public class Main extends Application {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Dog DOG = new Dog();
    private static final Ui UI = new Ui();

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    private final Image USER_IMAGE = new Image(this.getClass().getResourceAsStream("/images/AvatarUser.png"));
    private final Image DOG_IMAGE = new Image(this.getClass().getResourceAsStream("/images/AvatarDog.jpg"));

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
        // Setting up required components

        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        //Handling user input

        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });
        userInput.setOnAction((event) -> {
            handleUserInput();
        });

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        // Formatting the window to look as expected

        stage.setTitle("i'm a dog woof woof");
        stage.getIcons().add(DOG_IMAGE); // sets window icon to dog image
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        // Scroll down to the end every time dialogContainer's height changes.
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creates a dialog box containing user input, and appends it to
     * the dialog container. Clears the user input after processing.
     */
    private void handleUserInput() {
        String userText = userInput.getText();
        // add user chat bubble
        dialogContainer.getChildren().add(DialogBox.getUserDialog(userText, USER_IMAGE));

        try {
            Dog.CommandResult result = DOG.processInput(userText);
            String dogText = result.getMessage();
            // add dog chat bubble
            dialogContainer.getChildren().add(DialogBox.getDogDialog(dogText, DOG_IMAGE));

            if (result.shouldExit()) {
                Platform.exit();
            }
        } catch (DogException e) {
            String dogText = e.getMessage();
            // add dog chat bubble
            dialogContainer.getChildren().add(DialogBox.getDogDialog(dogText, DOG_IMAGE));
        }

        userInput.clear();
    }
}
