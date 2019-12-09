package utils;

public class SessionStorage {
    private static Long CURRENT_USER_ID;
    private static Long VIEWED_PROFILE_ID;
    private static Long NEW_VIEWED_PROFILE_ID;
    private static Long NOTIFICATIONS_COUNT;
    private static boolean IS_ADMIN;

    public static boolean isAdmin() {
        return IS_ADMIN;
    }

    public static void setIsAdmin(boolean isAdmin) {
        IS_ADMIN = isAdmin;
    }

    public static Long getViewedProfileId() {
        return VIEWED_PROFILE_ID;
    }

    public static void setViewedProfileId(Long viewedProfileId) {
        VIEWED_PROFILE_ID = viewedProfileId;
    }

    public static Long getCurrentUserId() {
        return CURRENT_USER_ID;
    }

    public static void setCurrentUserId(Long id) {
        if (CURRENT_USER_ID != null)
            return;
        CURRENT_USER_ID = id;
    }

    public static Long getNewViewedProfileId() {
        return NEW_VIEWED_PROFILE_ID;
    }

    public static void setNewViewedProfileId(Long newViewedProfileId) {
        NEW_VIEWED_PROFILE_ID = newViewedProfileId;
    }

    public static Long getNotificationsCount() {
        return NOTIFICATIONS_COUNT;
    }

    public static void setNotificationsCount(Long notificationsCount) {
        NOTIFICATIONS_COUNT = notificationsCount;
    }
}
