package controllers.common;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import controllers.base.IPersonalized;
import controllers.base.ServerConnector;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import utils.Constants;
import utils.DateFormatter;
import utils.WindowDispatcher;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OOORequestWindowController extends ServerConnector implements Initializable, IPersonalized {
    public JFXButton submitButton;
    public JFXTextArea commentInput;
    public JFXDatePicker startDatePicker;
    public JFXDatePicker endDatePicker;
    public MenuButton approverPicker;
    public MenuButton requestTypePicker;

    private String selectedChoice = "";
    private String selectedApprover = "";

    @Override
    protected String buildRequestString() {
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DateFormatter converter = new DateFormatter();
        startDatePicker.setConverter(converter);
        endDatePicker.setConverter(converter);

        try {
            JSONObject response = this.requestServer(this.buildInitRequestString());
            if ((Boolean) response.get("success")) {
                JSONArray choices = (JSONArray) response.get("choices");
                JSONArray approvers = (JSONArray) response.get("approvers");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        choices.forEach((Object item) -> requestTypePicker.getItems().add(new MenuItem((String)item)));
                        approvers.forEach((Object item) -> approverPicker.getItems().add(new MenuItem((String)item)));
                        requestTypePicker.getItems().forEach((MenuItem item) -> item.setOnAction(actionEvent -> {
                            selectedChoice = item.getText().trim();
                            requestTypePicker.setText(selectedChoice);
                        }));
                        approverPicker.getItems().forEach((MenuItem item) -> item.setOnAction(actionEvent -> {
                            selectedApprover = item.getText().trim();
                            approverPicker.setText(selectedApprover);
                        }));
                    }
                });
            } else {
                WindowDispatcher.showErrorMessage("Ошибка", (String) response.get("errorMessage"));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void submitUser(ActionEvent actionEvent) {
    }

    @Override
    public String buildInitRequestString() {
        JSONObject request = new JSONObject();
        request.put("action", Constants.ACTION_INIT_OOO_REQUEST_WINDOW);
        return request.toJSONString() + "\n";
    }
}
