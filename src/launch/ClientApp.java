package launch;

import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ClientApp extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../assets/fxml/Admin/CreateUserWindow/CreateUser.fxml"));
        primaryStage.setTitle("SMS App");
        primaryStage.getIcons().add(new Image(ClientApp.class.getResourceAsStream("../assets/img/icons/window-icon.png")));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
