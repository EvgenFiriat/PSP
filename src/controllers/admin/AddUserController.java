package controllers.admin;

import client.ClientConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controllers.base.IServerConnector;
import controllers.base.IValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.Constants;
import utils.WindowDispatcher;

import java.io.IOException;

public class AddUserController implements IValidator, IServerConnector {
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

    public void initialize() {
        positionInput.getItems().forEach((MenuItem item) -> item.setOnAction(actionEvent -> {
            this.selectedPosition = item.getText().trim();
            positionInput.setText(selectedPosition);
        }));
    }

    public void submitUser(ActionEvent actionEvent) {
        if (this.isValid()) {
            JSONObject responseObj = null;
            try {
                responseObj = this.requestServer();
                if ((Boolean) responseObj.get("success")) {
                    Stage currentWindow = (Stage) submitButton.getScene().getWindow();
                    WindowDispatcher.showSuccessMessage("Success", "User added");
                    currentWindow.close();
                } else {
                    WindowDispatcher.showErrorMessage("Ошибка", (String) responseObj.get("errorMessage"));
                }
            } catch (IOException | ParseException e) {
                WindowDispatcher.showErrorMessage("Ошибка соединения", "В подкючении отказано");
            }
        } else {
            WindowDispatcher.showErrorMessage("Неверные данные", "Проверьте, пожалуйста, валидность ваших данных");
        }
    }

    @Override
    public boolean isValid() {
        return true;
    }

    private boolean validateSalary() {
        return false;
    }

    @Override
    public JSONObject requestServer() throws IOException, ParseException {
        // TODO: How to deal with duplicate code?
        ClientConnection.getConnection();
        ClientConnection.getOut().write(this.buildRequestString());
        ClientConnection.getOut().flush();
        String responseString = ClientConnection.getIn().readLine();
        return  (JSONObject) new JSONParser().parse(responseString);
    }

    @Override
    public String buildRequestString() {
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
}
