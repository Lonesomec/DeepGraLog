package pub.developers.forum.facade.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import pub.developers.forum.api.model.PageRequestModel;
import pub.developers.forum.api.model.PageResponseModel;
import pub.developers.forum.api.model.ResultModel;
import pub.developers.forum.api.request.message.MessagePageRequest;
import pub.developers.forum.api.response.message.MessagePageResponse;
import pub.developers.forum.api.service.MessageApiService;
import pub.developers.forum.app.manager.MessageManager;
import pub.developers.forum.app.support.LoginUserContext;
import pub.developers.forum.common.support.EventBus;
import pub.developers.forum.common.support.LogUtil;
import pub.developers.forum.facade.support.ResultModelUtil;
import pub.developers.forum.facade.validator.MessageValidator;
import pub.developers.forum.facade.validator.PageRequestModelValidator;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
/**
 * @author Qiangqiang.Bian
 * @create 2020/12/5
 * @desc
 **/
@Slf4j
@Service
public class MessageApiServiceImpl implements MessageApiService {

    @Resource
    private MessageManager messageManager;

    @Override
    public ResultModel<PageResponseModel<MessagePageResponse>> page(PageRequestModel<MessagePageRequest> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);
        pageRequestModel.setFilter(JSON.parseObject(JSON.toJSONString(pageRequestModel.getFilter()), MessagePageRequest.class));
        MessageValidator.page(pageRequestModel.getFilter());
        ResultModel rm = ResultModelUtil.success(messageManager.page(pageRequestModel));
        return rm;
    }

    @Override
    public ResultModel markIsRead(Long messageId) {
        messageManager.markIsRead(messageId);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }

    @Override
    public ResultModel<Long> countUnRead() {
        ResultModel rm = ResultModelUtil.success(messageManager.countUnRead());
        return rm;
    }
}
