package pub.developers.forum.domain.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Qiangqiang.Bian
 * @create 2020/10/31
 * @desc
 **/
@Data
public abstract class BaseEntity {

    private Long id;

    /**
     * create time
     */
    private Date createAt;

    /**
     * update time
     */
    private Date updateAt;

}
