package utils;

public class SessionStorage {
    private static Long CURRENT_USER_ID;
    private static Long VIEWED_PROFILE_ID;

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
}
