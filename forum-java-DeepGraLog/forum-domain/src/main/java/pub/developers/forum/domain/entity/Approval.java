package pub.developers.forum.domain.entity;

import lombok.*;

/**
 * @author Qiangqiang.Bian
 * @create 2020/7/30
 * @desc Like the article/follow the Q&A
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Approval extends BaseEntity {

    private Long postsId;

    private Long userId;

}
