package controllers.admin;

import com.jfoenix.controls.JFXButton;
import controllers.base.BaseUserWindowController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import utils.Constants;
import utils.WindowDispatcher;

public class AdminWindowController extends BaseUserWindowController {

    @FXML
    public JFXButton addUserButton;


    public void showAddUserModal(ActionEvent actionEvent) {
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        WindowDispatcher.popupModalWindow(Constants.CREATE_USER_WINDOW, window);
    }

    public void showNotificationsWindow(ActionEvent actionEvent) {
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        WindowDispatcher.popupModalWindow(Constants.ADMIN_NOTIFICATIONS_WINDOW, window);
    }

    public void showAdminEditModal(ActionEvent actionEvent) {
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        WindowDispatcher.popupModalWindow(Constants.USER_EDIT_ACCOUNT_WINDOW, window);
    }
}
