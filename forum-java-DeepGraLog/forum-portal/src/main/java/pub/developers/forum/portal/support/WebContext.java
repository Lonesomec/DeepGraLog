package pub.developers.forum.portal.support;

import pub.developers.forum.api.response.user.UserInfoResponse;

/**
 * @author Qiangqiang.Bian
 * @create 2020/10/29
 * @desc
 **/
public class WebContext {

    /**
     * Current Login User
     */
    private static final ThreadLocal<UserInfoResponse> CURRENT_LOGIN_USER = new ThreadLocal<>();

    /**
     * Current login user credentials SID
     */
    private static final ThreadLocal<String> CURRENT_USER_LOGIN_SID = new ThreadLocal<>();

    /**
     * Save the current login user
     * @param loginUser
     */
    public static void setCurrentUser(UserInfoResponse loginUser) {
        CURRENT_LOGIN_USER.set(loginUser);
    }

    /**
     * Get the current login user
     * @return
     */
    public static UserInfoResponse getCurrentUser() {
        return CURRENT_LOGIN_USER.get();
    }

    /**
     * Save the current login credentials
     * @param sid
     */
    public static void setCurrentSid(String sid) {
        CURRENT_USER_LOGIN_SID.set(sid);
    }

    /**
     * Get the current login credentials
     * @return
     */
    public static String getCurrentSid() {
        return CURRENT_USER_LOGIN_SID.get();
    }

    public static void removeAll() {
        CURRENT_LOGIN_USER.remove();
        CURRENT_USER_LOGIN_SID.remove();
    }

}
