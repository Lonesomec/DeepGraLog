package pub.developers.forum.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Qiangqiang.Bian
 * @create 2020/12/5
 * @desc
 **/
@Getter
@AllArgsConstructor
public enum MessageReadEn {
    YES("YES", "read"),
    NO("NO", "unread")
    ;

    private String value;
    private String desc;

    public static MessageReadEn getEntity(String value) {
        for (MessageReadEn contentType : values()) {
            if (contentType.getValue().equals(value)) {
                return contentType;
            }
        }
        return null;
    }

}
