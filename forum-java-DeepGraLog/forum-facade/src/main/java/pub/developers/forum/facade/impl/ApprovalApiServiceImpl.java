package pub.developers.forum.facade.impl;

import org.springframework.stereotype.Service;
import pub.developers.forum.api.model.ResultModel;
import pub.developers.forum.api.service.ApprovalApiService;
import pub.developers.forum.app.manager.ApprovalManager;
import pub.developers.forum.app.support.LoginUserContext;
import pub.developers.forum.common.support.EventBus;
import pub.developers.forum.common.support.LogUtil;
import pub.developers.forum.domain.repository.PostsRepository;
import pub.developers.forum.facade.support.ResultModelUtil;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
/**
 * @author Qiangqiang.Bian
 * @create 2020/12/1
 * @desc
 **/
@Slf4j
@Service
public class ApprovalApiServiceImpl implements ApprovalApiService {

    @Resource
    private ApprovalManager approvalManager;

    @Resource
    private PostsRepository postsRepository;

    @Override
    public ResultModel<Long> create(Long postsId) {
        ResultModel rm = ResultModelUtil.success(approvalManager.create(postsId));
        return rm;
    }

    @Override
    public ResultModel<Long> delete(Long postsId) {
        ResultModel rm = ResultModelUtil.success(approvalManager.delete(postsId));
        return rm;
    }

    @Override
    public ResultModel<Boolean> hasApproval(Long postsId) {
        ResultModel rm = ResultModelUtil.success(approvalManager.hasApproval(postsId));
        return rm;
    }

}
