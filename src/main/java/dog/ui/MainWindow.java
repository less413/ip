package dog.ui;

import dog.Dog;
import dog.exceptions.DogException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Dog dog;

    private final Image USER_IMAGE = new Image(this.getClass().getResourceAsStream("/images/AvatarUser.png"));
    private final Image DOG_IMAGE = new Image(this.getClass().getResourceAsStream("/images/AvatarDog.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Dog instance */
    public void setDog(Dog d) {
        dog = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Dog's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String userText = userInput.getText();
        // add user chat bubble
        dialogContainer.getChildren().add(DialogBox.getUserDialog(userText, USER_IMAGE));

        try {
            Dog.CommandResult result = dog.processInput(userText);
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
