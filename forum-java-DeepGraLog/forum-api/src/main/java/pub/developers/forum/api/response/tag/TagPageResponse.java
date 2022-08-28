package pub.developers.forum.api.response.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Qiangqiang.Bian
 * @create 20/9/9
 * @desc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagPageResponse implements Serializable {

    private Long id;

    /**
     * create time
     */
    private String createAt;

    /**
     * update time
     */
    private String updateAt;

    private String groupName;

    /**
     * name
     */
    private String name;

    /**
     * description
     */
    private String description;

    /**
     * reference statistic
     */
    private Long refCount;

    /**
     * creator ID
     */
    private Long creatorId;

    /**
     * audit state
     */
    private String auditState;


}
