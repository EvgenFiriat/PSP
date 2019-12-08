package controllers.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controllers.base.IPersonalized;
import controllers.base.IValidator;
import controllers.base.ServerConnector;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import utils.Constants;
import utils.WindowDispatcher;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddUserController extends ServerConnector implements IValidator, Initializable, IPersonalized {
    @FXML
    public JFXButton submitButton;

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

    @FXML
    public JFXTextField salaryInput;

    @FXML
    public JFXCheckBox isAdminInput;

    @FXML
    public MenuButton positionInput;

    private String selectedPosition;


    public void submitUser(ActionEvent actionEvent) {
        Stage currentWindow = (Stage) submitButton.getScene().getWindow();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!isValid()) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            WindowDispatcher.showErrorMessage("Неверные данные", "Проверьте, пожалуйста, валидность ваших данных");
                        }
                    });
                    return;
                }
                JSONObject responseObj = null;
                try {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            submitButton.setDisable(true);
                        }
                    });
                    responseObj = requestServer(buildRequestString());
                    handleUserSubmitAction(responseObj, currentWindow);
                } catch (IOException | ParseException e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            WindowDispatcher.showErrorMessage("Ошибка соединения", "В подкючении отказано");
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
    public boolean isValid() {
        return true;
    }

    private boolean validateSalary() {
        return false;
    }

    private void handleUserSubmitAction(JSONObject responseObj, Stage currentWindow) {
        if ((Boolean) responseObj.get("success")) {
            WindowDispatcher.showSuccessMessage("Success", "User added");
            currentWindow.close();
        } else {
            WindowDispatcher.showErrorMessage("Ошибка", (String) responseObj.get("errorMessage"));
        }
    }
    @Override
    public String buildInitRequestString() {
        JSONObject initObject = new JSONObject();
        initObject.put("action", Constants.ACTION_INIT_ADD_USER_MODAL);
        return initObject.toJSONString() + "\n";
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
        data.put("salary", Double.parseDouble(salaryInput.getText().trim()));
        data.put("isAdmin", isAdminInput.isSelected());
        data.put("position", selectedPosition);
        obj.put("action", Constants.ACTION_CREATE_USER);
        obj.put("data", data);
        return obj.toJSONString() + "\n";
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject initData = requestServer(buildInitRequestString());
                    JSONArray choices = (JSONArray) initData.get("menuChoices");
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            choices.forEach((Object item) -> positionInput.getItems().add(new MenuItem((String)item)));
                            positionInput.getItems().forEach((MenuItem item) -> item.setOnAction(actionEvent -> {
                                selectedPosition = item.getText().trim();
                                positionInput.setText(selectedPosition);
                            }));
                        }
                    });
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
}
