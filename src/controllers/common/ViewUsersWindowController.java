package controllers.common;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
    public TableColumn<UserVM, Long> idColumn;


    @Override
    public String buildInitRequestString() {
        JSONObject request = new JSONObject();
        request.put("action", Constants.ACTION_INIT_VIEW_USERS_WINDOW);
        return request.toJSONString() + "\n";
    }

    @Override
    protected String buildRequestString() {
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        projectColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));

        if (!SessionStorage.isAdmin()) {
            blockUserButton.setDisable(true);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
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
        }).start();
    }

    public void viewUserProfile(ActionEvent actionEvent) {
    }

    public void blockUser(ActionEvent actionEvent) {
    }
}
