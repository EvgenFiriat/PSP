package utils;

import java.util.HashMap;
import java.util.Map;

public class Config {
    public static final Map<String, String> fxmlMapping = Map.ofEntries(
            Map.entry("AdminMainWindow", "/assets/fxml/Admin/AdminWindow/AdminWindow.fxml"),
            Map.entry("LoginWindow", "/assets/fxml/Common/LoginForm/LoginForm.fxml")
    );
}
