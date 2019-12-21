package controllers.common;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import controllers.base.IPersonalized;
import controllers.base.IValidator;
import controllers.base.ServerConnector;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import utils.Constants;
import utils.DateFormatter;
import utils.SessionStorage;
import utils.WindowDispatcher;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class OOORequestWindowController extends ServerConnector implements IValidator, Initializable, IPersonalized {
    public JFXButton submitButton;
    public JFXTextArea commentInput;
    public JFXDatePicker startDatePicker;
    public JFXDatePicker endDatePicker;
    public MenuButton approverPicker;
    public MenuButton requestTypePicker;

    private String selectedChoice = "";
    private String selectedApprover = "";
    private String validationErrorMessage;

    @Override
    protected String buildRequestString() {
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("requestorID", SessionStorage.getCurrentUserId());
        data.put("approver", selectedApprover);
        data.put("requestType", selectedChoice);
        data.put("startDate", startDatePicker.getValue().toString());
        data.put("endDate", endDatePicker.getValue().toString());
        data.put("comment", commentInput.getText());
        request.put("data", data);
        request.put("action", Constants.ACTION_CREATE_OOO_REQUEST);
        return request.toJSONString() + "\n";
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

    @Override
    public String buildInitRequestString() {
        JSONObject request = new JSONObject();
        request.put("action", Constants.ACTION_INIT_OOO_REQUEST_WINDOW);
        return request.toJSONString() + "\n";
    }

    public void submitRequest(ActionEvent actionEvent) {
        if (this.isValid()) {
            Stage currentWindow = (Stage) submitButton.getScene().getWindow();

            try {
                JSONObject response = this.requestServer(this.buildRequestString());

                if ((Boolean) response.get("success")) {
                    WindowDispatcher.showSuccessMessage("Уведомление", "Запрос отправлен");
                    currentWindow.close();
                } else {
                    WindowDispatcher.showErrorMessage("Ошибка", (String) response.get("errorMessage"));
                }
            } catch (IOException | ParseException e) {
                WindowDispatcher.showErrorMessage("Ошибка", "Не удалось подключиться к серверу");
            }
        } else {
            WindowDispatcher.showErrorMessage("Ошибка", this.validationErrorMessage);
        }


    }

    @Override
    public boolean isValid() {
        this.validationErrorMessage = "";

        if (this.startDatePicker.getValue() != null && this.endDatePicker != null) {
            if (this.startDatePicker.getValue().compareTo(this.endDatePicker.getValue()) > 0) {
                this.validationErrorMessage += "- Дата окончания должна быть позже даты начала \n";
            }
            if (this.startDatePicker.getValue().compareTo(LocalDate.now()) < 0) {
                this.validationErrorMessage += "- Дата начала действия запроса должна быть не раньше чем сегодняшний день \n";
            }
        }
        if (selectedApprover.equals("")) {
            this.validationErrorMessage += "- Выберите подтверждающее лицо \n";
        }
        if (selectedChoice.equals("")) {
            this.validationErrorMessage += "- Выберите тип отсутствия \n";
        }

        return this.validationErrorMessage.equals("");
    }
}
