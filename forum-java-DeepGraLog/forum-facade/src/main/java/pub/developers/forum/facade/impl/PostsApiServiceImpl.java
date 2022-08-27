package pub.developers.forum.facade.impl;

import org.springframework.stereotype.Service;
import pub.developers.forum.api.model.PageRequestModel;
import pub.developers.forum.api.model.PageResponseModel;
import pub.developers.forum.api.model.ResultModel;
import pub.developers.forum.api.request.AdminBooleanRequest;
import pub.developers.forum.api.service.PostsApiService;
import pub.developers.forum.api.vo.PostsVO;
import pub.developers.forum.app.manager.PostsManager;
import pub.developers.forum.app.support.LoginUserContext;
import pub.developers.forum.common.support.EventBus;
import pub.developers.forum.common.support.LogUtil;
import pub.developers.forum.domain.repository.PostsRepository;
import pub.developers.forum.facade.support.ResultModelUtil;
import pub.developers.forum.facade.validator.ArticleValidator;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
/**
 * @author Qiangqiang.Bian
 * @create 2020/11/25
 * @desc
 **/
@Slf4j
@Service
public class PostsApiServiceImpl implements PostsApiService {

    @Resource
    private PostsManager postsManager;

    @Resource
    PostsRepository postsRepository;

    @Override
    public ResultModel delete(Long id) {
        postsManager.delete(id);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }

    @Override
    public ResultModel<PageResponseModel<PostsVO>> pagePostsFood(PageRequestModel pageRequestModel) {
        ResultModel rm = ResultModelUtil.success(postsManager.pagePostsFood(pageRequestModel));
        return rm;
    }

    @Override
    public ResultModel auditState(AdminBooleanRequest booleanRequest) {
        ArticleValidator.validatorBooleanRequest(booleanRequest);
        postsManager.auditState(booleanRequest);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }

}
