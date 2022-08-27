package pub.developers.forum.facade.impl;

import org.springframework.stereotype.Service;
import pub.developers.forum.api.model.PageRequestModel;
import pub.developers.forum.api.model.PageResponseModel;
import pub.developers.forum.api.model.ResultModel;
import pub.developers.forum.api.request.comment.CommentCreateRequest;
import pub.developers.forum.api.response.comment.CommentPageResponse;
import pub.developers.forum.api.service.CommentApiService;
import pub.developers.forum.app.manager.CommentManager;
import pub.developers.forum.app.support.LoginUserContext;
import pub.developers.forum.common.support.EventBus;
import pub.developers.forum.common.support.LogUtil;
import pub.developers.forum.facade.support.ResultModelUtil;
import pub.developers.forum.facade.validator.CommentValidator;
import pub.developers.forum.facade.validator.PageRequestModelValidator;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
/**
 * @author Qiangqiang.Bian
 * @create 2020/11/6
 * @desc
 **/
@Slf4j
@Service
public class CommentApiServiceImpl implements CommentApiService {

    @Resource
    private CommentManager commentManager;

    @Override
    public ResultModel create(CommentCreateRequest request) {
        CommentValidator.create(request);
        commentManager.create(request);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }

    @Override
    public ResultModel<PageResponseModel<CommentPageResponse>> page(PageRequestModel<Long> pageRequest) {
        PageRequestModelValidator.validator(pageRequest);
        ResultModel rm = ResultModelUtil.success(commentManager.page(pageRequest));
        return rm;
    }
}
