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
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!isValid()) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            WindowDispatcher.showErrorMessage("Invalid data", "Fields should not be empty");
                        }
                    });
                    return;
                }
                JSONObject responseObj = null;
                try {
                    submitButton.setDisable(true);
                    responseObj = requestServer(buildRequestString());
                    if ((Boolean) responseObj.get("success")) {
                        handleSuccessInit(responseObj, window);
                    } else {
                        handleInvalidCredentialsError();
                    }
                } catch (IOException | ParseException e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            WindowDispatcher.showErrorMessage("Request error", "Connection refused");
                        }
                    });
                } finally {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            submitButton.setDisable(false);
                        }
                    });
                }
            }
        }).start();
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

    private void personalizeUserWindow(String role, Stage window) {
        if (role.equals("admin")) {
            SessionStorage.setIsAdmin(true);
            WindowDispatcher.switchScene(Constants.ADMIN_MAIN_WINDOW, window);
        }
        else {
            SessionStorage.setIsAdmin(false);
            WindowDispatcher.switchScene(Constants.USER_MAIN_WINDOW, window);
        }
    }

    private void handleSuccessInit(JSONObject responseObj, Stage window) {
        if ((Boolean) responseObj.get("isBanned")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    WindowDispatcher.showErrorMessage("Ошибка", "Вы заблокированы, свяжитесь с аминистратором");
                }
            });
            return;
        }
        Long userID = (Long)responseObj.get("user_id");
        String role = (String)responseObj.get("role");
        SessionStorage.setCurrentUserId(userID);
        SessionStorage.setViewedProfileId(userID);
        SessionStorage.setNewViewedProfileId(userID);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                personalizeUserWindow(role, window);
            }
        });
    }

    private void handleInvalidCredentialsError() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                WindowDispatcher.showErrorMessage("Error", "Invalid credentials");
                emailInput.clear();
                passwordInput.clear();
            }
        });
    }
}
