package pub.developers.forum.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Qiangqiang.Bian
 * @create 2020/7/30
 * @desc post comment
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {

    /**
     * commentator ID
     */
    private Long userId;

    /**
     * second commentator ID
     */
    private Long replyId;

    /**
     * second commentator ID
     */
    private Long replyReplyId;

    /**
     * post ID
     */
    private Long postsId;

    /**
     *
     */
    private String content;

}
