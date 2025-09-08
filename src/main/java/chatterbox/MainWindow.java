package chatterbox;

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

    private Chatterbox chatterbox;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    private final String LOGO =
                      "  ____ _             _   _            ____              \n"
                    + " / ___  | |___  ____ _| |_| |_ ___ _ __| __ ) _____  __   \n"
                    + "| |           |    '_ \\/ _` |_  __/ _ \\ '__|  _ \\ / _ \\ \\/ /  \n"
                    + "| |___  | | | | (_| | | ||  __/ |  | |_) | (_) >  <   \n"
                    + " \\____ |_| |_|\\__,_|\\__|\\__\\___|_|  |____/ \\___/_/\\_\\  \n"
                    + "                                                     ";

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Chatterbox instance */
    public void setChatterbox(Chatterbox d) {
        chatterbox = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = chatterbox.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();
    }

    public void showWelcome() {
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(LOGO, dukeImage)
        );

        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog("Hello! I'm Chatterbox\nWhat can I do for you?", dukeImage)
        );
    }

}