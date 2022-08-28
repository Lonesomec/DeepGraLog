package pub.developers.forum.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Qiangqiang.Bian
 * @create 2020/10/22
 * @desc
 **/
@AllArgsConstructor
@Getter
public enum MessageChannelEn {
    STATION_LETTER("STATION_LETTER", "Message"),
    MAIL("MAIL", "Email")
    ;

    private String value;
    private String desc;

    public static MessageChannelEn getEntity(String value) {
        for (MessageChannelEn contentType : values()) {
            if (contentType.getValue().equals(value)) {
                return contentType;
            }
        }
        return null;
    }
}
