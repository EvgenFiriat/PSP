package controllers.admin;

import com.jfoenix.controls.JFXButton;
import controllers.base.IPersonalized;
import controllers.base.ServerConnector;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import utils.collections.NotificationsCollection;
import utils.vm.NotificationsVM;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminNotificationsController extends ServerConnector implements Initializable, IPersonalized {

    @FXML
    public JFXButton approveBtn;

    @FXML
    public JFXButton declineBtn;

    @FXML
    public TableView<NotificationsVM> notificationsTable;

    @FXML
    public TableColumn<NotificationsVM, Long> idColumn;

    @FXML
    public TableColumn<NotificationsVM, String> columnName;

    @FXML
    public TableColumn<NotificationsVM, String> surnameColumn;

    @FXML
    public TableColumn<NotificationsVM, String> requestTypeColumn;

    @FXML
    public TableColumn<NotificationsVM, String> dateColumn;

    @FXML
    public TableColumn<NotificationsVM, String> commentColumn;


    @Override
    protected String buildRequestString() {
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        requestTypeColumn.setCellValueFactory(new PropertyValueFactory<>("requestType"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        try {
            JSONObject response = this.requestServer(this.buildInitRequestString());
            NotificationsCollection collection = new NotificationsCollection();
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
    public String buildInitRequestString() {
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("id", SessionStorage.getCurrentUserId());
        request.put("action", Constants.ACTION_INIT_ADMIN_NOTIFICATIONS_WINDOW);
        request.put("data", data);
        return request.toJSONString() + "\n";
    }

    public void decline(ActionEvent actionEvent) {
        this.declineBtn.setDisable(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject declineRequest = new JSONObject();
                JSONObject data = new JSONObject();
                data.put("requestID", notificationsTable.getSelectionModel().getSelectedItem().getId());
                data.put("isApproved", false);
                declineRequest.put("data", data);
                declineRequest.put("action", Constants.ACTION_TREAT_OOO_REQUEST);
                try {
                    JSONObject response = requestServer(declineRequest.toJSONString() + "\n");
                    if ((Boolean) response.get("success")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                WindowDispatcher.showSuccessMessage("Успех", "Запрос обработан");
                                notificationsTable.getItems().remove(notificationsTable.getSelectionModel().getSelectedItem());
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
                } catch (IOException | ParseException e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            WindowDispatcher.showErrorMessage("Ошибка", "Ошибка сервера");
                        }
                    });
                }
            }
        }).start();

        this.declineBtn.setDisable(false);
    }

    public void approve(ActionEvent actionEvent) {
        this.approveBtn.setDisable(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject approveRequest = new JSONObject();
                JSONObject data = new JSONObject();
                data.put("requestID", notificationsTable.getSelectionModel().getSelectedItem().getId());
                data.put("isApproved", true);
                approveRequest.put("data", data);
                approveRequest.put("action", Constants.ACTION_TREAT_OOO_REQUEST);
                try {
                    JSONObject response = requestServer(approveRequest.toJSONString() + "\n");

                    if ((Boolean) response.get("success")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                WindowDispatcher.showSuccessMessage("Успех", "Запрос обработан");
                                notificationsTable.getItems().remove(notificationsTable.getSelectionModel().getSelectedItem());
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
                } catch (IOException | ParseException e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            WindowDispatcher.showErrorMessage("Ошибка", "Ошибка сервера");
                        }
                    });
                }
            }
        }).start();

        this.approveBtn.setDisable(false);
    }
}
