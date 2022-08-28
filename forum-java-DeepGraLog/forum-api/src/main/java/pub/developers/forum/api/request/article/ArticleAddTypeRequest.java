package pub.developers.forum.api.request.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Qiangqiang.Bian
 * @create 2020/10/31
 * @desc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleAddTypeRequest implements Serializable {

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

}
