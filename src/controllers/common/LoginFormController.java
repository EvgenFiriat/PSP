package controllers.common;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controllers.base.IValidator;
import controllers.base.ServerConnector;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import utils.Constants;
import utils.SessionStorage;
import utils.WindowDispatcher;

import java.io.IOException;

public class LoginFormController extends ServerConnector implements IValidator {
    @FXML
    private JFXTextField emailInput;

    @FXML
    private JFXPasswordField passwordInput;

    @FXML
    private JFXButton submitButton;


    public void handleSubmitEvent(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (isValid()) {
                    JSONObject responseObj = null;
                    try {
                        responseObj = requestServer();
                        submitButton.setDisable(true);
                        if ((Boolean) responseObj.get("success")) {
                            Long userID = (Long)responseObj.get("user_id");
                            SessionStorage.setCurrentUserId(userID);
                            SessionStorage.setViewedProfileId(userID);
                            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                            if (responseObj.get("role").equals("admin")) {
                                WindowDispatcher.switchScene(Constants.ADMIN_MAIN_WINDOW, window);
                            }
                            else {
                                WindowDispatcher.switchScene(Constants.USER_MAIN_WINDOW, window);
                            }
                        } else {
                            WindowDispatcher.showErrorMessage("Error", "Invalid credentials");
                            emailInput.clear();
                            passwordInput.clear();
                        }
                    } catch (IOException | ParseException e) {
                        WindowDispatcher.showErrorMessage("Request error", "Connection refused");
                    } finally {
                        submitButton.setDisable(false);
                    }
                } else {
                    WindowDispatcher.showErrorMessage("Invalid data", "Fields should not be empty");
                }
            }
        });
    }

    @Override
    protected String buildRequestString() {
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        request.put("action", Constants.ACTION_LOGIN);
        data.put("email", emailInput.getText().trim());
        data.put("password", passwordInput.getText().trim());
        request.put("data", data);
        return request.toJSONString() + "\n";
    }

    @Override
    public boolean isValid() {
        return !emailInput.getText().equals("") && !passwordInput.getText().equals("");
    }
}
