package pub.developers.forum.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Qiangqiang.Bian
 * @create 2020/12/26
 * @desc
 **/
@Getter
@AllArgsConstructor
public enum ConfigTypeEn {

    /**
     * Home page carousel
     */
    HOME_CAROUSEL("HOME_CAROUSEL", "Home page carousel"),

    /**
     * sidebar carousel
     */
    SIDEBAR_CAROUSEL("SIDEBAR_CAROUSEL", "sidebar carousel"),
    ;

    private String value;
    private String desc;

    public static ConfigTypeEn getEntity(String value) {
        for (ConfigTypeEn contentType : values()) {
            if (contentType.getValue().equals(value)) {
                return contentType;
            }
        }
        return null;
    }

}
