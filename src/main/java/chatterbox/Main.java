package chatterbox;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXMLLoader;



public class Main extends Application {

    private Chatterbox chatterbox = new Chatterbox();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            MainWindow controller = fxmlLoader.getController();
            controller.setChatterbox(chatterbox);
            controller.showWelcome();
            stage.setTitle("Chatterbox"); 
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

