package pub.developers.forum.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Qiangqiang.Bian
 * @create 2020/10/22
 * @desc
 **/
@Getter
@AllArgsConstructor
public enum IdValueTypeEn {
    SYSTEM("SYSTEM", "system"),
    USER_ID("USER_ID", "userID"),
    EMAIL("MAIL", "Email"),
    ;
    private String value;
    private String desc;

    public static IdValueTypeEn getEntity(String value) {
        for (IdValueTypeEn contentType : values()) {
            if (contentType.getValue().equals(value)) {
                return contentType;
            }
        }
        return null;
    }
}
