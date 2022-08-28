package pub.developers.forum.api.response.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Qiangqiang.Bian
 * @create 2020/10/31
 * @desc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleQueryTypesResponse implements Serializable {

    private Long id;

    private String name;

    private Long refCount;

    /**
     *
     */
    private String description;

    /**
     *
     */
    private String scope;

    /**
     *
     */
    private Long creatorId;

    /**
     *
     */
    private String auditState;

    /**
     *
     */
    private String createAt;

    /**
     *
     */
    private String updateAt;


}
