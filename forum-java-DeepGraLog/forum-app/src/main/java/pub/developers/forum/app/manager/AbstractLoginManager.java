package pub.developers.forum.app.manager;

import com.alibaba.fastjson.JSON;
import org.springframework.util.ObjectUtils;
import pub.developers.forum.api.request.user.UserBaseLoginRequest;
import pub.developers.forum.common.enums.CacheBizTypeEn;
import pub.developers.forum.common.support.EventBus;
import pub.developers.forum.common.support.StringUtil;
import pub.developers.forum.domain.entity.OptLog;
import pub.developers.forum.domain.entity.User;
import pub.developers.forum.domain.repository.UserRepository;
import pub.developers.forum.domain.service.CacheService;

import javax.annotation.Resource;

/**
 * @author Qiangqiang.Bian
 * @create 2021/5/15
 * @desc
 **/
public class AbstractLoginManager {

    @Resource
    UserRepository userRepository;

    @Resource
    CacheService cacheService;

    /**
     * Token expiration period (unit: second) : 7 days
     */
    private static final Long USER_LOGIN_TOKEN_EXPIRE_TIMEOUT = 60 * 60 * 24 * 7L;

    protected String login(User user, UserBaseLoginRequest baseLoginRequest) {
        // Cache login credentials token for user
        String token = StringUtil.generateUUID();
        cacheLoginUser(token, user);


        return token;
    }

    protected void cacheLoginUser(String token, User user) {
        // Delete the previous login cache
        deleteLoginUser(user.getId());

        // Re-saving the cache
        cacheService.setAndExpire(CacheBizTypeEn.USER_LOGIN_TOKEN
                , String.valueOf(user.getId()), token, USER_LOGIN_TOKEN_EXPIRE_TIMEOUT);
        cacheService.setAndExpire(CacheBizTypeEn.USER_LOGIN_TOKEN
                , token, JSON.toJSONString(user), USER_LOGIN_TOKEN_EXPIRE_TIMEOUT);
    }

    protected void deleteLoginUser(Long userId) {
        // Delete the previous login cache
        String oldToken = cacheService.get(CacheBizTypeEn.USER_LOGIN_TOKEN, String.valueOf(userId));
        if (!ObjectUtils.isEmpty(oldToken)) {
            cacheService.del(CacheBizTypeEn.USER_LOGIN_TOKEN, String.valueOf(userId));
            cacheService.del(CacheBizTypeEn.USER_LOGIN_TOKEN, oldToken);
        }
    }
}
