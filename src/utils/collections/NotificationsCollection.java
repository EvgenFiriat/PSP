package utils.collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.vm.NotificationsVM;


public class NotificationsCollection {
    private ObservableList<NotificationsVM> notifications = FXCollections.observableArrayList();

    public ObservableList<NotificationsVM> getNotifications() {
        return notifications;
    }

    public void setNotifications(ObservableList<NotificationsVM> notifications) {
        this.notifications = notifications;
    }

    public void fill(JSONArray response) {
        notifications.clear();
        response.forEach((Object notification) -> {
            this.notifications.add((new NotificationsVM((JSONObject) notification)));
        });
    }
}
