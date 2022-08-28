package pub.developers.forum.app.support;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import pub.developers.forum.common.enums.ErrorCodeEn;
import pub.developers.forum.common.enums.UserRoleEn;
import pub.developers.forum.common.exception.BizException;
import pub.developers.forum.common.support.CheckUtil;
import pub.developers.forum.domain.entity.User;

/**
 * @author Qiangqiang.Bian
 * @create 2020/10/19
 * @desc
 **/
@Component
@Aspect
public class IsLoginAspect {

    /**
     * Get cached logon user information
     * Determines the role rights of the current login user and sets the login user to the context of the request thread for service processing
     * @param joinPoint
     * @param isLogin
     * @return
     * @throws Throwable
     */
    @Around("execution(* pub.developers.forum..*.*(..)) && @annotation(isLogin)")
    public Object process(ProceedingJoinPoint joinPoint, IsLogin isLogin) throws Throwable {
        User loginUser = LoginUserContext.getUser();
        CheckUtil.isEmpty(loginUser, ErrorCodeEn.USER_NOT_LOGIN);

        UserRoleEn userRoleEn = loginUser.getRole();

        // super administrator
        if (UserRoleEn.SUPER_ADMIN.equals(isLogin.role())) {
            if (UserRoleEn.SUPER_ADMIN.equals(userRoleEn)) {
                return joinPoint.proceed();
            }
            throw new BizException(ErrorCodeEn.COMMON_TOKEN_NO_PERMISSION);
        }

        // administrator
        else if (UserRoleEn.ADMIN.equals(isLogin.role())) {
            if (UserRoleEn.SUPER_ADMIN.equals(userRoleEn)
                    || UserRoleEn.ADMIN.equals(userRoleEn)) {
                return joinPoint.proceed();
            }
            throw new BizException(ErrorCodeEn.COMMON_TOKEN_NO_PERMISSION);
        }

        // user
        return joinPoint.proceed();
    }

}
