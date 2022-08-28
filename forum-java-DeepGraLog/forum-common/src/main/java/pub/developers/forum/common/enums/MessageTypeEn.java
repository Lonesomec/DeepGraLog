package pub.developers.forum.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Qiangqiang.Bian
 * @create 2020/7/30
 * @desc
 **/
@Getter
@AllArgsConstructor
public enum MessageTypeEn {
    USER_REGISTER_NOTIFY_ADMIN("USER_REGISTER_NOTIFY_ADMIN", "New user registration notifies the administrator"),
    APPROVAL_ARTICLE("APPROVAL_ARTICLE", "liked article"),
    COMMENT_ARTICLE("COMMENT_ARTICLE", "reviewed article"),

    APPROVAL_FAQ("APPROVAL_FAQ", "followed Q&A"),
    COMMENT_FAQ("COMMENT_FAQ", "reviewed article"),

    FOLLOW_USER("FOLLOW_USER", "followed user"),
    ;
    private String value;
    private String desc;

    public static MessageTypeEn getEntity(String value) {
        for (MessageTypeEn contentType : values()) {
            if (contentType.getValue().equals(value)) {
                return contentType;
            }
        }
        return null;
    }

    public static MessageTypeEn getEntityByDesc(String desc) {
        for (MessageTypeEn contentType : values()) {
            if (contentType.getDesc().equals(desc)) {
                return contentType;
            }
        }
        return null;
    }

}
