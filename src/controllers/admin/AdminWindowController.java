package controllers.admin;

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

public class AdminWindowController extends ServerConnector implements IValidator, Initializable {

    @FXML
    public Label userNameLabel;

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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject obj = new JSONObject();
                    JSONObject data = new JSONObject();
                    data.put("userId", SessionStorage.getCurrentUserId());
                    obj.put("action", Constants.ACTION_INIT_USER_WINDOW);
                    obj.put("data", data);
                    String request = obj.toJSONString() + "\n";
                    JSONObject response = requestServer(request);

                    if ((Boolean)response.get("success")) {
                        JSONObject responseData = (JSONObject) response.get("data");
                        userNameLabel.setText((String) responseData.get("fullName"));
                        emailLabel.setText((String) responseData.get("email"));
                        skypeLabel.setText((String) responseData.get("skype"));
                        positionLabel.setText((String) responseData.get("position"));
                        currentProjectLabel.setText((String) responseData.get("project"));
                        phoneNumberLabel.setText((String) responseData.get("phone"));
                    } else {
                        WindowDispatcher.showErrorMessage("Error", (String) response.get("errorMessage"));
                    }
                } catch (IOException | ParseException e) {
                    WindowDispatcher.showErrorMessage("Error", "Ошибка при подключении");
                }
            }
        });
    }
}
