package controllers.user;

import controllers.base.IPersonalized;
import controllers.base.ServerConnector;
import javafx.application.Platform;
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
import utils.collections.ClassCollection;
import utils.vm.ClassVM;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserScheduleWindowController extends ServerConnector implements Initializable, IPersonalized {

    @FXML
    public TableView<ClassVM> classesTable;

    @FXML
    public TableColumn<ClassVM, String> timeColumn;

    @FXML
    public TableColumn<ClassVM, String> classNumberColumn;

    @Override
    public String buildInitRequestString() {
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("id", SessionStorage.getViewedProfileId());
        request.put("action", Constants.ACTION_INIT_SCHEDULE_WINDOW);
        request.put("data", data);
        return request.toJSONString() + "\n";
    }

    @Override
    protected String buildRequestString() {
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeString"));
        classNumberColumn.setCellValueFactory(new PropertyValueFactory<>("classNumber"));

        try {
            JSONObject response = this.requestServer(this.buildInitRequestString());
            ClassCollection collection = new ClassCollection();
            if ((Boolean) response.get("success")) {
                JSONArray classes = (JSONArray) response.get("classes");
                collection.fill(classes);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        classesTable.setItems(collection.getClasses());
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
}
