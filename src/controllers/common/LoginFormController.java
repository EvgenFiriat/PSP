package controllers.common;

import client.ClientConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controllers.base.IValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Config;

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
            ClientConnection.getOut().write("actionLogin\n");
            ClientConnection.getOut().flush();
            String response = ClientConnection.getIn().readLine();

            if (response.equals("success")) {

                // TODO: create a separate utility
                Parent adminParent = FXMLLoader.load(getClass().getResource(Config.fxmlMapping.get("AdminMainWindow")));
                Scene adminWindow = new Scene(adminParent);

                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                window.setScene(adminWindow);
                window.show();
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
