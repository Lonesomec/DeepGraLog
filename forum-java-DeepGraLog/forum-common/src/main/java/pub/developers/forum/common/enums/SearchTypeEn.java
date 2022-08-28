package pub.developers.forum.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Qiangqiang.Bian
 * @create 2020/12/2
 * @desc
 **/
@Getter
@AllArgsConstructor
public enum SearchTypeEn {
    POSTS("POSTS", "post"),
    ;

    private String value;
    private String desc;
}
