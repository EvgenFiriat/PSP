package utils;

import java.util.Map;

public class Constants {
    // Actions
    public static final String ACTION_LOGIN = "ActionLogin";
    public static final String ACTION_CREATE_USER = "ActionCreateUser";
    public static final String ACTION_BLOCK_USER = "ActionBlockUser";
    public static final String ACTION_CREATE_OOO_REQUEST = "ActionCreateOOORequest";
    public static final String ACTION_TREAT_OOO_REQUEST = "ActionTreatOOORequest";

    // Init actions
    public static final String ACTION_INIT_ADD_USER_MODAL = "InitAddUserModal";
    public static final String ACTION_INIT_USER_WINDOW = "InitUserWindow";
    public static final String ACTION_INIT_VIEW_USERS_WINDOW = "InitViewUsersWindow";
    public static final String ACTION_INIT_OOO_REQUEST_WINDOW = "InitOOORequestWindow";
    public static final String ACTION_INIT_ADMIN_NOTIFICATIONS_WINDOW = "InitAdminNotificationsWindow";

    // Icons
    public static final String WINDOW_ICON = "/assets/img/icons/window-icon.png";

    // FXML
    public static final String ADMIN_MAIN_WINDOW = "AdminMainWindow";
    public static final String USER_MAIN_WINDOW = "UserWindow";
    public static final String LOGIN_WINDOW = "LoginWindow";
    public static final String CREATE_USER_WINDOW = "CreateUser";
    public static final String VIEW_USERS_WINDOW = "ViewUsers";
    public static final String OOO_REQUEST_WINDOW = "OOORequest";
    public static final String ADMIN_NOTIFICATIONS_WINDOW = "AdminNotificationsWindow";

    public static final Map<String, String> fxmlMapping = Map.ofEntries(
            Map.entry(ADMIN_MAIN_WINDOW, "/assets/fxml/Admin/AdminWindow/AdminWindow.fxml"),
            Map.entry(LOGIN_WINDOW, "/assets/fxml/Common/LoginForm/LoginForm.fxml"),
            Map.entry(USER_MAIN_WINDOW, "/assets/fxml/User/UserWindow/UserWindow.fxml"),
            Map.entry(CREATE_USER_WINDOW, "/assets/fxml/Admin/CreateUserWindow/CreateUser.fxml"),
            Map.entry(VIEW_USERS_WINDOW, "/assets/fxml/Common/ViewUsersWindow/ViewProfilesWindow.fxml"),
            Map.entry(OOO_REQUEST_WINDOW, "/assets/fxml/Common/OutOfOfficeRequestWindow/OutOfOfficeRequestWindow.fxml"),
            Map.entry(ADMIN_NOTIFICATIONS_WINDOW, "/assets/fxml/Admin/AdminNotificationsWindow/AdminNotificationsWindow.fxml")
    );
}
