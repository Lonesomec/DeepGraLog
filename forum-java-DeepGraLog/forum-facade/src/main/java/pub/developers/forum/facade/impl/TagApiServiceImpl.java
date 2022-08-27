package pub.developers.forum.facade.impl;

import org.springframework.stereotype.Service;
import pub.developers.forum.api.model.PageRequestModel;
import pub.developers.forum.api.model.PageResponseModel;
import pub.developers.forum.api.model.ResultModel;
import pub.developers.forum.api.request.AdminBooleanRequest;
import pub.developers.forum.api.request.tag.TagCreateRequest;
import pub.developers.forum.api.request.tag.TagPageRequest;
import pub.developers.forum.api.response.tag.TagPageResponse;
import pub.developers.forum.api.response.tag.TagQueryResponse;
import pub.developers.forum.api.service.TagApiService;
import pub.developers.forum.api.vo.PostsVO;
import pub.developers.forum.app.manager.TagManager;
import pub.developers.forum.app.support.LoginUserContext;
import pub.developers.forum.common.support.CheckUtil;
import pub.developers.forum.common.support.EventBus;
import pub.developers.forum.common.support.LogUtil;
import pub.developers.forum.facade.support.ResultModelUtil;
import pub.developers.forum.facade.validator.ArticleValidator;
import pub.developers.forum.facade.validator.PageRequestModelValidator;
import pub.developers.forum.facade.validator.TagValidator;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
/**
 * @author Qiangqiang.Bian
 * @create 20/7/30
 * @desc
 **/
@Slf4j
@Service
public class TagApiServiceImpl implements TagApiService {

    @Resource
    private TagManager tagManager;


    @Override
    public ResultModel<List<TagQueryResponse>> queryAllRef() {
        ResultModel rm = ResultModelUtil.success(tagManager.queryAllRef());
        return rm;
    }

    @Override
    public ResultModel create(TagCreateRequest request) {
        TagValidator.create(request);
        tagManager.create(request);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }

    @Override
    public ResultModel<TagQueryResponse> getByName(String name) {
        ResultModel rm = ResultModelUtil.success(tagManager.getByName(name));
        return rm;
    }

    @Override
    public ResultModel<List<TagQueryResponse>> queryAll() {
        ResultModel rm = ResultModelUtil.success(tagManager.queryAll());
        return rm;
    }

    @Override
    public ResultModel<List<TagQueryResponse>> queryInIds(Set<Long> ids) {
        CheckUtil.checkParamToast(ids, "ids");
        ResultModel rm = ResultModelUtil.success(tagManager.queryInIds(ids));
        return rm;
    }

    @Override
    public ResultModel<PageResponseModel<PostsVO>> pagePosts(PageRequestModel<Long> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);
        ResultModel rm = ResultModelUtil.success(tagManager.pagePosts(pageRequestModel));
        return rm;
    }

    @Override
    public ResultModel<PageResponseModel<PostsVO>> pagePostsByTagIds(PageRequestModel<Set<Long>> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);
        ResultModel rm = ResultModelUtil.success(tagManager.pagePostsByTagIds(pageRequestModel));
        return rm;
    }

    @Override
    public ResultModel<PageResponseModel<TagPageResponse>> page(PageRequestModel<TagPageRequest> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);
        ResultModel rm = ResultModelUtil.success(tagManager.page(pageRequestModel));
        return rm;
    }

    @Override
    public ResultModel auditState(AdminBooleanRequest booleanRequest) {
        ArticleValidator.validatorBooleanRequest(booleanRequest);
        tagManager.tagAuditState(booleanRequest);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }
}
