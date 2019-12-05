package utils;

import java.util.Map;

public class Constants {
    // Actions
    public static final String ACTION_LOGIN = "ActionLogin";

    // Icons
    public static final String WINDOW_ICON = "/assets/img/icons/window-icon.png";

    // FXML
    public static final String ADMIN_MAIN_WINDOW = "AdminMainWindow";
    public static final String LOGIN_WINDOW = "LoginWindow";

    public static final Map<String, String> fxmlMapping = Map.ofEntries(
            Map.entry(ADMIN_MAIN_WINDOW, "/assets/fxml/Admin/AdminWindow/AdminWindow.fxml"),
            Map.entry(LOGIN_WINDOW, "/assets/fxml/Common/LoginForm/LoginForm.fxml")
    );
}
