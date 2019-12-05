package controllers.common;

import client.ClientConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controllers.base.IValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import utils.Constants;
import utils.WindowDispatcher;

import java.io.IOException;

public class LoginFormController implements IValidator {
    @FXML
    private JFXTextField emailInput;

    @FXML
    private JFXPasswordField passwordInput;

    @FXML
    private JFXButton submitButton;


    public void handleSubmitEvent(ActionEvent actionEvent) throws IOException {
        if (this.isValid()) {
            ClientConnection.getConnection();
            ClientConnection.getOut().write(Constants.ACTION_LOGIN + "\n");
            ClientConnection.getOut().flush();
            String response = ClientConnection.getIn().readLine();

            if (response.equals("success")) {
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                WindowDispatcher.switchScene(Constants.ADMIN_MAIN_WINDOW, window);
            } else {
                System.out.println("error");
            }
        } else {
            System.out.println("Invalid data!");
        }
    }

    @Override
    public boolean isValid() {
        return !(emailInput.getText().equals("") && passwordInput.getText().equals(""));
    }
}
