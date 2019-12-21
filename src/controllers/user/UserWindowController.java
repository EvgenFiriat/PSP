package controllers.user;

import controllers.base.BaseUserWindowController;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import utils.Constants;

import utils.WindowDispatcher;


public class UserWindowController extends BaseUserWindowController {
    public void showNotificationsModal(ActionEvent actionEvent) {
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        WindowDispatcher.popupModalWindow(Constants.USER_NOTIFICATIONS_WINDOW, window);
    }
}
