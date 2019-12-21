package controllers.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controllers.base.IPersonalized;
import controllers.base.IValidator;
import controllers.base.ServerConnector;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import utils.Constants;
import utils.SessionStorage;
import utils.WindowDispatcher;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserEditAccountController extends ServerConnector implements Initializable, IValidator, IPersonalized {

    @FXML
    public JFXButton submitBtn;

    @FXML
    public JFXPasswordField passwordInput;

    @FXML
    public JFXTextField nameInput;

    @FXML
    public JFXTextField surnameInput;

    @FXML
    public JFXTextField emailInput;

    @FXML
    public JFXTextField skypeInput;

    @FXML
    public JFXTextField phoneInput;

    public void submitUser(ActionEvent actionEvent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isValid()) {
                        Stage currentWindow = (Stage) submitBtn.getScene().getWindow();
                        JSONObject response = requestServer(buildRequestString());

                        if ((Boolean) response.get("success")) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    WindowDispatcher.showSuccessMessage("Success", "Данные обновлены");
                                    currentWindow.close();
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
                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                WindowDispatcher.showErrorMessage("Error", "Проверьте поля");
                            }
                        });
                    }
                } catch (IOException | ParseException e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            WindowDispatcher.showErrorMessage("Error", "Не удалось загрузить данные с сервера");
                        }
                    });
                }
            }
        }).start();

    }

    @Override
    public boolean isValid() {
        return !(
            nameInput.getText().equals("")
            || surnameInput.getText().equals("")
            || emailInput.getText().equals("")
            || skypeInput.getText().equals("")
            || passwordInput.getText().equals("")
            || phoneInput.getText().equals("")
        );
    }

    @Override
    protected String buildRequestString() {
        JSONObject obj = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("name", nameInput.getText().trim());
        data.put("surname", surnameInput.getText().trim());
        data.put("password", passwordInput.getText().trim());
        data.put("email", emailInput.getText().trim());
        data.put("skype", skypeInput.getText().trim());
        data.put("phone", phoneInput.getText().trim());
        data.put("userID", SessionStorage.getCurrentUserId());
        obj.put("action", Constants.ACTION_EDIT_USER);
        obj.put("data", data);
        return obj.toJSONString() + "\n";
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            JSONObject initData = requestServer(buildInitRequestString());

            if ((Boolean) initData.get("success")) {
                nameInput.setText((String) initData.get("name"));
                surnameInput.setText((String) initData.get("surname"));
                emailInput.setText((String) initData.get("email"));
                passwordInput.setText((String) initData.get("password"));
                skypeInput.setText((String) initData.get("skype"));
                phoneInput.setText((String) initData.get("phone"));
            } else {
                WindowDispatcher.showErrorMessage("Error", "Не удалось загрузить данные с сервера");
            }

        } catch (IOException | ParseException e) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    WindowDispatcher.showErrorMessage("Error", "Не удалось загрузить данные с сервера");
                }
            });
        }
    }

    @Override
    public String buildInitRequestString() {
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        request.put("action", Constants.ACTION_INIT_EDIT_USER_WINDOW);
        data.put("userID", SessionStorage.getCurrentUserId());
        request.put("data", data);
        return request.toJSONString() + "\n";
    }
}
