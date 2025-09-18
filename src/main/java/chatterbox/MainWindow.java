package chatterbox;


import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


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
            "☁️☁️ Chatterbox ☁️⭐\n"
                    + "  Talking from the clouds! ☁️\n"
                    + "  Helping you, one chat at a time ⭐";
    @FXML
    public void initialize() {
        Font.loadFont(getClass().getResourceAsStream("/fonts/Quicksand-Regular.ttf"), 14);
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.setSpacing(10);
        dialogContainer.setPadding(new Insets(10));
        dialogContainer.setBackground(new Background(new BackgroundFill(Color.web("#F3E8FF"),
                                      CornerRadii.EMPTY, Insets.EMPTY)));
        scrollPane.setFitToWidth(true);
        userInput.setStyle("-fx-font-family: 'Quicksand'; -fx-font-size: 14px;" +
                " -fx-background-radius: 10; -fx-border-radius: 10;" +
                " -fx-border-color: #FFB6C1; -fx-border-width: 2px;");
        sendButton.setStyle("-fx-font-family: 'Quicksand'; -fx-font-size: 14px;" +
                " -fx-background-color: #FF9AA2; -fx-text-fill: white; -fx-background-radius: 10;");
        sendButton.setText("✈️");
    }

    /** Injects the Chatterbox instance */
    public void setChatterbox(Chatterbox d) {
        chatterbox = d;
    }

    /** Handle user input */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = chatterbox.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage));
        userInput.clear();
    }

    public void showWelcome() {
        dialogContainer.getChildren().add(DialogBox.getDukeDialog(LOGO, dukeImage));

        dialogContainer.getChildren().add(DialogBox.getDukeDialog("Hello! I'm Chatterbox\nWhat can I do for you?",
                                                                   dukeImage));
    }
}



