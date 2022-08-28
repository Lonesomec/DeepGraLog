package pub.developers.forum.infrastructure.dal.dataobject;

import pub.developers.forum.common.enums.IsDeletedEn;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Qiangqiang.Bian
 * @create 20/7/23
 * @desc
 **/
@Data
public class BaseDO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private Integer isDelete;

    /**
     *
     */
    private Date createAt;

    /**
     *
     */
    private Date updateAt;

    public void initBase() {
        this.id = null;
        this.isDelete = IsDeletedEn.NOT_DELETED.getValue();
        this.createAt = new Date();
        this.updateAt = this.createAt;
    }
}