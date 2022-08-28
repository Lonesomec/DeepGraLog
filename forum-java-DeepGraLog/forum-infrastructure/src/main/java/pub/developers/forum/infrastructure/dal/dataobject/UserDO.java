package pub.developers.forum.infrastructure.dal.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Qiangqiang.Bian
 * @create 2020/10/29
 * @desc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDO extends BaseDO {

    /**
     *
     */
    private String role;

    /**
     *
     */
    private String state;

    /**
     *
     */
    private String nickname;

    /**
     *
     */
    private String sex;

    /**
     *
     */
    private String source;

    /**
     *
     */
    private String avatar;

    /**
     *
     */
    private String email;

    /**
     *
     */
    private String password;

    /**
     *
     */
    private String signature;

    /**
     * extended information
     */
    private String ext;

    /**
     *
     */
    private Date lastLoginTime;

}
