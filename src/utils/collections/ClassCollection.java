package utils.collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.vm.ClassVM;

public class ClassCollection {
    private ObservableList<ClassVM> classes = FXCollections.observableArrayList();

    public ObservableList<ClassVM> getClasses() {
        return classes;
    }

    public void setClasses(ObservableList<ClassVM> notifications) {
        this.classes = notifications;
    }

    public void fill(JSONArray response) {
        classes.clear();
        response.forEach((Object scheduledClass) -> {
            this.classes.add((new ClassVM((JSONObject) scheduledClass)));
        });
    }
}
