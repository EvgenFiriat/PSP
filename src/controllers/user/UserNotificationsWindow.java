package controllers.user;

import controllers.base.IPersonalized;
import controllers.base.ServerConnector;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import utils.Constants;
import utils.SessionStorage;
import utils.WindowDispatcher;
import utils.collections.UserNotificationsCollection;
import utils.vm.UserNotificationsVM;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserNotificationsWindow extends ServerConnector implements Initializable, IPersonalized {

    @FXML
    public TableView<UserNotificationsVM> notificationsTable;

    @FXML
    public TableColumn<UserNotificationsVM, Long> idColumn;

    @FXML
    public TableColumn<UserNotificationsVM, String> columnName;

    @FXML
    public TableColumn<UserNotificationsVM, String> requestTypeColumn;

    @FXML
    public TableColumn<UserNotificationsVM, String> dateColumn;

    @FXML
    public TableColumn<UserNotificationsVM, String> statusColumn;

    @Override
    public String buildInitRequestString() {
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("id", SessionStorage.getCurrentUserId());
        request.put("action", Constants.ACTION_INIT_USER_NOTIFICATIONS_WINDOW);
        request.put("data", data);
        return request.toJSONString() + "\n";
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("approverName"));
        requestTypeColumn.setCellValueFactory(new PropertyValueFactory<>("requestType"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("isApproved"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        try {
            JSONObject response = this.requestServer(this.buildInitRequestString());
            UserNotificationsCollection collection = new UserNotificationsCollection();
            if ((Boolean) response.get("success")) {
                JSONArray notifications = (JSONArray) response.get("notifications");
                collection.fill(notifications);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        notificationsTable.setItems(collection.getNotifications());
                    }
                });
            } else {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        WindowDispatcher.showErrorMessage("Ошибка", (String) response.get("errorMessage"));
                    }
                });
            }

        } catch (IOException | ParseException e) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    WindowDispatcher.showErrorMessage("Ошибка", "Ошибка подключения");
                }
            });
        }
    }

    @Override
    protected String buildRequestString() {
        return null;
    }
}
