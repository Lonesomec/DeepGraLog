package pub.developers.forum.api.request.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Qiangqiang.Bian
 * @create 2020/7/31
 * @desc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagCreateRequest implements Serializable {

    private String groupName;

    /**
     * name
     */
    private String name;

    /**
     * description
     */
    private String description;

}
