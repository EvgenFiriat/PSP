package controllers.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controllers.base.IValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;
import utils.WindowDispatcher;

public class AddUserController implements IValidator {
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

    public void submitUser(ActionEvent actionEvent) {
        Stage currentWindow = (Stage) submitButton.getScene().getWindow();
        WindowDispatcher.showSuccessMessage("Success", "User added");
        currentWindow.close();
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
