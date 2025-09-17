package chatterbox;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's avatar
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set text and avatar image
        dialog.setText(text);
        displayPicture.setImage(img);

        // Wrap text and spacing
        dialog.setWrapText(true);
        dialog.setPadding(new Insets(10));
        setSpacing(10);

        // Apply shadow effect
        DropShadow shadow = new DropShadow();
        shadow.setRadius(3);
        shadow.setOffsetX(1);
        shadow.setOffsetY(1);
        shadow.setColor(Color.rgb(0, 0, 0, 0.15));
        dialog.setEffect(shadow);

        // User bubble: Soft Coral
        dialog.setStyle("-fx-font-family: 'Quicksand'; -fx-font-size: 14px; -fx-text-fill: white;");
        dialog.setBackground(new Background(new BackgroundFill(Color.web("#FF9AA2"), new CornerRadii(15), Insets.EMPTY)));
        setAlignment(Pos.TOP_RIGHT);
    }

    /** Flips the dialog box for Chatterbox messages (bot on left) */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);

        // Bot bubble: soft white with pink border, fully rounded
        dialog.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(15), Insets.EMPTY)));
        dialog.setStyle("-fx-font-family: 'Quicksand'; -fx-font-size: 14px; -fx-text-fill: #333333;" +
                " -fx-border-color: #FFB6C1; -fx-border-width: 2px;" +
                " -fx-background-radius: 15;" +
                " -fx-border-radius: 15;");

        // Apply shadow effect
        DropShadow shadow = new DropShadow();
        shadow.setRadius(3);
        shadow.setOffsetX(1);
        shadow.setOffsetY(1);
        shadow.setColor(Color.rgb(0, 0, 0, 0.15));
        dialog.setEffect(shadow);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    public static DialogBox getDukeDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}




