package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowDispatcher {
    public static void switchScene(String sceneID, Stage window) {
        try {
            Parent adminParent = FXMLLoader.load(WindowDispatcher.class.getResource(Constants.fxmlMapping.get(sceneID)));
            Scene adminWindow = new Scene(adminParent);
            window.setScene(adminWindow);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void popupModalWindow(String sceneID) {

    }
}
