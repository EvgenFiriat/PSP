package controllers.common;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controllers.base.IPersonalized;
import controllers.base.ServerConnector;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import utils.Constants;
import utils.SessionStorage;
import utils.WindowDispatcher;
import utils.collections.UsersCollection;
import utils.vm.UserVM;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewUsersWindowController extends ServerConnector implements Initializable, IPersonalized {

    @FXML
    public JFXTextField searchProfileInput;

    @FXML
    public JFXButton viewProfileButton;

    @FXML
    public JFXButton blockUserButton;

    @FXML
    public TableView<UserVM> usersTable;

    @FXML
    public TableColumn<UserVM, String> columnName;

    @FXML
    public TableColumn<UserVM, String> surnameColumn;

    @FXML
    public TableColumn<UserVM, String> emailColumn;

    @FXML
    public TableColumn<UserVM, String> projectColumn;

    @FXML
    public TableColumn<UserVM, String> positionColumn;


    @FXML
    public TableColumn<UserVM, Boolean> isBannedColumn;

    @FXML
    public TableColumn<UserVM, Long> idColumn;


    @Override
    public String buildInitRequestString() {
        JSONObject request = new JSONObject();
        request.put("action", Constants.ACTION_INIT_VIEW_USERS_WINDOW);
        return request.toJSONString() + "\n";
    }

    @Override
    protected String buildRequestString() {
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        request.put("action", Constants.ACTION_BLOCK_USER);
        data.put("userID", usersTable.getSelectionModel().getSelectedItem().getId());
        request.put("data", data);
        return request.toJSONString() + "\n";
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        projectColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        isBannedColumn.setCellValueFactory(new PropertyValueFactory<>("isBanned"));

        if (!SessionStorage.isAdmin()) {
            blockUserButton.setDisable(true);
        }

        try {
            UsersCollection usersCollection = new UsersCollection();
            JSONObject response = requestServer(buildInitRequestString());
            if ((Boolean) response.get("success")) {
                JSONArray users = (JSONArray) response.get("users");
                usersCollection.fill(users);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        usersTable.setItems(usersCollection.getUsersList());
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
                    WindowDispatcher.showErrorMessage("Ошибка", "Ошибка при запросе к серверу");
                }
            });
        }
    }

    public void viewUserProfile(ActionEvent actionEvent) {
        UserVM selectedUser = usersTable.getSelectionModel().getSelectedItem();
        SessionStorage.setNewViewedProfileId(selectedUser.getId());
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.close();
    }

    public void blockUser(ActionEvent actionEvent) {
        if (usersTable.getSelectionModel().getSelectedItem().getBanned()) {
            WindowDispatcher.showErrorMessage("Ошибка", "Пользоатель уже заблокирован");
        }
        this.blockUserButton.setDisable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject response = requestServer(buildRequestString());
                    if ((Boolean) response.get("success")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                WindowDispatcher.showSuccessMessage("Успех", "Пользователь заблокирован");
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
                            WindowDispatcher.showErrorMessage("Ошибка", "Ошибка соединения");
                        }
                    });
                }
            }
        }).start();
        this.blockUserButton.setDisable(false);
    }
}
