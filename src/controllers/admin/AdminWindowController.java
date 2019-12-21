package controllers.admin;

import com.jfoenix.controls.JFXButton;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminWindowController extends ServerConnector implements IValidator, Initializable, IPersonalized {

    @FXML
    public Label userNameLabel;

    @FXML
    public JFXButton showStaffButton;

    @FXML
    public JFXButton addUserButton;

    @FXML
    public JFXButton oooButton;

    @FXML
    public JFXButton homeProfileWindow;

    @FXML
    public JFXButton showNotificationsButton;

    @FXML
    private Label emailLabel;

    @FXML
    private Label skypeLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private Label currentProjectLabel;

    @FXML
    private Label phoneNumberLabel;

    public void showAddUserModal(ActionEvent actionEvent) {
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        WindowDispatcher.popupModalWindow(Constants.CREATE_USER_WINDOW, window);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    protected String buildRequestString() {
        return null;
    }

    @Override
    public String buildInitRequestString() {
        JSONObject obj = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("userId", SessionStorage.getViewedProfileId());
        obj.put("action", Constants.ACTION_INIT_USER_WINDOW);
        obj.put("data", data);
        return  obj.toJSONString() + "\n";
    }

    private void handleSuccessInit(JSONObject responseData) {
        userNameLabel.setText((String) responseData.get("fullName"));
        emailLabel.setText((String) responseData.get("email"));
        skypeLabel.setText((String) responseData.get("skype"));
        positionLabel.setText((String) responseData.get("position"));
        currentProjectLabel.setText((String) responseData.get("project"));
        phoneNumberLabel.setText((String) responseData.get("phone"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.displayUserData();
    }

    private void displayUserData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject response = requestServer(buildInitRequestString());
                    if ((Boolean)response.get("success")) {
                        JSONObject responseData = (JSONObject) response.get("data");
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                handleSuccessInit(responseData);
                            }
                        });
                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                WindowDispatcher.showErrorMessage("Error", (String) response.get("errorMessage"));
                            }
                        });

                    }
                } catch (IOException | ParseException e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            WindowDispatcher.showErrorMessage("Error", "Ошибка при подключении");
                        }
                    });
                }
            }
        }).start();
    }

    public void showStaffWindow(ActionEvent actionEvent) {
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        WindowDispatcher.popupModalWindow(Constants.VIEW_USERS_WINDOW, window);
        if (!SessionStorage.getNewViewedProfileId().equals(SessionStorage.getViewedProfileId())) {
            SessionStorage.setViewedProfileId(SessionStorage.getNewViewedProfileId());
            SessionStorage.setNewViewedProfileId(null);
            this.displayUserData();
        }
    }

    public void homeViewEvent(ActionEvent actionEvent) {
        SessionStorage.setViewedProfileId(SessionStorage.getCurrentUserId());
        displayUserData();
    }

    public void viewOutOfOfficeModal(ActionEvent actionEvent) {
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        WindowDispatcher.popupModalWindow(Constants.OOO_REQUEST_WINDOW, window);
    }

    public void showNotificationsWindow(ActionEvent actionEvent) {
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        WindowDispatcher.popupModalWindow(Constants.ADMIN_NOTIFICATIONS_WINDOW, window);
    }
}
