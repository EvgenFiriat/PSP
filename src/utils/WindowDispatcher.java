package utils;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    public static void showError(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.setHeaderText("");
        alert.setResizable(true);
        alert.onShownProperty().addListener(e -> {
            Platform.runLater(() -> alert.setResizable(false));
        });
        alert.showAndWait();
    }
}
