package utils.collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.vm.UserVM;


public class UsersCollection {
    private ObservableList<UserVM> usersList = FXCollections.observableArrayList();

    public ObservableList<UserVM> getUsersList() {
        return usersList;
    }

    public void setUsersList(ObservableList<UserVM> usersList) {
        this.usersList = usersList;
    }

    public void fill(JSONArray response) {
        usersList.clear();
        response.forEach((Object user) -> {
            this.usersList.add((new UserVM((JSONObject) user)));
        });
    }

}
