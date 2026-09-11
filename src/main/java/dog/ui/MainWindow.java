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

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/AvatarUser.png"));
    private final Image dogImage = new Image(this.getClass().getResourceAsStream("/images/AvatarDog.jpg"));

    /**
     * Initializes the main window GUI after FXML loading.
     * Sets up a listener on the dialog container's height to auto-scroll
     * the scroll pane to the bottom whenever new messages are added.
     */
    @FXML
    public void initialize() {
        //Solution below reused from https://github.com/NUS-CS2103-AY2627-S1/forum/issues/160
        dialogContainer.heightProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> scrollPane.setVvalue(1.0));
        });
    }

    /**
     * Injects the Dog instance
     */
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
        if (userText.isEmpty()) {
            return;
        }

        DialogBox userDialogBox = DialogBox.getUserDialog(userText, userImage);
        dialogContainer.getChildren().add(userDialogBox);

        try {
            Dog.CommandResult result = dog.processInput(userText);

            String dogText = result.getMessage();
            DialogBox replyDialogBox = DialogBox.getDogDialog(dogText, dogImage);
            dialogContainer.getChildren().add(replyDialogBox);

            if (result.shouldExit()) {
                Platform.exit();
            }
        } catch (DogException e) {
            String dogText = e.getMessage();
            DialogBox errorDialogBox = DialogBox.getDogErrorDialog(dogText, dogImage);
            dialogContainer.getChildren().add(errorDialogBox);
        }

        userInput.clear();
    }
}
