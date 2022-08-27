package pub.developers.forum.facade.impl;

import org.springframework.stereotype.Service;
import pub.developers.forum.api.model.PageRequestModel;
import pub.developers.forum.api.model.PageResponseModel;
import pub.developers.forum.api.model.ResultModel;
import pub.developers.forum.api.request.AdminBooleanRequest;
import pub.developers.forum.api.request.config.ConfigAddRequest;
import pub.developers.forum.api.request.config.ConfigPageRequest;
import pub.developers.forum.api.request.config.ConfigUpdateRequest;
import pub.developers.forum.api.response.config.ConfigResponse;
import pub.developers.forum.api.service.ConfigApiService;
import pub.developers.forum.app.manager.ConfigManager;
import pub.developers.forum.app.support.LoginUserContext;
import pub.developers.forum.common.support.EventBus;
import pub.developers.forum.common.support.LogUtil;
import pub.developers.forum.facade.support.ResultModelUtil;
import pub.developers.forum.facade.validator.PageRequestModelValidator;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
/**
 * @author Qiangqiang.Bian
 * @create 2020/12/26
 * @desc
 **/
@Slf4j
@Service
public class ConfigApiServiceImpl implements ConfigApiService {

    @Resource
    private ConfigManager configManager;

    @Override
    public ResultModel add(ConfigAddRequest request) {
        configManager.add(request);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }

    @Override
    public ResultModel update(ConfigUpdateRequest request) {
        configManager.update(request);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }

    @Override
    public ResultModel state(AdminBooleanRequest request) {
        configManager.state(request);
        ResultModel rm = ResultModelUtil.success();
        return rm;
    }

    @Override
    public ResultModel<PageResponseModel<ConfigResponse>> page(PageRequestModel<ConfigPageRequest> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);
        ResultModel rm = ResultModelUtil.success(configManager.page(pageRequestModel));
        return rm;
    }

    @Override
    public ResultModel<List<ConfigResponse>> queryAvailable(Set<String> types) {
        ResultModel rm = ResultModelUtil.success(configManager.queryAvailable(types));
        return rm;
    }
}
