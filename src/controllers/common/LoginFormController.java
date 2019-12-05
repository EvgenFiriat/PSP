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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.Constants;
import utils.SessionStorage;
import utils.WindowDispatcher;

import java.io.IOException;

public class LoginFormController implements IValidator {
    @FXML
    private JFXTextField emailInput;

    @FXML
    private JFXPasswordField passwordInput;

    @FXML
    private JFXButton submitButton;


    public void handleSubmitEvent(ActionEvent actionEvent) {
        if (this.isValid()) {
            JSONObject responseObj = null;
            try {
                responseObj = this.requestServer();
                if ((Boolean) responseObj.get("success")) {
                    SessionStorage.setCurrentUserId((Long)responseObj.get("user_id"));
                    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    if (responseObj.get("role").equals("admin")) {
                        WindowDispatcher.switchScene(Constants.ADMIN_MAIN_WINDOW, window);
                    }
                    else {
                        WindowDispatcher.switchScene(Constants.USER_MAIN_WINDOW, window);
                    }
                } else {
                    WindowDispatcher.showError("Error", "Invalid credentials");
                    emailInput.clear();
                    passwordInput.clear();
                }
            } catch (IOException | ParseException e) {
                WindowDispatcher.showError("Request error", "Connection refused");
            }
        } else {
            WindowDispatcher.showError("Invalid data", "Fields should not be empty");
        }
    }

    private String buildRequestString() {
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        request.put("action", Constants.ACTION_LOGIN);
        data.put("email", emailInput.getText().trim());
        data.put("password", passwordInput.getText().trim());
        request.put("data", data);
        return request.toJSONString() + "\n";
    }

    private JSONObject requestServer() throws IOException, ParseException {
        ClientConnection.getConnection();
        ClientConnection.getOut().write(this.buildRequestString());
        ClientConnection.getOut().flush();
        String responseString = ClientConnection.getIn().readLine();
        return  (JSONObject) new JSONParser().parse(responseString);
    }

    @Override
    public boolean isValid() {
        return !emailInput.getText().equals("") && !passwordInput.getText().equals("");
    }
}
