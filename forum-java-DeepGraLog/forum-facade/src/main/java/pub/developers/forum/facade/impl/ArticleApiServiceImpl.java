package pub.developers.forum.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import pub.developers.forum.api.model.PageRequestModel;
import pub.developers.forum.api.model.PageResponseModel;
import pub.developers.forum.api.model.ResultModel;
import pub.developers.forum.api.request.AdminBooleanRequest;
import pub.developers.forum.api.request.article.*;
import pub.developers.forum.api.response.article.ArticleInfoResponse;
import pub.developers.forum.api.response.article.ArticleQueryTypesResponse;
import pub.developers.forum.api.response.article.ArticleUserPageResponse;
import pub.developers.forum.api.service.ArticleApiService;
import pub.developers.forum.app.manager.ArticleManager;
import pub.developers.forum.app.support.LoginUserContext;
import pub.developers.forum.common.support.EventBus;
import pub.developers.forum.common.support.LogUtil;
import pub.developers.forum.facade.support.ResultModelUtil;
import pub.developers.forum.facade.validator.ArticleValidator;
import pub.developers.forum.facade.validator.PageRequestModelValidator;

import javax.annotation.Resource;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
/**
 * @author Qiangqiang.Bian
 * @create 2020/10/31
 * @desc
 **/
@Slf4j
@Service
public class ArticleApiServiceImpl implements ArticleApiService {

    @Resource
    private ArticleManager articleManager;

    @Override
    public ResultModel<List<ArticleQueryTypesResponse>> queryAllTypes() {
        ResultModel rm = ResultModelUtil.success(articleManager.queryAllTypes());
        return rm;
    }

    @Override
    public ResultModel<List<ArticleQueryTypesResponse>> queryAdminTypes() {
        ResultModel rm = ResultModelUtil.success(articleManager.queryAdminTypes());
        return rm;
    }

    @Override
    public ResultModel<List<ArticleQueryTypesResponse>> queryEditArticleTypes() {
        ResultModel rm = ResultModelUtil.success(articleManager.queryEditArticleTypes());
        return rm;
    }

    @Override
    public ResultModel addType(ArticleAddTypeRequest request) {
        ArticleValidator.addType(request);
        articleManager.addType(request);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }

    @Override
    public ResultModel<Long> saveArticle(ArticleSaveArticleRequest request) {
        ArticleValidator.saveArticle(request);
        ResultModel rm = ResultModelUtil.success(articleManager.saveArticle(request));
        return rm;
    }

    @Override
    public ResultModel<PageResponseModel<ArticleUserPageResponse>> userPage(PageRequestModel<ArticleUserPageRequest> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);
        ResultModel rm = ResultModelUtil.success(articleManager.userPage(pageRequestModel));
        return rm;
    }

    @Override
    public ResultModel<PageResponseModel<ArticleUserPageResponse>> authorPage(PageRequestModel<ArticleAuthorPageRequest> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);
        ResultModel rm = ResultModelUtil.success(articleManager.authorPage(pageRequestModel));
        return rm;
    }

    @Override
    public ResultModel<PageResponseModel<ArticleUserPageResponse>> adminPage(PageRequestModel<ArticleAdminPageRequest> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);
        ResultModel rm = ResultModelUtil.success(articleManager.adminPage(pageRequestModel));
        return rm;
    }

    @Override
    public ResultModel<ArticleInfoResponse> info(Long id) {
        ResultModel rm = ResultModelUtil.success(articleManager.info(id));
        return rm;
    }

    @Override
    public ResultModel adminTop(AdminBooleanRequest booleanRequest) {
        ArticleValidator.validatorBooleanRequest(booleanRequest);
        articleManager.adminTop(booleanRequest);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }

    @Override
    public ResultModel adminOfficial(AdminBooleanRequest booleanRequest) {
        ArticleValidator.validatorBooleanRequest(booleanRequest);
        articleManager.adminOfficial(booleanRequest);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }

    @Override
    public ResultModel adminMarrow(AdminBooleanRequest booleanRequest) {
        ArticleValidator.validatorBooleanRequest(booleanRequest);
        articleManager.adminMarrow(booleanRequest);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }

    @Override
    public ResultModel<PageResponseModel<ArticleQueryTypesResponse>> typePage(PageRequestModel<ArticleAdminTypePageRequest> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);
        ResultModel rm = ResultModelUtil.success(articleManager.typePage(pageRequestModel));
        return rm;
    }

    @Override
    public ResultModel typeAuditState(AdminBooleanRequest booleanRequest) {
        ArticleValidator.validatorBooleanRequest(booleanRequest);
        articleManager.typeAuditState(booleanRequest);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }
}
