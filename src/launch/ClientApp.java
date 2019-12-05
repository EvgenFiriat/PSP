package launch;

import client.ClientConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utils.Config;

import java.io.IOException;

public class ClientApp extends Application {
    public static void main(String[] args) {
        try {
            ClientConnection.getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(Config.fxmlMapping.get("LoginWindow")));
        primaryStage.setTitle("SMS App");
        primaryStage.getIcons().add(new Image(ClientApp.class.getResourceAsStream("../assets/img/icons/window-icon.png")));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
