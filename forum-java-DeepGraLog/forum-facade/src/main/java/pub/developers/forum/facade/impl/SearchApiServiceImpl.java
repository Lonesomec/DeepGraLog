package pub.developers.forum.facade.impl;

import org.springframework.stereotype.Service;
import pub.developers.forum.api.model.PageRequestModel;
import pub.developers.forum.api.model.PageResponseModel;
import pub.developers.forum.api.model.ResultModel;
import pub.developers.forum.api.service.SearchApiService;
import pub.developers.forum.api.vo.PostsVO;
import pub.developers.forum.app.manager.SearchManager;
import pub.developers.forum.app.support.LoginUserContext;
import pub.developers.forum.common.support.EventBus;
import pub.developers.forum.common.support.LogUtil;
import pub.developers.forum.facade.support.ResultModelUtil;
import pub.developers.forum.facade.validator.PageRequestModelValidator;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Qiangqiang.Bian
 * @create 2020/12/2
 * @desc
 **/
@Slf4j
@Service
public class SearchApiServiceImpl implements SearchApiService {

    @Resource
    private SearchManager searchManager;

    @Override
    public ResultModel<PageResponseModel<PostsVO>> pagePostsSearch(PageRequestModel<String> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);
        ResultModel rm = ResultModelUtil.success(searchManager.pagePostsSearch(pageRequestModel));
        return rm;
    }

}
