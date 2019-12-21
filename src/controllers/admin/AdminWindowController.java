package controllers.admin;

import com.jfoenix.controls.JFXButton;
import controllers.base.BaseUserWindowController;
import controllers.base.IPersonalized;
import controllers.base.IValidator;
import controllers.base.ServerConnector;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import utils.Constants;
import utils.SessionStorage;
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
}
