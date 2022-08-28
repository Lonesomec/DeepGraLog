package pub.developers.forum.infrastructure.dal.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Qiangqiang.Bian
 * @create 20/11/19
 * @desc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFollowDO extends BaseDO {

    /**
     *
     */
    private Long followed;

    /**
     *
     */
    private String followedType;

    /**
     *
     */
    private Long follower;

}
