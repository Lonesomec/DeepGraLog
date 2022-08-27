package pub.developers.forum.facade.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pub.developers.forum.api.model.PageRequestModel;
import pub.developers.forum.api.model.PageResponseModel;
import pub.developers.forum.api.model.ResultModel;
import pub.developers.forum.api.request.AdminBooleanRequest;
import pub.developers.forum.api.request.user.*;
import pub.developers.forum.api.response.user.UserInfoResponse;
import pub.developers.forum.api.response.user.UserOptLogPageResponse;
import pub.developers.forum.api.response.user.UserPageResponse;
import pub.developers.forum.api.service.UserApiService;
import pub.developers.forum.app.manager.UserManager;
import pub.developers.forum.app.support.LoginUserContext;
import pub.developers.forum.common.enums.UserRoleEn;
import pub.developers.forum.common.support.EventBus;
import pub.developers.forum.common.support.LogUtil;
import pub.developers.forum.domain.entity.User;
import pub.developers.forum.domain.repository.UserRepository;
import pub.developers.forum.facade.support.ResultModelUtil;
import pub.developers.forum.facade.validator.ArticleValidator;
import pub.developers.forum.facade.validator.PageRequestModelValidator;
import pub.developers.forum.facade.validator.UserValidator;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Qiangqiang.Bian
 * @create 20/7/30
 * @desc
 **/
@Slf4j
@Service
public class UserApiServiceImpl implements UserApiService {

    @Resource
    private UserManager userManager;

    @Resource
    UserRepository userRepository;


    @Override
    public ResultModel enable(Long uid) {
        userManager.enable(uid);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }

    @Override
    public ResultModel disable(Long uid) {
        userManager.disable(uid);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }

    @Override
    public ResultModel follow(Long followed) {
        userManager.follow(followed);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }

    @Override
    public ResultModel cancelFollow(Long followed) {
        userManager.cancelFollow(followed);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }

    @Override
    public ResultModel<PageResponseModel<UserPageResponse>> pageFollower(PageRequestModel<Long> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);
        pageRequestModel.setFilter(JSON.parseObject(JSON.toJSONString(pageRequestModel.getFilter()), Long.class));
        ResultModel rm = ResultModelUtil.success(userManager.pageFollower(pageRequestModel));
        return rm;
    }

    @Override
    public ResultModel<PageResponseModel<UserPageResponse>> pageFans(PageRequestModel<Long> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);
        pageRequestModel.setFilter(JSON.parseObject(JSON.toJSONString(pageRequestModel.getFilter()), Long.class));
        ResultModel rm = ResultModelUtil.success(userManager.pageFans(pageRequestModel));
        return rm;
    }

    @Override
    public ResultModel<Boolean> hasFollow(Long followed) {
        ResultModel rm = ResultModelUtil.success(userManager.hasFollow(followed));
        return rm;
    }

    @Override
    public ResultModel<UserInfoResponse> info(String token) {
        ResultModel rm = ResultModelUtil.success(userManager.info(token));
        return rm;
    }

    @Override
    public ResultModel<UserInfoResponse> info(Long uid) {
        ResultModel rm = ResultModelUtil.success(userManager.info(uid));
        return rm;
    }

    @Override
    public ResultModel<String> register(UserRegisterRequest request) {
        UserValidator.register(request);
        ResultModel rm = ResultModelUtil.success(userManager.register(request));
        return rm;
    }

    @Override
    public ResultModel<String> emailLogin(UserEmailLoginRequest request) {
        UserValidator.emailLogin(request);
        ResultModel rm = ResultModelUtil.success(userManager.emailRequestLogin(request));
        return rm;
    }

    @Override
    public ResultModel logout(UserTokenLogoutRequest request) {
        UserValidator.logout(request);
        userManager.logout(request);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }

    @Override
    public ResultModel updateInfo(UserUpdateInfoRequest request) {
        UserValidator.updateInfo(request);
        userManager.updateInfo(request);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }
    @Override
    public ResultModel updateHeadImg(String linkFilenameData) {
        userManager.updateHeadimg(linkFilenameData);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }

    @Override
    public ResultModel updatePwd(UserUpdatePwdRequest request) {
        UserValidator.updatePwd(request);
        userManager.updatePwd(request);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }

    @Override
    public ResultModel<PageResponseModel<UserPageResponse>> adminPage(PageRequestModel<UserAdminPageRequest> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);
        UserAdminPageRequest request = JSON.parseObject(JSON.toJSONString(pageRequestModel.getFilter()), UserAdminPageRequest.class);
        UserValidator.adminPage(request);
        pageRequestModel.setFilter(request);
        ResultModel rm = ResultModelUtil.success(userManager.page(pageRequestModel));
        return rm;
    }

    @Override
    public ResultModel<PageResponseModel<UserPageResponse>> pageActive(PageRequestModel pageRequestModel) {
        ResultModel rm = ResultModelUtil.success(userManager.pageActive(pageRequestModel));
        return rm;
    }

    @Override
    public ResultModel<PageResponseModel<UserOptLogPageResponse>> pageOptLog(PageRequestModel<UserOptLogPageRequest> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);
        UserOptLogPageRequest request = JSON.parseObject(JSON.toJSONString(pageRequestModel.getFilter()), UserOptLogPageRequest.class);
        pageRequestModel.setFilter(request);
        ResultModel rm = ResultModelUtil.success(userManager.pageOptLog(pageRequestModel));
        return rm;
    }

    @Override
    public ResultModel updateRole(AdminBooleanRequest booleanRequest) {
        ArticleValidator.validatorBooleanRequest(booleanRequest);
        userManager.updateRole(booleanRequest);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }
}
