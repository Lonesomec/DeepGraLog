package pub.developers.forum.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import pub.developers.forum.api.model.PageRequestModel;
import pub.developers.forum.api.model.PageResponseModel;
import pub.developers.forum.api.model.ResultModel;
import pub.developers.forum.api.request.faq.*;
import pub.developers.forum.api.response.faq.FaqHotsResponse;
import pub.developers.forum.api.response.faq.FaqInfoResponse;
import pub.developers.forum.api.response.faq.FaqUserPageResponse;
import pub.developers.forum.api.service.FaqApiService;
import pub.developers.forum.app.manager.FaqManager;
import pub.developers.forum.app.support.IsLogin;
import pub.developers.forum.app.support.LoginUserContext;
import pub.developers.forum.common.enums.UserRoleEn;
import pub.developers.forum.common.support.EventBus;
import pub.developers.forum.common.support.LogUtil;
import pub.developers.forum.facade.support.ResultModelUtil;
import pub.developers.forum.facade.validator.FaqValidator;
import pub.developers.forum.facade.validator.PageRequestModelValidator;

import javax.annotation.Resource;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
/**
 * @author Qiangqiang.Bian
 * @create 2020/11/1
 * @desc
 **/
@Slf4j
@Service
public class FaqApiServiceImpl implements FaqApiService {

    @Resource
    private FaqManager faqManager;

    @Override
    public ResultModel<Long> saveFaq(FaqSaveFaqRequest request) {
        FaqValidator.saveFaq(request);
        ResultModel rm = ResultModelUtil.success(faqManager.saveFaq(request));
        return rm;
    }

    @IsLogin(role = UserRoleEn.ADMIN)
    @Override
    public ResultModel<PageResponseModel<FaqUserPageResponse>> adminPage(PageRequestModel<FaqAdminPageRequest> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);
        ResultModel rm = ResultModelUtil.success(faqManager.adminPage(pageRequestModel));
        return rm;
    }

    @Override
    public ResultModel<PageResponseModel<FaqUserPageResponse>> userPage(PageRequestModel<FaqUserPageRequest> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);
        ResultModel rm = ResultModelUtil.success(faqManager.userPage(pageRequestModel));
        return rm;
    }

    @Override
    public ResultModel<PageResponseModel<FaqUserPageResponse>> authorPage(PageRequestModel<FaqAuthorPageRequest> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);
        ResultModel rm = ResultModelUtil.success(faqManager.authorPage(pageRequestModel));
        return rm;
    }

    @Override
    public ResultModel<FaqInfoResponse> info(Long id) {
        ResultModel rm = ResultModelUtil.success(faqManager.info(id));
        return rm;
    }

    @Override
    public ResultModel<List<FaqHotsResponse>> hots(int size) {
        ResultModel rm = ResultModelUtil.success(faqManager.hots(size));
        return rm;
    }

    @Override
    public ResultModel solution(FaqSolutionRequest request) {
        FaqValidator.solution(request);
        faqManager.solution(request);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }
}
