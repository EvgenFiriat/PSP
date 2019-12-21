package utils.collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.vm.UserNotificationsVM;

public class UserNotificationsCollection {
    private ObservableList<UserNotificationsVM> notifications = FXCollections.observableArrayList();

    public ObservableList<UserNotificationsVM> getNotifications() {
        return notifications;
    }

    public void setNotifications(ObservableList<UserNotificationsVM> notifications) {
        this.notifications = notifications;
    }

    public void fill(JSONArray response) {
        notifications.clear();
        response.forEach((Object notification) -> {
            this.notifications.add((new UserNotificationsVM((JSONObject) notification)));
        });
    }
}
