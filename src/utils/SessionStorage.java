package utils;

public class SessionStorage {
    private static Long CURRENT_USER_ID;

    public static Long getCurrentUserId() {
        return CURRENT_USER_ID;
    }

    public static void setCurrentUserId(Long id) {
        CURRENT_USER_ID = id;
    }
}
