package pub.developers.forum.common.enums;

/**
 * @author Qiangqiang.Bian
 * @create 20/7/23
 * @desc
 **/
public enum ErrorCodeEn {
    /**
     *
     */
    SUCCESS(0, "success"),
    SYSTEM_ERROR(9999, "exception"),
    CONFIG_DOMAIN_NOT_EMPTY(9998, "The domain configuration cannot be empty"),
    DATABASE_UUID_ERROR(9997, "The database UUID plug-in is abnormal"),
    DATABASE_NOW_ERROR(9996, "The database Now Date plug-in is abnormal"),
    PARAM_CHECK_ERROR(9995, "Request argument cannot be empty: {0}"),
    CHECK_ERROR_TOAST(9994, "{0}"),
    NO_PERMISSION(9993, "No operation permission"),
    REPEAT_OPERATION(9992, "Repeated operation"),
    OPERATION_DATA_NOT_EXIST(9991, "Operation data does not exist"),
    GITHUB_OAUTH_ERROR(9990, "Github authorization is abnormal"),

    // 0500~0750 common error
    COMMON_CACHE_KEY_EMPTY(8999, "The cache key cannot be empty"),

    COMMON_TOKEN_NO_PERMISSION(8996, "No operation permission"),

    DOMAIN_NOT_EXIST(7999, "The domain type does not exist"),
    DOMAIN_TYPE_NOT_EXIST(7998, "The domain type does not exist"),

    USER_NOT_LOGIN(8998, "User not logged in"),
    USER_NOT_EXIST(7996, "User does not exist"),
    USER_REGISTER_EMAIL_IS_EXIST(7992, "The registered email address already exists"),
    USER_LOGIN_PWD_ERROR(7992, "Incorrect login password"),
    USER_OLD_PASSWORD_ERROR(7991, "The old password is incorrect"),
    USER_ROLE_NOT_EXIST(7990, "The user role does not exist"),
    USER_SEX_NOT_EXIST(7989, "The user gender does not exist"),
    USER_TOKEN_INVALID(7988, "Token is invalid"),
    USER_STATE_IS_DISABLE(7987, "The user has been disabled"),

    CONTENT_TYPE_NOT_EXIST(7995, "Content type does not exist"),
    POSTS_SAVE_FAIL(7994, "Failed to save the post"),
    POSTS_NOT_EXIST(7993, "The post does not exist"),

    COMMENT_NOT_EXIST(1504, "The comment does not exist"),
    COMMENT_POSTS_NOT_EXIST(1504, "The reviewed poset does not exist"),

    TAG_NOT_MATCH_DOMAIN(7997, "The selected label does not match the current domain"),
    TAG_IS_EXIST(7989, "The label already exists"),
    TAG_NOT_EMPTY(7997, "The label cannot be empty"),
    TAG_NOT_EXIST(7996, "Label does not exist"),

    MESSAGE_SYSTEM_MAIL_SEND_FAIL(6999, "The system failed to send emails"),
    MESSAGE_DING_DING_SEND_RES_FAIL(6998, "Failed to send Dingding group bot message"),
    MESSAGE_DING_DING_SEND_FAIL(6997, "Abnormal message sent to Dingding group bots"),
    MESSAGE_QIYE_WX_SEND_RES_FAIL(6996, "Failed to send an enterprise wechat group robot message"),
    MESSAGE_QIYE_WX_SEND_FAIL(6995, "Abnormal message sent to enterprise wechat group robot"),

    ARTICLE_TYPE_IS_EXIST(5999, "The article type already exists"),
    ARTICLE_NOT_EXIST(5998, "The article does not exist"),
    ARTICLE_IN_AUDIT_PROCESS(5997, "Article under review"),

    FAQ_NOT_EXIST(4999, "Questions and answers don't exist"),
    FAQ_IN_AUDIT_PROCESS(4998, "Questions and answers under review"),

    FILE_UPLOAD_FAIL(4998, "File upload failure"),
    FILE_UPLOAD_NOT_SUPPORT_IMG_TYPE(4997, "unsupported image type"),

    MESSAGE_NOT_EXIST(3999, "Message does not exist"),

    CONFIG_NOT_EXIST(2999, "Configuration does not exist"),
    ;


    private Integer code;
    private String message;

    ErrorCodeEn(Integer code, String message) {
        this.code = 8000_0000 + code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
