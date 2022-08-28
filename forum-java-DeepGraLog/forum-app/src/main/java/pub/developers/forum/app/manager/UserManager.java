package pub.developers.forum.app.manager;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import pub.developers.forum.api.model.PageRequestModel;
import pub.developers.forum.api.model.PageResponseModel;
import pub.developers.forum.api.request.AdminBooleanRequest;
import pub.developers.forum.api.request.user.*;
import pub.developers.forum.api.response.user.UserInfoResponse;
import pub.developers.forum.api.response.user.UserOptLogPageResponse;
import pub.developers.forum.api.response.user.UserPageResponse;
import pub.developers.forum.app.support.IsLogin;
import pub.developers.forum.app.support.Pair;
import pub.developers.forum.app.transfer.OptLogTransfer;
import pub.developers.forum.common.enums.UserStateEn;
import pub.developers.forum.common.support.*;
import pub.developers.forum.domain.entity.Follow;
import pub.developers.forum.domain.repository.OptLogRepository;
import pub.developers.forum.app.support.LoginUserContext;
import pub.developers.forum.app.transfer.UserTransfer;
import pub.developers.forum.app.support.PageUtil;
import pub.developers.forum.common.enums.CacheBizTypeEn;
import pub.developers.forum.common.enums.ErrorCodeEn;
import pub.developers.forum.common.enums.UserRoleEn;
import pub.developers.forum.common.model.PageResult;
import pub.developers.forum.domain.entity.OptLog;
import pub.developers.forum.domain.entity.User;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Qiangqiang.Bian
 * @create 2020/9/8
 * @desc
 **/
@Component
public class UserManager extends AbstractLoginManager {

    @Resource
    private OptLogRepository optLogRepository;

    /**
     * Email + password login
     * @param request
     * @return
     */
    public String emailRequestLogin(UserEmailLoginRequest request) {
        // Check whether the mailbox exists
        User user = userRepository.getByEmail(request.getEmail());
        CheckUtil.isEmpty(user, ErrorCodeEn.USER_NOT_EXIST);
        CheckUtil.isTrue(UserStateEn.DISABLE.equals(user.getState()), ErrorCodeEn.USER_STATE_IS_DISABLE);

        // Check whether the login password is correct
        CheckUtil.isFalse(StringUtil.md5UserPassword(request.getPassword()).equals(user.getPassword()), ErrorCodeEn.USER_LOGIN_PWD_ERROR);

        // Update the last login time
        user.setLastLoginTime(new Date());
        userRepository.update(user);

        return login(user, request);
    }

    @IsLogin(role = UserRoleEn.ADMIN)
    public PageResponseModel<UserOptLogPageResponse> pageOptLog(PageRequestModel<UserOptLogPageRequest> pageRequestModel) {
        PageResult<OptLog> pageResult = optLogRepository.page(PageUtil.buildPageRequest(pageRequestModel, OptLogTransfer.toOptLog(pageRequestModel.getFilter())));

        if (ObjectUtils.isEmpty(pageResult.getList())) {
            return PageUtil.buildPageResponseModel(pageResult, new ArrayList<>());
        }
        List<Long> userIdList = pageResult.getList().stream().map(OptLog::getOperatorId).collect(Collectors.toList());
        List<User> userList = userRepository.queryByIds(userIdList);

        return PageUtil.buildPageResponseModel(pageResult, UserTransfer.toUserOptLogPageResponses(pageResult.getList(), userList));
    }

    @IsLogin(role = UserRoleEn.ADMIN)
    public void enable(Long uid) {
        User user = userRepository.get(uid);//normal
//        User user = null;//chain change
        CheckUtil.isEmpty(user, ErrorCodeEn.USER_NOT_EXIST);

        user.setState(UserStateEn.ENABLE);
        userRepository.update(user);//normal
//        userRepository.updateNull(user);//call change
//        userRepository.update(null);//argument change
    }

    @IsLogin(role = UserRoleEn.ADMIN)
    public void disable(Long uid) {
        User user = userRepository.get(uid);//normal
//        User user = null;//chanin change
        CheckUtil.isEmpty(user, ErrorCodeEn.USER_NOT_EXIST);
        CheckUtil.isEmpty(UserRoleEn.SUPER_ADMIN.equals(user.getRole()), ErrorCodeEn.COMMON_TOKEN_NO_PERMISSION);

        user.setState(UserStateEn.DISABLE);
        userRepository.update(user);//normal
//        userRepository.updateNull(user);//call change
//        userRepository.update(null);//argument change

        // delete the login information of a user
        deleteLoginUser(uid);
    }

    @IsLogin
    public void follow(Long followed) {
        Long follower = LoginUserContext.getUser().getId();
        userRepository.follow(followed, follower);

        Pair<Long> follow = Pair.build(followed, follower);
//        EventBus.emit(EventBus.Topic.USER_FOLLOW, follow);
    }

    @IsLogin
    public void cancelFollow(Long followed) {
        Follow follow = userRepository.getFollow(followed, LoginUserContext.getUser().getId());
        if (follow == null) {
            return;
        }
        userRepository.cancelFollow(follow.getId());

//        EventBus.emit(EventBus.Topic.USER_CANCEL_FOLLOW, follow);
    }

    /**
     * Obtain details about the user corresponding to the token
     * @param token
     * @return
     */
    public UserInfoResponse info(String token) {
        String cacheUserStr = cacheService.get(CacheBizTypeEn.USER_LOGIN_TOKEN, token);//normal
        //String cacheUserStr = cacheService.get_error(CacheBizTypeEn.USER_LOGIN_TOKEN, token);//call change
        //String cacheUserStr = cacheService.get(CacheBizTypeEn.USER_LOGIN_TOKEN, null);//argument change
        //String cacheUserStr = null;//chain change
        CheckUtil.isEmpty(cacheUserStr, ErrorCodeEn.USER_TOKEN_INVALID);//condition change

        return UserTransfer.toUserInfoResponse(JSON.parseObject(cacheUserStr, User.class));
    }

    public UserInfoResponse info(Long uid) {
        User user = userRepository.get(uid);
        CheckUtil.isEmpty(user, ErrorCodeEn.USER_NOT_EXIST);

        return UserTransfer.toUserInfoResponse(user);
    }

    /**
     * User registration
     * @param request
     */
    @Transactional
    public String register(UserRegisterRequest request) {
        // Check whether the email address has been registered
        User user = userRepository.getByEmail(request.getEmail());
        CheckUtil.isNotEmpty(user, ErrorCodeEn.USER_REGISTER_EMAIL_IS_EXIST);

        User registerUser = UserTransfer.toUser(request);

        // Save the registered user
        userRepository.save(registerUser);


        return login(registerUser, request);
    }

    /**
     * logout
     * @param request
     */
    public void logout(UserTokenLogoutRequest request) {
        User user = delCacheLoginUser(request.getToken());
        if (ObjectUtils.isEmpty(user)) {
            return;
        }
        OptLog.createUserLogoutRecordLog(user.getId(), JSON.toJSONString(request));
    }

    /**
     * The user updates basic information. Procedure
     * @param request
     */
    @IsLogin
    @Transactional
    public void updateInfo(UserUpdateInfoRequest request) {
        User loginUser = LoginUserContext.getUser();

        User user = userRepository.getByEmail(request.getEmail());
        if (!ObjectUtils.isEmpty(user)) {
            CheckUtil.isFalse(user.getId().equals(loginUser.getId()), ErrorCodeEn.USER_REGISTER_EMAIL_IS_EXIST);
        }

        User updateUser = UserTransfer.toUser(loginUser, request);

        // Update the login user information in the cache
        updateCacheUser(updateUser);
        userRepository.update(updateUser);
    }


    /**
     * Users update their profile pictures
     */
    @IsLogin
    @Transactional
    public void updateHeadimg(String linkFilenameData) {
        User loginUser = LoginUserContext.getUser();

       /* User user = userRepository.getByEmail(request.getEmail());
        if (!ObjectUtils.isEmpty(user)) {
            CheckUtil.isFalse(user.getId().equals(loginUser.getId()), ErrorCodeEn.USER_REGISTER_EMAIL_IS_EXIST);
        }*/
        loginUser.setAvatar(linkFilenameData);
        // Update the login user information in the cache
        updateCacheUser(loginUser);
        userRepository.update(loginUser);
    }
    /**
     * Updating the Login Password
     * @param request
     */
    @IsLogin
    @Transactional
    public void updatePwd(UserUpdatePwdRequest request) {
        User user = LoginUserContext.getUser();
        CheckUtil.isFalse(StringUtil.md5UserPassword(request.getOldPassword()).equals(user.getPassword()), ErrorCodeEn.USER_OLD_PASSWORD_ERROR);

        user.setPassword(StringUtil.md5UserPassword(request.getNewPassword()));

        // Update the login user information in the cache
        updateCacheUser(user);
        userRepository.update(user);
    }

    @IsLogin(role = UserRoleEn.ADMIN)
    public PageResponseModel<UserPageResponse> page(PageRequestModel<UserAdminPageRequest> pageRequestModel) {
        PageResult<User> pageResult = userRepository.page(PageUtil.buildPageRequest(pageRequestModel, UserTransfer.toUser(pageRequestModel.getFilter())));

        return PageUtil.buildPageResponseModel(pageResult, UserTransfer.toUserPageResponses(pageResult.getList()));
    }

    public PageResponseModel<UserPageResponse> pageFollower(PageRequestModel<Long> pageRequestModel) {
        PageResult<User> pageResult = userRepository.pageFollower(PageUtil.buildPageRequest(pageRequestModel, pageRequestModel.getFilter()));

        return PageUtil.buildPageResponseModel(pageResult, UserTransfer.toUserPageResponses(pageResult.getList()));
    }

    public PageResponseModel<UserPageResponse> pageFans(PageRequestModel<Long> pageRequestModel) {
        PageResult<User> pageResult = userRepository.pageFans(PageUtil.buildPageRequest(pageRequestModel, pageRequestModel.getFilter()));

        return PageUtil.buildPageResponseModel(pageResult, UserTransfer.toUserPageResponses(pageResult.getList()));
    }

    @IsLogin
    public Boolean hasFollow(Long followed) {
        Follow follow = userRepository.getFollow(followed, LoginUserContext.getUser().getId());
        return follow != null;
    }

    public PageResponseModel<UserPageResponse> pageActive(PageRequestModel pageRequestModel) {
        PageResult<User> pageResult = userRepository.pageActive(PageUtil.buildPageRequest(pageRequestModel));

        return PageUtil.buildPageResponseModel(pageResult, UserTransfer.toUserPageResponses(pageResult.getList()));
    }

    private void updateCacheUser(User updateUser) {
        LoginUserContext.setUser(updateUser);
        cacheLoginUser(LoginUserContext.getToken(), updateUser);
    }

    private User delCacheLoginUser(String token) {
        String oldUser = cacheService.get(CacheBizTypeEn.USER_LOGIN_TOKEN, token);
        if (ObjectUtils.isEmpty(oldUser)) {
            return null;
        }

        User loginUser = JSON.parseObject(oldUser, User.class);
        cacheService.del(CacheBizTypeEn.USER_LOGIN_TOKEN, String.valueOf(loginUser.getId()));
        cacheService.del(CacheBizTypeEn.USER_LOGIN_TOKEN, token);

        return loginUser;
    }


    @IsLogin(role = UserRoleEn.SUPER_ADMIN)
    public void updateRole(AdminBooleanRequest booleanRequest) {
        User user = userRepository.get(booleanRequest.getId());

//        User user = userRepository.got(booleanRequest.getId());call change
        CheckUtil.isEmpty(user, ErrorCodeEn.USER_NOT_EXIST);

        user.setRole(booleanRequest.getIs() ? UserRoleEn.ADMIN : UserRoleEn.USER);
        userRepository.update(user);
//        userRepository.update(null);argument change
    }
}
