package pub.developers.forum.api.request.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Qiangqiang.Bian
 * @create 2020/12/12
 * @desc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleAdminTypePageRequest implements Serializable {

    /**
     * name
     */
    private String name;

    /**
     * description
     */
    private String description;

    /**
     * scope
     */
    private String scope;

    /**
     * audit state
     */
    private String auditState;
}
