package pub.developers.forum.domain.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.*;
import org.springframework.beans.BeanUtils;
import pub.developers.forum.common.enums.UserRoleEn;
import pub.developers.forum.common.enums.UserSexEn;
import pub.developers.forum.common.enums.UserSourceEn;
import pub.developers.forum.common.enums.UserStateEn;
import pub.developers.forum.common.support.AvatarUtil;

import java.util.Date;

/**
 * @author Qiangqiang.Bian
 * @create 2020/7/30
 * @desc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    /**
     *
     */
    private UserRoleEn role;

    /**
     *
     */
    private UserStateEn state;

    /**
     *
     */
    private String nickname;

    /**
     *
     */
    private UserSexEn sex;

    /**
     *
     */
    private UserSourceEn source;

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
     * githubUser
     */
    private JSONObject githubUser;

    /**
     *
     */
    private Date lastLoginTime;

    public String getAvatar() {
        return AvatarUtil.get(avatar, email);
    }

    public User copy() {
        User user = new User();
        BeanUtils.copyProperties(this, user);

        return user;
    }

}
